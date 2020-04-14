import lejos.hardware.Button;

public class BehaviourSetTracker extends Thread{

	private BehaviourSet behaviourSet, previousBehaviourSet;
	private Thread t;
	
	public BehaviourSetTracker(){
		behaviourSet = previousBehaviourSet = BehaviourSet.STAYING;
	}
	
	public void run() {
		try {
			while (!isInterrupted()) {
				
				if(Button.LEFT.isDown()) {
					setBehaviourSet(BehaviourSet.EXPLORING);
				}
				if(Button.UP.isDown()) {
					setBehaviourSet(BehaviourSet.FETCHING);
				}
				if(Button.RIGHT.isDown()) {
					setBehaviourSet(BehaviourSet.SPINNING);
				}
				if(Button.DOWN.isDown()) {
					setBehaviourSet(BehaviourSet.STAYING);
				}
				if(Button.ENTER.isDown()) {
					setBehaviourSet(BehaviourSet.BEEPING);
				}
					
				Thread.sleep(100);
			}
		} catch (InterruptedException e) {
		}
	}
	
	public void start() {
		t = new Thread(this, "Behaviour Set Tracker");
		t.start();
	}

	public void setBehaviourSet(BehaviourSet behaviourSet) {
		if (behaviourSet == this.behaviourSet)
			return;
		previousBehaviourSet = this.behaviourSet;
		this.behaviourSet = behaviourSet;
	}
	
	public BehaviourSet getBehvaiourSet() {
		return behaviourSet;
	}
	
	public void returnToPreviousBehaviourSet() {
		behaviourSet = previousBehaviourSet;
	}
}
