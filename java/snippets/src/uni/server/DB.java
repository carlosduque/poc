package uni.server;

import java.util.ArrayList;
import java.util.HashMap;

import org.apache.log4j.Logger;

import uni.exception.DataException;
import uni.xml.Registro;

public class DB {
	
    private Logger logger = Logger.getLogger(DB.class);
	private static HashMap registros;
	
	public DB() {
		registros = new HashMap();
	}
	
	public void addRegistro(Registro r) {
		registros.put(r.getId(), r);
	}
        
    public int getCantidadRegistros(String key) throws DataException {
		 return findRegistroByKey(key).getTalones().size();         
    }
        
    public Registro getRegistro(String key) throws DataException {
         //return (Registro)registros.get((String)key);
		 return findRegistroByKey(key);
    }
        
    public ArrayList getRegistros(String key) throws DataException {         
         return findRegistroByKey(key).getTalones();
    }
        
    public String getNombre(String key) throws DataException {       
         return findRegistroByKey(key).getNombre();
    }
    
    private Registro findRegistroByKey(String key) throws DataException {    	
    	Registro r = (Registro)registros.get((String)key);		
		return r;      
    }
}
