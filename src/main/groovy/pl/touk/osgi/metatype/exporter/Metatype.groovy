package pl.touk.osgi.metatype.exporter

import groovy.transform.CompileStatic
import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString

@CompileStatic
@ToString
@EqualsAndHashCode
class Metatype {
    @Delegate
    Map<String, String> params
}
