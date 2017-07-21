package pl.touk.osgi.metatype.exporter

import spock.lang.Specification

class MarkdownTest extends Specification {
    Config config = new Config([:])

    def 'all fields and one row'() {
        given:
            List<Metatype> metatypes = [
                new Metatype(
                    params: [
                        'id': 'sample.id',
                        'name': 'sample name',
                        'description': 'sample description',
                        'type': 'sample type',
                        'required': 'true',
                        'default': 'sample default value'
                    ]
                )
            ]
        expect:
            new Markdown(config, metatypes).content() == '''\
| ID        | Name        | Required | Type        | Description        | Default value        |
| --------- | ----------- | -------- | ----------- | ------------------ | -------------------- |
| sample.id | sample name | Yes      | sample type | sample description | sample default value |'''
    }

    def 'all fields and two rows'() {
        given:
            List<Metatype> metatypes = [
                new Metatype(
                    params: [
                        'id': 'sample.id',
                        'name': 'sample name',
                        'description': 'sample description',
                        'type': 'sample type',
                        'required': 'true',
                        'default': 'sample default value'
                    ]
                ),
                new Metatype(
                    params: [
                        'id': 'other.id',
                        'name': 'other name',
                        'description': 'other description',
                        'type': 'other type',
                        'required': 'false',
                        'default': 'other default value'
                    ]
                )
            ]
        expect:
            new Markdown(config, metatypes).content() == '''\
| ID        | Name        | Required | Type        | Description        | Default value        |
| --------- | ----------- | -------- | ----------- | ------------------ | -------------------- |
| sample.id | sample name | Yes      | sample type | sample description | sample default value |
| other.id  | other name  | No       | other type  | other description  | other default value  |'''
    }

    def 'all fields and two rows reversed'() {
        given:
            List<Metatype> metatypes = [
                new Metatype(
                    params: [
                        'id': 'other.id',
                        'name': 'other name',
                        'description': 'other description',
                        'type': 'other type',
                        'required': 'false',
                        'default': 'other default value'
                    ]
                ),
                new Metatype(
                    params: [
                        'id': 'sample.id',
                        'name': 'sample name',
                        'description': 'sample description',
                        'type': 'sample type',
                        'required': 'true',
                        'default': 'sample default value'
                    ]
                )
            ]
        expect:
            new Markdown(config, metatypes).content() == '''\
| ID        | Name        | Required | Type        | Description        | Default value        |
| --------- | ----------- | -------- | ----------- | ------------------ | -------------------- |
| other.id  | other name  | No       | other type  | other description  | other default value  |
| sample.id | sample name | Yes      | sample type | sample description | sample default value |'''
    }

    def 'only required and full rows'() {
        given:
            List<Metatype> metatypes = [
                new Metatype(
                    params: [
                        'id': 'sample.id',
                        'name': 'sample name',
                        'description': 'sample description',
                        'type': 'sample type',
                        'required': 'true',
                        'default': 'sample default value'
                    ]
                ),
                new Metatype(
                    params: [
                        'id': 'empty.id',
                        'type': 'some type'
                    ]
                )
            ]
        expect:
            new Markdown(config, metatypes).content() == '''\
| ID        | Name        | Required | Type        | Description        | Default value        |
| --------- | ----------- | -------- | ----------- | ------------------ | -------------------- |
| sample.id | sample name | Yes      | sample type | sample description | sample default value |
| empty.id  |             | Yes      | some type   |                    |                      |'''
    }

    def 'only required fields'() {
        given:
            List<Metatype> metatypes = [
                new Metatype(
                    params: [
                        'id': 'empty.id',
                        'type': 'some type'
                    ]
                )
            ]
        expect:
            new Markdown(config, metatypes).content() == '''\
| ID       | Required | Type      |
| -------- | -------- | --------- |
| empty.id | Yes      | some type |'''
    }

    def 'should return empty value for empty input'() {
        expect:
            new Markdown(config, metatypes).content() == null
        where:
            metatypes << [null, []]
    }
}
