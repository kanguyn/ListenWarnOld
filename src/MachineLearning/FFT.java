package MachineLearning;

import org.apache.commons.math3.complex.Complex;
import org.apache.commons.math3.transform.TransformType;
import org.apache.commons.math3.transform.DftNormalization;
import org.apache.commons.math3.transform.FastFourierTransformer;

public class FFT {
    private double[] real;
    private double[] imag;
    public double[] getReal(){return real;}
    public double[] getImag(){return imag;}
    
    // le signal doit correspondre a ce que l'on recoit d'un
    //waveread en matlab, dont on doit untiliser la fonction convert
    //sur le wav en arguement d'entrée de la méthode
    
    public Complex[] performFFT(double signal[]) {
    	
    	Complex[] fft = new Complex[(int) Math.pow(2, 16)];
    	double[] signalPadded= new double[(int) Math.pow(2, 16)];
    	
    	System.out.println("signal length is: " + signal.length);
    	
    	for (int i=0; i<signal.length;i++)
    		signalPadded[i]=signal[i];
    	
    	for (int i1=signal.length; i1<(int) Math.pow(2, 16);i1++)
    		signalPadded[i1]=0;
    	System.out.println("signalPadded length is: " + signalPadded.length);
    		
    	
    	System.out.println("line 22 about to execute in fft");
        FastFourierTransformer transformer = new FastFourierTransformer(DftNormalization.STANDARD);
        System.out.println("line 22 executed in fft");
        try {           
        	System.out.println("line 26 about to execute in fft"); 
        	fft= transformer.transform(signalPadded, TransformType.FORWARD);
        	System.out.println("line 26 executed in fft");

          

        } catch (IllegalArgumentException e) {
            System.out.println(e);
        }
        System.out.println("line 33 executed in fft");

        return fft;
    		

        } 
}
