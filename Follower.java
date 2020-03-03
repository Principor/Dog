import lejos.robotics.SampleProvider;
import lejos.robotics.subsumption.Arbitrator;
import lejos.robotics.subsumption.Behavior;

public class Follower implements Behavior{

	Arbitrator ab;
	SampleProvider distance;
	
	public Follower(SampleProvider distance) {
		this.distance = distance;
		ab = new Arbitrator(new Behavior[] {new Follow(distance)});
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
