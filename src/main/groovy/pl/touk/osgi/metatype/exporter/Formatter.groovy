package pl.touk.osgi.metatype.exporter

import groovy.transform.CompileStatic

@CompileStatic
interface Formatter {
    String format(List<Metatype> metatypes)
}
