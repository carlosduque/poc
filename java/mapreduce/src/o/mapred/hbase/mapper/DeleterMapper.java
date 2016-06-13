package o.mapred.hbase.mapper;

import java.io.IOException;

import o.mapred.hbase.util.Counters;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.client.Delete;
import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.io.ImmutableBytesWritable;
import org.apache.hadoop.hbase.mapreduce.TableMapper;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.hadoop.io.Writable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DeleterMapper extends TableMapper<ImmutableBytesWritable, Writable>  {
	public static final Logger LOG = LoggerFactory.getLogger(DeleterMapper.class);
	Configuration hbaseConf = null;
	long count = 0;
	//+HTable table = null;

	@Override
	protected void setup(Context context) throws IOException, InterruptedException {
		super.setup(context);
		hbaseConf = context.getConfiguration();
		//+table = new HTable(context.getConfiguration(), "a07942a");
	}

	@Override
	public void map(ImmutableBytesWritable row, Result value, Context context) throws IOException, InterruptedException {
		try {
			Delete del = new Delete(row.get());
			LOG.info("row.get()" + Bytes.toStringBinary(row.get()));
			context.write(null, del);
			if((count % 100000) == 0){
				context.setStatus("This mapper has processed: " + count + " records");
			}
			//+table.delete(del);
			context.getCounter(Counters.DELETES).increment(1);
			count++;
		} catch (Exception e) {
			LOG.error("Error with message: " + e.getMessage());
		}
	}
}
