package o.mapred;

import java.io.IOException;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.commons.cli.PosixParser;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.KeyValue;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.io.ImmutableBytesWritable;
import org.apache.hadoop.hbase.mapreduce.TableOutputFormat;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.Writable;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.util.GenericOptionsParser;

public class ImportFromFileToHbase {
	public static final String NAME = "ImportFromFileToHbase";
	public enum Counters { 
		LINES 
	}

	static class ImportMapper extends Mapper<LongWritable, 
	Text, 
	ImmutableBytesWritable, 
	Writable> {

		private byte[] family = null;
		private byte[] qualifier = null;

		@Override
		protected void setup(Context context) throws IOException, 
		InterruptedException {
			String column = context.getConfiguration().get("conf.column");
			byte[][] colkey = KeyValue.parseColumn(Bytes.toBytes(column));
			family = colkey[0];
			if (colkey.length > 1) {
				qualifier = colkey[1];
			}
		}

		@Override
		public void map(LongWritable offset, Text line, Context context) throws IOException {
			try {
				String lineString = line.toString();
				byte[] rowkey = DigestUtils.md5(lineString);
				Put put = new Put(rowkey);
				put.add(family, qualifier, Bytes.toBytes(lineString));
				context.write(new ImmutableBytesWritable(rowkey), put);
				context.getCounter(Counters.LINES).increment(1);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	} //static class ImportMapper

	private static CommandLine parseArgs(String[] args) throws ParseException {
		Options options = new Options();
		Option opt = new Option("t", "table", true, "table to import into (must exist)");
		opt.setArgName("table-name");
		opt.setRequired(true);
		options.addOption(opt);
		opt = new Option("c", "column", true, "column to store row data into (must exist)");
		opt.setArgName("family:qualifier");
		opt.setRequired(true);
		options.addOption(opt);
		opt = new Option("i", "input", true, "the directory or file to read from");
		opt.setArgName("path-in-HDFS");
		opt.setRequired(true);
		options.addOption(opt);
		opt = new Option("z", "zookeeper", true, "Zookeeper server address");
		opt.setArgName("zkHost:zkPort");
		opt.setRequired(true);
		options.addOption(opt);
		options.addOption("d", "debug", false, "switch on DEBUG log level");
		CommandLineParser parser = new PosixParser();
		CommandLine cmd = null;

		try {
			cmd = parser.parse(options, args);
		} catch (Exception e) {
			System.err.println("ERROR: " + e.getMessage() + "\n");
			HelpFormatter formatter = new HelpFormatter();
			formatter.printHelp(NAME + " ", options, true);
			System.exit(-1);
		}

		return cmd;
	}

	public static void main(String[] args) throws Exception {
		Configuration conf = HBaseConfiguration.create();
		String[] otherArgs = new GenericOptionsParser(conf, args).getRemainingArgs();

		CommandLine cmd = parseArgs(otherArgs);
		String table = cmd.getOptionValue("t");
		String input = cmd.getOptionValue("i");
		String column = cmd.getOptionValue("c");
		String zk = cmd.getOptionValue("z");

		conf.set("conf.column", column);
		conf.set("hbase.zookeeper.quorum", zk);
		Job job = new Job(conf, "Import from file " + input + " into table " + table);
		job.setJarByClass(ImportFromFileToHbase.class);
		job.setMapperClass(ImportMapper.class);
		job.setOutputFormatClass(TableOutputFormat.class);
		job.getConfiguration().set(TableOutputFormat.OUTPUT_TABLE, table);
		job.setOutputKeyClass(ImmutableBytesWritable.class);
		job.setOutputValueClass(Writable.class);
		job.setNumReduceTasks(0);

		FileInputFormat.addInputPath(job, new Path(input));

		System.exit(job.waitForCompletion(true) ? 0 : 1);
	}
}
