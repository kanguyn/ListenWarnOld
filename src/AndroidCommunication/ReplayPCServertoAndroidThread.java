package AndroidCommunication;
// Connection locale : 4444

import java.net.*;

import Logging.Log;

import java.io.*;
import Test.*;

public class ReplayPCServertoAndroidThread implements Runnable{
	
	public static TestFindWord testFindWord;
	int portNumber;
	private Log logging;
	private BufferedReader input;
	private PrintWriter output;
	private String inputLine, outputLine;
	private String filePath;
	private BufferedReader inputLogging;
	public static boolean ready=false;
	
	public ReplayPCServertoAndroidThread(int port, String filePath) throws IOException{
		
		this.logging=logging;
		this.filePath="data/"+filePath;
		portNumber=port;
		
		try{
			this.inputLogging = new BufferedReader(new FileReader(this.filePath));
		}
		catch(FileNotFoundException e){
			logging.write("File "+filePath+" not found","warning",getClass().getName());
		}
		
	}
	
	public void run(){
		
		try{
			String ligne = inputLogging.readLine(); // This line contains date and other information about the file.
			while (!(ligne == null)){
				ligne = inputLogging.readLine(); // This line contains the real content sent to the logger.
				if(!(ligne == null) && ligne.contains("AndroidCommunication")){
					if(ligne.contains("PCServer")){
						System.out.println("Server Side : "+ligne);
					}
					else{
						System.out.println("Client Side : "+ligne);
					}
					ligne = inputLogging.readLine();
				}
				else{
					if(!(ligne==null)){
						ligne = inputLogging.readLine();
					}
				}
			}
			inputLogging.close();
		}
		catch(IOException e){
			logging.write("IOException","warning",getClass().getName());
		}
		
	}
}