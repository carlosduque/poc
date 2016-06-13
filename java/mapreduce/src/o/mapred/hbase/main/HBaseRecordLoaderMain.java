package o.mapred.hbase.main;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.client.Get;
import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.hadoop.conf.Configuration;


// Class that has nothing but a main.
// Does a Put, Get and a Scan against an hbase table.
public class HBaseRecordLoaderMain {

  public static void main(String[] args) throws IOException {
    // You need a configuration object to tell the client where to connect.
    // When you create a HBaseConfiguration, it reads in whatever you've set
    // into your hbase-site.xml and in hbase-default.xml, as long as these can
    // be found on the CLASSPATH
    Configuration conf = HBaseConfiguration.create();

    //conf.addResource("resources/core-site.xml");
    //conf.addResource("resources/hdfs-site.xml");
    //conf.addResource("resources/hbase-site.xml");
    //conf.addResource("resources/zoo.cfg");
    conf.set("hbase.zookeeper.quorum", "10.10.178.104");
    conf.set("hbase.zookeeper.property.clientPort", "2181");
    // This instantiates an HTable object that connects you to
    // the table.
    HTable table = new HTable(conf, "lenguajes");

    FileReader fileReader = new FileReader("resources/espa~nol.words");
    BufferedReader br = new BufferedReader(fileReader);
    
    String thisLine = "";
    int rowNumber = 0;
    //while ((thisLine = br.readLine()) != null) { // while loop begins here
    //    rowNumber++;
    //    Put p = new Put(Bytes.toBytes(rowNumber));
    //    p.add(Bytes.toBytes("es"), Bytes.toBytes("palabra"), Bytes.toBytes(thisLine));
    //    System.out.println("linea: " + thisLine);
    //    table.put(p);
    //  } // end while 

    // Now, to retrieve the data we just wrote. The values that come back are
    // Result instances. Generally, a Result is an object that will package up
    // the hbase return into the form you find most palatable.
    Get g = new Get(Bytes.toBytes("29"));
    Result r = table.get(g);
    byte [] value = r.getValue(Bytes.toBytes("es"), Bytes.toBytes("palabra"));
    // If we convert the value bytes, we should get back the
    // value we inserted at this location.
    String valueStr = Bytes.toString(value);
    System.out.println("la palabra 29: " + valueStr);

    // Sometimes, you won't know the row you're looking for. In this case, you
    // use a Scanner. This will give you cursor-like interface to the contents
    // of the table.  To set up a Scanner, do like you did above making a Put
    // and a Get, create a Scan.  Adorn it with column names, etc.
    Scan s = new Scan();
    s.addColumn(Bytes.toBytes("es"), Bytes.toBytes("palabra"));
    ResultScanner scanner = table.getScanner(s);
    try {
      // Scanners return Result instances.
      // Now, for the actual iteration. One way is to use a while loop like so:
      //for (Result rr = scanner.next(); rr != null; rr = scanner.next()) {
        // print out the row we found and the columns we were looking for
      //  System.out.println("Found row: " + rr);
      //}

      // The other approach is to use a foreach loop. Scanners are iterable!
      for (Result rr : scanner) {
         //System.out.println("Found row: " + rr);
          System.out.println("Found row: " + r.getValue(Bytes.toBytes("es"), Bytes.toBytes("palabra")));
      }
    } finally {
      // Make sure you close your scanners when you are done!
      // Thats why we have it inside a try/finally clause
      scanner.close();
    }
  }
}
