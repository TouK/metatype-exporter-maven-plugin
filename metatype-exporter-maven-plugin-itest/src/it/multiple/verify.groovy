File file = new File(basedir, 'target/Configuration.md')
assert file.exists()

String text = file.text
assert text.contains('this.is.first.pid')
assert text.contains('this.is.second.pid')
assert text.split('\n').length == 15
