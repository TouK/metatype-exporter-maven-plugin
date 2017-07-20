package pl.touk.osgi.metatype.exporter

import spock.lang.Specification

class MarkdownFormatterTest extends Specification {
    MarkdownFormatter sut = new MarkdownFormatter()

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
            sut.format(metatypes) == '''| ID        | Nazwa       | Wymagany | Typ         | Opis               | Wartość domyślna     |
| --------- | ----------- | -------- | ----------- | ------------------ | -------------------- |
| sample.id | sample name | Tak      | sample type | sample description | sample default value |'''
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
            sut.format(metatypes) == '''\
| ID        | Nazwa       | Wymagany | Typ         | Opis               | Wartość domyślna     |
| --------- | ----------- | -------- | ----------- | ------------------ | -------------------- |
| sample.id | sample name | Tak      | sample type | sample description | sample default value |
| other.id  | other name  | Nie      | other type  | other description  | other default value  |'''
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
            sut.format(metatypes) == '''\
| ID        | Nazwa       | Wymagany | Typ         | Opis               | Wartość domyślna     |
| --------- | ----------- | -------- | ----------- | ------------------ | -------------------- |
| other.id  | other name  | Nie      | other type  | other description  | other default value  |
| sample.id | sample name | Tak      | sample type | sample description | sample default value |'''
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
            sut.format(metatypes) == '''\
| ID        | Nazwa       | Wymagany | Typ         | Opis               | Wartość domyślna     |
| --------- | ----------- | -------- | ----------- | ------------------ | -------------------- |
| sample.id | sample name | Tak      | sample type | sample description | sample default value |
| empty.id  |             | Tak      | some type   |                    |                      |'''
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
            sut.format(metatypes) == '''\
| ID       | Wymagany | Typ       |
| -------- | -------- | --------- |
| empty.id | Tak      | some type |'''
    }

    def 'should return empty value for empty input'() {
        expect:
            sut.format(input) == null
        where:
            input << [null, []]
    }
}
