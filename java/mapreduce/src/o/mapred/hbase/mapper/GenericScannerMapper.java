package o.mapred.hbase.mapper;

import java.io.IOException;

import o.mapred.hbase.util.Counters;

import org.apache.hadoop.hbase.KeyValue;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.io.ImmutableBytesWritable;
import org.apache.hadoop.hbase.mapreduce.TableMapper;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.hadoop.io.Writable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GenericScannerMapper extends TableMapper<ImmutableBytesWritable, Writable> {
    public static final Logger LOGGER = LoggerFactory.getLogger(GenericScannerMapper.class);
    private static final String FIELD_SEP = ",";

    @Override
    public void map(ImmutableBytesWritable row, Result result, Context context) throws IOException, InterruptedException {
        try {
            StringBuilder keyValueStr = null;
            //while (context.nextKeyValue()) {
                LOGGER.info("*** ROW <" + Bytes.toStringBinary(row.get()) + "><size:" + result.size() + ">");
                for (KeyValue keyValue : result.list()) {
                    keyValueStr = new StringBuilder();
                    keyValueStr.append("row:").append(Bytes.toString(keyValue.getRow())).append(FIELD_SEP)
                        .append("family:").append(Bytes.toString(keyValue.getFamily())).append(FIELD_SEP)
                        .append("qualifier:").append(Bytes.toString(keyValue.getQualifier())).append(FIELD_SEP)
                        .append("value:").append(Bytes.toString(keyValue.getValue())).append(FIELD_SEP)
                        .append("length:").append(keyValue.getLength()).append(FIELD_SEP)
                        .append("rowLength:").append(keyValue.getRowLength()).append(FIELD_SEP)
                        .append("timestamp:").append(keyValue.getTimestamp()).append(FIELD_SEP)
                        .append("type:").append(keyValue.getType());
                    LOGGER.info("  * KV  [" + keyValueStr.toString() + "]");
                }
                context.getCounter(Counters.LINES).increment(1);
            //}
        } catch (Exception e) {
            LOGGER.error("Danger Will Robinson, Danger !!! ", e);
        }
    }
}
