package uni.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class UServerThread extends Thread {
    private Socket clientSocket = null;
	
    public UServerThread(Socket clientSocket) {
    	super("UServerThread");
    	this.clientSocket = clientSocket;
    }

    public void run() {

	try {
	    PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
	    BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            
            String rules = "resources/student-records-digester-rules.xml";
            String data = "/resources/student-records.xml";
	    
	    String input = in.readLine();
	    String output;
	    Datamanager dm = Datamanager.getInstance();
            dm.init(rules, data);
	    output = dm.processInput(input);	    
            out.println(output);	    

	    while (((input = in.readLine()) != null) && (!input.equals("exit"))) {
	    	output = dm.processInput(input);
	    	out.println(output);
	    }
	    
	    out.close();
	    in.close();
	    clientSocket.close();

	} catch (IOException e) {
	    e.printStackTrace();
	}
    }
}
