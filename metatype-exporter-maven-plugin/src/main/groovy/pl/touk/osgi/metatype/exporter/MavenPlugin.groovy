package pl.touk.osgi.metatype.exporter

import groovy.transform.CompileStatic
import org.apache.maven.plugin.AbstractMojo
import org.apache.maven.plugin.MojoExecutionException
import org.apache.maven.plugin.MojoFailureException
import org.apache.maven.plugins.annotations.LifecyclePhase
import org.apache.maven.plugins.annotations.Mojo
import org.apache.maven.plugins.annotations.Parameter
import org.apache.maven.project.MavenProject
import pl.touk.osgi.metatype.exporter.domain.MetatypeExporter
import pl.touk.osgi.metatype.exporter.domain.MetatypeParser
import pl.touk.osgi.metatype.exporter.domain.model.ObjectClassDefinition

@Mojo(name = 'export', defaultPhase = LifecyclePhase.PACKAGE)
@CompileStatic
class MavenPlugin extends AbstractMojo {
    @Parameter(defaultValue = '${project}', required = true, readonly = true)
    private MavenProject project

    @Parameter(required = false, defaultValue = '${project.build.directory}')
    private String destination

    @Parameter(required = false, defaultValue = 'Configuration.md')
    private String outputFileName

    @Parameter(required = false, defaultValue = 'en')
    private String language

    @Parameter(required = false, defaultValue = '')
    private String country

    @Override
    void execute() throws MojoExecutionException, MojoFailureException {
        log.info('exporting metatype documentation')
        List<File> metatypeFiles = findMetatypes()
        log.debug("Found metatype files: \n${metatypeFiles*.absolutePath.join('\n')}")
        exportConfigIfMetatypeFileExists(metatypeFiles)
    }

    private List<File> findMetatypes() {
        File baseDir = new File(project.build.outputDirectory)
        File metatypeDir = new File(baseDir, 'OSGI-INF/metatype')
        return metatypeDir.exists() ? metatypeDir.listFiles(new XmlFileFilter()) as List : []
    }

    private void exportConfigIfMetatypeFileExists(List<File> metatypeFiles) {
        if (metatypeFiles) {
            File destinationDir = new File(destination)
            destinationDir.mkdirs()
            File destinationFile = new File(destinationDir, outputFileName)
            destinationFile.createNewFile()
            new FileOutputStream(destinationFile).withCloseable { os ->
                log.debug("Generation for language $language and country $country")
                MetatypeExporter.exportContent(parseFiles(metatypeFiles), os, new Locale(language, country ?: ''))
            }
            log.info("Configuration description written to file ${destinationFile.absolutePath}")
        }
    }

    private static List<ObjectClassDefinition> parseFiles(List<File> metatypeFiles) {
        return metatypeFiles
            .collectMany { MetatypeParser.parseMetatype(it) }
            .sort { (it as ObjectClassDefinition).forPid }
    }

}
