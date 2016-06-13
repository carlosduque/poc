package o.serialization.avro;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;

import org.apache.avro.Schema;
import org.apache.avro.file.DataFileReader;
import org.apache.avro.file.DataFileStream;
import org.apache.avro.file.DataFileWriter;
import org.apache.avro.generic.GenericData;
import org.apache.avro.generic.GenericDatumReader;
import org.apache.avro.generic.GenericDatumWriter;
import org.apache.avro.generic.GenericRecord;
import org.apache.avro.io.DatumReader;
import org.apache.avro.io.DatumWriter;

public class AvroFieldDemo {
    private static final String schemaDescription = "{\n"
            + "\"type\" : \"record\","
            + "\"name\" : \"Field\","
            + "\"namespace\" : \"com.biz.app.Field\","
            + "\"fields\" : ["
            + "{\"name\":\"order\", \"type\":[\"int\",\"null\"]},"
            + "{\"name\":\"name\", \"type\":\"string\"},"
            + "{\"name\":\"alias\", \"type\":\"string\"},"
            + "{\"name\":\"family\", \"type\":\"string\", \"default\":\"\"},"
            + "{\"name\":\"value\", \"type\":\"string\"}"
            + "]"
            + "}";
    private static final String NEWLINE = "\n";

    public OutputStream serialize(String schemaDesc, GenericRecord rec) {
        // Schema
        Schema.Parser schemaParser = new Schema.Parser();
        Schema schema = schemaParser.parse(schemaDesc);

        DatumWriter<GenericRecord> datumWriter = new GenericDatumWriter<GenericRecord>(schema);
        DataFileWriter<GenericRecord> dataFileWriter = new DataFileWriter<GenericRecord>(datumWriter);

        OutputStream outputStream = new ByteArrayOutputStream();
        try {
            dataFileWriter.create(schema, outputStream);
            dataFileWriter.append(rec);
            dataFileWriter.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return outputStream;
    }

    public void serialize(String schemaDesc, GenericRecord rec, File file) {
        // Schema
        Schema.Parser schemaParser = new Schema.Parser();
        Schema schema = schemaParser.parse(schemaDesc);

        DatumWriter<GenericRecord> datumWriter = new GenericDatumWriter<GenericRecord>(schema);
        DataFileWriter<GenericRecord> dataFileWriter = new DataFileWriter<GenericRecord>(datumWriter);

        try {
            dataFileWriter.create(schema, file);
            dataFileWriter.append(rec);
            dataFileWriter.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String deserialize(byte[] in) {
        ByteArrayInputStream input = new ByteArrayInputStream(in);

        Schema.Parser schemaParser = new Schema.Parser();
        Schema schema = schemaParser.parse(schemaDescription);

        DatumReader<GenericRecord> datumReader = null;
        DataFileStream<GenericRecord> dataFileStream = null;
        GenericRecord record = null;
        StringBuilder sb = new StringBuilder();
        try {
            datumReader = new GenericDatumReader<GenericRecord>(schema);
            dataFileStream = new DataFileStream<GenericRecord>(input, datumReader);
            while (dataFileStream.hasNext()) {
                record = dataFileStream.next(record);
                sb.append(record).append(NEWLINE);
            }
            dataFileStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return sb.toString();
    }

    public String deserialize(final File file) {
        Schema.Parser schemaParser = new Schema.Parser();
        Schema schema = schemaParser.parse(schemaDescription);

        DatumReader<GenericRecord> datumReader = null;
        DataFileReader<GenericRecord> dataFileReader = null;
        GenericRecord record = null;
        StringBuilder sb = new StringBuilder();
        try {
            datumReader = new GenericDatumReader<GenericRecord>(schema);
            dataFileReader = new DataFileReader<GenericRecord>(file, datumReader);
            while (dataFileReader.hasNext()) {
                record = dataFileReader.next(record);
                sb.append(record).append(NEWLINE);
            }
            dataFileReader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return sb.toString();
    }

    //Libraries needed to run below programs: avro-1.5.1.jar, jackson-core-asl-1.8.2.jar, jackson-mapper-asl-1.8.2.jar, paranamer-2.3.jar
    public static void main(String[] args) throws IOException {
        AvroFieldDemo afd = new AvroFieldDemo();

        // Schema
        Schema.Parser schemaParser = new Schema.Parser();
        Schema schema = schemaParser.parse(schemaDescription);
        // Populate data
        GenericRecord record = new GenericData.Record(schema);
//        record.put("order", 1);
        record.put("name", "first_name");
        record.put("alias", "firstname_stgni");
        record.put("family", "name");
        record.put("value", "Coyote");

        File file = new File("field.avro");
        System.out.println(":: Using a file " + file.getCanonicalPath() + " ::");
        System.out.println("serializing...");
        afd.serialize(schemaDescription, record, file);

        System.out.println("deserializing...");
        System.out.println(afd.deserialize(file));

        System.out.println(":: Using an OutputStream ::");
        System.out.println("serializing...");
        OutputStream out = afd.serialize(schemaDescription, record);
        System.out.println(out.toString());

        System.out.println("deserializing...");
        System.out.println(afd.deserialize(((ByteArrayOutputStream) out).toByteArray()));
    }

}
