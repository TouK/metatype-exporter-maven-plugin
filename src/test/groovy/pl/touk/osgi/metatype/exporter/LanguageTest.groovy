package pl.touk.osgi.metatype.exporter

import spock.lang.Specification

class LanguageTest extends Specification {
    Language sut = new Language()

    def 'should return friendly names using defaults'() {
        when:
            Map<String, String> translation = sut.getFriendlyNames('xxx')
        then:
            translation['name'] == 'Name'
    }

    def 'should return boolean translation using default'() {
        when:
            Map<String, String> translation = sut.getBooleanTranslation('xxx')
        then:
            translation['true'] == 'Yes'
    }
}
