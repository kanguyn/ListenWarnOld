/*
 * This class enables to get data from 2 microphones coming from focusrite interface.
 * This simulates the 2 others, which are for the moment, copied from the 2 first.
 */
package Materiel;

import Logging.Log;

public class MicrophonesDataThread implements Runnable{
	
	public MicrophonesDataThread(Log logging){
	
	}
	
	public void run(){
		
		Process p;
		try{
			p = Runtime.getRuntime().exec("python code4ChannelsWindows.py");
			p.waitFor();
			p.destroy();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
}
