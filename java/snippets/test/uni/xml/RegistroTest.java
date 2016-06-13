/*
 * RegistroTest.java
 * JUnit based test
 *
 * Created on May 29, 2007, 11:53 AM
 */

package uni.xml;

import java.util.ArrayList;

import junit.framework.TestCase;

/**
 *
 * @author cduque
 */
public class RegistroTest extends TestCase {
    
    private Registro _registro; 
    private ArrayList _lista;
    
    public RegistroTest(String testName) {
        super(testName);
    }

    protected void setUp() throws Exception {
        _registro = new Registro();          
        _lista = new ArrayList();        
        /*Talon(id, fecha, codigo concepto, monto, moneda */
        Talon t1 = new Talon("0000055551", "20070508", "017", "00000000024530", "HNL");
        Talon t2 = new Talon("0000055552", "20071205", "029", "00000000181070", "HNL");
        _lista.add(t1);
        _lista.add(t2);        
        _registro = new Registro("0000000001", "CARLOS DUQUE", _lista);    
        
    }

    protected void tearDown() throws Exception {
         _registro = null;
    }

    /**
     * Test of getId method, of class uni.xml.Registro.
     */
    public void testGetId() {        
        assertEquals("Los ID deberian ser iguales.","0000000001", _registro.getId());
    }

    /**
     * Test of getNombre method, of class uni.xml.Registro.
     */
    public void testGetNombre() {
        assertEquals("Los nombres deberian ser iguales.","CARLOS DUQUE", _registro.getNombre());
    }

    /**
     * Test of getTalones method, of class uni.xml.Registro.
     */
    public void testGetTalones() {
        ArrayList l = new ArrayList();        
        Talon talon1 = new Talon("0000055551", "20070508", "017", "00000000024530", "HNL");
        Talon talon2 = new Talon("0000055552", "20071205", "029", "00000000181070", "HNL");
        l.add(talon1);
        l.add(talon2);
        
        assertEquals("Las clases de los contenedores deben ser iguales.", l.getClass() , _lista.getClass());        
        assertEquals("Las _listas deberían ser iguales.", l, _registro.getTalones());
        
        /*
         * Al utilizar assertEquals("Las _listas deberían ser iguales.", l, _registro.getTalones());
         * el siguiente codigo para comparar cada elemento esta de mas
         * pero igual lo dejo para efectos de documentacion
         */
        Object[] array1 = l.toArray();
        Object[] array2 = _registro.getTalones().toArray();                
        assertTrue("Arreglos de distinta longitud.", array1.length == array2.length);        
        for (int i = 0; i < array1.length; i++) {       
            assertEquals("Los elementos deberían ser iguales.", array1[i], array2[i]);
        }                 
        
    }
    
    /**
     * Test of ToString method, of class uni.xml.Registro.
     */
    public void testAddTalon() {        
        assertEquals("Las _listas No tienen el mismo tamaño", 2, _registro.getTalones().size());
        
        Talon talon3 = new Talon("0000055553", "20071103", "033", "000000001750750", "QTZ");
        _registro.addTalon(talon3);
        assertEquals("Las _listas No tienen el mismo tamaño", 3, _registro.getTalones().size());       
                     
    }
    
    /**
     * Test of ToString method, of class uni.xml.Registro.
     */
    public void testToString() {
        assertEquals("Las cadenas deberian ser iguales.",
                     "00000555512007050801700000000024530HNL00000555522007120502900000000181070HNL", 
                     _registro.toString());
    }


    
}
