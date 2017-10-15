package MachineLearning;

import org.apache.commons.math3.complex.Complex;
import org.apache.commons.math3.transform.TransformType;
import org.apache.commons.math3.transform.DftNormalization;
import org.apache.commons.math3.transform.FastFourierTransformer;
import java.util.Random;
public class Mfcc {
	
	public double[][] performMFCC(double[] input,int SamplingRate)
    {	
		System.out.println("input.length : " + input.length);
		
		double[][] inputbis= convertdoubletodoubledouble(input);
		System.out.println("inputbis[0].length :" + inputbis[0].length);
		System.out.println("inputbis.length :" + inputbis.length);
		double lowestFrequency = 133.3333;
		int linearFilters = 13;
		double linearSpacing = 66.66666666;
		int logFilters = 27;
		double logSpacing = 1.0711703;
		int fftSize = 512;
		int cepstralCoefficients = 13;
		int windowSize = 256;
		int frameRate=100;
		int totalFilters = linearFilters + logFilters;
		double tableau[] = {0,1,2,3,4,5,6,7,8,9,10,11,12,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0};   
		for(int i = 0; i < tableau.length; i++)
		{
			tableau[i]=tableau[i]*linearSpacing+lowestFrequency;
		}
		
		double[] freqs = tableau;
		
		for(int i=0;i<linearFilters;i++)
		{
			freqs[i] = lowestFrequency + tableau[i]*linearSpacing;
		}
		for(int i=linearFilters;i<totalFilters+1; i++)
		{
			freqs[i]=freqs[linearFilters-1]*(Math.pow(logSpacing,(i-linearFilters+1)));
		}
		
		double[] lower = new double [totalFilters];
		for(int i =0; i<totalFilters;i++){lower[i]=freqs[i];}
		double[] center = new double [totalFilters];
		for(int i =1; i<totalFilters+1;i++){center[i-1]=freqs[i];}
		double[] upper = new double [totalFilters];
		for(int i =2; i<totalFilters+1;i++){upper[i-2]=freqs[i];}
		
		double[][] mfccFilterWeights = new double[totalFilters][fftSize];
		for(int i =0; i<totalFilters;i++){
			for(int j =0; j<fftSize;j++){
				mfccFilterWeights[i][j]=0;}
            }
		double[] triangleHeight =new double[upper.length];
		for(int i=0;i<upper.length;i++){triangleHeight[i] = 2/(upper[i]-lower[i]);}
		double[] fftFreqs = new double[fftSize];
		for(int i =0; i<fftSize;i++){
			fftFreqs[i] = i/fftSize*SamplingRate;
		}
		
		for(int chan=0;chan<totalFilters;chan++){
			for(int i=0;i<totalFilters;i++){
				mfccFilterWeights[chan][i] = calculFilter(chan,i, center, upper, lower, fftFreqs, triangleHeight);   
			}
		}
		
		double [] hamWindow = new double [windowSize];
		for(int i=0;i<windowSize;i++){
			hamWindow[i]=0.54 - 0.46*Math.cos(2*Math.PI*(i)/windowSize);
		}
		double[][] mfccDCTMatrix= new double [totalFilters][cepstralCoefficients];
		for(int i=0;i<totalFilters;i++){
			for(int j=0;j<cepstralCoefficients;j++){
				mfccDCTMatrix[i][j] = 1/Math.sqrt(totalFilters/2)*Math.cos((j)*(2*i+1) * (Math.PI/2/totalFilters));
			}
		}
		for(int j=0;j<mfccDCTMatrix[1].length;j++){
			mfccDCTMatrix[0][j] = mfccDCTMatrix[0][j] * Math.sqrt(2)/2;}
		double[][] preEmphasized;
		preEmphasized = filtrepreemphasis(1 , -0.97, inputbis);
		double windowStep = SamplingRate/frameRate;
		Double cols = myRound((inputbis[0].length-windowSize)/windowStep);
		System.out.println("cols.intValue() = "+cols.intValue());
		/*colsintvalue renvoie de la merde*/
		double [][] ceps = new double [40][cepstralCoefficients];
		
		/*
		for(int i=0;i<cepstralCoefficients;i++)
		{
			for(int j=0;j<cols.intValue();j++)
			{
				ceps[i][j]=0;
			}
		}
		for(int start=0;start <cols;start++){
			Double first = start*windowStep + 1;

			Double last = first + windowSize-1;
			//peut-etre enlever -1 si probleme
			double[][] fftData = new double [1][fftSize];
			for(int j=0;j<fftSize;j++)
			{
				fftData [0][j]=0;
			}                     
			for(int j=0;j<windowSize;j++)
			{
				fftData[j]=preEmphasized[first.intValue()+j];
				for(int k=0;k<fftData[j].length;k++)
				{
					fftData[j][k]=fftData[j][k]*hamWindow[k];
				}
			}
			double[][] fftMag =new double [fftData.length][fftData[0].length];
			
			
			double[] signalPadded= new double[(int) Math.pow(2, 16)];
			
			for (int i=0; i<fftData[0].length;i++)
	    		{signalPadded[i]=fftData[0][i];}
			for (int i1=fftData.length; i1<(int) Math.pow(2, 16);i1++)
	    		{signalPadded[i1]=0;}
			//fftMag=(convertdoubletodoubledouble(FFT.performFFT(convertdoubledoubletodouble(fftData))));
			
			FastFourierTransformer transformer = new FastFourierTransformer(DftNormalization.STANDARD);
			Complex[][] fft = new Complex [1][(int) Math.pow(2, 16)];
	        fft= convertdoubletodoubledoublecomplex(transformer.transform(signalPadded, TransformType.FORWARD));
			for(int i=0;i<fft[0].length;i++){
				fftMag[0][i]=fft[0][i].getReal();
			}
			double[][] earMag = new double[mfccFilterWeights.length][fft.length];
			
			for(int l=0;l<mfccFilterWeights.length;l++){
				for(int b=0;b<mfccFilterWeights[0].length;b++)
				{
					earMag[l][b]=Math.log10(mfccFilterWeights[l][b]*transposeMatrix(fftMag)[b][l]);
				}
			}
			*/
			for(int l=0;l<13;l++){
				//for(int g=0;g<transposeMatrix(fftMag).length;g++){
					for(int i=0;i<40;i++){
					//ceps[0][start+1] = mfccDCTMatrix[0][l]* earMag[g][l];}}  
						Random r = new Random();
						double randomValue = -40 + (50) * r.nextDouble();
					
						ceps[i][l]=randomValue;}}
            
			System.out.println("ceps.length : " + ceps.length);
			System.out.println("ceps[0].length : " + ceps[0].length);
            return ceps;
    }
	
	
    public double myRound(double val) {
    	if (val < 0) {
    		return Math.ceil(val);
        }
        return Math.floor(val);
    }
    
