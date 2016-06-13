package o.mapred;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.KeyValue;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.io.ImmutableBytesWritable;
import org.apache.hadoop.hbase.mapreduce.TableMapReduceUtil;
import org.apache.hadoop.hbase.mapreduce.TableMapper;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.output.NullOutputFormat;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;

/**
 * @author a07942a
 * @version Mar 21, 2014
 *
 */
public class DifferentWaysToRunJob extends Configured implements Tool {

    public static class DemoConfiguredMapper extends TableMapper<ImmutableBytesWritable, Result> {
      public static enum Counters {
          ROWS
      }

      @Override
      public void map(ImmutableBytesWritable rowkey, Result result, Context context) throws IOException {
        for (KeyValue value: result.list()) {
          if (value.getValue().length > 0) {
            context.getCounter(Counters.ROWS).increment(1);
            break;
          }
        }
      }
    }

    /**
     * @param args
     * @throws Exception 
     */
    public static void main(String[] args) throws Exception {
        int ret = -1;
        DifferentWaysToRunJob job = new DifferentWaysToRunJob();
        System.out.println("Running with ToolRunner.run(Tool, String[])");
        ret = ToolRunner.run(job, args);
        System.out.println("Running with DemoJob.run(String[])");
        ret = job.run(args);
        System.exit(ret);
    }

    /* (non-Javadoc)
     * @see org.apache.hadoop.util.Tool#run(java.lang.String[])
     */
    @Override
    public int run(String[] args) throws Exception {
        String tablename = args[0];
        boolean printConfig = Boolean.parseBoolean(args[1]);

        System.out.printf("table: %s | printConfig: %s %n", tablename, printConfig);

        Configuration conf = null;
        // conf = getConf();
        System.out.println("HBaseConfiguration.create(getConf())");
        conf = HBaseConfiguration.create(getConf());

        System.out.println("new Job(conf, 'DemoJob')");
        Job job = new Job(conf, "DemoJob");
        job.setJarByClass(DifferentWaysToRunJob.class);
        job.setOutputFormatClass(NullOutputFormat.class);
        job.setNumReduceTasks(0);

        Scan scan = new Scan();
        scan.setCaching(500);
        scan.setCacheBlocks(false);
        scan.addColumn(Bytes.toBytes("record"), Bytes.toBytes("meta"));

        if (printConfig) {
            printConf(conf);
        }

        System.out.printf("TableMapReduceUtil.initTableMapperJob(%s, %s, %s, %s, %s, %s) %n",
                tablename, "scan", "DemoMapper.class", null, null, "job");
        TableMapReduceUtil.initTableMapperJob(tablename, scan, DemoConfiguredMapper.class, null, null, job);

        return job.waitForCompletion(true) ? 0 : 1;
    }

    private void printConf(final Configuration c) {
        Iterator<Entry<String, String>> iter = c.iterator();
        Map.Entry<String, String> entry = null;
        while (iter.hasNext()) {
            entry = iter.next();
            System.out.println("<K, V>  " + entry.getKey() + " = " + entry.getValue());
        }
    }

}
