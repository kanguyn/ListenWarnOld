/*
 * The program is expected to execute the whole program into the PC.
 * In the same time, another program should work in the android device.
 * Contrary to TestIntegration2.java, the program executed by the PC is 
 * not called by Test/Integration module but these are two different process.
 */

package Test;

import java.io.IOException;

import AndroidCommunication.*;
import Logging.Log;

public class TestIntegration22 {
	
	public static Log logging = new Log();
	
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		
		logging.initialize();
		
		logging.write("Beginning of a new session","info");
		
		Thread t = new Thread(new IndependantPCServertoAndroidThread(4444,logging));
		
		// Creation of a thread that plays the role of the client :
		Thread t2 = new Thread(new TestFindWordThread(logging)); 
		
		t.start();
		t2.start();
		
	}

}
