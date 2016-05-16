import java.util.Vector;

public class State {
	private int position;
	private Vector<Directions> directions;
	private int reward;
	
	public State(int position, Vector<Directions> directions, int reward) {
		super();
		this.position = position;
		this.directions = directions;
		this.reward = reward;
	}

	public int getReward() {
		return reward;
	}

	public void setReward(int reward) {
		this.reward = reward;
	}

	public State() {
		super();
	}

	public int getPosition() {
		return position;
	}

	public void setPosition(int position) {
		this.position = position;
	}

	public Vector<Directions> getDirections() {
		return directions;
	}

	public void setDirections(Vector<Directions> directions) {
		this.directions = directions;
	}

	@Override
	public String toString() {
		return "S" + position + "" + directions + "";
	}

	
}
