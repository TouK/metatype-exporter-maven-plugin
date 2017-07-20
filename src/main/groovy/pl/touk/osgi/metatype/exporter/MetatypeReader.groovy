package pl.touk.osgi.metatype.exporter

import groovy.util.slurpersupport.GPathResult

class MetatypeReader {
    List<Metatype> read(String path) {
        String text = new File(path).text
        GPathResult parsed = new XmlSlurper().parseText(text)
        return parsed.OCD.AD.collect {
            return new Metatype(params: it.attributes())
        }
    }
}
