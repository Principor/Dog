import lejos.hardware.Sound;
import lejos.robotics.subsumption.Behavior;

public class Beep implements Behavior{

	BehaviourSetTracker behaviourSet;
	
	public Beep(BehaviourSetTracker behaviourSet) {
		this.behaviourSet = behaviourSet;
	}
	
	public boolean takeControl() {
		return behaviourSet.getBehvaiourSet() == BehaviourSet.BEEPING;
	}
	
	public void action() {
		Sound.beepSequence();
		behaviourSet.returnToPreviousBehaviourSet();
	}
	
	public void suppress() {
		//Do nothing
	}
}
