package AndroidCommunication;
// Connection locale : localhost 4444

import java.io.*;
import java.net.*;
import java.util.concurrent.TimeUnit;

import Logging.Log;
import Test.TestIntegration1;

public class AndroidSimulatorClientThread implements Runnable {
	
	private Log logging;
	int portNumber;
	private String hostName;
	
	public AndroidSimulatorClientThread(int portNumber,String hostName,Log logging) throws IOException{
		
		this.logging=logging;
		this.portNumber=portNumber;
		this.hostName=hostName;
		
	}
	
    public void run(){

    	try (
    	         Socket kkSocket = new Socket(hostName, portNumber);
    	         PrintWriter out = new PrintWriter(kkSocket.getOutputStream(), true);
    	         BufferedReader in = new BufferedReader(
    	             new InputStreamReader(kkSocket.getInputStream()));
    	     ) {
    	         String fromServer;
    	         String fromClient;

    	         while ((fromServer = in.readLine()) != null) {
    	             if (fromServer.equals("Bye.")){
    	                 try{
    	                	 logging.write("Client waiting 5s before communicating with server another time","info",getClass().getName());
    	                	 TimeUnit.SECONDS.sleep(5);
    	                 }
    	             	catch(InterruptedException e){
    	             		logging.write("An interruption occured","warning",getClass().getName());
    	             	}
    	             }
    	             
    	             if(fromServer.equals("Waiting...")){
    	            	logging.write("Communication established with server","info",getClass().getName());
    	             	fromClient = "Communication established with server";
    	             }
    	             
    	             else{
    	            	logging.write("Information transmitted by the server are received : "+fromServer,"info",getClass().getName());
    	             	fromClient = "Information transmitted by the server are received : "+fromServer;
    	             }
    	             if (fromClient != null) {
    	                 out.println(fromClient);
    	             }
    	         }
    	     } catch (UnknownHostException e) {
    	    	 logging.write("Do not know about host " + hostName,"warning",getClass().getName());
    	         System.exit(1);
    	     } catch (IOException e) {
    	    	 logging.write("Couldn't get I/O for the connection to " + hostName,"warning",getClass().getName());
    	         System.exit(1);
    	     }
    }
}