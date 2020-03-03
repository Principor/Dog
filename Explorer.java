import lejos.robotics.SampleProvider;
import lejos.robotics.navigation.MovePilot;
import lejos.robotics.subsumption.Arbitrator;
import lejos.robotics.subsumption.Behavior;

public class Explorer implements Behavior{

	Arbitrator ab;
	SampleProvider distance;
	MovePilot pilot;
	
	public Explorer(SampleProvider distance, MovePilot pilot) {
		this.distance = distance;
		this.pilot = pilot;
		ab = new Arbitrator (new Behavior[]{new MoveForward(pilot), new BackUp(pilot, distance)});
	}
	
	public boolean takeControl() {
		return false;
	}

	public void action() {
		ab.go();
	}

	public void suppress() {
		ab.stop();
	}

	
	
}
