package AndroidCommunication;
// Connection locale : 4444

import java.net.*;

import Logging.Log;

import java.io.*;
import Test.*;

public class PCServertoAndroidThread implements Runnable{
	
	public static TestFindWord testFindWord;
	int portNumber;
	private Log logging;
	
	public PCServertoAndroidThread(int port, Log logging) throws IOException{
		
		this.logging=logging;
		portNumber=port;
		
	}
	
    public void run(){
    	while(true){
	        try ( 
	            ServerSocket serverSocket = new ServerSocket(portNumber);
	            Socket clientSocket = serverSocket.accept();
	            PrintWriter out =
	                new PrintWriter(clientSocket.getOutputStream(), true);
	            BufferedReader in = new BufferedReader(
	                new InputStreamReader(clientSocket.getInputStream()));
	        ) {
	        	
	        	logging.write("Connection initialized","info",getClass().getName());
	        	
	            String inputLine, outputLine;
	            
	            // Initiate conversation with client
	            testFindWord = new TestFindWord(logging);
	            PCServertoAndroidProtocolThread kkp = new PCServertoAndroidProtocolThread(testFindWord,logging);
	            
	            logging.write("Protocol initialized","info",getClass().getName());
	        	
	            outputLine = kkp.processInput(null);
	            out.println(outputLine);
	
	            while ((inputLine = in.readLine()) != null) {
	            	if(inputLine.equals("break")){
	            		System.out.println("break");
	                 	break;
	                }
	            	else{
	            		outputLine = kkp.processInput(inputLine);
	            		out.println(outputLine);
	            	}
	            }
	        } catch (IOException e) {
	        	logging.write("Exception caught when trying to listen on port "
	                + portNumber + " or listening for a connection","warning",getClass().getName());
	        	
	        }
    	}
    }
}