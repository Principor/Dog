import java.io.DataInputStream;
import java.io.IOException;

import lejos.hardware.Button;
import lejos.hardware.lcd.LCD;
import lejos.hardware.motor.BaseRegulatedMotor;
import lejos.robotics.RegulatedMotor;
import lejos.robotics.navigation.MovePilot;
import lejos.robotics.subsumption.Behavior;

public class Fetcher implements Behavior {

	BaseRegulatedMotor mLeft, mRight;
	DataInputStream in;
	
	MovePilot pilot;
	
    final static int BASE_SPEED = 300;
	
	public Fetcher(BaseRegulatedMotor  mLeft, BaseRegulatedMotor mRight, DataInputStream in, MovePilot pilot) {
		this.mLeft = mLeft;
		this.mRight = mRight;
		this.in = in;
		this.pilot = pilot;
	}
	
	public boolean takeControl() {
		return Button.RIGHT.isDown();
	}

	public float toFloat(String string) {
		String newString = "";
		for (int i = 0; i < string.length(); i += 2)
		{
			newString += string.charAt(i);
		}
		LCD.drawString(string, 0, 1);
		LCD.drawString(newString, 0, 2);
		Button.ENTER.waitForPressAndRelease();
		return 0;
	}
	
	public void action() {
		mLeft.synchronizeWith(new RegulatedMotor[]{mRight});
		boolean finished = false;
		mLeft.setSpeed(100);
		mRight.setSpeed(100);
		while(!finished) {
			try {
				String input = in.readUTF().replaceAll(";", "");
				float x, y;
				if(input.equals("NULL")) {
					x = 0.5f;
					y = 0.5f;
				}else {
					String[] directions = input.split(",");
					x = Float.parseFloat(directions[0]);
					y = Float.parseFloat(directions[1]);
				}
				
				float xthresehold = 0.05f;
				float ythresehold = 0f;
				
				if (x > xthresehold) {
					mLeft.backward();
					mRight.forward();
				}else if(x < -xthresehold) {
					mLeft.forward();
					mRight.backward();
				}else {
					int distance = 20;
					mLeft.stop();
					mRight.stop();
					LCD.drawString(Float.toString(y), 0, 1);
					int startTacho = mLeft.getTachoCount();
					mLeft.forward();
					mRight.forward();
					while(y < ythresehold) {
						//keep moving forward
					}
					mLeft.stop();
					mRight.stop();
					int tachoDiff = mLeft.getTachoCount() - startTacho;
					pilot.rotate(-90);
					pilot.arc(distance, 180);
					pilot.rotate(90);
					pilot.travel(distance * 2);
					mLeft.rotate(tachoDiff);
					mRight.rotate(tachoDiff);
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
