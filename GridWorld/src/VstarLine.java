import java.util.Vector;

public class VstarLine {

	private int state;
	private double maxvalueV;
	
	private int direction;
	private Vector<DirectionVstarLine>directions = new Vector<DirectionVstarLine>();
	private double actualValueV;

	
	public VstarLine(int state, double max, double actal) {
		super();
		this.state = state;
		maxvalueV = max;
		 actualValueV = actal;
	}

	public double getActualValueV() {
		return actualValueV;
	}

	public void setActualValueV(double actualValueV) {
		
		
		double _aux = actualValueV;
		_aux = Math.round(_aux * 100);
		_aux = _aux / 100;// 2 decimal
							// places
		this.actualValueV = _aux;
	}

	private void CalculateMax() {
		// TODO Auto-generated method stub
		double noting_value = 0;
		for (int i = directions.size()-1; i >= 0; i--) {//last position = stay so needs to add to all other states
			if (i == directions.size()-1){
				noting_value = directions.get(i).getVstarValue();
			}else {
				maxvalueV = Math.max(maxvalueV, (directions.get(i).getVstarValue() + noting_value));
				if (maxvalueV == (directions.get(i).getVstarValue() + noting_value)) {
					direction = directions.get(i).getDirection();
				}
			}
		}
	}

	public double getMaxvalueV() {
		return maxvalueV;
	}

	public void setMaxvalueV(double maxvalueV) {
		this.maxvalueV = maxvalueV;
	}

	public Vector<DirectionVstarLine> getDirections() {
		return directions;
	}

	public void setDirections(Vector<DirectionVstarLine> directions) {
		this.directions = directions;
		CalculateMax();
	}

	public int getDirection() {
		return direction;
	}


	public void setDirection(int direction) {
		this.direction = direction;
	}


	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public double getMaxVal() {
		CalculateMax();
		return maxvalueV;
	}

	public void setMaxVal(double valueV) {
		this.maxvalueV = valueV;
	}

	@Override
	public String toString() {
		return "" + actualValueV ;
	}

	
}
