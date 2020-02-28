import lejos.hardware.motor.BaseRegulatedMotor;
import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.port.MotorPort;
import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.hardware.sensor.EV3UltrasonicSensor;
import lejos.hardware.sensor.SensorMode;
import lejos.robotics.SampleProvider;
import lejos.robotics.subsumption.Arbitrator;
import lejos.robotics.subsumption.Behavior;

public class Dog {

	public static void main(String[] args) {
		
		EV3UltrasonicSensor us = new EV3UltrasonicSensor(SensorPort.S2);
		SampleProvider distance = (SampleProvider) us.getDistanceMode();
		
		EV3ColorSensor cs = new EV3ColorSensor(SensorPort.S1);
		SensorMode color = cs.getAmbientMode(); 
		
		BaseRegulatedMotor mLeft = new EV3LargeRegulatedMotor(MotorPort.B);
		BaseRegulatedMotor mRight = new EV3LargeRegulatedMotor(MotorPort.A);
		
		
		
		Arbitrator ab = new Arbitrator (new Behavior[]{});
		ab.go();
		
		us.close();
		cs.close();
	}
	
}
