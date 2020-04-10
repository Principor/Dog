import lejos.robotics.navigation.MovePilot;
import lejos.robotics.subsumption.Behavior;

public class MoveForward implements Behavior {

	private MovePilot pilot;
	private BehaviourSetTracker behaviourSet;
	
	public MoveForward(MovePilot pilot, BehaviourSetTracker behaviourSet) {
		this.pilot = pilot;
		this.behaviourSet = behaviourSet;
	}
	
	public boolean takeControl() {
		return behaviourSet.getBehvaiourSet() == BehaviourSet.EXPLORING;
	}

	public void action() {
		if(!pilot.isMoving())
			pilot.forward();
	}

	public void suppress() {
	}

	
	
}
