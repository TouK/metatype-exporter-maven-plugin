File file = new File(basedir, 'target/Configuration.md')
assert file.exists()

String text = file.text
assert text.split('\n').length == 9
