import lejos.hardware.Button;
import lejos.hardware.motor.BaseRegulatedMotor;
import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.port.MotorPort;
import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.EV3UltrasonicSensor;
import lejos.robotics.SampleProvider;
import lejos.robotics.chassis.Chassis;
import lejos.robotics.chassis.Wheel;
import lejos.robotics.chassis.WheeledChassis;
import lejos.robotics.navigation.MovePilot;
import lejos.robotics.subsumption.Arbitrator;
import lejos.robotics.subsumption.Behavior;

public class Dog {

	final static float WHEEL_DIAMETER = 56;
	final static float AXLE_LENGTH = 190;
	final static float ANGULAR_SPEED = 100;
	final static float SPEED = 50;
	
	public static void main(String[] args) {
		
		
		EV3UltrasonicSensor us = new EV3UltrasonicSensor(SensorPort.S1);
		SampleProvider distance = (SampleProvider) us.getDistanceMode();
		
//		EV3ColorSensor cs = new EV3ColorSensor(SensorPort.S2);
//		SensorMode colour = cs.getAmbientMode(); 
//		
//		NXTSoundSensor ss = new NXTSoundSensor(SensorPort.S3);
//		SampleProvider sound = ss.getDBAMode();
		
		BaseRegulatedMotor mLeft = new EV3LargeRegulatedMotor(MotorPort.A);
		Wheel wLeft = WheeledChassis.modelWheel(mLeft, WHEEL_DIAMETER).offset(-AXLE_LENGTH / 2);
		BaseRegulatedMotor mRight = new EV3LargeRegulatedMotor(MotorPort.B);
		Wheel wRight = WheeledChassis.modelWheel(mRight, WHEEL_DIAMETER).offset(AXLE_LENGTH / 2);
		Chassis chassis = new WheeledChassis(new Wheel[] { wRight, wLeft}, WheeledChassis.TYPE_DIFFERENTIAL);
		MovePilot pilot = new MovePilot(chassis);
		pilot.setLinearSpeed(SPEED);
		
		Arbitrator ab = new Arbitrator(new Behavior[] {new Explorer(distance, pilot), new Follower(distance)});
		
		Button.ENTER.waitForPressAndRelease();
		
		ab.go();
		
		us.close();
//		cs.close();
//		ss.close();
	}
}

