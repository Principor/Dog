import java.util.Random;

import lejos.hardware.lcd.LCD;
import lejos.robotics.SampleProvider;
import lejos.robotics.navigation.MovePilot;
import lejos.robotics.subsumption.Behavior;

public class Spin implements Behavior{
	private MovePilot pilot;
	private SampleProvider sound;
	private float[] level = new float[1];
	Random random;
	
	public Spin(MovePilot pilot, SampleProvider sound) {
		this.pilot = pilot;
		this.sound = sound;
	}
	
	public boolean takeControl(){
		sound.fetchSample(level,0);
		return level[0] > 0.5;
		
	}

	public void action() {
		LCD.drawString("Do duh spin", 1, 1);
		pilot.rotate(360 * (random.nextBoolean() ? -1 : 1));
	}

	public void suppress() {
		
	}
}