package o.visualvm;

import java.util.ArrayList;
import java.util.Properties;

public class HeapTroubleDemo {
   
   public static void main(String[] args) {
      long waitDuration = 0;
      if (args.length > 0)
         waitDuration = Long.parseLong(args[0]);
      HeapTroubleDemo app = new HeapTroubleDemo();
      app.runTest(waitDuration);
   }
   
   public void runTest(long waitDuration) {
      long total = 10000000;
      long count = 0;
      try {
         ArrayList<MyClass> stuff = new ArrayList<MyClass>();
         while (count < total) {
            count++;
            MyClass p = new MyClass();
            p.put("title", "Title #" + count);
            stuff.add(p);
            if ((count % (total / 100)) == 0) {
               System.out.println("Total elements saved=" + stuff.size()
                  + " - " + (((count * 100) / total)) + "% done");
               Thread.sleep(waitDuration);
            }
         }
         System.out.println("Done!");
      } catch (Exception e) {
         System.err.println("Exception at count " + count + "\n"
            + e.toString());
         e.printStackTrace();
      }
   }
   
   public class MyClass extends Properties {
      MyClass() {
         super();
      }
   }
}