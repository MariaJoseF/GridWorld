
public class DirectionVstarLine {

	private int direction;
	private double VstarValue;

	public DirectionVstarLine(int dir, double Vstar_value) {
		// TODO Auto-generated constructor stub
		direction = dir;
		double _aux = Vstar_value;
		_aux = Math.round(_aux * 100);
		_aux = _aux / 100;// 2 decimal
							// places
		VstarValue = _aux;
	}

	@Override
	public String toString() {
		return "[direction=" + direction + ", VstarValue=" + VstarValue + "]";
	}

	public int getDirection() {
		return direction;
	}

	public void setDirection(int direction) {
		
		this.direction = direction;
	}

	public double getVstarValue() {
		return VstarValue;
	}

	public void setVstarValue(double vstarValue) {
		double _aux = vstarValue;
		_aux = Math.round(_aux * 100);
		_aux = _aux / 100;// 2 decimal
							// places
		vstarValue = _aux;
		VstarValue = vstarValue;
	}

}
