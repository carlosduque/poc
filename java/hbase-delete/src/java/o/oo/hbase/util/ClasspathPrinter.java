package o.oo.hbase.util;

import java.net.URL;
import java.net.URLClassLoader;

public class ClasspathPrinter {

	public static String getClasspathString() {
	     StringBuffer classpath = new StringBuffer();
	     //ClassLoader applicationClassLoader = this.getClass().getClassLoader();
	     ClassLoader applicationClassLoader = null;
	     if (applicationClassLoader == null) {
	         applicationClassLoader = ClassLoader.getSystemClassLoader();
	     }
	     URL[] urls = ((URLClassLoader)applicationClassLoader).getURLs();

	     for(URL url: urls){
	      	classpath.append(url.getFile()).append("\r\n");
	     }

        return classpath.toString();
	}
}
