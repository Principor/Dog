import java.util.Random;

import lejos.hardware.lcd.LCD;
import lejos.robotics.navigation.MovePilot;
import lejos.robotics.subsumption.Behavior;

public class Spin implements Behavior{
	
	private MovePilot pilot;
	private Random random;
	private BehaviourSetTracker behaviourSet;
	
	public Spin(MovePilot pilot, BehaviourSetTracker behaviourSet) {
		this.pilot = pilot;
		this.behaviourSet = behaviourSet;
		random = new Random();
	}
	
	public boolean takeControl(){
		return behaviourSet.getBehvaiourSet() == BehaviourSet.SPINNING;
		
	}

	public void action() {
		LCD.clear();
		LCD.drawString("Spinning", 0, 0);
		pilot.rotate(360 * (random.nextBoolean() ? -1 : 1));
		behaviourSet.returnToPreviousBehaviourSet();
	}

	public void suppress() {
		//Do nothing
	}
}