package Test;

import java.io.IOException;
import java.util.ArrayList;

import MachineLearning.*;
import IntegrationTest.*;
import Logging.Log;

public class TestFindWord {
	
	private MachineLearning machineLearning;
	private Log loggingModule;
	private Log logging;
	
	public TestFindWord(Log logging) throws IOException{
		
		this.logging=logging;
		loggingModule = new Log("Test");
		loggingModule.initialize();
		
		loggingModule.write("Initializing Machine Learning","info",getClass().getName());
		
		// The following lines enable to initialize machine learning.
		// Creation of some different songs : could be replaced later by a list of words and sound.
		this.machineLearning = new MachineLearning(logging);
	}
	
	public void refresh() throws IOException{
		
		// Part supposed to initialize the different tracks with the four directions :  
		AudioInterface audio1 = new Audio("audio1.txt","left",logging);
		AudioInterface audio2 = new Audio("audio2.txt","top",logging);
		AudioInterface audio3 = new Audio("audio3.txt","right",logging);
		AudioInterface audio4 = new Audio("audio4.txt","bottom",logging);
		
		// Part enabling to initialize MultiAudio object containing different audioTracks gathered at the same time :
		ArrayList<AudioInterface> audioList = new ArrayList<AudioInterface>();
		audioList.add(audio1);
		audioList.add(audio2);
		audioList.add(audio3);
		audioList.add(audio4);
		MultiAudio multiAudio = new MultiAudio(audioList,16000,100,logging);
		
		// Obtaining results going to be displayed by the connected device : 
		AudioInterface audio = multiAudio.acquireDirectionAudio();
		loggingModule.write("Finding the direction of the sound","info",getClass().getName());
    	
    	loggingModule.write("Direction found : "+audio.getDirection(),"info",getClass().getName());
    	
    	loggingModule.write("Finding the nature of the sound present in the sound","info",getClass().getName());
    	
		String song = machineLearning.findSound(audio);
		loggingModule.write("Sound detected : "+song,"info",getClass().getName());
		
	}
	
}
