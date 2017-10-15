package Test;

import java.io.IOException;

import AndroidCommunication.AndroidSimulatorClientThread;
import AndroidCommunication.PCServerThread;
import AndroidCommunication.ReplayPCServertoAndroidThread;
import Logging.Log;

public class LogReplay {
	
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		
		ReplayPCServertoAndroidThread replay = new ReplayPCServertoAndroidThread(4444,"Logging_7_2_19.txt");
		
		Thread t = new Thread(replay);
		
		t.start();
	}

}
