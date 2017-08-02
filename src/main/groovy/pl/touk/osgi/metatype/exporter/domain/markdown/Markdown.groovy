package pl.touk.osgi.metatype.exporter.domain.markdown

import pl.touk.osgi.metatype.exporter.domain.Content
import pl.touk.osgi.metatype.exporter.domain.model.ObjectClassDefinition

class Markdown implements Content {
    private final List<ObjectClassDefinition> objectClassDefinitions

    Markdown(List<ObjectClassDefinition> objectClassDefinitions) {
        this.objectClassDefinitions = objectClassDefinitions
    }

    @Override
    String content() {
        return objectClassDefinitions.collect { ocd -> new OcdWriter(ocd).write() }.join('\n\n')
    }

}
