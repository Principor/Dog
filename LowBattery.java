import lejos.hardware.Battery;
import lejos.hardware.lcd.LCD;
import lejos.robotics.subsumption.Behavior;

public class LowBattery implements Behavior{

	public boolean takeControl() {
		return Battery.getVoltage() < 3;
	}

	public void action() {
		LCD.clear();
		LCD.drawString("Low Battery", 0, 0);
		LCD.drawString("Turning off in 5 seconds", 0, 1);
		System.exit(0);
	}

	public void suppress() {
		//Do nothing
	}

}
