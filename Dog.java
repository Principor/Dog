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
import lejos.hardware.sensor.NXTSoundSensor;
import lejos.robotics.SampleProvider;
import lejos.robotics.chassis.Chassis;
import lejos.robotics.chassis.Wheel;
import lejos.robotics.chassis.WheeledChassis;
import lejos.robotics.navigation.MovePilot;
import lejos.robotics.subsumption.Arbitrator;
import lejos.robotics.subsumption.Behavior;

public class Dog {

    static private DataInputStream in;
	static Socket socket;

    final static String IPPrefix = "10.0.1.";
    
	final static float WHEEL_DIAMETER = 56;
	final static float AXLE_LENGTH = 190;
	final static float ANGULAR_SPEED = 100;
	final static float SPEED = 50;
	
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
		while(connecting) {
			LCD.clear();
			LCD.drawString("Set IP:", 0, 0);
			LCD.drawString(IPPrefix + IPSuffix, 0, 1);
			int press = Button.waitForAnyPress();
			if(press == Button.ID_UP) {
				IPSuffix = Math.min(IPSuffix + 1, 99);
			}else if(press == Button.ID_DOWN) {
				IPSuffix = Math.max(IPSuffix - 1, 0);
			}else if(press == Button.ID_ENTER) {
				connecting = !connect(IPPrefix + IPSuffix);
			}
		}
	}
	
    static boolean connect (String IP) {
    	LCD.clear();
        try {
        	LCD.drawString("Connecting...", 0, 0);
            socket = new Socket(IP, 1234);
            in = new DataInputStream(socket.getInputStream());
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
        	LCD.drawString("Disconnected", 0, 0);
            return true;
        } catch (IOException e) {
            LCD.drawString("E:" + e.getMessage(), 0, 0);
            Button.waitForAnyPress();
        }        
        return false;
    }
    
	public static void main(String[] args) {
		
		startScreen();
		
		startConnection();
		
		EV3UltrasonicSensor us = new EV3UltrasonicSensor(SensorPort.S2);
		SampleProvider distance = (SampleProvider) us.getDistanceMode();
		
		NXTSoundSensor ss = new NXTSoundSensor(SensorPort.S1);
		SampleProvider sound = ss.getDBAMode();
		
		BaseRegulatedMotor mLeft = new EV3LargeRegulatedMotor(MotorPort.A);
		Wheel wLeft = WheeledChassis.modelWheel(mLeft, WHEEL_DIAMETER).offset(-AXLE_LENGTH / 2);
		BaseRegulatedMotor mRight = new EV3LargeRegulatedMotor(MotorPort.B);
		Wheel wRight = WheeledChassis.modelWheel(mRight, WHEEL_DIAMETER).offset(AXLE_LENGTH / 2);
		Chassis chassis = new WheeledChassis(new Wheel[] { wRight, wLeft}, WheeledChassis.TYPE_DIFFERENTIAL);
		MovePilot pilot = new MovePilot(chassis);
		pilot.setLinearSpeed(SPEED);
		
		Arbitrator ab = new Arbitrator(new Behavior[] {new Explorer(distance, sound, pilot), new Stay(pilot), new Follow(mLeft, mRight, distance), new Fetcher(mLeft, mRight, in, pilot)});
		LCD.clear();
		LCD.drawString("Press Enter to start", 0, 0);
		Button.ENTER.waitForPressAndRelease();
		LCD.clear();
		
		ab.go();
		
		us.close();
		
		disconnect();
	}
}

