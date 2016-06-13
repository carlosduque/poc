package o.mapred.hbase;

import java.io.IOException;

import org.apache.hadoop.hbase.client.Get;
import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.hadoop.conf.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class HBaseReader {
    
    static final Logger log = LoggerFactory.getLogger(HBaseReader.class);
    private Configuration conf = null;
    private HTable table = null;
    
    HBaseReader(Configuration conf) {
        this.conf = conf;
    }
    
    public String getRow(String rowId, String columnFamily, String column) throws IOException {
        Get g = new Get(Bytes.toBytes(rowId));
        Result r = this.table.get(g);
        byte [] value = r.getValue(Bytes.toBytes(columnFamily), Bytes.toBytes(column));
        String valueStr = Bytes.toString(value);
        log.info("read: " + "|row=" + rowId + "|cf=" + columnFamily + "|c=" + column + "|value=" + valueStr);
        return valueStr;
    }

    public void setTable(String tableName) throws IOException {
        this.table = new HTable(this.conf, tableName);
    }
}
