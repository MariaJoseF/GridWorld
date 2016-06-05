
public class Policy {
	private int state;
	private int direction;

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public int getDirection() {
		return direction;
	}

	public void setDirection(int direction) {
		this.direction = direction;
	}

	public Policy(int state, int direction) {
		super();
		this.state = state;
		this.direction = direction;
	}

	@Override
	public String toString() {
		String d = "";
		switch (direction) {
		case 1:
			d = "U";
			break;
		case 2:
			d = "D";
			break;
		case 3:
			d = "L";
			break;
		case 4:
			d = "R";
			break;
		case 5:
			d = "[N]";
			break;
		case -1:
			d = "[]";
			break;

		case -100:
			d = "[-100]";
			break;

		case 10:
			d = "[10]";
			break;
		}
		return "" + d;
	}
}
