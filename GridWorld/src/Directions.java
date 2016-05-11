
public class Directions {
	private int direction;
	private double probability;
	private int nextposition;
	private boolean overshoot;
	
	public Directions(int direction, double probability, int nextposition, boolean overshoot) {
		super();
		this.direction = direction;
		this.probability = probability;
		this.nextposition = nextposition;
		this.overshoot = overshoot;
	}
	public boolean isOvershoot() {
		return overshoot;
	}
	public void setOvershoot(boolean overshoot) {
		this.overshoot = overshoot;
	}
	public int getDirection() {
		return direction;
	}
	public void setDirection(int direction) {
		this.direction = direction;
	}
	public double getProbability() {
		return probability;
	}
	public void setProbability(double probability) {
		this.probability = probability;
	}
	public int getNextposition() {
		return nextposition;
	}
	public void setNextposition(int nextposition) {
		this.nextposition = nextposition;
	}

	
	public Directions() {
		super();
	}
	
	public Directions(int direction) {
		super();
		this.direction = direction;
	}                             
	
	public Directions(int direction, double probability) {
		super();
		this.direction = direction;
		this.probability = probability;
	}
	@Override
	public String toString() {
		String d = "";
		switch (direction) {
		case 1:
			if (overshoot) {
				d = "UU";
			} else {
				d = "U";
			}
			break;
		case 2:
			if (overshoot) {
				d = "DD";
			} else {
				d = "D";
			}
			break;
		case 3:
			if (overshoot) {
				d = "LL";
			} else {
				d = "L";
			}
			break;
		case 4:
			if (overshoot) {
				d = "RR";
			} else {
				d = "R";
			}
			break;
		case 5:
			d = "N";
			break;
		}
		
		/*return "Direction [direction =" + d + ", probability =" + probability + ", nextposition =" + nextposition
				+ "]";*/
		return d;
		
	}
}
