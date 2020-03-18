import lejos.hardware.Button;
import lejos.hardware.motor.BaseRegulatedMotor;
import lejos.robotics.SampleProvider;
import lejos.robotics.subsumption.Behavior;

public class Follow implements Behavior{


	SampleProvider distance;
	float[] level;
	BaseRegulatedMotor mLeft, mRight;
	
	public Follow(BaseRegulatedMotor mLeft, BaseRegulatedMotor mRight, SampleProvider distance) {
		this.distance = distance;
		this.mLeft = mLeft;
		this.mRight = mRight;
		level = new float[1];
	}

	public boolean takeControl() {
		return Button.DOWN.isDown();
	}

	public void action() {
		distance.fetchSample(level, 0);
		if (level[0] < 0.2f) {
			mLeft.stop();
			mRight.stop();
		}else if(level[0] < 0.6f) {
			mLeft.setSpeed(100);
			mRight.setSpeed(50);
			mLeft.forward();
			mRight.forward();
		}else {
			mLeft.setSpeed(50);
			mRight.setSpeed(100);
			mLeft.forward();
			mRight.forward();
		}
	}

	public void suppress() {
		
	}
	
	
}
