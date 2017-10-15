package MachineLearning;

import java.util.ArrayList;
import IntegrationTest.IntensityTable;
import org.apache.commons.math3.complex.Complex;
import org.apache.commons.math3.transform.FastFourierTransformer;
import org.apache.commons.math3.transform.TransformType;
import org.apache.commons.math3.transform.DftNormalization;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Serializable;

public class AttackDetection {
	
	public IntensityTable halfWaveRectify(IntensityTable signal){
		IntensityTable copy = new IntensityTable(signal);
		int n = signal.size();
		
		System.out.println("original signal is :");
		for (int i =0; i<n; i++){
			System.out.print(copy.get(i)+";");
		}
		System.out.println("");
		
		//il faudra ecrire le constructeur de la classe IntensityTable pour copier tout "signal" dans une nouvelle ArrayList
		
		
		for (int i =0; i<n; i++){
			if (copy.get(i) <0) copy.set(i, (double) 0);
		}
		
		System.out.println("halfWaveRectify is :");
		for (int i =0; i<n; i++){
			System.out.print(copy.get(i)+";");
		}
		System.out.println("");
		
		return copy;
	
			    
			//Test avec: aPlus = halfWaveRectify(fonction1(T))
	}
	
	
	
	public IntensityTable differencier(IntensityTable signal){
		IntensityTable copy = new IntensityTable(signal);
		
		int n = signal.size();
		
		System.out.println("original signal is :");
		for (int i =0; i<n; i++){
			System.out.print(copy.get(i)+";");
		}
		System.out.println("");
		//il faudra ecrire le constructeur de la classe IntensityTable pour copier tout "signal" dans une nouvelle ArrayList
		IntensityTable derivee = new IntensityTable();

		for (int i =1; i<n; i++) derivee.add(copy.get(i)-copy.get(i-1));
		
		System.out.println("Derivee is :");
		for (int i =0; i<derivee.size(); i++){
			System.out.print(derivee.get(i)+";");
		}
		System.out.println("");
		
		return halfWaveRectify(derivee);
	}
	
	public IntensityTable redressementEnveloppe(IntensityTable signal){
		IntensityTable copy = new IntensityTable(signal);
		copy = halfWaveRectify(copy);
		int n = signal.size();
		
		for (int i = 0 ; i< copy.size(); i++){
			copy.set(i,copy.get(i)*copy.get(i));
		}
		
		System.out.println("redressement is :");
		for (int i =0; i<n; i++){
			System.out.print(copy.get(i)+";");
		}
		System.out.println("");
		
		return copy;		
	}
	    
	    
	//public IntensityTable lowPassFilter(IntensityTable data, Double cutoff,Double sampleFreq, int order=1)
	
