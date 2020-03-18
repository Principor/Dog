import lejos.hardware.Button;
import lejos.robotics.navigation.MovePilot;
import lejos.robotics.subsumption.Behavior;

public class Stay implements Behavior {

	MovePilot pilot;
	
	public Stay(MovePilot pilot) {
		this.pilot = pilot;
	}
	public boolean takeControl() {
		return Button.UP.isDown();
	}

	public void action() {
		pilot.stop();
	}

	public void suppress() {

	}

}
