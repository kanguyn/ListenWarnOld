/*
 * The program is expected to execute the whole program into the PC.
 * In the same time, another program should work in the android device.
 * Contrary to TestIntegration1.java, the client side that was simulated 
 * by a thread executed in the PC has been replaced by the app executed 
 * into the device.
 */

package Test;

import java.io.IOException;

import AndroidCommunication.*;
import Logging.Log;

public class TestIntegration21 {
	
	public static final Log logging = new Log();
	
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		
		logging.initialize();
		
		logging.write("Beginning of a new session","info");
		
		Thread t = new Thread(new PCServertoAndroidThread(4444,logging));
		
		t.start();
		
	}

}
