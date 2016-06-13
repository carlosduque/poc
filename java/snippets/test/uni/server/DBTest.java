/*
 * Proyecto:
 * Clase:DBTest.java
 * Fecha: May 28, 2007, 2:16 PM
 *
 */

package uni.server;

import java.util.ArrayList;

import junit.framework.TestCase;
import uni.exception.DataException;
import uni.xml.Registro;
import uni.xml.Talon;

public class DBTest extends TestCase{
    
    private DB _db;
    private ArrayList _lista;
    
    public void setUp() {
        _db = new DB();
        _lista = new ArrayList();
        /*Talon(id, fecha, codigo concepto, monto, moneda */
        Talon t1 = new Talon("0000055551", "20070508", "017", "00000000024530", "HNL");
        Talon t2 = new Talon("0000055552", "20071205", "029", "00000000181070", "HNL");
        _lista.add(t1);
        _lista.add(t2);        
        Registro r = new Registro("0000000001", "CARLOS DUQUE", _lista);        
        _db.addRegistro(r);
    }

    public void tearDown() {
        _db = null;
    }    
    
    /** Crea una nueva instancia de DBTest */
    public DBTest(String name) {
        super(name);
    }
    
    public void testAddRegistro() {        
		try {
			assertEquals("La cantidad de registros deberia ser igual",2,_db.getCantidadRegistros("0000000001"));
			ArrayList l = new ArrayList();
			/*Talon(id, fecha, codigo concepto, monto, moneda */
			Talon t3 = new Talon("0000055551", "20070508", "017", "00000000024530", "HNL");        
			l.add(t3);
			Registro r = new Registro("0000000002", "MELISSA MURILLO", l);        
			_db.addRegistro(r);
			assertEquals("La cantidad de registros deberia ser igual",2,_db.getCantidadRegistros("0000000001"));
		} catch (DataException e) {
			//Si llego aquí, la excepción se lanza bien		
		} 
    }
    
    public void testGetRegistro() {
        try {
			Registro r = _db.getRegistro("0000000001");
			assertEquals("El Id deberia ser igual","0000000001", r.getId());        
			assertEquals("Los Talones deberian ser iguales",_lista, r.getTalones());
		} catch (DataException e) {
			fail("no debio lanzar excepción");
		}
    }
    
	public void testGetRegistroInexistente() {		
		try {
			Registro r = _db.getRegistro("0000000029");
		} catch (DataException e) {
			//Si llego aquí, la excepción se lanza bien			
		}        
		
	}
    
    public void testGetNombre() {        
        try {
			assertEquals("El nombre No es igual","CARLOS DUQUE", _db.getNombre("0000000001"));
		} catch (DataException e) {
			//Si llego aquí, la excepción se lanza bien			
		}        
    }
    
    public void testGetCantidadRegistros() {
        try {
			assertEquals("La cantidad deberia ser igual", 2, _db.getCantidadRegistros("0000000001"));
		} catch (DataException e) {			
			//Si llego aquí, la excepción se lanza bien			
		}
    }
    
    public void testGetRegistros(){
        try {
			assertEquals("los registros no son iguales", _lista, _db.getRegistros("0000000001"));
		} catch (DataException e) {
			//Si llego aquí, la excepción se lanza bien			
		}		
    }
    
}
