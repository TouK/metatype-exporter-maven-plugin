package pl.touk.osgi.metatype.exporter.domain.model

import groovy.transform.CompileStatic
import groovy.transform.Immutable

@CompileStatic
@Immutable
class Attribute {
    String id
    String name
    String description
    boolean required = true
    String type
    String defaultValue
    List<Option> options
}
