package uni.server;

import java.util.ArrayList;

import junit.framework.TestCase;
import uni.xml.Talon;

public class DatamanagerTest extends TestCase {
    
    private Datamanager _datamanager;
    private String _rules = "resources/test-records-digester-rules.xml";
    private String _data = "/resources/test-records.xml";
    
    public DatamanagerTest(String testName) {
        super(testName);
    }

    /**
     * Test of getInstance method, of class uni.server.Datamanager.
     */
    public void testGetInstance() {
        Datamanager dm = Datamanager.getInstance();
        assertTrue("El objeto deberia estar instanciado, no deberia ser null",null != dm);
    }

    /**
     * Test of init method, of class uni.server.Datamanager.
     */
//    public void testInit() {        
//        _datamanager = Datamanager.getInstance();        
//        _datamanager.init(_rules, _data); 
//        String result = _datamanager.getNombre("0000011111");
//        assertEquals("No se pudo extraer un dato del datamager", "CARLOS DUQUE", result);        
//        result = _datamanager.getNombre("0000022222");
//        assertEquals("No se pudo extraer un dato del datamager", "MELISSA MURILLO", result);        
//    }
    /**
     * Test of processInput method, of class uni.server.Datamanager.
     */
    public void testProcessInput() {
        _datamanager = Datamanager.getInstance();      
        _datamanager.init(_rules, _data);         
    }
    
    /**
     * Test of getNombre method, of class uni.server.Datamanager.
     */
//    public void testGetNombre() {
//        _datamanager = Datamanager.getInstance();
//		_datamanager.init(_rules, _data);      
//        String result = _datamanager.getNombre("0000022222");
//        assertEquals("No se pudo extraer un dato del datamager", "MELISSA MURILLO", result);        
//    }

    /**
     * Test of getNombre method, of class uni.server.Datamanager.
     */
//    public void testGetCantidadTalones() {
//        _datamanager = Datamanager.getInstance();              
//        assertEquals("No existe el mismo numero de registros", 17, _datamanager.getCantidadTalones("0000022222"));
//    }
    
        /**
     * Test of getNombre method, of class uni.server.Datamanager.
     */
//    public void testGetTalones() {
//        _datamanager = Datamanager.getInstance();      
//        
//        ArrayList lista = new ArrayList();
//        /*Talon(id, fecha, codigo concepto, monto, moneda */                
//        Talon t1 = new Talon("0000021800", "20010105", "005", "00000000152500", "001");
//        Talon t2 = new Talon("0000028759", "20020204", "038", "00000000130000", "001");
//        Talon t3 = new Talon("0000032083", "20030303", "004", "00000000050000", "001");
//        Talon t4 = new Talon("0000127774", "20040402", "007", "00000000142100", "001");         
//        lista.add(t1);
//        lista.add(t2);
//        lista.add(t3);
//        lista.add(t4);
//        
//        assertEquals("Las listas no son del mismo tamaño", lista.size(), _datamanager.getTalones("0000011111").size());
//        assertEquals("No es la misma lista de talones", lista, _datamanager.getTalones("0000011111"));
//    }
    
    public void testProcessInputCodigoDesconocido(){
		_datamanager = Datamanager.getInstance();
		_datamanager.init(_rules, _data);
		String result = _datamanager.processInput("0091111122222");
		assertEquals("Debe ser error si el codigo de transaccion es desconocido", "950", result);    	
    }

	public void testProcessInputCodigoInvalido(){   
		_datamanager = Datamanager.getInstance();
		_datamanager.init(_rules, _data);      
		String result = _datamanager.processInput("009");
		assertEquals("Debe ser error si la longitud del codigo de transaccion es invalida", "951", result);	
	}
    
	public void testProcessInputCodigo001Inexistente(){
		_datamanager = Datamanager.getInstance();
		_datamanager.init(_rules, _data);      
		String result = _datamanager.processInput("0011111122222");
		assertEquals("Debe ser error si la cuenta del alumno no existe", "960", result);    	
	}
	
	public void testProcessInputCodigo001(){
		fail("Solo es un prototipo");    	
	}
	
	public void testProcessInputCodigo003Inexistente(){
		fail("Solo es un prototipo");    	
	}
	
	public void testProcessInputCodigo003(){
		fail("Solo es un prototipo");    	
	}
	
	public void testProcessInputCodigo004Inexistente(){
		fail("Solo es un prototipo");    	
	}
	
	public void testProcessInputCodigo004(){
		fail("Solo es un prototipo");    	
	}    

}
