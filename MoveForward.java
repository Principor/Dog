import lejos.robotics.navigation.MovePilot;
import lejos.robotics.subsumption.Behavior;

public class MoveForward implements Behavior {

	MovePilot pilot;
	
	public MoveForward(MovePilot pilot) {
		this.pilot = pilot;
	}
	
	public boolean takeControl() {
		return true;
	}

	public void action() {
		if(!pilot.isMoving())
			pilot.forward();
	}

	public void suppress() {
	}

	
	
}
