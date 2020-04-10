import java.io.DataInputStream;
import java.io.IOException;

public class InputReader extends Thread{

	private BehaviourSetTracker behaviourSet;
	private DirectionTracker direction;
	private DataInputStream stream;
	private Thread t;
	
	public InputReader(DirectionTracker direction, BehaviourSetTracker behaviourSet, DataInputStream stream) {
		this.behaviourSet = behaviourSet;
		this.direction = direction;
		this.stream = stream;
	}

	public void run() {

		try {
			while (!isInterrupted()) {
				try {
					String in = stream.readUTF();
					String[] args = in.split(" ");
					if(args[0].equals("DIRECTION")) {
						if (!args[1].equals("NULL")) {
							direction.updateDirections(Float.parseFloat(args[1]), Float.parseFloat(args[2]));
						} else {
							direction.updateDirections(0.5f, -0.5f);
						}
					}else {
						behaviourSet.setBehaviourSet(BehaviourSet.valueOf(args[0]));
					}
					
				} catch (IOException e) {
				}
				Thread.sleep(10);
			}
		} catch (InterruptedException e) {
		}
	}
	
	public void start() {
		t = new Thread(this, "Input Reader");
		t.start();
	}
}
