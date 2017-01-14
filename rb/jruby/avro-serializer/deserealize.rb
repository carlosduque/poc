require 'java'

require 'lib/avro-1.7.5.jar'
require 'lib/jackson-core-2.2.3.jar'
require 'lib/jackson-core-asl-1.9.13.jar'
require 'lib/jackson-mapper-asl-1.9.13.jar'

java_import 'org.apache.avro.Schema'
java_import 'org.apache.avro.generic.GenericData'
java_import 'org.apache.avro.generic.GenericDatumReader'

java_import 'org.apache.avro.file.DataFileReader'

parser = Schema::Parser.new
schema = parser.parse(java.io.File.new "res/record.avsc")
datum_reader = GenericDatumReader.new schema
file = java.io.File.new("records.avro")

data_filereader = DataFileReader.new file, datum_reader
data_filereader.each do |generic_record|
  puts "#{generic_record}"
end


