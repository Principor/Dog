import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;

import lejos.hardware.Button;
import lejos.hardware.lcd.LCD;
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

	private static Socket socket;

	private final static String IPPrefix = "10.0.1.";

	private final static float WHEEL_DIAMETER = 56;
	private final static float ANGULAR_SPEED = 30;
	private final static float SPEED = 50;
	// The wheels do not get enough grip and therefore do not turn enough
	// To counter this, the movePilot is told the axle length is larger than it
	// really is, so that it makes the wheels spin more
	private final static float COEFFICIENT = 1.1f;

	public static void startScreen() {
		LCD.drawString("Dog v1.0", 0, 0);
		LCD.drawString("Abhiraj Kane", 0, 1);
		LCD.drawString("Christopher Jewsel", 0, 2);
		LCD.drawString("Nerwin Carlos", 0, 3);
		LCD.drawString("Haris Bin Zahid", 0, 4);
		Button.waitForAnyPress();
		LCD.clear();
	}

	public static void startConnection() {
		boolean connecting = true;
		int IPSuffix = 3;
		while (connecting) {
			LCD.clear();
			LCD.drawString("Set IP:", 0, 0);
			LCD.drawString(IPPrefix + IPSuffix, 0, 1);
			int press = Button.waitForAnyPress();
			if (press == Button.ID_UP) {
				IPSuffix = Math.min(IPSuffix + 1, 99);
			} else if (press == Button.ID_DOWN) {
				IPSuffix = Math.max(IPSuffix - 1, 0);
			} else if (press == Button.ID_ENTER) {
				connecting = !connect(IPPrefix + IPSuffix);
			}
		}
	}

	static boolean connect(String IP) {
		LCD.clear();
		try {
			LCD.drawString("Connecting...", 0, 0);
			socket = new Socket(IP, 1234);
			LCD.drawString("Connected!", 0, 1);
			return true;
		} catch (IOException e) {
			LCD.drawString("E:" + e.getMessage(), 0, 1);
			Button.waitForAnyPress();
		}
		return false;
	}

	public static boolean disconnect() {
		LCD.clear();
		try {
			LCD.drawString("Disconnecting", 0, 0);
			socket.close();
			LCD.clear();
			LCD.drawString("Disconnected", 0, 0);
			return true;
		} catch (IOException e) {
			LCD.drawString("E:" + e.getMessage(), 0, 0);
			Button.waitForAnyPress();
		}
		return false;
	}

	public static void main(String[] args) throws IOException {

		EV3UltrasonicSensor us = new EV3UltrasonicSensor(SensorPort.S2);
		SampleProvider distance = (SampleProvider) us.getDistanceMode();

		BaseRegulatedMotor mLeft = new EV3LargeRegulatedMotor(MotorPort.A);
		Wheel wLeft = WheeledChassis.modelWheel(mLeft, WHEEL_DIAMETER).offset(-88 * COEFFICIENT);
		BaseRegulatedMotor mRight = new EV3LargeRegulatedMotor(MotorPort.D);
		Wheel wRight = WheeledChassis.modelWheel(mRight, WHEEL_DIAMETER).offset(24 * COEFFICIENT);
		Chassis chassis = new WheeledChassis(new Wheel[] { wRight, wLeft }, WheeledChassis.TYPE_DIFFERENTIAL);
		MovePilot pilot = new MovePilot(chassis);
		pilot.setLinearSpeed(SPEED);
		pilot.setAngularSpeed(ANGULAR_SPEED);

		mLeft.setAcceleration(600);
		mRight.setAcceleration(600);

		startScreen();
		startConnection();

		DirectionTracker direction = new DirectionTracker();
		BehaviourSetTracker behaviourSet = new BehaviourSetTracker();
		InputReader reader = new InputReader(direction, behaviourSet, new DataInputStream(socket.getInputStream()));
		reader.start();
		behaviourSet.start();
		
		Fetcher fetcher = new Fetcher(mLeft, mRight, direction, pilot, behaviourSet);
		Stay stay = new Stay(pilot, behaviourSet);
		Spin spin = new Spin(pilot, behaviourSet);
		MoveForward forward = new MoveForward(pilot, behaviourSet);
		BackUp backUp = new BackUp(pilot, behaviourSet, distance);

		Arbitrator ab = new Arbitrator(new Behavior[] { stay, fetcher, spin, forward, backUp });
		ab.go();

		LCD.clear();
		LCD.drawString("Press Enter to start", 0, 0);
		Button.ENTER.waitForPressAndRelease();
		LCD.clear();

		us.close();

		disconnect();
	}
}
