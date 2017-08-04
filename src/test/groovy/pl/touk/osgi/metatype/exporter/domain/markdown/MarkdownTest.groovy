package pl.touk.osgi.metatype.exporter.domain.markdown

import pl.touk.osgi.metatype.exporter.domain.model.Attribute
import pl.touk.osgi.metatype.exporter.domain.model.ObjectClassDefinition
import pl.touk.osgi.metatype.exporter.domain.model.Option
import spock.lang.Specification

class MarkdownTest extends Specification {

    def 'should write header and full attribute'() {
        given:
            List<ObjectClassDefinition> objectClassDefinitions = [
                new ObjectClassDefinition(
                    forPid: 'pl.touk.test.test1',
                    id: 'ocd1',
                    name: 'sample OCD',
                    description: 'sample OCD description',
                    attributes: [
                        new Attribute(
                            id: 'sample.id',
                            name: 'sample name',
                            description: 'sample description',
                            type: 'String',
                            required: true,
                            defaultValue: 'sample default value',
                            options: [
                                new Option(value: 'v1'),
                                new Option(value: 'sample default value'),
                            ]
                        )
                    ]
                )
            ]
            String expected = '''\
# `sample OCD (ocd1)` for pid `pl.touk.test.test1`

sample OCD description

| ID        | Name        | Required | Type   | Default value        | Options                                           | Description        |
| --------- | ----------- | -------- | ------ | -------------------- | ------------------------------------------------- | ------------------ |
| sample.id | sample name | Yes      | String | sample default value | <ul><li>v1</li><li>sample default value</li></ul> | sample description |'''
        expect:
            new Markdown(objectClassDefinitions, Locale.ENGLISH).content() == expected
    }

    def 'should write header without ocd name and description'() {
        given:
            List<ObjectClassDefinition> objectClassDefinitions = [
                new ObjectClassDefinition(
                    forPid: 'pl.touk.test.test1',
                    id: 'ocd1',
                    attributes: [
                        new Attribute(
                            id: 'sample.id',
                            name: 'sample name',
                            description: 'sample description',
                            type: 'String',
                            required: true,
                            defaultValue: 'sample default value'
                        )
                    ]
                )
            ]
            String expected = '''\
# `ocd1` for pid `pl.touk.test.test1`

| ID        | Name        | Required | Type   | Default value        | Description        |
| --------- | ----------- | -------- | ------ | -------------------- | ------------------ |
| sample.id | sample name | Yes      | String | sample default value | sample description |'''
        expect:
            new Markdown(objectClassDefinitions, Locale.ENGLISH).content() == expected
    }

    def 'should write two attributes'() {
        given:
            List<ObjectClassDefinition> objectClassDefinitions = [
                new ObjectClassDefinition(
                    forPid: 'bla',
                    id: 'test',
                    attributes: [
                        new Attribute(
                            id: 'other.id',
                            name: 'other name',
                            description: 'other description',
                            type: 'other type',
                            required: false,
                            defaultValue: 'other default value'
                        ),
                        new Attribute(
                            id: 'sample.id',
                            name: 'sample name',
                            description: 'sample description',
                            type: 'sample type',
                            required: true,
                            defaultValue: 'sample default value'
                        )
                    ]
                )
            ]
            String expected = '''\
# `test` for pid `bla`

| ID        | Name        | Required | Type        | Default value        | Description        |
| --------- | ----------- | -------- | ----------- | -------------------- | ------------------ |
| other.id  | other name  | No       | other type  | other default value  | other description  |
| sample.id | sample name | Yes      | sample type | sample default value | sample description |'''
        expect:
            new Markdown(objectClassDefinitions, Locale.ENGLISH).content() == expected
    }

