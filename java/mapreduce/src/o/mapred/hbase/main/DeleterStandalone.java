package o.mapred.hbase.main;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.client.Delete;
import org.apache.hadoop.hbase.client.HTableInterface;
import org.apache.hadoop.hbase.client.HTablePool;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.filter.Filter;
import org.apache.hadoop.hbase.filter.FilterList;
import org.apache.hadoop.hbase.filter.SingleColumnValueFilter;
import org.apache.hadoop.hbase.filter.SubstringComparator;
import org.apache.hadoop.hbase.filter.CompareFilter.CompareOp;
import org.apache.hadoop.hbase.util.Bytes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DeleterStandalone {
    public static final Logger LOG = LoggerFactory.getLogger(DeleterStandalone.class);

    public static void main(String[] args) {

        check(args);
        final String tableName = args[0];
        final byte[] family = Bytes.toBytes(args[1]);
        final byte[] qualifier = Bytes.toBytes(args[2]);
        final String value = args[3];

        final Configuration hbaseConf = HBaseConfiguration.create();
        final HTablePool tablePool = new HTablePool(hbaseConf, 1);
        final HTableInterface tableInt = tablePool.getTable(tableName);
        List<Delete> deletes = new ArrayList<Delete>();

        // set the scan information to fetch the records from HBase
        Scan scan = new Scan();
        scan.addColumn(family, qualifier);

        Filter filter = new SingleColumnValueFilter(family, qualifier, CompareOp.EQUAL, new SubstringComparator(value));
        FilterList filterList = new FilterList(FilterList.Operator.MUST_PASS_ALL);
        filterList.addFilter(filter);

        scan.setFilter(filterList);

        //HTable table = new HTable(hbaseConf, tableName);
        // now get the results from HBase to delete
        long fullCounter = 0;
        long t1 = System.currentTimeMillis();
        long t2;
        int hBaseDeleteBlockSize = 20000;
        Delete delete = null;
        ResultScanner rs;
        try {
            rs = tableInt.getScanner(scan);
            for(Result result : rs) {
                // set the individual record to delete
                delete = new Delete(result.getRow());
                   deletes.add(delete);
                fullCounter++;
                    
                // delete the records in blocks using the HTableInterface and HTablePool
                if (fullCounter % hBaseDeleteBlockSize == 0) {
                    tableInt.delete(deletes);
                    t2 = System.currentTimeMillis();
                    LOG.info("Deleted " + fullCounter + " records in " + ((t2-t1)/1000) + " seconds");
                }
            }
            tableInt.delete(deletes);
        } catch (IOException e) {
            e.printStackTrace();
        }

        t2 = System.currentTimeMillis();
        LOG.info("Total Records Deleted is " + fullCounter + " records and took " + ((t2-t1)/1000) + " seconds");
    }

    private static void check(String[] args) {
        final String usage = "Usage: <table> <column-family> <qualifier> <value>";

        if(args.length < 4) {
            throw new IllegalArgumentException(usage);
        }

    }
}
