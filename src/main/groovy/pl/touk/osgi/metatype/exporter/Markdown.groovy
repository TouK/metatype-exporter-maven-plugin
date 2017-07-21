package pl.touk.osgi.metatype.exporter

class Markdown implements Content {
    private final Language language = new Language()
    private final Config config
    private final List<Metatype> metatypes

    private final List<String> order = [
        'id',
        'name',
        'required',
        'type',
        'description',
        'default'
    ]

// TODO from config
    Markdown(Config config, List<Metatype> metatypes) {
        this.config = config
        this.metatypes = metatypes
    }

    @Override
    String content() {
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
            it['required'] = language.getBooleanTranslation(config.language)[it['required'] ?: '']
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
        return Math.max(longestValue, language.getFriendlyNames(config.language)[attribute].length())
    }

    private String header(Map<String, Integer> attributesLengths) {
        return '| ' + attributesLengths.collect { attribute, length -> language.getFriendlyNames(config.language)[attribute].padRight(length) }.join(' | ') + ' |'
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
