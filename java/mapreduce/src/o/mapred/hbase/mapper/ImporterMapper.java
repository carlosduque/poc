package o.mapred.hbase.mapper;

import java.io.IOException;

import o.mapred.hbase.job.ImporterJob;
import o.mapred.hbase.util.Counters;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.io.ImmutableBytesWritable;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.Writable;
import org.apache.hadoop.mapreduce.Mapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ImporterMapper extends Mapper<LongWritable, Text, ImmutableBytesWritable, Writable> {
	public static final Logger LOG = LoggerFactory.getLogger(ImporterMapper.class);

	private static final String SEP = " | ";
	private String family = null;
	private String loadId = null;

	@Override
	protected void setup(Context context) throws IOException, InterruptedException {
		family = context.getConfiguration().get(ImporterJob.COLUMN_FAMILY_KEY);
		loadId = context.getConfiguration().get(ImporterJob.LOAD_ID_KEY);
	}

	@Override
	public void map(LongWritable offset, Text line, Context context) throws IOException {
		try {
			while(context.nextKeyValue()) {
				String myline = context.getCurrentValue().toString();
				byte[] rowkey = DigestUtils.md5(myline + loadId);

				//  0    1     2        3       4     5
				// id |num | name | lastname | ts | hash
				String[] tokens = myline.split("\\|");

				String id = tokens[0];
				String num = tokens[1];
				String name = tokens[2];
				String lastname = tokens[3];
				String ts = tokens[4];
				String hash = tokens[5];

				String meta = loadId + SEP + id + SEP + hash;
				String data = loadId + SEP + id + SEP + num + SEP + name + SEP + lastname + SEP + ts;

				LOG.info("meta: " + meta);
				LOG.info("data: " + data);

				Put put = new Put(rowkey);
				put.add(Bytes.toBytes(family), Bytes.toBytes("meta"), Bytes.toBytes(meta));
				put.add(Bytes.toBytes(family), Bytes.toBytes("data"), Bytes.toBytes(data));

				context.write(new ImmutableBytesWritable(rowkey), put);
				context.getCounter(Counters.LINES).increment(1);
			}
		} catch (Exception e) {
			LOG.error("Error message: " + e.getMessage());
		}
	}
}
