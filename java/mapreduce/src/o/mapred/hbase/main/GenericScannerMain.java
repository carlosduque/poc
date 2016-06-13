package o.mapred.hbase.main;

import o.mapred.hbase.job.GenericScannerJob;

import org.apache.hadoop.util.ToolRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GenericScannerMain {
    public static final Logger LOGGER = LoggerFactory.getLogger(GenericScannerMain.class);

    public static void main(String[] args) throws Exception {
        long startJob = System.currentTimeMillis();
        int res = ToolRunner.run(new GenericScannerJob(), args);
        long endJob = System.currentTimeMillis();

        LOGGER.info(GenericScannerMain.class.getSimpleName() + " execution time (in secs):" + (endJob - startJob) / 1000);
        System.exit(res);
    }

}
