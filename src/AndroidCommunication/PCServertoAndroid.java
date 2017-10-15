package AndroidCommunication;
// Connection locale : 4444

import java.net.*;

import Logging.Log;

import java.io.*;

public class PCServertoAndroid {
	
	public static String className="PCServertoAndroid";
	
    public static void main(String[] args) throws IOException {
        
    	Log loggingModule;
    	loggingModule = new Log("AndroidCommunication");
    	loggingModule.initialize();
    	
        if (args.length != 1) {
        	loggingModule.write("Parameters do not correspond to <port number> ","severe",className);
            System.exit(1);
        }

        int portNumber = Integer.parseInt(args[0]);

        try ( 
            ServerSocket serverSocket = new ServerSocket(portNumber);
            Socket clientSocket = serverSocket.accept();
            PrintWriter out =
                new PrintWriter(clientSocket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(
                new InputStreamReader(clientSocket.getInputStream()));
        ) {
        	loggingModule.write("Connection initialized","info",className);
        	
            String inputLine, outputLine;
            
            // Initiate conversation with client
            PCtoAndroidProtocolTest kkp = new PCtoAndroidProtocolTest();
            
            loggingModule.write("Protocol initialized","info",className);
            
            outputLine = kkp.processInput(null);
            out.println(outputLine);

            while ((inputLine = in.readLine()) != null) {
            	System.out.println(inputLine);
                outputLine = kkp.processInput(inputLine);
            }
        } catch (IOException e) {
        	loggingModule.write("Exception caught when trying to listen on port "
                + portNumber + " or listening for a connection","severe",className);
        }
    }
}