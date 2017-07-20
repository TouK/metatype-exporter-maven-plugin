package pl.touk.osgi.metatype.exporter

class MarkdownFormatter implements Formatter {
    // TODO refactor to multiple languages - maybe pluggable?
    private final Map<String, String> friendlyNamesInPolish = [
        'id' : 'ID',
        'name' : 'Nazwa',
        'description' : 'Opis',
        'type' : 'Typ',
        'default' : 'Wartość domyślna',
        'required' : 'Wymagany'
    ]
    private final Map<String, String> booleanInPolsih = [
        'true' : 'Tak',
        'false' : 'Nie',
        '' : 'Tak'
    ]

    // TODO from config
    private final List<String> order = [
        'id',
        'name',
        'required',
        'type',
        'description',
        'default'
    ]

    @Override
    String format(List<Metatype> metatypes) {
        if (!metatypes) {
            return null
        }

        translateBooleans(metatypes)
        Set<String> allAttributes = new HashSet(metatypes*.keySet().flatten())
        Map<String, Integer> longestAttributesNotSorted = allAttributes.collectEntries {
            [(it): longestStringInColumn(it, metatypes)]
        }
        Map<String, Integer> longestAttributes = sort(longestAttributesNotSorted, order)

        String header = header(longestAttributes)
        String border = border(longestAttributes)
        List<String> lines = lines(metatypes, longestAttributes)
        return ([header, border] + lines).join('\n')
    }

    private void translateBooleans(List<Metatype> metatypes) {
        metatypes.each {
            it['required'] = booleanInPolsih[it['required'] ?: '']
        }
    }

    private Map<String, Integer> sort(Map<String, Integer> attributesLengths, List<String> attributeOrder) {
        Map<String, Integer> ordered = new LinkedHashMap<String, Integer>()
        attributeOrder.findAll { attributesLengths[it] }
            .each { ordered[it] = attributesLengths[it] }
        return ordered
    }

    private Integer longestStringInColumn(String attribute, List<Metatype> metatypes) {
        int longestValue = metatypes.collect { it[attribute] }
            .findAll()
            .collect { it.length() }
            .max()
        return Math.max(longestValue, friendlyNamesInPolish[attribute].length())
    }

    private String header(Map<String, Integer> attributesLengths) {
        return '| ' + attributesLengths.collect { attribute, length -> friendlyNamesInPolish[attribute].padRight(length) }.join(' | ') + ' |'
    }

    private String border(Map<String, Integer> attributesLengths) {
        return '| ' + attributesLengths.collect { _, length -> '-'.multiply(length) }.join(' | ') + ' |'
    }

    private List<String> lines(List<Metatype> metatypes, Map<String, Integer> longestAttributes) {
        return metatypes.collect { line(it, longestAttributes) }
    }

    private String line(Metatype metatype, Map<String, Integer> longestAttributes) {
        return '| ' + longestAttributes.collect { attribute, length -> metatype[attribute]?.padRight(length) ?: ' '.multiply(length) }.join(' | ') + ' |'
    }
}
