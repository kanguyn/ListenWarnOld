package MachineLearning;

import java.util.Comparator;

public class Neighbour implements Comparator<Neighbour>{
	private int number;//number of the sound in the list
	private double distance; //distance between the neighbour i and the tested sound
	
	public Neighbour(int number,double distance){
		this.number = number;
		this.distance = distance;
	}
	
	public Neighbour() {
		
	}
	
	public int getNumber(){
		return number;
	}
	public double getDistance(){
		return distance;
	}
	
	// Overriding the compare method to sort the age 
	public int compare(Neighbour d, Neighbour d1) {
		if (d.distance - d1.distance<0) 
			return -1;
		if (d.distance - d1.distance>0) 
			return 1;
		return 0;
		}
	
	

}
