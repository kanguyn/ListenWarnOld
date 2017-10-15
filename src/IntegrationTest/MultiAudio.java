package IntegrationTest;
import Localisation.Localisation;
import Logging.Log;

import java.util.ArrayList;

public class MultiAudio implements MultiAudioInterface{
	
	private ArrayList<AudioInterface> multiAudio;
	private int sampleFrequency;
	private int sampleNumber;
	private Log logging;
	
	public MultiAudio(ArrayList<AudioInterface> multiAudio,int sampleFrequency,int sampleNumber,Log logging){
		this.multiAudio=multiAudio;
		this.sampleFrequency=sampleFrequency;
		this.sampleNumber=sampleNumber;
		this.logging=logging;
		
		logging.write("Initializing multi-audio object","info",getClass().getName());
	}
	
	public void add(AudioInterface audio){
		multiAudio.add(audio);
	}
	
	public AudioInterface getAudio(int i){
		return multiAudio.get(i);
	}
	
	public AudioInterface acquireDirectionAudio(){
		Localisation localisation = new Localisation(this,logging);
		return localisation.acquireLocalisation();
	}

	public ArrayList<AudioInterface> getMultiAudio() {
		return multiAudio;
	}

	public void setMultiAudioTrack(ArrayList<AudioInterface> multiAudio) {
		this.multiAudio = multiAudio;
	}

	public int getSampleFrequency() {
		return sampleFrequency;
	}

	public void setSampleFrequency(int sampleFrequency) {
		this.sampleFrequency = sampleFrequency;
	}

	public int getSampleNumber() {
		return sampleNumber;
	}

	public void setSampleNumber(int sampleNumber) {
		this.sampleNumber = sampleNumber;
	}
	
	
	

}