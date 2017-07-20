package pl.touk.osgi.metatype.exporter

import spock.lang.Specification

class MetatypeReaderTest extends Specification {
    def 'should read example metatype'() {
        given:
            String path = MetatypeReaderTest.classLoader.getResource('metatype.xml').path
        expect:
            new MetatypeReader().read(path) == [
                new Metatype(
                    params: [
                        'id': 'id1',
                        'required': 'true',
                        'type': 'String',
                        'description': 'desc1',
                        'name': 'name1'
                    ]
                ),
                new Metatype(
                    params: [
                        'id': 'id2',
                        'required': 'false',
                        'type': 'Long',
                        'description': 'desc2',
                        'default': '123'
                    ]
                ),
                new Metatype(
                    params: [
                        'id': 'id3',
                        'type': 'Integer'
                    ]
                )
            ]
    }
}
