package pl.touk.osgi.metatype.exporter.domain

import pl.touk.osgi.metatype.exporter.domain.model.ObjectClassDefinition
import spock.lang.Specification

class MetatypeExporterTest extends Specification {

    def 'should write content to output stream'() {
        given:
            String inputPath = MetatypeExporterTest.classLoader.getResource('metatype.xml').path
            List<ObjectClassDefinition> model = MetatypeParser.parseMetatype(new File(inputPath))
            File destination = File.createTempFile('metatype', '.md')
        when:
            new FileOutputStream(destination).withCloseable {
                MetatypeExporter.exportContent(model, it, Locale.ENGLISH)
            }
        then:
            destination.exists()
            !destination.text.empty
    }
}
