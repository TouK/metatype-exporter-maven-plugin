package pl.touk.osgi.metatype.exporter.domain.markdown.language

import groovy.transform.CompileStatic

@CompileStatic
class Language {
    private final Map<String, String> friendlyNamesPl = [
        'id'         : 'ID',
        'name'       : 'Nazwa',
        'description': 'Opis',
        'type'       : 'Typ',
        'default'    : 'Wartość domyślna',
        'required'   : 'Wymagany'
    ]

    private final Map<String, String> booleanPl = [
        'true' : 'Tak',
        'false': 'Nie',
        ''     : 'Tak'
    ]

    private final Map<String, String> friendlyNamesEng = [
        'id'         : 'ID',
        'name'       : 'Name',
        'description': 'Description',
        'type'       : 'Type',
        'default'    : 'Default value',
        'required'   : 'Required'
    ]

    private final Map<String, String> booleanEng = [
        'true' : 'Yes',
        'false': 'No',
        ''     : 'Yes'
    ]

    private final Map<String, Map<String, String>> friendlyNamesMap = [
        'pl': friendlyNamesPl,
        'en': friendlyNamesEng
    ].withDefault { friendlyNamesEng }

    private final Map<String, Map<String, String>> booleanTranslationMap = [
        'pl': booleanPl,
        'en': booleanEng
    ].withDefault { booleanEng }

    Map<String, String> getFriendlyNames(String language) {
        return friendlyNamesMap.get(language)
    }

    Map<String, String> getBooleanTranslation(String language) {
        return booleanTranslationMap.get(language)
    }
}
