package MachineLearning;
import java.io.*;

import java.util.ArrayList;
import java.util.Collections;

import IntegrationTest.Audio;
import IntegrationTest.AudioInterface;
import IntegrationTest.IntensityTable;
import WavFile.WavFile ;


public class MachineLearningMethods {
	private final int nombreDeSonsDansBDD = 83;
	
	//a rajouter une fois que l'on aura teste plusieurs distances max
	
	//private final double distanceMaxBetweenNeighbours;
	//private final double distanceMaxBetweenWordNeighbours;
	
	
	/*public static final ArrayList<ArrayList< double >> var = ... ca ce sera la base de donnees*/
	
	/**
	 * *lit le fichier dont le nom est fileName (la BaseDeDonnees des MFCC des sons de la BDD) et
	 * *renvoie sous forme de double[] les coefficients du ie Mfcc
	 * @param i - le numero du son desire (numero de ligne dans le fichier .txt)
	 * @return la classe du son
	 */
	public int readLineLabel(int i)throws IOException {
		BufferedReader readerWithBuffer = null;
		String line;
		int resultat=0;
		try {
			readerWithBuffer = new BufferedReader(new FileReader("DataBase/labelsFile.txt"));
			int lineNumber=0;
			
			while((line=readerWithBuffer.readLine()) != null && lineNumber <i) {
				int x=0;
				String currentDouble = new String();
				while(x<line.length()){
					
					switch (line.charAt(x)){
					
					case ')': //on a atteint la fin
						resultat= (int) Double.parseDouble(currentDouble);
						x++;break;
					case '(': x++;break;
					case ' ': x++;break;
					case ','://on a atteint la fin d'un nombre/coefficient
						resultat= (int) Double.parseDouble(currentDouble);
						currentDouble = new String();//remettre a vide
						x++;break;
					default: 
						currentDouble+= Character.toString(line.charAt(x));
						x++;break;						
					}
				}
				lineNumber++;
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
		return resultat;	
	}
	

	
	
	
	
	
	/**
	 * *lit le fichier dont le nom est fileName (la BaseDeDonnees des MFCC des sons de la BDD) et
	 * *renvoie sous forme de double[][] les coefficients du ie Mfcc
	 * @param i - le numero du son/vecteur Mfcc desire
	 * @param numberOfLines - le nombre de lignes dans la matrice de MFCC (ici on decide 36)
	 * @return le vecteur Mfcc n.i 
	 */
	public double[][] readMfcc(int i, int numberOfLines) throws IOException {
		BufferedReader readerWithBuffer = null;
		String line;
		double[] currentLine = new double[13];
		double[][] matriceMfcc = new double[numberOfLines][13];
		
		try {
			readerWithBuffer = new BufferedReader(new FileReader("DataBase/MFCCFile.txt"));
			int compteurMatrice=0;
			int compteurLigneMatrice=0;
			
			while((line=readerWithBuffer.readLine()) != null && compteurMatrice <i) {
				int compteurCaractere=0;
				int compteurDouble=0; //indice du double considere dans la ligne considere
				String currentDouble = new String();
				char caracterePrecedent ='a';
				
				while(compteurCaractere<line.length()){
					char caractere =line.charAt(compteurCaractere);
				
					if (caractere == ' ') {
						//ajouter double a currentLine
						currentLine[compteurDouble] = Double.parseDouble(currentDouble);currentDouble = new String();//remettre a vide
						compteurDouble ++;
					}
					if (caractere != ' ' && caractere != '(' && caractere != ')'&&caractere != ','){
						currentDouble+= Character.toString(caractere);
					}
					
					if (caractere == ')'){
						char caractereSuivant = line.charAt(compteurCaractere+1);
						if (caracterePrecedent != ')'&& caractereSuivant == ','){//c'est la fin d'une ligne
							currentLine[compteurDouble] = Double.parseDouble(currentDouble);compteurLigneMatrice ++;
							currentDouble = new String();//remettre a vide
							
							if(compteurLigneMatrice<numberOfLines){//on n'est pas a la derniere ligne de la matrice encore
								matriceMfcc = this.copyLineInMatrix(matriceMfcc,currentLine,compteurLigneMatrice-1);
							}
							if (compteurLigneMatrice == numberOfLines){//fin matrice "voulue"
								matriceMfcc = this.copyLineInMatrix(matriceMfcc,currentLine,compteurLigneMatrice-1); compteurMatrice ++;
								if(compteurMatrice == i-1)
									break;
							}
						}
						
						if (caractereSuivant == ')' && caracterePrecedent != ')'){//fin d'une matrice reelle
							currentLine[compteurDouble] = Double.parseDouble(currentDouble);compteurLigneMatrice =0;
							currentDouble = new String();//remettre a vide
							 if (compteurLigneMatrice == numberOfLines){
								 matriceMfcc = this.copyLineInMatrix(matriceMfcc,currentLine,compteurLigneMatrice-1); compteurMatrice ++;
							 }
							 if (compteurLigneMatrice == numberOfLines)
								 System.out.println("error, fewer lines in mfcc that expected");
						}
					}
					caracterePrecedent=caractere;
					compteurCaractere++;
				} 
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
		return matriceMfcc;	
	}	
	
	
	
	

	
	public double euclideanDistance(double[][] mfcc1, double[][] mfcc2){
		double distance = 0;
		int nLignes = mfcc1.length; int nColonnes = mfcc1[0].length;
		
		for (int i=0; i<nLignes;i++){
			for (int j=0 ; j<nColonnes ; j++){
				distance += Math.pow((mfcc1[i][j] -mfcc2[i][j]), 2);
			}
		} 
		return (Math.sqrt(distance));		
	}
	
	
	public double euclideanDistance2(double[][] mfcc1, double[][] mfcc2){
		double distance = 0;
		double maxi =0;
		double[] distances = new double[13]; //coefficient i = distnace entre tous les coeff de la colonne i dans chaque mfcc
		int nLignes = mfcc1.length; 
		System.out.println("nLignes : " + nLignes);
		int nColonnes = mfcc1[0].length;
		System.out.println("nColonnes : " + nColonnes);
		
		for (int colonne=0; colonne<nColonnes;colonne++){
			for (int ligne=0 ; ligne<nLignes ; ligne++){
				distance += (mfcc1[ligne][colonne] -mfcc2[ligne][colonne]);
			}
			distances[colonne] = distance;
			if (distance > maxi){
				maxi = distance;
			}
			distance = 0; 
		} 
		return (maxi);		
	}
	
	
	
	public double[] transformInDoubleList(ArrayList<Double> signal1){
		double[] signal = new double[signal1.size()];
		for (int i=0;i<signal1.size();i++){
			signal[i]=signal1.get(i);
		}
		return signal;
	}
	
	//Voisins
	//retourne les k plus proches sons voisins, par rapport a la distance euclidienne definie
//renvoie 1 si c'est un mot, -1 si c'est une alarme
	public ArrayList<Neighbour> getNeighborsWordAlarm(int sampleRate, int numCoefficients, AudioInterface audio ,int k) throws IOException{	
		double distance=0;
		ArrayList<Neighbour> neighbours = new ArrayList<Neighbour>();
		Mfcc mfccSoundTest = new Mfcc();
		
		//ReadWavFile read = new ReadWavFile(); old version
		//double[] soundVector  = read.read(soundTestWavFile);
		IntensityTable intensityArray = audio.getIntensityTable();
		double[] soundVector = this.transformInDoubleList(intensityArray);//
		double[][] mfccSoundTestVector = mfccSoundTest.performMFCC(soundVector, sampleRate);
		
		
		for (int i=0; i< nombreDeSonsDansBDD;i++){
			double [][] mfcc = this.readMfcc(i,36);
			
			distance = euclideanDistance2(mfcc,mfccSoundTestVector);
			
			/*
			 * pour quand on rajoutera l'attribut distanceMaxBetweenNeighbours
			if (distance<distanceMaxBetweenNeighbours)
				neighbours.add(new Neighbour(i,distance));
				*/
			//pour le moment on prend toutes les distances
			neighbours.add(new Neighbour(i,distance));
			}
		System.out.println("neighbours not sorted nor selected is:");
		for (int i=0; i< neighbours.size();i++){
			System.out.print("(" + neighbours.get(i).getNumber() + "|" + neighbours.get(i).getDistance() + ") ; ");
		}
		System.out.println("");
		Collections.sort(neighbours, new Neighbour());
		System.out.println("neighbours sorted but not selected is:");
		for (int i=0; i< neighbours.size();i++){
			System.out.print("(" + neighbours.get(i).getNumber() + "|" + neighbours.get(i).getDistance() + ") ; ");
		}
		System.out.println("");
		
		ArrayList<Neighbour> neighboursReal = new ArrayList<Neighbour>();
		for (int i=0; i< k;i++){
			neighboursReal.add(neighbours.get(i));
		}
		System.out.println("neighbours sorted and selected is:");
		for (int i=0; i< neighboursReal.size();i++){
			System.out.print("(" + neighboursReal.get(i).getNumber() + "|" + neighboursReal.get(i).getDistance() + ") ; ");
		}
		System.out.println("");
		return (neighboursReal);
		}
	

	
	//obtenir la classe majoritaire parmi tous les voisins
	public int classificationWordAlarm(int sampleRate, int numCoefficients, AudioInterface audio,int k) throws IOException{
		ArrayList<Neighbour> neighbours = getNeighborsWordAlarm(sampleRate, numCoefficients, audio, k);
		int nbMot =0; int nbAlarme =0;//nbMot = nombre de voisins de la classe Mot (classe -1)
		for (int i=0; i< neighbours.size();i++){
			int label = this.readLineLabel(neighbours.get(i).getNumber());
			System.out.println("label of sound is:" + label);
			//verifier si labels[0] est un double ou un int !!
			if (label == 2 || label == 3 || label == 4 || label== 5 || label== 6)
				nbMot ++;
			if (label == -1)
				nbAlarme++;
			}
		System.out.println("nbAlarme is: " + nbAlarme);
		System.out.println("nbMot is: " + nbMot);
		//double result = Math.random(); (test)
		if (nbAlarme<nbMot){
			return 1;
		}else
			return -1;
	
		}
	
	
	
	
	
	//Voisins si c'est un mot
	//retourne les k plus proches sons voisins, par rapport a la distance euclid definie
	
	public ArrayList<Neighbour> getNeighborsWords(int sampleRate, int numCoefficients, AudioInterface audio,int k) throws IOException{	
		double distance=0;
		ArrayList<Neighbour> neighbours = new ArrayList<Neighbour>();
		Mfcc mfccSoundTest = new Mfcc();
		
		//ReadWavFile read = new ReadWavFile();
		//double[] soundVector  = read.read(soundTestWavFile);
		IntensityTable intensityArray = audio.getIntensityTable();
		double[] soundVector = this.transformInDoubleList(intensityArray);
		double[][] mfccSoundTestVector = mfccSoundTest.performMFCC(soundVector, sampleRate);
		
		
		for (int i=0; i< nombreDeSonsDansBDD;i++){
			double [][] mfcc = this.readMfcc(i,36);
			int label = this.readLineLabel(i);
			
			if (label != -1)
			{
				distance = euclideanDistance2(mfcc,mfccSoundTestVector);
				
				/*
				if (distance<distanceMaxBetweenWordNeighbours)
					neighbours.add(new Neighbour(i,distance));
					*/
				
				//pour l'instant on prend toutes les distances
				neighbours.add(new Neighbour(i,distance));
			}	
		}
		
		System.out.println("neighbours not sorted nor selected is:");
		for (int i=0; i< neighbours.size();i++){
			System.out.print("(" + neighbours.get(i).getNumber() + "|" + neighbours.get(i).getDistance() + ") ; ");
		}
		System.out.println("");
		Collections.sort(neighbours, new Neighbour());
		System.out.println("neighbours sorted but not selected is:");
		for (int i=0; i< neighbours.size();i++){
			System.out.print("(" + neighbours.get(i).getNumber() + "|" + neighbours.get(i).getDistance() + ") ; ");
		}
		System.out.println("");
		
		ArrayList<Neighbour> neighboursReal = new ArrayList<Neighbour>();
		for (int i=0; i< k;i++){
			neighboursReal.add(neighbours.get(i));
		}
		System.out.println("neighbours sorted and selected is:");
		for (int i=0; i< neighboursReal.size();i++){
			System.out.print("(" + neighboursReal.get(i).getNumber() + "|" + neighboursReal.get(i).getDistance() + ") ; ");
		}
		System.out.println("");
		return (neighboursReal);
		}
	
	
	
	//obtenir la classe majoritaire parmi tous les voisins
	//renvoie 2 si c'est "Bonjour", 3 si c'est "Madame", 4 si c'est "Monsieur"; 5 si c'est "Attention", 6 si c'est "Marc"
	public int classificationWord(int sampleRate, int numCoefficients, AudioInterface audio,int k) throws IOException{
		ArrayList<Neighbour> neighbours = getNeighborsWords(sampleRate, numCoefficients, audio, k);
		int nb2 =0; int nb3 =0;
		int nb4 =0;int nb5 =0;int nb6 =0;
		for (int i=0; i< neighbours.size();i++){
			int label = this.readLineLabel(neighbours.get(i).getNumber());
			
			switch (label) {
			case 2:	nb2++; break;
			case 3: nb3++;break;
			case 4: nb4++;break;
			case 5: nb5++;break;
			case 6: nb6++;break;
			}
		}
		System.out.println("nb2 is: " + nb2);
		System.out.println("nb3 is: " + nb3);
		System.out.println("nb4 is: " + nb4);
		System.out.println("nb5 is: " + nb5);
		System.out.println("nb6 is: " + nb6);
		
		int[] numberClasses = new int[5];
		numberClasses[0]=nb2;
		numberClasses[1]=nb3;
		numberClasses[2]=nb4;
		numberClasses[3]=nb5;
		numberClasses[4]=nb6;
		int max = 0; int indiceMax=0;
		
		for (int i=0;i<5;i++){
			if (numberClasses[i]>max){
				max=numberClasses[i];
				indiceMax=i;
			}	
		}
		//test:	double result = Math.random();
		/*
		 test
		 if (result<0.2){
		 
			return 2;
		}
			
		if (0.2<result && result <0.4){
			return 3;
		}
		if (0.4<result && result <0.6){
			return 4;
		}
		if (0.6<result && result <0.8){
			return 5;
		}
		if (0.8<result && result <=1){
			return 6;
		}
		*/
		return(indiceMax+2);
	}
	
	
	
	public double[][] copyLineInMatrix(double[][] matrix, double[] line, int lineNumber){
		for (int j=0;j<matrix[0].length;j++){
			matrix[lineNumber][j] = line[j];
		}
		return(matrix);
	}
}
