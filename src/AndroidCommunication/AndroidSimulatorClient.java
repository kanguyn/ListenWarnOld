package AndroidCommunication;
//Connection locale : localhost 4444

import java.io.*;
import java.net.*;
import java.util.concurrent.TimeUnit;

import Logging.Log;

public class AndroidSimulatorClient {
	
	public static String className="AndroidClient";
	
 public static void main(String[] args) throws IOException {
    
	Log loggingModule;
	loggingModule = new Log("AndroidCommunication");
	loggingModule.initialize();
		
     if (args.length != 2) {
    	loggingModule.write("Parameters do not correspond to  <host name> <port number> ","severe",className);
         System.exit(1);
     }

     String hostName = args[0];
     int portNumber = Integer.parseInt(args[1]);

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
                	 loggingModule.write("Client waiting 1s before communicating with server another time","info",className);
                	 TimeUnit.SECONDS.sleep(2);
                 }
             	catch(InterruptedException e){
             		loggingModule.write("An interruption occured","warning",className);
             	}
             }
             
             if(fromServer.equals("Waiting...")){
            	loggingModule.write("Communication established with server","info",className);
             	fromClient = "Communication established";
             }
             
             else{
            	loggingModule.write("Information transmitted by the server are received : "+fromServer,"info",className);
             	fromClient = "Informations received";
             }
             if (fromClient != null) {
                 out.println(fromClient);
             }
         }
     } catch (UnknownHostException e) {
    	 loggingModule.write("Do not know about host " + hostName,"info",className);
         System.exit(1);
     } catch (IOException e) {
    	 loggingModule.write("Couldn't get I/O for the connection to " + hostName,"info",className);
         System.exit(1);
     }
 }
}