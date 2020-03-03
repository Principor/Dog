import lejos.hardware.lcd.LCD;
import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.NXTColorSensor;
import lejos.hardware.sensor.SensorMode;
import lejos.robotics.navigation.MovePilot;
import lejos.robotics.subsumption.Behavior;

public class Spin implements Behavior{
	private MovePilot pilot;
	private NXTColorSensor col = new NXTColorSensor(SensorPort.S3);
	private SensorMode color = col.getRedMode();
	private float[] level = new float[1];
	
	@Override
	public boolean takeControl(){
		color.fetchSample(level,0);
		return level[0] > 0.5; //scale between 0-1. Closer to 1 the more red it is
		
	}
	@Override
	public void action() {
		LCD.drawString("Do duh spin", 1, 1);
		pilot.rotate(360);
	}

	@Override
	public void suppress() {
		// TODO Auto-generated method stub
		
	}
}
