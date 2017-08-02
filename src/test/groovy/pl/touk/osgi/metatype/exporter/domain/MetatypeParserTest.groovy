package pl.touk.osgi.metatype.exporter.domain

import pl.touk.osgi.metatype.exporter.domain.model.Attribute
import pl.touk.osgi.metatype.exporter.domain.model.ObjectClassDefinition
import pl.touk.osgi.metatype.exporter.domain.model.Option
import spock.lang.Specification

class MetatypeParserTest extends Specification {
    def "should parse metatype"() {
        given:
            File file = new File(MetatypeParserTest.classLoader.getResource('metatype.xml').path)
        expect:
            MetatypeParser.parseMetatype(file) == [
                new ObjectClassDefinition(
                    forPid: 'this.is.sample.pid',
                    id: 'theseAreProperties',
                    name: 'Properties name',
                    description: 'Bla',
                    attributes: [
                        new Attribute(
                            id: 'id1',
                            required: true,
                            type: 'String',
                            description: 'desc1',
                            name: 'name1'
                        ),
                        new Attribute(
                            id: 'id2',
                            required: false,
                            type: 'Long',
                            description: 'desc2',
                            defaultValue: '123'
                        ),
                        new Attribute(
                            id: 'id3',
                            required: true,
                            type: 'Integer',
                            options: [
                                new Option(value: 15),
                                new Option(value: 30)
                            ]
                        )
                    ]
                )
            ]

    }
}
