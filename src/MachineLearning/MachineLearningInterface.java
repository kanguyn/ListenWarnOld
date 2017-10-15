package MachineLearning;
import java.io.IOException;

import IntegrationTest.Audio;
import IntegrationTest.AudioInterface;

public interface MachineLearningInterface {
	
	public String findSound(AudioInterface audio) throws IOException;
	public void saveSongDetected(String fileName, String text) throws IOException;

}
