package pl.touk.osgi.metatype.exporter.domain.markdown

import groovy.transform.CompileStatic
import groovy.transform.PackageScope
import net.steppschuh.markdowngenerator.table.Table
import pl.touk.osgi.metatype.exporter.domain.model.Attribute
import pl.touk.osgi.metatype.exporter.domain.model.ObjectClassDefinition
import pl.touk.osgi.metatype.exporter.domain.model.Option

@CompileStatic
@PackageScope
class OcdWriter {

    private final ObjectClassDefinition ocd
    private final LanguageSupport languageSupport

    OcdWriter(ObjectClassDefinition ocd, LanguageSupport languageSupport) {
        this.languageSupport = languageSupport
        this.ocd = ocd
    }

    String write() {
        return [
            printHeader(),
            printAttributes()
        ].join('\n')
    }

    private String printHeader() {
        return """\
# `${ocd.name ? "$ocd.name ($ocd.id)" : ocd.id}` ${languageSupport.headerForPid} `$ocd.forPid`
${ocd.description ? "\n${ocd.description}\n" : ''}"""
    }

    private String printAttributes() {
        Table.Builder tableBuilder = new Table.Builder()
            .addRow(neededHeaders())
        Table.Builder withRows = ocd.attributes.inject(tableBuilder) { acc, cur ->
            tableBuilder.addRow(asRow(cur))
        }
        return withRows.build()
    }

    private static String format(List<Option> options) {
        return options ? "<ul>${options.collect { "<li>$it.value</li>" }.join('')}</ul>" : ''
    }

    private String[] neededHeaders() {
        return [
            languageSupport.attributeHeaderId,
            hasAttributeNames() ? languageSupport.attributeHeaderName : null,
            languageSupport.attributeHeaderRequired,
            languageSupport.attributeHeaderType,
            hasDefaultValue() ? languageSupport.attributeHeaderDefaultValue : null,
            hasOptions() ? languageSupport.attributeHeaderOptions : null,
            hasDescription() ? languageSupport.attributeHeaderDescription : null,
        ].findAll() as String[]
    }

    private String[] asRow(Attribute attribute) {
        return [
            "`${attribute.id}`",
            hasAttributeNames() ? (attribute.name ?: '') : null,
            attribute.required ? languageSupport.attributeRequiredTrue : languageSupport.attributeRequiredFalse,
            attribute.type,
            hasDefaultValue() ? (attribute.defaultValue ?: '') : null,
            hasOptions() ? format(attribute.options) : null,
            hasDescription() ? (attribute.description ?: '') : null,
        ].findAll { it != null } as String[]
    }

    private boolean hasDescription() {
        return ocd.attributes.description.any()
    }

    private boolean hasOptions() {
        return ocd.attributes.options.any()
    }

    private boolean hasDefaultValue() {
        return ocd.attributes.defaultValue.any()
    }

    private boolean hasAttributeNames() {
        return ocd.attributes.name.any()
    }
}
