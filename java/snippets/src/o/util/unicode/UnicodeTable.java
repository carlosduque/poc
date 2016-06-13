package o.util.unicode;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;

public class UnicodeTable {

	  public static void main(String[] args) {

	    String encoding = System.getProperty("file.encoding", "8859_1");
	    String lineSeparator = System.getProperty("line.separator", "\r\n");
	    
	    OutputStream target = System.out;
	    try {
	      if (args.length > 0) target = new FileOutputStream(args[0]);
	    }
	    catch (IOException e) {
	      System.err.println("Sending text to System.out");
	    }
	    if (args.length > 1) encoding = args[1];
	    
	    OutputStreamWriter osw = null;
	    try {
	      osw = new OutputStreamWriter(target, encoding); 
	    }
	    catch (UnsupportedEncodingException e) {
	      osw = new OutputStreamWriter(target);
	    }

	    try {
	      for (int i = Character.MIN_VALUE; i <= Character.MAX_VALUE; i++) {
	        char c = (char) i;
	        osw.write(i + ":\t" + c + lineSeparator);
	      }
	      osw.close();
	    } catch (IOException e) {
	      System.err.println(e);
	      e.printStackTrace();
	    }
	  }

	}