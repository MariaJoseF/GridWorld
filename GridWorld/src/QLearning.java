public class QLearning {
	private int state;
	private Directions action;
	private double QL;
	private int next;

	public int getNext() {
		return next;
	}

	public void setNext(int next) {
		this.next = next;
	}

	public int getState() {
		return state;
	}

	public QLearning(int state, Directions action, int next, double qL) {
		super();
		this.state = state;
		this.action = action;
		QL = qL;
		this.next = next;
	}

	public void setState(int state) {
		this.state = state;
	}

	public Directions getAction() {
		return action;
	}

	public void setAction(Directions action) {
		this.action = action;
	}

	public double getQL() {
		return QL;
	}

	public void setQL(double qL) {
		QL = qL;
	}

	@Override
	public String toString() {
		return "[" + action + "] " + QL;
	}

}
