package Localisation;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Random;

import IntegrationTest.AudioInterface;
import IntegrationTest.MultiAudio;
import Logging.Log;

public class Localisation implements LocalisationInterface{
	
	private MultiAudio multiAudio;
	private Log logging;
	
	public Localisation(MultiAudio multiAudio,Log logging){
		this.multiAudio=multiAudio;
		this.logging=logging;
	}
	
	public AudioInterface acquireLocalisation(){
		// Localization algorithm must be completed.
		
		logging.write("Trying to find the localization of the sound","info",getClass().getName());
		
		Random rand = new Random(); // must be changed : determined randomly for the test.
		Integer number = rand.nextInt(4);
		
		AudioInterface audio = multiAudio.getAudio(number); // 0 correspond to the track (direction) detected.
		saveLocalisation("data/localisation.txt",audio.getDirection());
		
		logging.write("Localization of the sound determined : "+audio.getDirection(),"info",getClass().getName());
		
		return audio; 
	}
	
	
	public void saveLocalisation(String fileName, String text){
		
		try{
			PrintWriter File = new PrintWriter(fileName);
			File.write(text);
			File.close();
		}
		catch(FileNotFoundException e){
			e.printStackTrace();
		}
		
	}
	
}
