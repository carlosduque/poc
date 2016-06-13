package uni.common;

import java.util.Properties;

public class Constants {

	/**ruta del archivo principal de configuración*/
	public static final String FILE_PATH_CFG = "/resources/userver.properties";
	/**ruta del archivo de mensajes de excepciones*/
	public static final String FILE_PATH_EXCEPTION_MSGS = "/resources/exceptionMessages.properties";  //$NON-NLS-1$ 
	/**property name asociado al valor del puerto donde escucha la aplicación*/
	public static final String PORT_PN = "port";
	/**property name asociado al valor del archivo con las reglas de digester*/
	public static final String FILE_XML_RULES_PN = "xml-rules";
	/**property name asociado al valor del archivo con los datos*/
	public static final String FILE_XML_DATA_PN = "xml-data";
	
	/**Codigos de la aplicacion*/
	public static final String ERR_DEFAULT = "999";
	public static final String ERR_TRX_CODE_UNKNOWN = "950";
	public static final String ERR_TRX_CODE_INVALID = "951";
	public static final String ERR_ID_UNKNOWN = "960";
	public static final String TRX_CODE_CONSULT = "001";	
	public static final String TRX_CODE_CONSULT_ITEMS = "003";
	public static final String TRX_CODE_PAY = "004";
	
		
	
	
	//para constantes leídas del arhivo de propiedades
	/**almacena las propiedades definidas en el archivo paguelo.properties*/
	private static Properties properties = null;
	/**indica si <code>properties</code> ya fue cargado*/
	private static boolean initialized = false;

	/**
	 * Retorna el valor de la propiedad solicitada.
	 * @param propertyName nombre de la propiedad requerida
	 * @return el valor de la propiedad solicitada
	 */
	public static String getPropertyValue(String propertyName) {

		if (!initialized) {
			properties = PropertyLoader.loadProperties(FILE_PATH_CFG);
			initialized = true;
		}
		return properties.getProperty(propertyName);
	}
	
	/**
	 * Constructor privado
	 */
	private Constants () {
		
	}

}
