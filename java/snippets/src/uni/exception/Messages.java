/*
 * Created on Jul 3, 2007
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package uni.exception;

import java.util.Properties;

import uni.common.Constants;
import uni.common.PropertyLoader;

public class Messages {

	//para mensajes del archivo de propiedades
	/**almacena los mensajes definidos en el archivo exceptionMessages.properties*/
	private static Properties properties = null;
	/**indica si <code>properties</code> ya fue cargado*/
	private static boolean initialized = false;

	/**
	 * Retorna el mensaje solicitado.
	 * @param messageId id del mensaje solicitado
	 * @return el mensaje solicitado
	 */
	public static String getMessage(String messageId) {

		if (!initialized) {
			properties = PropertyLoader.loadProperties(Constants.FILE_PATH_EXCEPTION_MSGS);
			initialized = true;
		}
		return properties.getProperty(messageId, Constants.ERR_DEFAULT);
	}
	
	/**
	 * Constructor privado
	 */
	private Messages () {
		
	}

}
