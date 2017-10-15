package MachineLearning;


public class DCT {
//nombre de coeffs
	final int numCoefficients=13;
//nombre de filtres de mels	
	final int M=26;

	

	 public double[] performDCT(double y[]) {
         final double cepc[] = new double[numCoefficients];
         System.out.println("line16 about to execute in DCT");
         // DCT sur une matrice de double
         for (int n = 1; n <= numCoefficients; n++) {
                 for (int i = 1; i <= M; i++) {
                         cepc[n - 1] += y[i - 1] * Math.cos(Math.PI * (n - 1) / M * (i - 0.5));
                 }
         }System.out.println("line16 executed in DCT");
         return cepc;
	 }
}


