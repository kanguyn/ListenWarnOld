package MachineLearning;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import IntegrationTest.Audio;
import IntegrationTest.AudioInterface;
import Logging.Log;

public class MachineLearning implements MachineLearningInterface{
	private final int nombreDeSonsDansBDD = 83;
	private final int k=2; //nombre de voisin pour les k plus proches voisins
	private final int sampleRate = 44100;
	
	//constructeur a mettre
	
	private Log logging;
	
	public MachineLearning(Log logging){
		this.logging=logging;
	}
	

	public int getK(){
		return k;
	}
	

	//puis on applique les differentes methodes de machinelearningmethodes avec 1 pour un mot, -1 pour une alarme, 
	//si c'est un mot, on aura les possibilites 2,3,4,5,6
	
	//il faut transformer la methode en :
	//public String findSound(AudioInterface audio)
	
	
	public String findSound(AudioInterface audio) throws IOException {

		logging.write("Trying to find the Sound present in audio track","info",getClass().getName());
		String resultsmachinelearning="";
		String wordIfOne ="";
		MachineLearningMethods machineLearning = new MachineLearningMethods(); 
		try{
			if (machineLearning.classificationWordAlarm(sampleRate, 13,audio,k)==-1)
			{resultsmachinelearning="Alarm is ringing";}
			else {
				switch(machineLearning.classificationWord(sampleRate, 13, audio,k)){
				case 2: wordIfOne="Bonjour !";	break;
				case 3: wordIfOne="Madame !";	break;
				case 4: wordIfOne="Monsieur !";	break;
				case 5: wordIfOne="Attention !";	break;
				case 6: wordIfOne="Marc !";		break;
				}
				resultsmachinelearning=wordIfOne;
			}
		}
		catch (Exception e){
			e.printStackTrace();
		}	
		this.saveSongDetected("detectedword.txt", resultsmachinelearning);
		return resultsmachinelearning;
	}
			
			
	

	public void saveSongDetected(String fileName, String text) throws IOException
	{
		try {
			PrintWriter File = new PrintWriter(fileName);
			File.write(text);
			File.close();
		}
		catch(FileNotFoundException e){
			e.printStackTrace();
		}
	}
	}

