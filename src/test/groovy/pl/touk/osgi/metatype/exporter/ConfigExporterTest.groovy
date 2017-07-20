package pl.touk.osgi.metatype.exporter

import spock.lang.Specification

class ConfigExporterTest extends Specification {
    ConfigExporter sut = new ConfigExporter()

    def 'should create file if parent directories exist'() {
        given:
            String inputPath = ConfigExporterTest.classLoader.getResource('metatype.xml').path
            String outputPath = ConfigExporterTest.classLoader.getResource('.').path + File.separator + 'output.md'
        when:
            sut.configToFile(inputPath, outputPath)
        then:
            new File(outputPath).exists()
    }

    def 'should create file if parent directories don\'t exist'() {
        given:
            String inputPath = ConfigExporterTest.classLoader.getResource('metatype.xml').path
            String outputPath = ConfigExporterTest.classLoader.getResource('.').path + "${File.separator}a${File.separator}b${File.separator}output.md"
        when:
            sut.configToFile(inputPath, outputPath)
        then:
            new File(outputPath).exists()
        cleanup:
            new File(ConfigExporterTest.classLoader.getResource('./a').path).delete()
    }
}
