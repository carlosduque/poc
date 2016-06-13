package o.mapred.hbase.job;

import o.mapred.hbase.mapper.ScannerMapper;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.commons.cli.PosixParser;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.filter.Filter;
import org.apache.hadoop.hbase.filter.FilterList;
import org.apache.hadoop.hbase.filter.SingleColumnValueFilter;
import org.apache.hadoop.hbase.filter.SubstringComparator;
import org.apache.hadoop.hbase.filter.CompareFilter.CompareOp;
import org.apache.hadoop.hbase.mapreduce.TableInputFormat;
import org.apache.hadoop.hbase.mapreduce.TableMapReduceUtil;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.output.NullOutputFormat;
import org.apache.hadoop.util.GenericOptionsParser;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ScannerJobDemo extends Configured implements Tool {
	public static final Logger LOG = LoggerFactory.getLogger(ScannerJobDemo.class);
	public static final String NAME = ScannerJobDemo.class.getName();
	public static final String TABLE_KEY = "conf.table";
	public static final String HBASE_ZK_QUORUM_KEY = "hbase.zookeeper.quorum";

	@Override
	public int run(String[] args) throws Exception {
		Configuration conf = HBaseConfiguration.create();
		String[] otherArgs = new GenericOptionsParser(conf, args).getRemainingArgs();

		CommandLine cmd = parseArgs(otherArgs);

		String table = cmd.getOptionValue("t");
		String family = cmd.getOptionValue("f");
		String column = cmd.getOptionValue("c");
		String value = cmd.getOptionValue("v");
		String zk = cmd.getOptionValue("z");

		conf.set(TABLE_KEY, table);
		conf.set(HBASE_ZK_QUORUM_KEY, zk);

		Job job = new Job(conf, "> Scan table " + table + " " + family + ":" + column + " value = " + value);

		Scan scan = new Scan();
		scan.setCaching(500);
		scan.setCacheBlocks(false);

		byte[] fam = Bytes.toBytes(family);
		byte[] col = Bytes.toBytes(column);

		scan.addColumn(fam, col);

		Filter filter = new SingleColumnValueFilter(fam, col, CompareOp.EQUAL, new SubstringComparator(value));
		
		FilterList filterList = new FilterList(FilterList.Operator.MUST_PASS_ALL);
		filterList.addFilter(filter);
		
		scan.setFilter(filterList);
		
		job.setJarByClass(ScannerJobDemo.class);
		job.setMapperClass(ScannerMapper.class);
        job.setNumReduceTasks(0);
		job.setInputFormatClass(TableInputFormat.class);
        job.setOutputFormatClass(NullOutputFormat.class);

		TableMapReduceUtil.initTableMapperJob(table, scan, ScannerMapper.class, null, null, job);

		return job.waitForCompletion(true) ? 0 : 1;
	}

	public static void main(String[] args) throws Exception {
		LOG.info(NAME + " started.");
		long startJob = System.currentTimeMillis();
		
		int ret = ToolRunner.run(new ScannerJobDemo(), args);
		
		long endJob = System.currentTimeMillis();
		LOG.info(NAME + " execution time (in secs):" + (endJob - startJob) / 1000);
		System.exit(ret);
	}

	private static CommandLine parseArgs(String[] args) throws ParseException {
		Options options = new Options();
		//
		Option opt = new Option("t", "table", true, "table to delete data from.");
		opt.setArgName("table-name");
		opt.setRequired(true);
		options.addOption(opt);
		//
		opt = new Option("f", "column-family", true, "column-family to delete from");
		opt.setArgName("column-family");
		opt.setRequired(true);
		options.addOption(opt);
		//
		opt = new Option("c", "column", true, "column");
		opt.setArgName("column");
		opt.setRequired(true);
		options.addOption(opt);
		//
		opt = new Option("v", "value", true, "value");
		opt.setArgName("value");
		opt.setRequired(true);
		options.addOption(opt);
		//
		opt = new Option("z", "zookeeper", true, "Zookeeper server address");
		opt.setArgName("zkHost:zkPort");
		opt.setRequired(true);
		options.addOption(opt);

		CommandLineParser parser = new PosixParser();
		CommandLine cmd = null;
		try {
			cmd = parser.parse(options, args);
		} catch (Exception e) {
			LOG.error("Error mesage: " + e.getMessage() + "\n");
			HelpFormatter formatter = new HelpFormatter();
			formatter.printHelp(NAME + " ", options, true);
			System.exit(-1);
		}
		return cmd;
	}
}
