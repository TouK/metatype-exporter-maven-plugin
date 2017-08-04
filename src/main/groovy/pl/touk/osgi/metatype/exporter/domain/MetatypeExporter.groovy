package pl.touk.osgi.metatype.exporter.domain

import groovy.transform.CompileStatic
import pl.touk.osgi.metatype.exporter.domain.markdown.Markdown
import pl.touk.osgi.metatype.exporter.domain.model.ObjectClassDefinition

@CompileStatic
class MetatypeExporter {
    static void exportContent(List<ObjectClassDefinition> objectClassDefinitions, OutputStream outputStream, Locale locale) {
        Markdown markdown = new Markdown(objectClassDefinitions, locale)
        String content = markdown.content()
        if (content) {
            outputStream << content
        }
    }
}
