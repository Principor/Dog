import lejos.robotics.SampleProvider;
import lejos.robotics.subsumption.Behavior;

public class Follow implements Behavior{


	SampleProvider distance;
	
	public Follow(SampleProvider distance) {
		this.distance = distance;
	}

	public boolean takeControl() {
		return false;
	}

	public void action() {
		
	}

	public void suppress() {
		
	}
	
	
}
