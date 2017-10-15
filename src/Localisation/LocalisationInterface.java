package Localisation;

import IntegrationTest.AudioInterface;

public interface LocalisationInterface {
	
	public AudioInterface acquireLocalisation();
	
	public void saveLocalisation(String fileName, String text);
	
}
