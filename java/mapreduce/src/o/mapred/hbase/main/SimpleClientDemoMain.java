package o.mapred.hbase.main;

import java.io.IOException;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.KeyValue;
import org.apache.hadoop.hbase.client.Get;
import org.apache.hadoop.hbase.client.HBaseAdmin;
import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.hadoop.conf.Configuration;

// Class that has nothing but a main.
// Does a Put, Get and a Scan against an hbase table.
class SimpleClient {

    private String zkHost = null;
    private String zkPort = null;
    private Configuration conf = null;

    public SimpleClient(final String zookeeperHost, final String zookeeperPort) {
        zkHost = zookeeperHost;
        zkPort = zookeeperPort;

        // You need a configuration object to tell the client where to connect.
        // When you create a HBaseConfiguration, it reads in whatever you've set
        // into your hbase-site.xml and in hbase-default.xml, as long as these can
        // be found on the CLASSPATH
        conf = HBaseConfiguration.create();
        conf.set("hbase.zookeeper.quorum", zkHost);
        conf.set("hbase.zookeeper.property.clientPort", zkPort);
    }

    public void createTable(String tablename, String... families) throws IOException {
        HBaseAdmin admin = new HBaseAdmin(conf);
        HTableDescriptor tableDescriptor = new HTableDescriptor(Bytes.toBytes(tablename));
        for (String family : families) {
            tableDescriptor.addFamily(new HColumnDescriptor(family));
        }
        admin.createTable(tableDescriptor);

        StringBuilder sb = new StringBuilder(tableDescriptor.getNameAsString());
        sb.append(": families=[ ");
        for (HColumnDescriptor column : tableDescriptor.getFamilies()) {
            sb.append(column.getNameAsString()).append(" ");
        }
        sb.append("]");
        System.out.println("create table, " + sb.toString());
        admin.close();
    }

    public void put(String tablename, String rowkey, String family, String column, String value) throws IOException {
        // This instantiates an HTable object that connects you to
        // the table.
        HTable table = new HTable(conf, tablename);

        // To add to a row, use Put.  A Put constructor takes the name of the row
        // you want to insert into as a byte array.  In HBase, the Bytes class has
        // utility for converting all kinds of java types to byte arrays.  In the
        // below, we are converting the String "myLittleRow" into a byte array to
        // use as a row key for our update. Once you have a Put instance, you can
        // adorn it by setting the names of columns you want to update on the row,
        // the timestamp to use in your update, etc.If no timestamp, the server
        // applies current time to the edits.
        Put p = new Put(Bytes.toBytes(rowkey));

        // To set the value you'd like to update in the row 'myLittleRow', specify
        // the column family, column qualifier, and value of the table cell you'd
        // like to update.  The column family must already exist in your table
        // schema.  The qualifier can be anything.  All must be specified as byte
        // arrays as hbase is all about byte arrays.  Lets pretend the table
        // 'myLittleHBaseTable' was created with a family 'myLittleFamily'.
        p.add(Bytes.toBytes(family), Bytes.toBytes(column), Bytes.toBytes(value));

        // Once you've adorned your Put instance with all the updates you want to
        // make, to commit it do the following (The HTable#put method takes the
        // Put instance you've been building and pushes the changes you made into
        // hbase)
        table.put(p);
        System.out.println("put, " + p.toJSON());
        table.close();
    }

    public void get(String tablename, String rowkey) throws IOException {
        // This instantiates an HTable object that connects you to
        // the table.
        HTable table = new HTable(conf, tablename);

        // Now, to retrieve the data we just wrote. The values that come back are
        // Result instances. Generally, a Result is an object that will package up
        // the hbase return into the form you find most palatable.
        Get g = new Get(Bytes.toBytes(rowkey));
        System.out.println("get, " + g.toJSON());
        Result r = table.get(g);
        for (KeyValue kv : r.list()) {
            System.out.println("   results: rowkey=" + Bytes.toString(kv.getRow())
                    + ", family=" + Bytes.toString(kv.getFamily())
                    + ", column=" + Bytes.toString(kv.getQualifier())
                    + ", value=" + Bytes.toString(kv.getValue()));
        }
        table.close();
    }

    public void scan(String tablename) throws IOException {
        // This instantiates an HTable object that connects you to
        // the table.
        HTable table = new HTable(conf, tablename);

        // Sometimes, you won't know the row you're looking for. In this case, you
        // use a Scanner. This will give you cursor-like interface to the contents
        // of the table.  To set up a Scanner, do like you did above making a Put
        // and a Get, create a Scan.  Adorn it with column names, etc.
        Scan s = new Scan();
        ResultScanner scanner = table.getScanner(s);
        System.out.println("scan, " + s.toJSON());
        try {
            // Scanners return Result instances.
            // Now, for the actual iteration. One way is to use a while loop like so:
            //          for (Result r = scanner.next(); r != null; r = scanner.next()) {
            //            // print out the row we found and the columns we were looking for
            //            System.out.println("Found row: " + r);
            //          }

            //The other approach is to use a foreach loop. Scanners are iterable!
            for (Result rr : scanner) {
                System.out.println("   found row: " + rr);
            }
        } finally {
            // Make sure you close your scanners when you are done!
            // Thats why we have it inside a try/finally clause
            scanner.close();
            table.close();
        }
    }
}

public class SimpleClientDemoMain {

  public static void main(String[] args) throws IOException {
      String host = args[0];
      String port = args[1];
      SimpleClient client = new SimpleClient(host, port);

      client.createTable("simpleTable", new String[]{"fam1", "fam2", "fam3"});

      client.put("simpleTable", "001", "fam1", "name", "Wile");
      client.put("simpleTable", "001", "fam1", "lastname", "Coyote");
      client.put("simpleTable", "001", "fam2", "comments", "Supergenius");
      client.put("simpleTable", "001", "fam3", "nemesis", "RoadRunner");

      client.put("simpleTable", "002", "fam1", "name", "Sylvester");
      client.put("simpleTable", "002", "fam1", "lastname", "Pussycat");
      client.put("simpleTable", "002", "fam2", "comments", "The Cat");
      client.put("simpleTable", "002", "fam3", "nemesis", "Tweety");

      client.get("simpleTable", "002");

      client.scan("simpleTable");
  }
}
