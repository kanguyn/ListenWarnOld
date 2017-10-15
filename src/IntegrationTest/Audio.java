package IntegrationTest;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import Logging.Log;

public class Audio implements AudioInterface {
	
	private String fileName;
	private String direction;
	private IntensityTable intensityTable;
	private Log logging;
	private int Fe;
	
	public Audio(String fileName,String direction, Log logging){
		
		this.logging=logging;
		this.fileName=fileName;
		this.direction=direction;
		this.intensityTable=initFromAudioFile("data/"+fileName);
		this.Fe=44100;
		logging.write("Initializing audio track corresponding to direction "+direction,"info",getClass().getName());
	}
	
	public IntensityTable initFromAudioFile(String fileName){
		
		logging.write("Initializing Intensity table corresponding to the direction "+direction,"info",getClass().getName());
		IntensityTable intensityTable=new IntensityTable();
		
		try{
			BufferedReader in = new BufferedReader(new FileReader(fileName));
			try{
				String ligne = in.readLine();
				while (!(ligne == null)){
					Double doubleValue = new Double(ligne);
					intensityTable.add(doubleValue);
					ligne = in.readLine();
				}
				in.close();
			}
			catch(IOException e){
				logging.write("IOException","warning",getClass().getName());
			}
		}
		catch(FileNotFoundException e){
			logging.write("File "+fileName+" not found","warning",getClass().getName());
		}
		
		return intensityTable;
		
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getDirection() {
		return direction;
	}

	public void setDirection(String direction) {
		this.direction = direction;
	}

	public IntensityTable getIntensityTable() {
		return intensityTable;
	}

	public void setIntensityTable(IntensityTable intensityTable) {
		this.intensityTable = intensityTable;
	}
	

}
