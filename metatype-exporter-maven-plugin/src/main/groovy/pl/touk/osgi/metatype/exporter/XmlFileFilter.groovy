package pl.touk.osgi.metatype.exporter

import groovy.transform.CompileStatic

@CompileStatic
class XmlFileFilter implements FilenameFilter {
    boolean accept(File dir, String name) {
        return name.endsWith('.xml')
    }
}
