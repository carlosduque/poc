require 'java'

require 'lib/avro-1.7.5.jar'
require 'lib/jackson-core-2.2.3.jar'
require 'lib/jackson-core-asl-1.9.13.jar'
require 'lib/jackson-mapper-asl-1.9.13.jar'

java_import 'org.apache.avro.Schema'
java_import 'org.apache.avro.generic.GenericData'
java_import 'org.apache.avro.generic.GenericDatumWriter'

java_import 'org.apache.avro.file.DataFileWriter'

parser = Schema::Parser.new 
schema = parser.parse(java.io.File.new "res/record.avsc")
generic_record = GenericData::Record.new schema
generic_record.put "order", 1
generic_record.put "name", "Melissa"
generic_record.put "alias", "mochito"
generic_record.put "value", "N"

generic_record2 = GenericData::Record.new schema
generic_record2.put "order", 2
generic_record2.put "name", "Natalia"
generic_record2.put "alias", "tali"
generic_record2.put "value", "N"

file = java.io.File.new("records.avro")

datum_writer = GenericDatumWriter.new schema
data_filewriter = DataFileWriter.new datum_writer
data_filewriter.create schema, file
data_filewriter.append generic_record
data_filewriter.append generic_record2
data_filewriter.close


