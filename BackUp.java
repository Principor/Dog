import java.util.Random;

import lejos.hardware.Sound;
import lejos.robotics.SampleProvider;
import lejos.robotics.navigation.MovePilot;
import lejos.robotics.subsumption.Behavior;

public class BackUp implements Behavior{

	MovePilot pilot;
	SampleProvider distance;
	float[] level;
	Random random;
	
	public BackUp(MovePilot pilot, SampleProvider distance) {
		this.pilot = pilot;
		this.distance = distance;
		random = new Random();
		level = new float[1];
	}
	
	public boolean takeControl() {
		distance.fetchSample(level, 0);
		return level[0] < 0.5;
	}

	public void action() {
		Sound.beep();
		pilot.stop();
		int rotateAmount = (random.nextInt(90) + 90) * (random.nextBoolean() ? -1 : 1);
		pilot.rotate(rotateAmount);
	}

	public void suppress() {
	}

	
	
}
