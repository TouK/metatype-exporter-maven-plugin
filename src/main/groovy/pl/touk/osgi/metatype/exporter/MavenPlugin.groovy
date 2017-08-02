package pl.touk.osgi.metatype.exporter

import groovy.transform.CompileStatic
import org.apache.maven.plugin.AbstractMojo
import org.apache.maven.plugin.MojoExecutionException
import org.apache.maven.plugin.MojoFailureException
import org.apache.maven.plugins.annotations.LifecyclePhase
import org.apache.maven.plugins.annotations.Mojo
import org.apache.maven.plugins.annotations.Parameter
import org.apache.maven.project.MavenProject
import pl.touk.osgi.metatype.exporter.domain.Config
import pl.touk.osgi.metatype.exporter.domain.MetatypeExporter
import pl.touk.osgi.metatype.exporter.domain.MetatypeParser

@Mojo(name = 'export', defaultPhase = LifecyclePhase.PACKAGE)
@CompileStatic
class MavenPlugin extends AbstractMojo {
    @Parameter(defaultValue = '${project}', required = true, readonly = true)
    private MavenProject project

    @Parameter(required = false, defaultValue = '${project.build.directory}')
    private String destination

    @Parameter(required = false, defaultValue = 'Config.md')
    private String outputFileName

    @Override
    void execute() throws MojoExecutionException, MojoFailureException {
        log.info('exporting metatype documentation')
        List<File> metatypeFiles = findMetatypes()
        log.debug("Found metatype files: \n${metatypeFiles.collect { it.absolutePath }.join('\n')}")
        exportConfigIfMetatypeFileExists(metatypeFiles)
    }

    private List<File> findMetatypes() {
        // TODO add support for custom directories
        return project.resources
            .collect { new File(it.directory) }
            .collect { baseDir -> new File(baseDir, 'OSGI-INF') }
            .findAll { it.exists() }
            .collect { osgiInf -> new File(osgiInf, 'metatype') }
            .findAll { it.exists() }
            .collectMany { metatypeDir -> metatypeDir.listFiles({ String fileName -> fileName.endsWith('.xml') } as FilenameFilter) as List }
    }

    private void exportConfigIfMetatypeFileExists(List<File> metatypeFiles) {
        if (metatypeFiles) {
            File destinationDir = new File(destination)
            destinationDir.mkdirs()
            File destinationFile = new File(destinationDir, outputFileName)
            destinationFile.createNewFile()
            new FileOutputStream(destinationFile).withCloseable { os ->
                MetatypeExporter.exportContent(metatypeFiles.collectMany { MetatypeParser.parseMetatype(it) }, os)
            }
        }
    }

}
