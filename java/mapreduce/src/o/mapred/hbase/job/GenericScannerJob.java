package o.mapred.hbase.job;

import o.mapred.hbase.mapper.GenericScannerMapper;

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
import org.apache.hadoop.hbase.mapreduce.TableMapReduceUtil;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.output.NullOutputFormat;
import org.apache.hadoop.util.GenericOptionsParser;
import org.apache.hadoop.util.Tool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GenericScannerJob extends Configured implements Tool {
    public static final Logger LOGGER = LoggerFactory.getLogger(GenericScannerJob.class);

    /**
     * Zookeeper quorum key in HBase, e.g.:
     * <property>
     *   <name>hbase.zookeeper.quorum</name>
     *   <value>mckvdatpdb65.mck.experian.com,mckvdatpdb75.mck.experian.com,mckvdatpdb70.mck.experian.com</value>
     * </property>
     */
    private static final String HBASE_ZK_QUORUM_KEY = "hbase.zookeeper.quorum";
    private static Configuration conf = null;
    private static String tablename = "";
    private static String zookeeper = "";

    /* (non-Javadoc)
     * @see org.apache.hadoop.util.Tool#run(java.lang.String[])
     */
    @Override
    public int run(String[] args) throws Exception {
        conf = HBaseConfiguration.create();
        String[] remainingArgs = new GenericOptionsParser(conf, args).getRemainingArgs();
        CommandLine cmd = parseArgs(remainingArgs);
        tablename = cmd.getOptionValue("t");
        zookeeper = cmd.getOptionValue("z");

        conf.set(HBASE_ZK_QUORUM_KEY, zookeeper);

        Job job = new Job(conf, "Scanning from table '" + tablename + "'");
        job.setJarByClass(GenericScannerJob.class);
        job.setOutputFormatClass(NullOutputFormat.class);
        job.setNumReduceTasks(0);

        Scan scan = new Scan();
        TableMapReduceUtil.initTableMapperJob(tablename, scan, GenericScannerMapper.class, null, null, job);

        return job.waitForCompletion(true) ? 0 : 1;
    }

    private static CommandLine parseArgs(String[] args) throws ParseException {

        final String space = " ";
        StringBuilder usage = new StringBuilder();
        usage.append("hadoop jar mapred-poc.jar").append(space)
            .append("o.mapred.hbase.main.GenericScannerMain").append(space)
            .append("-t duq-chf-dec").append(space)
            .append("-z mckvdatpdb65.mck.experian.com").append(space);

        Options options = new Options();
        Option opt = new Option("t", "tablename", true, "table to scan");
        opt.setArgName("tablename");
        opt.setRequired(true);
        options.addOption(opt);

        opt = new Option("z", "zookeeper", true, "Zookeeper server address");
        opt.setArgName("zkHost:zkPort");
        opt.setRequired(true);
        options.addOption(opt);

        CommandLineParser parser = new PosixParser();
        CommandLine cmd = null;
        try {
            cmd = parser.parse(options, args);
        } catch (Exception e) {
            LOGGER.error("ERROR: ", e);
            HelpFormatter formatter = new HelpFormatter();

            formatter.printHelp(usage.toString(), options, true);
            System.exit(-1);
        }

        return cmd;
    }
}
