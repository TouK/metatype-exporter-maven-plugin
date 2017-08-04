package pl.touk.osgi.metatype.exporter.domain.markdown

import pl.touk.osgi.metatype.exporter.domain.Content
import pl.touk.osgi.metatype.exporter.domain.model.ObjectClassDefinition

class Markdown implements Content {
    private final List<ObjectClassDefinition> objectClassDefinitions
    private final LanguageSupport languageSupport

    Markdown(List<ObjectClassDefinition> objectClassDefinitions, Locale locale) {
        this.objectClassDefinitions = objectClassDefinitions
        this.languageSupport = new LanguageSupport(locale)
    }

    @Override
    String content() {
        return objectClassDefinitions.collect { ocd -> new OcdWriter(ocd, languageSupport).write() }.join('\n\n')
    }

}