	//http://stackoverflow.com/questions/4026648/how-to-implement-low-pass-filter-using-java
	public IntensityTable fourierLowPassFilter(IntensityTable data, double lowPass, double frequency){
		
	    //data: input data, must be spaced equally in time.
	    //lowPass: The cutoff frequency at which 
	    //frequency: The frequency of the input data.

		//plus tard mettre comme signature: public IntensityTable fourierLowPassFilter(IntensityTable data,  cutoff,  sampleFreq)
		
		//mettre data dans un tableau de taille data.size()
		double[] data1 = new double[data.size()];
		for (int k =0; k<data.size();k++){
			data1[k] = data.get(k);
		}
		
	    //The apache Fft (Fast Fourier Transform) accepts arrays that are powers of 2.
	    int minPowerOf2 = 1;
	    while(minPowerOf2 < data1.length)
	        minPowerOf2 = 2 * minPowerOf2;

	    //pad with zeros
	    double[] padded = new double[minPowerOf2];
	    for(int i = 0; i < data1.length; i++)
	        padded[i] = data1[i];


	    FastFourierTransformer transformer = new FastFourierTransformer(DftNormalization.STANDARD);
	    Complex[] fourierTransform = transformer.transform(padded, TransformType.FORWARD);

	    //build the frequency domain array
	    double[] frequencyDomain = new double[fourierTransform.length];
	    for(int i = 0; i < frequencyDomain.length; i++)
	        frequencyDomain[i] = frequency * i / (double)fourierTransform.length;

	    //build the classifier array, 2s are kept and 0s do not pass the filter
	    double[] keepPoints = new double[frequencyDomain.length];
	    keepPoints[0] = 1; 
	    for(int i = 1; i < frequencyDomain.length; i++){
	        if(frequencyDomain[i] < lowPass)
	            keepPoints[i] = 2;
	        else
	            keepPoints[i] = 0;
	    }

	    //filter the fft
	    for(int i = 0; i < fourierTransform.length; i++)
	        fourierTransform[i] = fourierTransform[i].multiply((double)keepPoints[i]);

	    //invert back to time domain
	    Complex[] reverseFourier = transformer.transform(fourierTransform, TransformType.INVERSE);

	    //get the real part of the reverse 
	    double[] result = new double[data1.length];
	    for(int i = 0; i< result.length; i++){
	        result[i] = reverseFourier[i].getReal();
	    }
	    
	    //want to return an IntensityTable
	    IntensityTable result1= new IntensityTable(data1.length);
	    for (int j=0;j<data1.length;j++)
	    	result1.set(j, result[j]);

	    System.out.println("now showing the array fft : ");
		for (int k =0; k<result1.size();k++ )
			System.out.print("(" + result1.get(k) + ")");
		System.out.println("");	   
		
	    return result1;
	}

	
	
	
	public IntensityTable detectionAttaque(IntensityTable data,Double sampleFreq ,int filterOrder, Double cutoffFreq){
				
	    int nbEch = data.size();
	    
	    //if len(data.shape) ==2: #stereo --> il faut au prealable ne prendre qu'une chaine dans "data"
	        
	   Double temps = nbEch/cutoffFreq ;
	   //t = np.linspace(0, Temps, nbEch, endpoint=False)
	    
	   IntensityTable filteredData = fourierLowPassFilter(halfWaveRectify(data), cutoffFreq, sampleFreq);
	   IntensityTable resultat = differencier(filteredData);
	   
		System.out.println("now showing the array result of filtre : ");
		for (int k =0; k<resultat.size();k++ )
			System.out.print("(" + resultat.get(k) + ")");
		System.out.println("");	   
		return (resultat);
	    		
	}
	
	public double tempsDeDetectionAttaque(IntensityTable data,Double sampleFreq, Double intensiteMin){
		int i=0;
		while(i<data.size() && data.get(i)<intensiteMin){
			i++;
		}
		Double tempsDeDepart= i/sampleFreq;
		
		return tempsDeDepart;
	}
	
	//pour le test, on compare les resultats entre ici et python
	public ArrayList<Double> readArrayInTxt(String fileName)throws IOException {
		BufferedReader readerWithBuffer = null;
		String line;

		ArrayList<Double> arrayResult= new ArrayList<Double>();
		try {
			readerWithBuffer = new BufferedReader(new FileReader(fileName));
			
			while((line=readerWithBuffer.readLine()) != null) {
				System.out.println("line.length(): "+ line.length());
				arrayResult.add(Double.parseDouble(line));
			}
			System.out.println("now showing the array read in DetectionAttaqueTest : ");
			for (int k =0; k<arrayResult.size();k++ )
				System.out.print("(" + arrayResult.get(k) + ")");
			System.out.println("");
		}
		
		
		catch (FileNotFoundException exception) {
			System.out.println("Opening error");
			exception.printStackTrace();
		}
		catch (IOException e) {	e.printStackTrace();} 
		finally {
			try {
				readerWithBuffer.close();
				} catch(Exception e){e.printStackTrace();}
			}
		return arrayResult;	
	}
	
	
	

}
