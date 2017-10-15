/*
 * This file is expected to test a model expected to determine the word and the direction only with interface and 
 * nearly empty class.
 */

package Test;

import java.io.IOException;
import java.util.ArrayList;

import MachineLearning.*;
import IntegrationTest.*;
import Logging.Log;

public class TestFirstInterface {
	
	public static final Log logging = new Log();

	public static void main(String[] args) throws IOException{
		// TODO Auto-generated method stub
		
		logging.initialize();
		
		logging.write("Beginning of a new session","info");
		
		// Part supposed to initialize MachineLearning.
		MachineLearning machineLearning = new MachineLearning(logging);
		
		// Part supposed to initialize the different tracks. 
		AudioInterface audio1 = new Audio("audio1.txt","left",logging);
		AudioInterface audio2 = new Audio("audio2.txt","top",logging);
		AudioInterface audio3 = new Audio("audio3.txt","right",logging);
		AudioInterface audio4 = new Audio("audio4.txt","bottom",logging);
		ArrayList<AudioInterface> audioList = new ArrayList<AudioInterface>();
		audioList.add(audio1);
		audioList.add(audio2);
		audioList.add(audio3);
		audioList.add(audio4);
		MultiAudio multiAudio = new MultiAudio(audioList,16000,100,logging);
		AudioInterface audio = multiAudio.acquireDirectionAudio();
		
		String song = machineLearning.findSound(audio);
		System.out.println(audio.getDirection()+";"+song);
	}

}