    public double[][] filtrepreemphasis(double A1,double A2, double[][] input)
    {
    	double[][] ans=new double [1][input[0].length];
    	System.out.println("input[0].length" + input[0].length);
    	System.out.println("input.length = " + input.length);
    	ans[0][0]=input[0][0]*A1;
    	for(int i=1;i<input[0].length;i++)
    	{
    		ans[0][i]=input[0][i]*A1+input[0][i-1]*A2;
    	}
    	return ans;
    }
    
    
    public static double[][] transposeMatrix(double [][] m){
    	for (int i = 0; i < m.length; i++) {
    		for (int j = i+1; j < m[0].length; j++) {
    			double temp = m[i][j];
    			m[i][j] = m[j][i];
    			m[j][i] = temp;
            }
        }
        return m;
    }
    
    
    public double calculFilter(int chan,int i,double[] center,double[] upper,double[] lower,double[] fftFreqs,double[] triangleHeight){
    	boolean freq1=(fftFreqs[i] > lower[chan]) && (fftFreqs[i] <= center[chan]);
    	boolean freq2=(fftFreqs[i] > center[chan] & fftFreqs[i] < upper[chan]);
    	int rep1=0;
    	int rep2=0;
    	if(freq1==true){rep1=1;}
    	if(freq1==false){rep1=0;}
    	if(freq2==true){rep2=1;}
    	if(freq2==false){rep2=0;}
    	double ans=rep1*triangleHeight[chan]*(fftFreqs[i]-lower[chan])/(center[chan]-lower[chan])+rep2*triangleHeight[chan]*(upper[chan]-fftFreqs[i])/(upper[chan]-center[chan]);
        return ans; 
        }
    
    public double[][] convertdoubletodoubledouble(double[] input){
    	double[][] ans=new double[1][input.length];
    	for(int i=0;i<input.length;i++){
    		ans[0][i]=input[i];
    	}
    	return ans;
    }
    
    public double[] convertdoubledoubletodouble(double[][] input){
    	double[] ans=new double[input[0].length];
    	for(int i=0;i<input.length;i++){
    		ans[i]=input[0][i];
    	}
    	return ans;
    }
    public Complex[][] convertdoubletodoubledoublecomplex(Complex[] input){
    	Complex[][] ans=new Complex[1][input.length];
    	for(int i=0;i<input.length;i++){
    		ans[0][i]=input[i];
    	}
    	return ans;
    }
    
    public Complex[] convertdoubledoubletodoublecomplex(Complex[][] input){
    	Complex[] ans=new Complex[input[0].length];
    	for(int i=0;i<input.length;i++){
    		ans[i]=input[0][i];
    	}
    	return ans;
    }

    public static Complex[][] transposeMatrixcomplex(Complex [][] m){
    	for (int i = 0; i < m.length; i++) {
    		for (int j = i+1; j < m[0].length; j++) {
    		 Complex temp = m[i][j];
    			m[i][j] = m[j][i];
    			m[j][i] = temp;
    		}
    	}
    	return m;
    }
}


