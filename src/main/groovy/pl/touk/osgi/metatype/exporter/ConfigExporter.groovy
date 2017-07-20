package pl.touk.osgi.metatype.exporter

import groovy.transform.CompileStatic

@CompileStatic
class ConfigExporter {
    private final MetatypeReader reader = new MetatypeReader()
    private final Formatter formatter = new MarkdownFormatter()

    void configToFile(String inputPath, String outputPath) {
        List<Metatype> metatypes = reader.read(inputPath)
        String content = formatter.format(metatypes)
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
