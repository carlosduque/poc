/*
 * Created on Jun 29, 2007
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package uni.server;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * @author cduque
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class PackageTests {

	public static Test suite() {
		TestSuite suite = new TestSuite("Test for uni.server");
		//$JUnit-BEGIN$
		suite.addTest(new TestSuite(DBTest.class));
		suite.addTest(new TestSuite(DatamanagerTest.class));
		//$JUnit-END$
		return suite;
	}
}
