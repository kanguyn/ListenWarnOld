package MachineLearning;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import IntegrationTest.Audio;
import IntegrationTest.AudioInterface;
import IntegrationTest.IntensityTable;
import Logging.Log;

public class TestMachineLearning {
	private static long startTime = System.nanoTime();

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		Log logging = new Log();
		logging.initialize();
		MachineLearningMethods classif =new MachineLearningMethods();
		MachineLearning classi = new MachineLearning(logging);
		
		/*
		ReadWavFile read = new ReadWavFile();
		read.read("C:/Users/oanh/Desktop/BDD/1.1.wav");
		//ok
		
		
		pour le test
		Audio audio = new Audio();
		IntensityTable intensity= new IntensityTable();
		for (int i=0;i<100;i++)
			intensity.add(Math.random()*2-1);
		audio.setIntensityTable(intensity);
		*/
		
		double[][] mfcc1 = new double[36][13];
		double[][] mfcc2 = new double[36][13];
		
		//test de la distance euclidienne euclideanDistance
		/*
		mfcc1 = classif.readMfcc(2,36);
		for (int i=0;i<83;i++){
			mfcc2 = classif.readMfcc(i+1,36);
			System.out.println("distance 1.2-" + (i+1) + "= " + classif.euclideanDistance(mfcc1, mfcc2));
		}
		
		*/
		
		
		BufferedReader readerWithBuffer = null;
		String line;
		ArrayList<Double> resultat= new ArrayList<Double>();
		try {
			readerWithBuffer = new BufferedReader(new FileReader("C:/Users/Kim-Anh-Nhi/Downloads/son1.txt"));
			
			while((line=readerWithBuffer.readLine()) != null ) {
				resultat.add(Double.parseDouble(line));					
			}
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
		
		for (int i=0;i<resultat.size();i++){
			System.out.println(resultat.get(i));
		}
		
		
		Log logging = new Log();
		logging.initialize();
		
		//AudioInterface test1 = new Audio("C:/Users/Kim-Anh-Nhi/Downloads/son1.txt","left",logging);
		
		AudioInterface audio = new Audio();
		IntensityTable intensity= new IntensityTable();
		
		for (int i=0;i<resultat.size();i++)
			intensity.add(resultat.get(i));
		audio.setIntensityTable(intensity); 
		
		
		
		String testClassif = classi.findSound(audio);
		
		System.out.println("resultat classification"+ testClassif);
		
		
		
		
		/*
		//test de readMfccBis pour toutes les matrices de la BDD
		for (int i=0;i<83;i++){
			mfcc1 = classif.readMfccBis(i+1,36);
			System.out.println("mfcc numero" + (i+1) + "calculee");
		}
		
		//afficher la derniere mfcc
		for (int i=0;i<mfcc1.length;i++){
			for (int j=0;j<mfcc1[0].length;j++){
				System.out.print(mfcc1[i][j] + " ; ");
			}System.out.println("");
		}
		*/
		
		
		//resultats : renvoie les bonnnes matrices pour readMfccBis(k,36) avec k = 1 a 83
		
		
		
		

		
		
		
		
		
		
		
		
		
		
		/*
		double[][] matrice1 = new double[2][5];
		
		double[] line= new double[5];
		for (int k =0 ; k<matrice1[0].length;k++){
			line[k]=k; System.out.println("line[k]" + line[k]);
		}
		for (int j=0;j<matrice1[0].length;j++){
			matrice1[0][j] = line[j];
		}
		
		for (int k =0 ; k<matrice1[0].length;k++){
			line[k]=k*2; System.out.println("line[k]" + line[k]);
		}
		
		for (int j=0;j<matrice1[0].length;j++){
			matrice1[1][j] = line[j];
		}
		
		
		for (int i=0;i<matrice1.length;i++){
			for (int j=0;j<matrice1[0].length;j++){
				System.out.print(matrice1[i][j] + " ; ");
			}System.out.println("");
		}
		*/
			
		/*
		int label = classif.readLineLabel(79); System.out.println("label is: " + label);
		//ça marche
		ArrayList<Neighbour> neighbours3= classif.getNeighborsWordAlarm(100, 44100, 13, audio,5);
		for (int i=0 ; i<neighbours3.size();i++){
			System.out.print("(" + neighbours3.get(i).getNumber() + ","+ neighbours3.get(i).getDistance() + ")");
		}
		
		int resultat3 = classif.classificationWordAlarm(100, 44100, 13, audio,5);
		System.out.println("classe is: " + resultat3);
		
		int resultat = classif.classificationWord(100, 44100, 13, audio,5);
		System.out.println("classe is: " + resultat);
		
		==> ok, les 2 methodes neighbours fonctionnent !
		*/
	
		/*
		//test KNN classification k=2
		MachineLearning knn = new MachineLearning();
		String resultat1 = knn.classification(100, 44100,audio,5);
		System.out.println("resultat machine learning : "+ resultat1);
		*/
		
		 
		 
		
		
		
		/*
		String a="2.3";
		String b="1"; String c="."; String d="6";
		String total = b+c+d;
		String negatif="-3.8";
		System.out.println("a is:" + a);System.out.println("b is "+b);
		System.out.println("c is "+c);	System.out.println("d is "+d); 
		System.out.println("total is"+ total); System.out.println("negatif is"+negatif);
		
		Double na=new Double(0),nnegatif=new Double(0),ntotal = new Double(0);
		na = Double.parseDouble(a); System.out.println("a converted to double is:" + na);
		nnegatif=Double.parseDouble(negatif);System.out.println("negatif converted to double is:" + nnegatif);
		ntotal=Double.parseDouble(total); System.out.println("total converted to double is:" + ntotal);
		
		double sum1 = na +nnegatif;System.out.println("2.3-3.8= "+sum1);
		//ca marche !
		
		String k=new String();
		char w='2'; char x='.';char v='6';
		k+=Character.toString(w); k+=Character.toString(x);k+=Character.toString(v);
		double fin = Double.parseDouble(k);
		System.out.println("double obtenu is: " + fin);
		System.out.println("double transforme en int :" + (int) fin); //2.6 devient 2 (tronque)
		//ça marche!
		
		
		
		MachineLearningMethods testRead1 = new MachineLearningMethods();
		int firstLine = testRead1.readLineLabel(80);
		System.out.println(firstLine);
			
		//ca marche !
		
		*/
		
		/*
		//test MFCC
		//met les MFCC dans la BDD
		//SamplePerFrame a verifier
		try
		{
			File file = new File("DataBase/MFCCFile.txt");
			PrintWriter f = new PrintWriter(new BufferedWriter(new FileWriter(file)))	;
			for (int j=1; j<6;j++) {
				for (int k=1;k<16;k++) {
					ReadWavFile read1 = new ReadWavFile();
					double[] soundVector  = read1.read("C:/Users/Kim-Anh-Nhi/Desktop/BDD/" + String.valueOf(j) + "." + String.valueOf(k) + ".wav");
					//double[] soundVector  = read.read("C:/Users/Kim-Anh-Nhi/Desktop/1.10.wav");
					//for (int i=0 ; i<soundVector.length;i++)
					//System.out.println(soundVector[i]);
					
					Mfcc mfccSoundTest = new Mfcc(512, 44100, 13,false); //SamplePerFrame a verifier !
					
					System.out.println("Now showing Mfcc vector");
					double[] mfccSoundTestVector = mfccSoundTest.performMFCC(soundVector);
					
					System.out.print("(");
					for (int i=0 ; i<mfccSoundTestVector.length;i++){
						System.out.print(mfccSoundTestVector[i]+ " ");
					}
					System.out.println("),");
					//ca marche !!
					
					int i = 0;
					String line1 = new String()	;
					line1 = "(";
					while (i< mfccSoundTestVector.length-1)
					{
						line1 = line1.concat(String.valueOf(mfccSoundTestVector[i])) ;
						line1+=" ";
						i++	;
					}
					line1 = line1.concat(String.valueOf(mfccSoundTestVector[i])) ;
					line1 += "),";
					f.println(line1);
					}
				}
			
			
			//idem pour les alarmes
			for (int j=1; j<9;j++) {
				ReadWavFile read1 = new ReadWavFile();
				double[] soundVector  = read1.read("C:/Users/Kim-Anh-Nhi/Desktop/BDD/al." + String.valueOf(j) + ".wav");
				//double[] soundVector  = read.read("C:/Users/Kim-Anh-Nhi/Desktop/1.10.wav");
				//for (int i=0 ; i<soundVector.length;i++)
				//System.out.println(soundVector[i]);
				Mfcc mfccSoundTest = new Mfcc(512, 44100, 13,false); //SamplePerFrame a verifier !
				System.out.println("Now showing Mfcc vector");
				double[] mfccSoundTestVector = mfccSoundTest.performMFCC(soundVector);
				System.out.print("(");
				for (int i=0 ; i<mfccSoundTestVector.length;i++){
					System.out.print(mfccSoundTestVector[i]+ " ");
				}
				System.out.println("),");
				//ca marche !!
				int i = 0;
				String line1 = new String()	;
				line1 = "(";
				while (i< mfccSoundTestVector.length-1)
				{
					line1 = line1.concat(String.valueOf(mfccSoundTestVector[i])) ;
					line1+=" ";
					i++	;
				}
				line1 = line1.concat(String.valueOf(mfccSoundTestVector[i])) ;
				line1 += "),";
				f.println(line1);
				}		
			
			f.close();
		}
		catch(FileNotFoundException exception) {
			System.out.println("Opening error")	;
			exception.printStackTrace();
			}
		catch (SecurityException e){
			System.err.println("Error class Maze, saveToTextFile:security exception \"" + "DataBase/MFCCFile.txt" + "\"");
			}
		catch (Exception e){
			System.err.println("Error: unknown error.");
			e.printStackTrace(System.err);
			}
			*/
		

		 /*
		
		ReadWavFile read = new ReadWavFile();
		double[] audio1 = read.read("C:/Users/Kim-Anh-Nhi/Desktop/BDD/al.1.wav");
		
		Audio audio = new Audio();
		IntensityTable intensity= new IntensityTable();
		
		for (int i=0;i<audio1.length;i++)
			intensity.add(audio1[i]);
		audio.setIntensityTable(intensity); 
		
		System.out.println("showing frames here :");
		System.out.print("(");
		for (int k=0; k<audio1.length ; k++)
			System.out.print(audio1[k] + ";");
		System.out.println("");
		*/
		/*
		//test mfcc: verifier que c'est la meme que la base de donnee
		Mfcc mfccSoundTest = new Mfcc(512, 44100, 13);
		double[] soundVector = classif.transformInDoubleList(intensity);
		double[] mfccSoundTestVector = mfccSoundTest.performMFCC(soundVector);
		System.out.println("mfcc vector od this sound is :");
		for (int i=0; i< mfccSoundTestVector.length;i++){
			System.out.print(mfccSoundTestVector[i] + ";");
		}
		System.out.println("");
		
		//ArrayList<Neighbour> neighbours3= classif.getNeighborsWordAlarm(100, 44100, 13, audio,7);
		//ca marche!
		
		
		int resultat1 = classif.classificationWordAlarm(512, 44100, 13,audio ,3);
		System.out.println("classe (word or alarm) is: " + resultat1);
		//ça marche pour un mot de la bdd pour k=1,2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15
		//ça marche pour une alarme de la bdd pour k=1, 2 et 4 (bof egalite), 3, pas pour 5
		
		
		//int resultat2 = classif.classificationWord(512, 44100, 13,audio ,7);
		//System.out.println("classe is: " + resultat2);
		
		//ça marche !! pour k=1, 2 (egalite, bof), 8 (egalite, bof), après les resultats sont completement aleatoires
		*/
		
		/*
		//test KNN classification k=2
		MachineLearning knn = new MachineLearning();
		String resultat = knn.classification(512, 44100, audio,1);
		System.out.println("resultat machine learning : "+ resultat);
		//ça fonctionne
		*/
		
		
		
		
		//test des methodes de AttackDetection
		
		AttackDetection attaque = new AttackDetection();
		
		//IntensityTable halfwaveTest = attaque.halfWaveRectify(audio.getIntensityTable()); //ok ca marche
		//IntensityTable differencierTest = attaque.differencier(audio.getIntensityTable());//ça a calcule, on sait pas trop si cest bon
		//IntensityTable redressementTest = attaque.redressementEnveloppe(audio.getIntensityTable());
		
		//ArrayList<Double> tableauIntensiteAl1 = attaque.readArrayInTxt("C:/Users/Kim-Anh-Nhi/Desktop/BDD/DetectionAttaqueTest.txt");
		//ca marche
		
		//fourierLowPassFilter(IntensityTable data, double lowPass, double frequency)	
		
		long endTime = System.nanoTime();
		System.out.println("Took "+(endTime - startTime) + " ns"); 


	}

}