    def 'should write only available attribute information'() {
        given:
            List<ObjectClassDefinition> objectClassDefinitions = [
                new ObjectClassDefinition(
                    forPid: 'bla',
                    id: 'test',
                    attributes: [
                        new Attribute(
                            id: 'sample.id',
                            name: 'sample name',
                            description: 'sample description',
                            type: 'sample type',
                            required: 'true',
                            defaultValue: 'sample default value',
                            options: [
                                new Option(value: 'a'),
                                new Option(value: 'b'),
                            ]
                        ),
                        new Attribute(
                            id: 'empty.id',
                            type: 'some type'
                        )
                    ]
                )
            ]
            String expected = '''\
# `test` for pid `bla`

| ID        | Name        | Required | Type        | Default value        | Options                       | Description        |
| --------- | ----------- | -------- | ----------- | -------------------- | ----------------------------- | ------------------ |
| sample.id | sample name | Yes      | sample type | sample default value | <ul><li>a</li><li>b</li></ul> | sample description |
| empty.id  |             | Yes      | some type   |                      |                               |                    |'''
        expect:
            new Markdown(objectClassDefinitions, Locale.ENGLISH).content() == expected
    }

    def 'should write only basic attribute'() {
        given:
            List<ObjectClassDefinition> objectClassDefinitions = [
                new ObjectClassDefinition(
                    forPid: 'bla',
                    id: 'test',
                    attributes: [
                        new Attribute(
                            id: 'empty.id',
                            type: 'some type'
                        )
                    ]
                )
            ]

            String expected = '''\
# `test` for pid `bla`

| ID       | Required | Type      |
| -------- | -------- | --------- |
| empty.id | Yes      | some type |'''
        expect:
            new Markdown(objectClassDefinitions, Locale.ENGLISH).content() == expected
    }

    def "should generate from multiple OCDs"() {
        given:
            List<ObjectClassDefinition> objectClassDefinitions = [
                new ObjectClassDefinition(
                    forPid: 'bla1',
                    id: 'test1',
                    attributes: [
                        new Attribute(
                            id: 'empty.id',
                            type: 'some type'
                        )
                    ]
                ),
                new ObjectClassDefinition(
                    forPid: 'bla2',
                    id: 'test2',
                    attributes: [
                        new Attribute(
                            id: 'other.id',
                            name: 'other name',
                            description: 'other description',
                            type: 'other type',
                            required: false,
                            defaultValue: 'other default value'
                        ),
                        new Attribute(
                            id: 'sample.id',
                            name: 'sample name',
                            description: 'sample description',
                            type: 'sample type',
                            required: true,
                            defaultValue: 'sample default value'
                        )
                    ]
                )
            ]
            String expected = '''\
# `test1` for pid `bla1`

| ID       | Required | Type      |
| -------- | -------- | --------- |
| empty.id | Yes      | some type |

# `test2` for pid `bla2`

| ID        | Name        | Required | Type        | Default value        | Description        |
| --------- | ----------- | -------- | ----------- | -------------------- | ------------------ |
| other.id  | other name  | No       | other type  | other default value  | other description  |
| sample.id | sample name | Yes      | sample type | sample default value | sample description |'''
        expect:
            new Markdown(objectClassDefinitions, Locale.ENGLISH).content() == expected

    }

    def 'should generate OCD in Polish'() {
        given:
            List<ObjectClassDefinition> objectClassDefinitions = [
                new ObjectClassDefinition(
                    forPid: 'pl.touk.test.test1',
                    id: 'ocd1',
                    name: 'sample OCD',
                    description: 'sample OCD description',
                    attributes: [
                        new Attribute(
                            id: 'sample.id',
                            name: 'sample name',
                            description: 'sample description',
                            type: 'String',
                            required: true,
                            defaultValue: 'sample default value',
                            options: [
                                new Option(value: 'v1'),
                                new Option(value: 'sample default value'),
                            ]
                        ),
                        new Attribute(
                            id: 'sample.empty',
                            required: false,
                            type: 'Long'
                        )
                    ]
                )
            ]
            String expected = '''\
# `sample OCD (ocd1)` dla pid `pl.touk.test.test1`

sample OCD description

| ID           | Nazwa       | Wymagany | Typ    | Warto\u015b\u0107 domy\u015blna     | Opcje                                             | Opis               |
| ------------ | ----------- | -------- | ------ | -------------------- | ------------------------------------------------- | ------------------ |
| sample.id    | sample name | Tak      | String | sample default value | <ul><li>v1</li><li>sample default value</li></ul> | sample description |
| sample.empty |             | Nie      | Long   |                      |                                                   |                    |'''
        expect:
            new Markdown(objectClassDefinitions, new Locale('pl', 'PL')).content() == expected
    }
}
