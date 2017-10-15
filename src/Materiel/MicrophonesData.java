/*
 * The program enables to save an intensity table (no normalization) with 2 microphones (mono).
 */

package Materiel;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.sound.sampled.*;

public class MicrophonesData {

	public static void main(String[] args) throws LineUnavailableException, FileNotFoundException {
		// TODO Auto-generated method stub
		
		AudioFormat.Encoding encoding = AudioFormat.Encoding.PCM_SIGNED;
		float rate = 44100;
		int sampleSize = 16;
		int channels = 2;
		boolean bigEndian = true;
		AudioFormat audioFormat = new AudioFormat(encoding,rate,sampleSize,channels,(sampleSize/8)*channels,rate,bigEndian);
		TargetDataLine line = null;
		DataLine.Info info = new DataLine.Info(TargetDataLine.class, 
		    audioFormat); // format is an AudioFormat object
		if (!AudioSystem.isLineSupported(info)) {
		    // Handle the error ... 

		}
		// Obtain and open the line.
		try {
		    line = (TargetDataLine) AudioSystem.getLine(info);
		    line.open(audioFormat);
		} catch (LineUnavailableException ex) {
		    // Handle the error ... 
		}
		
		if(line!=null){
			// Assume that the TargetDataLine, line, has already
			// been obtained and opened.
			ByteArrayOutputStream out  = new ByteArrayOutputStream();
			int numBytesRead;
			
			byte[] data = new byte[line.getBufferSize() / 5];
			float[] dataFloat = new float[line.getBufferSize() / 5];
			byte[] dataByteTotal = new byte[44100+(line.getBufferSize() / 5)];
			float[] dataTotal = new float[44100+(line.getBufferSize() / 5)];
			int compteurTotal=0;
	
			// Begin audio capture.
			line.start();
	
			// Here, stopped is a global boolean set by another thread.
			while (compteurTotal<44100) {
			   // Read the next chunk of data from the TargetDataLine.
			   numBytesRead =  line.read(data, 0, data.length);
			   dataFloat=floatDataFromByteArray(audioFormat,data);
			   for(int i=0;i<dataFloat.length;i++){
				   dataByteTotal[compteurTotal]=data[i];
				   dataTotal[compteurTotal]=dataFloat[i];
				   compteurTotal=compteurTotal+1;
			   }
			   // Save this chunk of data.
			   out.write(data, 0, numBytesRead);
			}   
			int dataLine=0;
			int dataLine2=0;
			saveFileByteArray("son2.wav",data);
			PrintWriter File = new PrintWriter("audio5.txt");
			PrintWriter File2 = new PrintWriter("audio6.txt");
			for(int i=0;i<dataFloat.length/2;i++){
				dataLine=(int) dataFloat[2*i];
				File.write(Integer.toString(dataLine));
				File.write("\r\n");
				dataLine2=(int) dataFloat[2*i+1];
				File2.write(Integer.toString(dataLine2));
				File2.write("\r\n");
			}
			File.close();
			File2.close();
		}
	}
	
	public static void saveFileByteArray(String fileName, byte[] arrFile) {
		FileOutputStream fos=null;
		try {
			fos = new FileOutputStream(fileName);
			fos.write(arrFile);
			fos.close();
		} catch (Exception ex) {
			System.err.println("Error during saving wave file " + fileName + " to disk" + ex.toString());
		}
		System.out.println("WAV Audio data saved to " + fileName);
	}
	
	public static float[] floatDataFromByteArray(AudioFormat format, byte[] audioBytes) {
		// convert
		float[] audioData = null;
		if (format.getSampleSizeInBits() == 16) {
			int nlengthInSamples = audioBytes.length / 2;
			audioData = new float[nlengthInSamples];
			if (format.isBigEndian()) {
				for (int i = 0; i < nlengthInSamples; i++) {
					/* First byte is MSB (high order) */
					int MSB = audioBytes[2 * i];
					/* Second byte is LSB (low order) */
					int LSB = audioBytes[2 * i + 1];
					audioData[i] = MSB << 8 | (255 & LSB);
				}
			}
			else {
				for (int i = 0; i < nlengthInSamples; i++) {
					/* First byte is LSB (low order) */
					int LSB = audioBytes[2 * i];
					/* Second byte is MSB (high order) */
					int MSB = audioBytes[2 * i + 1];
					audioData[i] = MSB << 8 | (255 & LSB);
				}
			}
		}
		else if (format.getSampleSizeInBits() == 8) {
			int nlengthInSamples = audioBytes.length;
			audioData = new float[nlengthInSamples];
			if (format.getEncoding().toString().startsWith("PCM_SIGN")) {
				for (int i = 0; i < audioBytes.length; i++) {
					audioData[i] = audioBytes[i];
				}
			}
			else {
				for (int i = 0; i < audioBytes.length; i++) {
					audioData[i] = audioBytes[i] - 128;
				}
			}
		}// end of if..else
			// System.out.println("PCM Returned===============" +
			// audioData.length);
		return audioData;
	}

}
