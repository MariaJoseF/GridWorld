
public class QLearning {
	int state;
	Directions action;
	double QL;
	int next;
	
	public int getNext() {
		return next;
	}
	public void setNext(int next) {
		this.next = next;
	}

	public int getState() {
		return state;
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
	public QLearning(int state, Directions action, int next, double qL) {
		super();
		this.state = state;
		this.action = action;
		QL = qL;
		this.next = next;
	}
	@Override
	public String toString() {
		return "QLearning [state=" + state + ", action=" + action + ", QL=" + QL + ", next=" + next + "]";
	}



}
