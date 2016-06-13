package o.serialization.avro;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Date;


import o.beans.biz.Person;
import o.beans.biz.Employee;

import org.apache.avro.Schema;
import org.apache.avro.file.DataFileStream;
import org.apache.avro.file.DataFileWriter;
import org.apache.avro.generic.GenericData;
import org.apache.avro.generic.GenericDatumReader;
import org.apache.avro.generic.GenericDatumWriter;
import org.apache.avro.generic.GenericRecord;
import org.apache.avro.io.DatumReader;
import org.apache.avro.io.DatumWriter;
import org.apache.avro.io.Decoder;
import org.apache.avro.io.DecoderFactory;
import org.apache.avro.io.Encoder;
import org.apache.avro.io.EncoderFactory;
import org.apache.avro.reflect.ReflectData;
import org.apache.avro.reflect.ReflectDatumReader;
import org.apache.avro.reflect.ReflectDatumWriter;

public class AvroDemo {
	
	//Libraries needed to run below programs: avro-1.5.1.jar, jackson-core-asl-1.8.2.jar, jackson-mapper-asl-1.8.2.jar, paranamer-2.3.jar
	
    public static void main1(String[] args) throws IOException {
        // Schema
        String schemaDescription = " {    \n"
                + " \"name\": \"FacebookUser\", \n"
                + " \"type\": \"record\",\n" + " \"fields\": [\n"
                + "   {\"name\": \"name\", \"type\": \"string\"},\n"
                + "   {\"name\": \"num_likes\", \"type\": \"int\"},\n"
                + "   {\"name\": \"num_photos\", \"type\": \"int\"},\n"
                + "   {\"name\": \"num_groups\", \"type\": \"int\"} ]\n" + "}";

        Schema s = Schema.parse(schemaDescription);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        Encoder e = EncoderFactory.get().binaryEncoder(outputStream, null);
        GenericDatumWriter w = new GenericDatumWriter(s);

        // Populate data
        GenericRecord r = new GenericData.Record(s);
        r.put("name", new org.apache.avro.util.Utf8("Doctor Who"));
        r.put("num_likes", 1);
        r.put("num_groups", 423);
        r.put("num_photos", 0);

        // Encode
        w.write(r, e);
        e.flush();
        
        String encodedString = outputStream.toString(); 
        
        System.out.println("encodedString: "+encodedString);
        
        // Decode using same schema
        DatumReader<GenericRecord> reader = new GenericDatumReader<GenericRecord>(s);
        Decoder decoder = DecoderFactory.get().binaryDecoder(encodedString.getBytes(), null);
        GenericRecord result = reader.read(null, decoder);
        System.out.println(result.get("name").toString());
        System.out.println(result.get("num_likes").toString());
        System.out.println(result.get("num_groups").toString());
        System.out.println(result.get("num_photos").toString());
    }
        
        public static void main2(String[] args) throws IOException {
            // Schema
            String schemaDescription = " {    \n"
                    + " \"name\": \"FacebookUser\", \n"
                    + " \"type\": \"record\",\n" + " \"fields\": [\n"
                    + "   {\"name\": \"name\", \"type\": \"string\"},\n"
                    + "   {\"name\": \"num_likes\", \"type\": \"int\"},\n"
                    + "   {\"name\": \"num_photos\", \"type\": \"int\"},\n"
                    + "   {\"name\": \"num_groups\", \"type\": \"int\"} ]\n" + "}";

            Schema schema = Schema.parse(schemaDescription);
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            DatumWriter<GenericRecord> writer = new GenericDatumWriter<GenericRecord>(
                    schema);
            DataFileWriter<GenericRecord> dataFileWriter = new DataFileWriter<GenericRecord>(
                    writer);
            dataFileWriter.create(schema, os);

            // Populate data
            GenericRecord datum = new GenericData.Record(schema);
            datum.put("name", new org.apache.avro.util.Utf8("Doctor Who"));
            datum.put("num_likes", 1);
            datum.put("num_groups", 423);
            datum.put("num_photos", 0);

            dataFileWriter.append(datum);
            dataFileWriter.close();

            System.out.println("encoded string: " + os.toString());

            // Decode
            DatumReader<GenericRecord> reader = new GenericDatumReader<GenericRecord>();
            ByteArrayInputStream is = new ByteArrayInputStream(os.toByteArray());
            DataFileStream<GenericRecord> dataFileReader = new DataFileStream<GenericRecord>(
                    is, reader);

            GenericRecord record = null;
            while (dataFileReader.hasNext()) {
                record = dataFileReader.next(record);
                System.out.println(record.get("name").toString());
                System.out.println(record.get("num_likes").toString());
                System.out.println(record.get("num_groups").toString());
                System.out.println(record.get("num_photos").toString());
            }
        }
        
}

class AvroReflect {
    final static ReflectData reflectData = ReflectData.get();
    final static Schema schema = reflectData.getSchema(Employee.class);

    public static void main(String[] args) throws IOException {
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        Encoder e = EncoderFactory.get().binaryEncoder(os, null);

        DatumWriter<Employee> writer = new ReflectDatumWriter<Employee>(schema);
        Employee employee = new Employee("Kamal", "Kunar", new Date());
        employee.setSsn("000-00-0000");

        writer.write(employee, e);
        e.flush();
        System.out.println(os.toString());

        ReflectData reflectData = ReflectData.get();
        Schema schm = reflectData.getSchema(Person.class);
        ReflectDatumReader<Person> reader = new ReflectDatumReader<Person>(schm);
        Decoder decoder = DecoderFactory.get().binaryDecoder(os.toByteArray(), null);
        Person decodedEmployee = reader.read(null, decoder);

        System.out.println("Name: "+decodedEmployee.getName());
        System.out.println("DOB: "+decodedEmployee.getDayOfBirth());
        System.out.println("SSN: "+decodedEmployee.getSsn());
    }
}
