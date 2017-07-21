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

    @Override
    void execute() throws MojoExecutionException, MojoFailureException {
        log.info('exporting metatype documentation')
        // TODO handle all files from OSGI-INF/metatype directory
        File metatypeFile = project.resources
            .collect { it.directory }
            .collect { "$it${File.separator}OSGI-INF${File.separator}metatype${File.separator}metatype.xml" }
            .collect { new File(it) }
            .find()

        exportConfigIfMetatypeFileExists(metatypeFile)
    }

    private void exportConfigIfMetatypeFileExists(File metatypeFile) {
        if (metatypeFile) {
            // TODO move to config
            MetatypeExporter exporter = new MetatypeExporter(new Config([:]))
            exporter.configToFile(metatypeFile.absolutePath, "${project.model.build.directory}${File.separator}config.md")
        }
    }

}
