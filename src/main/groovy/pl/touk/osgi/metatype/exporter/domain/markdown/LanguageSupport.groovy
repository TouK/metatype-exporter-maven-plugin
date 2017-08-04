package pl.touk.osgi.metatype.exporter.domain.markdown

import groovy.transform.CompileStatic
import groovy.transform.PackageScope

@CompileStatic
@PackageScope
class LanguageSupport {

    private final ResourceBundle resourceBundle

    final String headerForPid
    final String attributeHeaderId
    final String attributeHeaderName
    final String attributeHeaderRequired
    final String attributeHeaderType
    final String attributeHeaderDefaultValue
    final String attributeHeaderOptions
    final String attributeHeaderDescription
    final String attributeRequiredTrue
    final String attributeRequiredFalse

    LanguageSupport(Locale locale) {
        resourceBundle = ResourceBundle.getBundle('MarkdownBundle', locale)
        headerForPid = resourceBundle.getString('forPid')
        attributeHeaderId = resourceBundle.getString('attributeHeaderId')
        attributeHeaderName = resourceBundle.getString('attributeHeaderName')
        attributeHeaderRequired = resourceBundle.getString('attributeHeaderRequired')
        attributeHeaderType = resourceBundle.getString('attributeHeaderType')
        attributeHeaderDefaultValue = resourceBundle.getString('attributeHeaderDefaultValue')
        attributeHeaderOptions = resourceBundle.getString('attributeHeaderOptions')
        attributeHeaderDescription = resourceBundle.getString('attributeHeaderDescription')
        attributeRequiredTrue = resourceBundle.getString('attributeRequiredTrue')
        attributeRequiredFalse = resourceBundle.getString('attributeRequiredFalse')
    }

}
