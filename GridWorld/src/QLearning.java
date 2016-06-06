import java.util.Vector;

public class QLearning {
	private int state;
	private Vector<Directions> action;
	private int actionDirection;
	private double QL;
//	private int next;
	
/*	public int getNext() {
		return next;
	}
	public void setNext(int next) {
		this.next = next;
	}*/

	
	
	public int getState() {
		return state;
	}
	public QLearning(int state, Vector<Directions> action, int actionDirection, double qL) {
	super();
	this.state = state;
	this.action = action;
	this.actionDirection = actionDirection;
	QL = qL;
}
	public int getActionDirection() {
		return actionDirection;
	}
	public void setActionDirection(int actionDirection) {
		this.actionDirection = actionDirection;
	}
	public void setState(int state) {
		this.state = state;
	}
	public Vector<Directions> getAction() {
		return action;
	}
	public void setAction(Vector<Directions> action) {
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
		return "[" + actionDirection + "] " + QL;
	}



}
