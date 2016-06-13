package o.mapred.hbase.job;

import o.mapred.hbase.mapper.ImporterMapper;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.commons.cli.PosixParser;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.io.ImmutableBytesWritable;
import org.apache.hadoop.hbase.mapreduce.TableOutputFormat;
import org.apache.hadoop.io.Writable;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.util.GenericOptionsParser;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ImporterJob extends Configured implements Tool {
    public static final Logger LOG = LoggerFactory.getLogger(ImporterJob.class);
    public static final String NAME = ImporterJob.class.getName();
    public static final String COLUMN_FAMILY_KEY = "conf.column.family";
    public static final String HBASE_ZK_QUORUM_KEY = "hbase.zookeeper.quorum";
    public static final String LOAD_ID_KEY = "conf.load.id";

    @Override
    public int run(String[] args) throws Exception {
        Configuration conf = HBaseConfiguration.create();
        String[] otherArgs = new GenericOptionsParser(conf, args).getRemainingArgs();

        CommandLine cmd = parseArgs(otherArgs);

        String table = cmd.getOptionValue("t");
        String input = cmd.getOptionValue("i");
        String family = cmd.getOptionValue("f");
        String zk = cmd.getOptionValue("z");
        long id = System.currentTimeMillis();

        //ClasspathPrinter.getClasspathString();

        conf.set(COLUMN_FAMILY_KEY, family);
        conf.set(HBASE_ZK_QUORUM_KEY, zk);
        conf.set(LOAD_ID_KEY, String.valueOf(id));

        Job job = new Job(conf, "+ Import " + input + " to table " + table + ", id = " + String.valueOf(id));
        job.setJarByClass(ImporterJob.class);
        job.setMapperClass(ImporterMapper.class);
        job.setOutputFormatClass(TableOutputFormat.class);
        job.getConfiguration().set(TableOutputFormat.OUTPUT_TABLE, table);
        job.setOutputKeyClass(ImmutableBytesWritable.class);
        job.setOutputValueClass(Writable.class);
        job.setNumReduceTasks(0);

        FileInputFormat.addInputPath(job, new Path(input));

        return job.waitForCompletion(true) ? 0 : 1;
    }

    public static void main(String[] args) throws Exception {
        LOG.info(NAME + " started.");
        long startJob = System.currentTimeMillis();
        
        int ret = ToolRunner.run(new ImporterJob(), args);
        
        long endJob = System.currentTimeMillis();
        LOG.info(NAME + " execution time (in secs):" + (endJob - startJob) / 1000);
        System.exit(ret);
    }

    private static CommandLine parseArgs(String[] args) throws ParseException {
        Options options = new Options();
        Option opt = new Option("t", "table", true, "table to import into (must exist)");
        opt.setArgName("table-name");
        opt.setRequired(true);
        options.addOption(opt);
        opt = new Option("f", "column family", true, "column family to store row data into (must exist)");
        opt.setArgName("family");
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
            LOG.error("Error mesage: " + e.getMessage() + "\n");
            HelpFormatter formatter = new HelpFormatter();
            formatter.printHelp(NAME + " ", options, true);
            System.exit(-1);
        }
        return cmd;
    }
}
