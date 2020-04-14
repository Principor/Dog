import lejos.hardware.lcd.LCD;
import lejos.hardware.motor.BaseRegulatedMotor;
import lejos.robotics.RegulatedMotor;
import lejos.robotics.navigation.MovePilot;
import lejos.robotics.subsumption.Behavior;
import lejos.utility.Delay;

public class Fetcher implements Behavior {

	private BaseRegulatedMotor mLeft, mRight;
	private DirectionTracker tracker;
	private BehaviourSetTracker behaviourSet;
	
	private MovePilot pilot;

	final static int RADIUS = 300;

	public Fetcher(BaseRegulatedMotor mLeft, BaseRegulatedMotor mRight, DirectionTracker tracker, MovePilot pilot, BehaviourSetTracker behaviourSet) {
		this.mLeft = mLeft;
		this.mRight = mRight;
		this.tracker = tracker;
		this.pilot = pilot;
		this.behaviourSet = behaviourSet;
	}

	public boolean takeControl() {
		return behaviourSet.getBehvaiourSet() == BehaviourSet.FETCHING;
	}

	public void action() {
		LCD.clear();
		LCD.drawString("Fetching", 0, 0);
		mLeft.setSpeed(10);
		mRight.setSpeed(10);
		mLeft.synchronizeWith(new RegulatedMotor[] { mRight });

		// Face Ball
		int direction;
		while((direction = getDirection()) != 0) {
			
			mLeft.startSynchronization();
			if (direction < 0) {
				mLeft.forward();
				mRight.backward();
			} else {
				mLeft.backward();
				mRight.forward();
			}
			mLeft.endSynchronization();
			
			while(getDirection() == direction) {
				//Wait
			}
			
			mLeft.startSynchronization();
			mLeft.stop();
			mRight.stop();
			mLeft.endSynchronization();
			
			Delay.msDelay(2000);
		}
		
		// Go to ball
		mLeft.resetTachoCount();
		mLeft.setSpeed(100);
		mRight.setSpeed(100);
		
		mLeft.startSynchronization();
		mLeft.forward();
		mRight.forward();
		mLeft.endSynchronization();
		
		while (tracker.getY() < 0.35) {
			Delay.msDelay(10);
		}
		
		mLeft.startSynchronization();
		mLeft.stop();
		mRight.stop();
		mLeft.endSynchronization();
		
		int tachoDiff = mLeft.getTachoCount();

		// Go around Ball
		pilot.rotate(-90);
		Delay.msDelay(1000);
		pilot.arc(300, 140);
		Delay.msDelay(1000);
		pilot.rotate(110);
		pilot.travel(RADIUS * 2);

		// Return to start
		mLeft.startSynchronization();
		mLeft.rotate(tachoDiff);
		mRight.rotate(tachoDiff);
		mLeft.endSynchronization();
		
		behaviourSet.returnToPreviousBehaviourSet();
	}

	public void suppress() {
		// Do nothing
	}

	// Since the camera is not centred the centre of the ball must be between the
	// two straight lines given by the equations:
	// y=-2.35x-0.359 and y=-3.91x-0.363
	// These points allow 20mm of variance for the centre of the ball
	private int getDirection() {
		if (tracker.getY() > ((tracker.getX() * 2.35) - 0.359)) {
			if (tracker.getY() < ((tracker.getX() * 3.91) - .363))
				return 0;
			else
				return 1;
		} else
			return -1;

	}

}
