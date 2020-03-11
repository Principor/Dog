import lejos.hardware.Button;
import lejos.hardware.motor.BaseRegulatedMotor;
import lejos.robotics.RegulatedMotor;
import lejos.robotics.subsumption.Behavior;

public class Fetcher implements Behavior {

	BaseRegulatedMotor mLeft, mRight;
	
    final static int BASE_SPEED = 300;
	
	public Fetcher(BaseRegulatedMotor  mLeft, BaseRegulatedMotor mRight) {
		this.mLeft = mLeft;
		this.mRight = mRight;
	}
	
	public boolean takeControl() {
		return Button.RIGHT.isDown();
	}

	public void action() {
		mLeft.synchronizeWith(new RegulatedMotor[]{mRight});

	}

	public void suppress() {

	}

}
