/*
 * The program is expected to execute the whole program into the PC.
 * The server is exactly the one who will be used to communicate with the 
 * future android device whereas the client is simulated by a java program
 * situated into the PC.
 */

package Test;

import java.io.IOException;

import AndroidCommunication.*;
import Logging.Log;

public class TestIntegration1 {
	
	public static final Log logging = new Log();
	
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		
		logging.initialize();
		
		logging.write("Beginning of a new session","info");
		
		Thread t = new Thread(new PCServerThread(4444,logging));
		
		// Creation of a thread that plays the role of the client :
		Thread t2 = new Thread(new AndroidSimulatorClientThread(4444,"localhost",logging)); 
		
		t.start();
		t2.start();
		
	}

}
