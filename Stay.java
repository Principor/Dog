import lejos.hardware.lcd.LCD;
import lejos.robotics.navigation.MovePilot;
import lejos.robotics.subsumption.Behavior;

public class Stay implements Behavior {

	private MovePilot pilot;
	private BehaviourSetTracker behaviourSet;
	
	public Stay(MovePilot pilot, BehaviourSetTracker behaviourSet) {
		this.pilot = pilot;
		this.behaviourSet = behaviourSet;
	}
	public boolean takeControl() {
		return behaviourSet.getBehvaiourSet() == BehaviourSet.STAYING;
	}

	public void action() {		
		LCD.clear();
		LCD.drawString("Staying", 0, 0);
		pilot.stop();
	}

	public void suppress() {
		//Do nothing
	}

}
