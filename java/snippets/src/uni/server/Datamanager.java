package uni.server;


import java.io.IOException;
import java.net.MalformedURLException;
import org.apache.commons.digester.Digester;
import org.apache.commons.digester.xmlrules.DigesterLoader;
import org.apache.log4j.Logger;
import java.io.InputStream;
import org.xml.sax.SAXException;

import uni.common.Constants;
import uni.exception.DataException;

public class Datamanager {
	
	private String trx;
	private String id;
    private Logger logger = Logger.getLogger(Datamanager.class);
    private InputStream inputStream;
	
    private static Datamanager datamanager;
    private static DB db;
        
	public Datamanager() {}
        
    public void init(String rules, String data) {        
        try {                                
            Digester digester = DigesterLoader.createDigester(this.getClass().getClassLoader().getResource(rules));          
            inputStream = Datamanager.class.getResourceAsStream(data);                
            db = (DB)digester.parse(inputStream);                
        } catch (MalformedURLException ex) {
            logger.error("MalformedURLException: " + ex.toString() + " " + ex.getMessage());
        } catch (IOException ex) {
            logger.error("IOException: " + ex.toString()+ " " + ex.getMessage());                
        } catch (SAXException ex) {
            logger.error("SAXException: " + ex.toString()+ " " + ex.getMessage());                
        } 
    }
            
	public static Datamanager getInstance() {
		if (datamanager == null) {
			 datamanager = new Datamanager();
		}			
		return datamanager;
	}
      
	public String processInput(String input){
		
		String data = null; 
		if (input.length() < 13){
			return Constants.ERR_TRX_CODE_INVALID;
		}
		
		trx = input.substring(0,3);
		id = input.substring(3,13);

		logger.info("trx:" + trx + " | " + "id:" + id);		

		try {		               
        	switch (Integer.parseInt(trx)) {
        		case 1:        		
					data = db.getNombre(id);
			   		break;
				case 3:  
					data = "";
					break;
				case 4:  
					data = "";
					break;
				default: 
					data = Constants.ERR_TRX_CODE_UNKNOWN;
					break; 
        	}
		} catch (DataException e) {					
				data = Constants.ERR_ID_UNKNOWN;
		}
//		if(tipoTransaccion.equals("001")) {
//			//data = db.findNombre(numCuenta);
//                            //public String findNombre(String nc) {				
//                            //return (String)(((Registro)registros.get(nc)).getNombre());
//	 
//                        
//			//data = "Consulta";
//			
//		} else if (tipoTransaccion.equals("003")) {
//			//data = dstore.getTalones(numCuenta);
//			//data = "Cuotas por cobrar";			
//			
//			
//		} else if (tipoTransaccion.equals("004")) {
//			data = "Pago de Cuota";
//			
//		} else {
//			data = "Unknown";
//		}
//		logger.debug("Respuesta >> " + data);
		return data;
	}

}
