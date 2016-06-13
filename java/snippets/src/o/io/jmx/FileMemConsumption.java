/*
 * Hello.java - MBean implementation for the Hello MBean. This class must
 * implement all the Java methods declared in the HelloMBean interface,
 * with the appropriate behavior for each one.
 */

package o.io.jmx;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import javax.management.*;

public class FileMemConsumption extends NotificationBroadcasterSupport implements FileMemConsumptionMBean {

    private File file = null;
    private byte[] contents;
    private String contentsStr = "";
    private String message = "";

    private final int MB = 1024*1024;

    public void printStats() {
        Runtime runtime = Runtime.getRuntime();

        Object source = this;
        long timestamp = System.currentTimeMillis();
        message = "file read with input stream";
        String attributeName = "";
        String attributeType = "long";
        long oldValue = 0;
        long newValue = ((runtime.totalMemory() - runtime.freeMemory() / MB));
        Notification n = new AttributeChangeNotification(source, sequenceNumber++, timestamp, message,
                attributeName, attributeType, oldValue, newValue);

        System.out.println("Used memory (MB): " + (runtime.totalMemory() - runtime.freeMemory() / MB));
        sendNotification(n);
    }

    public void release() {
        contents = new byte[0];
        contentsStr = "";
        System.out.println("released: contents and contentsStr");
    }

    @Override
    public MBeanNotificationInfo[] getNotificationInfo() {
        String[] types = new String[] {
                AttributeChangeNotification.ATTRIBUTE_CHANGE
        };
        String name = AttributeChangeNotification.class.getName();
        String description = "An attribute of this MBean has changed";
        MBeanNotificationInfo info =
                new MBeanNotificationInfo(types, name, description);
        return new MBeanNotificationInfo[] {info};
    }

    public synchronized void readFileInputStream(String filename) {
        message = "file read with input stream";
        file = new File(filename);
        FileInputStream fis = null;
        BufferedInputStream bis = null;
        try {
            fis = new FileInputStream(file);
            bis = new BufferedInputStream(fis);
            System.out.println("File:length(): " + file.length());
            System.out.println("BufferedInputStream:available(): " + bis.available());
            System.out.println();
            contents = new byte[bis.available()];
            bis.read(contents);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                bis.close();
                fis.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    public synchronized void readFileBufferedReader(String filename) {
        message = "file read with buffered reader";
        file = new File(filename);
        String s = "";
        StringBuilder sb = new StringBuilder();
        BufferedReader in = null;
        try {
            in = new BufferedReader(new FileReader(file));
            while ((s = in.readLine()) != null)
                sb.append(s + "\n");
            contentsStr = sb.toString();
            System.out.println("File:length(): " + file.length());
            System.out.println("String:length(): " + contentsStr.length());
            System.out.println();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        contentsStr = sb.toString();
    }

    private long sequenceNumber = 1;
}
