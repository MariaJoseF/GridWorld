
public class Direction {
	private int direction;
	private float probability;
	private int nextposition;
	
	public int getDirection() {
		return direction;
	}
	public void setDirection(int direction) {
		this.direction = direction;
	}
	public float getProbability() {
		return probability;
	}
	public void setProbability(float probability) {
		this.probability = probability;
	}
	public int getNextposition() {
		return nextposition;
	}
	public void setNextposition(int nextposition) {
		this.nextposition = nextposition;
	}

	
	public Direction() {
		super();
	}
	
	public Direction(int direction) {
		super();
		this.direction = direction;
	}
	
	@Override
	public String toString() {
		return "Direction [direction=" + direction + ", probability=" + probability + ", nextposition=" + nextposition
				+ "]";
	}
}
