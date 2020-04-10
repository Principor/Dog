public class DirectionTracker extends Thread {

	private float x, y;

	public void updateDirections(float x, float y) {
		this.x = x;
		this.y = y;
	}

	
	public float getX() {
		return x;
	}

	public float getY() {
		return y;
	}
}
