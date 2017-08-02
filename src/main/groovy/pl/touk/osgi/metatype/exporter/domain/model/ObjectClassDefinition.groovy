package pl.touk.osgi.metatype.exporter.domain.model

import groovy.transform.CompileStatic
import groovy.transform.Immutable

@CompileStatic
@Immutable
class ObjectClassDefinition {
    String forPid
    String id
    String name
    String description
    List<Attribute> attributes
}
