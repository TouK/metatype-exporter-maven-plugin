package pl.touk.osgi.metatype.exporter.domain.model

import groovy.transform.CompileStatic
import groovy.transform.Immutable

@CompileStatic
@Immutable
class MetaData {
    List<ObjectClassDefinition> objectClassDefinitions
}
