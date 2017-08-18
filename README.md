# metatype-exporter-maven-plugin

`metatype-exporter-maven-plugin` generates a markdown description from OSGI metatype files. The files must be found in the location `${project.build.outputDirectory}/OSGI-INF/metatype/*.xml`. The markdown file will be generated in `target/Configuration.md` by deafault.

## Remote repository

`metatype-exporter-maven-plugin` is available at `philanthropist.touk.pl`.

Just add the plugin repository to your maven pom:

```xml
<project>
    ...
    <pluginRepositories>
        ...
        <pluginRepository>
            <id>touk</id>
            <url>https://philanthropist.touk.pl/nexus/content/repositories/releases</url>
        </pluginRepository>
    ...
    </pluginRepositories>
    ...
</project>
```

## Usage

### TL;DR;

Provided integration tests (located in `metatype-exporter-maven-plugin-it`) contain many examples of the plugin usage and configuration.

### Add metatype export to lifecycle

```xml
<project ...>

    ...

    <build>
        <plugins>
            <plugin>
                <groupId>pl.touk.osgi</groupId>
                <artifactId>metatype-exporter-maven-plugin</artifactId>
                <version>@metatype-exporter-maven-plugin.version@</version>
                <executions>
                    <execution>
                        <goals>
                            <goal>export</goal>
                        </goals>
                    </execution>
                </executions>
            </plugin>
        </plugins>
    </build>
</project>
```

### Change generated file location and name

```xml
<project ...>

    ...

    <build>
        <plugins>
            <plugin>
                <groupId>pl.touk.osgi</groupId>
                <artifactId>metatype-exporter-maven-plugin</artifactId>
                <version>@metatype-exporter-maven-plugin.version@</version>
                <executions>
                    <execution>
                        <goals>
                            <goal>export</goal>
                        </goals>
                    </execution>
                </executions>
                <configuration>
                    <destination>${project.build.directory}/classes/documentation</destination>
                    <outputFileName>ConfigurationDescription.md</outputFileName>
                </configuration>
            </plugin>
        </plugins>
    </build>
</project>
```

### Change language (default is `en`)

```xml
<project ...>

    ...

    <build>
        <plugins>
            <plugin>
                <groupId>pl.touk.osgi</groupId>
                <artifactId>metatype-exporter-maven-plugin</artifactId>
                <version>@metatype-exporter-maven-plugin.version@</version>
                <executions>
                    <execution>
                        <goals>
                            <goal>export</goal>
                        </goals>
                    </execution>
                </executions>
                <configuration>
                    <language>pl</language>
                    <country>PL</country>
                </configuration>
            </plugin>
        </plugins>
    </build>
</project>
```

The plugin is based on Resource Bundle named `MarkdownBundle` and supports English and Polish language. If you want to use another language then add jar with Resource Bundle for such language as the maven dependency for the plugin. Example: jar with file named `MarkdownBundle_de.properties` with content:

```
forPid=...
attributeHeaderId=...
attributeHeaderName=...
attributeHeaderDescription=...
attributeHeaderOptions=...
attributeHeaderType=...
attributeHeaderDefaultValue=...
attributeHeaderRequired=...
attributeRequiredTrue=...
attributeRequiredFalse=...
```
