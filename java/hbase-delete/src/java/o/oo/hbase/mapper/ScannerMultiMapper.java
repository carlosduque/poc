package o.oo.hbase.mapper;

import java.io.IOException;

import o.oo.hbase.job.ScannerMultiJob;
import o.oo.hbase.util.Counters;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.io.ImmutableBytesWritable;
import org.apache.hadoop.hbase.mapreduce.TableMapper;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.hadoop.io.Writable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ScannerMultiMapper extends TableMapper<ImmutableBytesWritable, Writable>  {
	public static final Logger LOG = LoggerFactory.getLogger(ScannerMultiMapper.class);
	private static String tablename;

	@Override
	protected void setup(Context context) throws IOException, InterruptedException {
		super.setup(context);
		tablename = context.getConfiguration().get(ScannerMultiJob.TABLE_KEY);
	}

	@Override
	public void map(ImmutableBytesWritable row, Result value, Context context) throws IOException, InterruptedException {
		try {
			LOG.info(" * table:"+ tablename + " | row: " + Bytes.toStringBinary(row.get()) + " | value: " + value.toString());
			context.getCounter(Counters.LINES).increment(1);
		} catch (Exception e) {
			LOG.error("Error with message: " + e.getMessage());
		}
	}
}
