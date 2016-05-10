
public class State {
	private int position;
	private Direction direction;
	
	public State() {
		super();
	}

	public State(int position, Direction direction) {
		super();
		this.position = position;
		this.direction = direction;
	}

	public int getPosition() {
		return position;
	}

	public void setPosition(int position) {
		this.position = position;
	}

	public Direction getDirection() {
		return direction;
	}

	public void setDirection(Direction direction) {
		this.direction = direction;
	}

	@Override
	public String toString() {
		return "State [position=" + position + ", direction=" + direction + "]";
	}
	
	

}
