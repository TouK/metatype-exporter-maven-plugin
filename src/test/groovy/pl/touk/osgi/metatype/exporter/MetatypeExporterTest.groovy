package pl.touk.osgi.metatype.exporter

import spock.lang.Specification

class MetatypeExporterTest extends Specification {
    MetatypeExporter sut = new MetatypeExporter(new Config([:]))

    def 'should create file if parent directories exist'() {
        given:
            String inputPath = MetatypeExporterTest.classLoader.getResource('metatype.xml').path
            String outputPath = MetatypeExporterTest.classLoader.getResource('.').path +
                File.separator + 'output.md'
        when:
            sut.configToFile(inputPath, outputPath)
        then:
            new File(outputPath).exists()
    }

    def "should create file if parent directories don't exist"() {
        given:
            String inputPath = MetatypeExporterTest.classLoader.getResource('metatype.xml').path
            String outputPath = MetatypeExporterTest.classLoader.getResource('.').path +
                "${File.separator}a${File.separator}b${File.separator}output.md"
        when:
            sut.configToFile(inputPath, outputPath)
        then:
            new File(outputPath).exists()
        cleanup:
            new File(MetatypeExporterTest.classLoader.getResource('./a').path).delete()
    }
}
