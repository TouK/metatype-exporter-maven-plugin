package pl.touk.osgi.metatype.exporter.domain

import groovy.transform.CompileDynamic
import groovy.util.slurpersupport.GPathResult
import pl.touk.osgi.metatype.exporter.domain.model.Attribute
import pl.touk.osgi.metatype.exporter.domain.model.ObjectClassDefinition
import pl.touk.osgi.metatype.exporter.domain.model.Option

@CompileDynamic
class MetatypeParser {

    static List<ObjectClassDefinition> parseMetatype(File metatypeFile) {
        GPathResult xml = new XmlSlurper().parseText(metatypeFile.text)
        return xml.Designate
            .findAll { it.@pid }
            .collect { designate -> parseOcd(designate.@pid as String, designate.Object.@ocdref as String, xml.OCD as GPathResult)
        }
    }

    private static ObjectClassDefinition parseOcd(String pid, String ocdRef, GPathResult ocds) {
        GPathResult ocd = ocds.find { it.@id == ocdRef }
        return new ObjectClassDefinition(
            forPid: pid,
            id: ocd.@id,
            name: ocd.@name ?: null,
            description: ocd.@description ?: null,
            attributes: ocd.AD.collect { ad ->
                new Attribute(
                    id: ad.@id,
                    name: ad.@name.size() ? ad.@name : null,
                    description: ad.@description.size() ? ad.@description : null,
                    required: ad.@required.size() ? Boolean.valueOf(ad.@required as String) : true,
                    type: ad.@type,
                    defaultValue: ad.@default.size() ? ad.@default : null,
                    options: ad.Option.collect { new Option(value: it.@value) } ?: null
                )
            }
        )
    }
}
