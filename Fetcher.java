import java.io.DataInputStream;
import java.io.IOException;

import lejos.hardware.Button;
import lejos.hardware.motor.BaseRegulatedMotor;
import lejos.robotics.RegulatedMotor;
import lejos.robotics.subsumption.Behavior;

public class Fetcher implements Behavior {

	BaseRegulatedMotor mLeft, mRight;
	DataInputStream in;
	
    final static int BASE_SPEED = 300;
	
	public Fetcher(BaseRegulatedMotor  mLeft, BaseRegulatedMotor mRight, DataInputStream in) {
		this.mLeft = mLeft;
		this.mRight = mRight;
		this.in = in;
	}
	
	public boolean takeControl() {
		return true;
	}

	public void action() {
		mLeft.synchronizeWith(new RegulatedMotor[]{mRight});
		for(;;) {
			try {
				double x = in.readDouble();
				int speed = (int) Math.abs(x) * 5;
				mLeft.setSpeed(speed);
				mRight.setSpeed(speed);
				if (x > 0) {
					mLeft.forward();
					mRight.backward();
				}else if(x < 0) {
					mLeft.backward();
					mRight.forward();
				}
			} catch (IOException e) {
				mLeft.stop();
				mRight.stop();
			}
		}

	}

	public void suppress() {

	}

}
