package ClientServeur;

import java.net.*;

import Logging.Log;

import java.io.*;

public class KnockKnockServer {
	
	public static String className="Server";
	
    public static void main(String[] args) throws IOException {
        
    	Log loggingModule;
    	loggingModule = new Log("ClientServeur");
    	loggingModule.initialize();
    	
        if (args.length != 1) {
        	loggingModule.write("Parameters do not correspond to  <port number> ","severe",className);
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
        
            String inputLine, outputLine;
            
            // Initiate conversation with client
            KnockKnockProtocol kkp = new KnockKnockProtocol();
            outputLine = kkp.processInput(null);
            loggingModule.write("Protocol initialized","info",className);
            out.println(outputLine);
            loggingModule.write("Server : "+outputLine,"info",className);

            while ((inputLine = in.readLine()) != null) {
            	loggingModule.write("Client : "+inputLine,"info",className);
                outputLine = kkp.processInput(inputLine);
                out.println(outputLine);
                loggingModule.write("Server : "+outputLine,"info",className);
                if (outputLine.equals("Bye."))
                    break;
            }
        } catch (IOException e) {
        	loggingModule.write("Exception caught when trying to listen on port "
                + portNumber + " or listening for a connection","severe",className);
        }
    }
}