package AndroidCommunication;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import Logging.Log;
import Test.*;

public class PCServertoAndroidProtocolThread {
	
	private TestFindWord testFindWord;
	private Log logging;
	
	public PCServertoAndroidProtocolThread(TestFindWord testFindWord,Log logging) throws IOException{
		this.testFindWord=testFindWord;
		this.logging=logging;
	}
	
    private static final int WAITING = 0;
    private static final int COMMUNICATIONREADY = 1;
    private static final int GOTINFORMATION = 2;

    private int state = WAITING;
    
    /**
     * The method enables to convert a file content to a string.
     * 
     * @param fileName
     * 		fileName correspond to the name of the file.
     * @return the string containing file content.
     */
    public String initFromFile(String fileName){
    	String message;
    	message="";
    	try{
			BufferedReader in = new BufferedReader(new FileReader(fileName));
			try{
				String ligne = in.readLine();
				while (!(ligne == null)){
					message=message+ligne;
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
    	return message;
    }

    /**
     * The method enables to return a string (answer) corresponding to the input (coming from the Client).
     * 
     * @param theInput
     * 		Last answer from the client
     * @return the text that has to be transmitted to the android clock.
     * @throws IOException 
     */
    public String processInput(String theInput) throws IOException {
        String theOutput = null;

        if (state == WAITING) {
            theOutput = "Waiting...";
            logging.write("Server waiting","info",getClass().getName());
            state = COMMUNICATIONREADY;
        } 
        else if (state == COMMUNICATIONREADY) {
        	logging.write("Preparing information to send to Android device","info",getClass().getName());
        	testFindWord.refresh();
        	theOutput=initFromFile("data/localisation.txt");
        	theOutput=theOutput+";";
        	theOutput=theOutput+initFromFile("data/detectedword.txt");
        	logging.write("Transmitting localisation and wordspotting results : "+theOutput,"info",getClass().getName());
        	state = GOTINFORMATION;
        } 
        else if (state == GOTINFORMATION) {
            theOutput = "Bye.";
            logging.write("Communication realized","info",getClass().getName());
            state = WAITING;
            }
        return theOutput;
    }
}
