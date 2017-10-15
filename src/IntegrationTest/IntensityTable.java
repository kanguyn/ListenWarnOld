package IntegrationTest;

import java.util.ArrayList;

public class IntensityTable extends ArrayList<Double> implements IntensityTableInterface{

	public void showTable(){
		for(int i=0;i<this.size();i++){
			System.out.println(this.get(i));
		}
	}

	//atribut
	private Float sampleFreq; //frequence d'echantillonage du signal

	public IntensityTable() {
		super();
	}
	public IntensityTable(IntensityTable signal) {
		super(signal);
	}
	public IntensityTable(int length) {
		super(length);
	}

	
}
