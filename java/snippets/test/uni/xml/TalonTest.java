/*
 * TalonTest.java
 * JUnit based test
 *
 * Created on June 7, 2007, 12:06 PM
 */

package uni.xml;

import junit.framework.TestCase;

/**
 *
 * @author cduque
 */
public class TalonTest extends TestCase {
    
    private Talon _talon;
    
    public TalonTest(String testName) {
        super(testName);
    }

    protected void setUp() throws Exception {
        _talon = new Talon("0000055551", "20070508", "017", "00000000024530", "HNL");
    }

    protected void tearDown() throws Exception {
        _talon = null;
    }

    /**
     * Test of toString method, of class uni.xml.Talon.
     */
    public void testToString() {                        
        assertEquals("No devuelve la cadena esperada","00000555512007050801700000000024530HNL", _talon.toString());        
    }

    /**
     * Test of equals method, of class uni.xml.Talon.
     */
    public void testEquals() {        
        Talon t = new Talon("0000055551", "20070508", "017", "00000000024530", "HNL");
        assertEquals("Los talones no son iguales",t, _talon);                
    }    
}
