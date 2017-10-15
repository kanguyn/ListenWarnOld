package IntegrationTest;

public interface AudioInterface {
	
	public IntensityTable initFromAudioFile(String fileName);
	
	public String getDirection();
	
	public void setDirection(String direction);
	
	public void setIntensityTable(IntensityTable intensityTable);
	
	public String getFileName();

	public void setFileName(String fileName);

	public IntensityTable getIntensityTable();

}
