package uni.server;

import java.io.IOException;
import java.net.ServerSocket;

import org.apache.log4j.Logger;

public class UServer {

	/**bitácora*/
	static Logger logger = Logger.getLogger(UServer.class);	

	private static final int PORT = 53100;

    public static void main(String[] args) throws IOException {
        
	ServerSocket serverSocket = null;
        boolean listening = true;

        try {
                logger.debug("Conectado al puerto: " + PORT);
		serverSocket = new ServerSocket(PORT);
        } catch (IOException e) {
            logger.error("No pude conectar al puerto: " + PORT);
            System.exit(-1);
        }

        while (listening)
        	new UServerThread(serverSocket.accept()).start();

        serverSocket.close();
    }
	
}
