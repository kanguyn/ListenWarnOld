package Test;

import java.io.IOException;

import AndroidCommunication.IndependantPCServertoAndroidThread;
import Logging.Log;
import Materiel.MicrophonesDataThread;

public class TestIntegration3 {

	public static Log logging = new Log();
	
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		
		logging.initialize();
		
		logging.write("Beginning of a new session","info");
		
		// Thread created in order to get Audio data :
		Thread t3 = new Thread(new MicrophonesDataThread(logging)); 
		
		Thread t = new Thread(new IndependantPCServertoAndroidThread(4444,logging));
		
		// Creation of a thread that plays the role of the client :
		Thread t2 = new Thread(new TestFindWordThread(logging)); 
		
		t3.start();
		t.start();
		t2.start();
		
	}

}
