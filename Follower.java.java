import lejos.hardware.motor.BaseRegulatedMotor;
import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.NXTColorSensor;
import lejos.hardware.sensor.SensorMode;
import lejos.robotics.SampleProvider;
import lejos.robotics.subsumption.Behavior;

public class follower implements Behavior {

	float[] level = new float[1];
    NXTColorSensor col = new NXTColorSensor(SensorPort.S3);
    SensorMode color = col.getRedMode();
    BaseRegulatedMotor mLeft;
    BaseRegulatedMotor mRight;
    float currentLevel;
    private static final int FAST_SPEED = 50; //put these in dog.java
    private static final int SLOW_SPEED = 25; //put these in dog.java
		
    public follower(SampleProvider distance, BaseRegulatedMotor mLeft, BaseRegulatedMotor mRight) {


		level = new float[1];
		this.mLeft =mLeft;
		this.mRight = mRight;
		
		
	}
	
	@Override
	public boolean takeControl() {
		color.fetchSample(level, 0);
        currentLevel = level[0];
        
		return (currentLevel > 0.5f)|| currentLevel > 0.5f;
	}

	@Override
	public void action() {
		if (currentLevel > 0.5f) {
            mLeft.setSpeed(FAST_SPEED);
            mRight.setSpeed(SLOW_SPEED);
		} else {
            mLeft.setSpeed(SLOW_SPEED);
            mRight.setSpeed(FAST_SPEED);
}
		
	}

	@Override
	public void suppress() {
		// TODO Auto-generated method stub
		
	}

}
