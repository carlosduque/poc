package o.avro;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.IllegalArgumentException;

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
import org.apache.avro.io.Encoder;
import org.apache.avro.io.EncoderFactory;
import org.apache.avro.specific.SpecificDatumWriter;

import o.beans.Author;
import o.Serializer;

public class AvroSerializer implements Serializer {

    private Schema schema = null;

    public AvroSerializer(String schema) {
        if (null == schema || schema.isEmpty())
            throw new IllegalArgumentException("Schema cant be null nor empty");

        try {
            this.schema = new Schema.Parser().parse(new File(schema));
        } catch (Exception e) {
            System.err.println("Exception parsing schema: " + schema);
        }
    }

    @Override
    public void serialize(Author author, File file) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        Encoder enc = EncoderFactory.get().binaryEncoder(baos, null);
        DatumWriter<Author> writer = new SpecificDatumWriter<Author>(Author.class);
        DataFileWriter<Author> dataFileWriter = new DataFileWriter<Author>(writer);

        try {
            dataFileWriter.create(this.schema, file);
            enc.flush();
            dataFileWriter.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Author deserialize(final File file) {
        return null;
    }

}
