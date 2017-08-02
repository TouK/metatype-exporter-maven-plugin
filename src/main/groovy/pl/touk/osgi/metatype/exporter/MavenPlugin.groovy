package pl.touk.osgi.metatype.exporter

import groovy.transform.CompileStatic
import org.apache.maven.plugin.AbstractMojo
import org.apache.maven.plugin.MojoExecutionException
import org.apache.maven.plugin.MojoFailureException
import org.apache.maven.plugins.annotations.LifecyclePhase
import org.apache.maven.plugins.annotations.Mojo
import org.apache.maven.plugins.annotations.Parameter
import org.apache.maven.project.MavenProject

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

        exportConfigIfMetatypeFileExists(metatypeFiles)
    }

    private List<File> findMetatypes() {
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
            // TODO move to config
            MetatypeExporter exporter = new MetatypeExporter(new Config([:]))
            // TODO parse all, not only first
            exporter.configToFile(metatypeFiles.find().absolutePath, new File(destination, outputFileName).absolutePath)
        }
    }

}
