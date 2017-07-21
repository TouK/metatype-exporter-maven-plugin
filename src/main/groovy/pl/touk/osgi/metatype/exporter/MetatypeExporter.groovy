package pl.touk.osgi.metatype.exporter

import groovy.transform.CompileStatic

@CompileStatic
class MetatypeExporter {
    private final MetatypeReader reader = new MetatypeReader()
    private final Config config

    MetatypeExporter(Config config) {
        this.config = config
    }

    void configToFile(String inputPath, String outputPath) {
        List<Metatype> metatypes = reader.read(inputPath)
        Markdown markdown = new Markdown(config, metatypes)
        String content = markdown.content()
        if (content) {
            saveToFile(outputPath, content)
        }
    }

    private void saveToFile(String outputPath, String content) {
        File file = new File(outputPath)
        new File(file.parent).mkdirs()
        file.createNewFile()
        file.text = content
    }
}
