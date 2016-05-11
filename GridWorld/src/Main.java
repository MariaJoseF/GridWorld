import java.util.Vector;

public class Main {
	static Vector<State>vec_States = new Vector<State>();
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		InitializeGrid();
		PrintStates();
	}

	private static void PrintStates() {
		// TODO Auto-generated method stub
		int[][] st = {
	            {-1,-1,-1,1,-1,-1,-1},
	            {20,21,22,2,3,4,5},
	            {19,-1,-1,23,-1,-1,6},
	            {18,-1,-1,-1,-1,-1,7},
	            {17,-1,-1,13,-1,-1,8},
	            {16,15,14,12,11,10,9}
	        };
		int pos;
		for (int i = 0; i < 6; i++) {
			for (int j = 0; j < 7; j++) {
				pos = st[i][j];
				if (pos == -1) {
					System.out.print("\t\t");
				} else {
					System.out.print(vec_States.get(pos-1).toString() + "\t");
				}
			}
			System.out.println("");
		}
	}

	@SuppressWarnings("null")
	private static void InitializeGrid() {
		// TODO Auto-generated method stub
		
		//Possible directions
		int UP = 1;
		int DOWN = 2;
		int LEFT = 3;
		int RIGHT = 4;
		int NOTHING = 5;
		double prob_next = 0.75;
		double prob_stay = 0.10;
		double prob_overshoot = 0.15;
		
		
		Vector<Directions> vec_directions = new Vector<Directions>();
		Directions e = new Directions();
		
		//STATE 1 - move down, down2x and nothing
		e.setDirection(NOTHING);
		e.setProbability(prob_stay);
		e.setNextposition(1);
		e.setOvershoot(false);
		vec_directions.add(e);
		e.setDirection(DOWN);
		e.setProbability(prob_next);
		e.setNextposition(2);
		e.setOvershoot(false);
		vec_directions.add(e);
		e.setDirection(DOWN);
		e.setProbability(prob_overshoot);
		e.setNextposition(23);
		e.setOvershoot(true);
		vec_directions.add(e);
		vec_States.add(new State(1,vec_directions));
		
		//STATE 2 - moves up, down, left, left2x, right, right2x, nothing
		vec_directions.clear();
		
		e.setDirection(NOTHING);
		e.setProbability(prob_stay);
		e.setNextposition(2);
		e.setOvershoot(false);
		vec_directions.add(e);
		e.setDirection(DOWN);
		e.setProbability(prob_next);
		e.setNextposition(23);
		e.setOvershoot(false);
		vec_directions.add(e);
		e.setDirection(UP);
		e.setProbability(prob_next);
		e.setNextposition(1);
		e.setOvershoot(false);
		vec_directions.add(e);
		e.setDirection(LEFT);
		e.setProbability(prob_next);
		e.setNextposition(22);
		e.setOvershoot(false);
		vec_directions.add(e);
		e.setDirection(RIGHT);
		e.setProbability(prob_next);
		e.setNextposition(3);
		e.setOvershoot(false);
		vec_directions.add(e);
		e.setDirection(LEFT);
		e.setProbability(prob_overshoot);
		e.setNextposition(21);
		e.setOvershoot(true);
		vec_directions.add(e);
		e.setDirection(RIGHT);
		e.setProbability(prob_overshoot);
		e.setNextposition(4);
		e.setOvershoot(true);
		vec_directions.add(e);
		vec_States.add(new State(2,vec_directions));
		
		//STATE 3 - moves left, left2x, right, right2x, nothing
		vec_directions.clear();
		
		e.setDirection(NOTHING);
		e.setProbability(prob_stay);
		e.setNextposition(3);
		e.setOvershoot(false);
		vec_directions.add(e);
		e.setDirection(LEFT);
		e.setProbability(prob_next);
		e.setNextposition(2);
		e.setOvershoot(false);
		vec_directions.add(e);
		e.setDirection(RIGHT);
		e.setProbability(prob_next);
		e.setNextposition(4);
		e.setOvershoot(false);
		vec_directions.add(e);
		e.setDirection(LEFT);
		e.setProbability(prob_overshoot);
		e.setNextposition(22);
		e.setOvershoot(true);
		vec_directions.add(e);
		e.setDirection(RIGHT);
		e.setProbability(prob_overshoot);
		e.setNextposition(5);
		e.setOvershoot(true);
		vec_directions.add(e);
		vec_States.add(new State(3,vec_directions));
		
		//STATE 4 - moves left, left2x, right, nothing
		vec_directions.clear();
		
		e.setDirection(LEFT);
		e.setProbability(prob_next);
		e.setNextposition(3);
		e.setOvershoot(false);
		vec_directions.add(e);
		e.setDirection(LEFT);
		e.setProbability(prob_overshoot);
		e.setNextposition(2);
		e.setOvershoot(true);
		vec_directions.add(e);
		e.setDirection(RIGHT);
		e.setProbability(prob_next);
		e.setNextposition(5);
		e.setOvershoot(false);
		vec_directions.add(e);
		e.setDirection(NOTHING);
		e.setProbability(prob_stay);
		e.setNextposition(4);
		e.setOvershoot(false);
		vec_directions.add(e);
		vec_States.add(new State(4,vec_directions));
		
		//STATE 5 - moves left, left2x, down, down2x, nothing
		vec_directions.clear();
		
		e.setDirection(LEFT);
		e.setProbability(prob_next);
		e.setNextposition(4);
		e.setOvershoot(false);
		vec_directions.add(e);
		e.setDirection(LEFT);
		e.setProbability(prob_overshoot);
		e.setNextposition(3);
		e.setOvershoot(true);
		vec_directions.add(e);
		e.setDirection(DOWN);
		e.setProbability(prob_next);
		e.setNextposition(6);
		e.setOvershoot(false);
		vec_directions.add(e);
		e.setDirection(DOWN);
		e.setProbability(prob_overshoot);
		e.setNextposition(7);
		e.setOvershoot(true);
		vec_directions.add(e);
		e.setDirection(NOTHING);
		e.setProbability(prob_stay);
		e.setNextposition(5);
		e.setOvershoot(false);
		vec_directions.add(e);
		vec_States.add(new State(5,vec_directions));
		
		//STATE 6 - moves down, down2x, up, nothing
		vec_directions.clear();
		
		e.setDirection(DOWN);
		e.setProbability(prob_next);
		e.setNextposition(7);
		e.setOvershoot(false);
		vec_directions.add(e);
		e.setDirection(DOWN);
		e.setProbability(prob_overshoot);
		e.setNextposition(8);
		e.setOvershoot(true);
		vec_directions.add(e);
		e.setDirection(UP);
		e.setProbability(prob_next);
		e.setNextposition(5);
		e.setOvershoot(false);
		vec_directions.add(e);
		e.setDirection(NOTHING);
		e.setProbability(prob_stay);
		e.setNextposition(6);
		e.setOvershoot(false);
		vec_directions.add(e);
		vec_States.add(new State(6,vec_directions));
		
		//STATE 7 - moves down, down2x, up, up2x, nothing
		vec_directions.clear();
		e.setDirection(DOWN);
		e.setProbability(prob_next);
		e.setNextposition(8);
		e.setOvershoot(false);
		vec_directions.add(e);
		e.setDirection(DOWN);
		e.setProbability(prob_overshoot);
		e.setNextposition(9);
		e.setOvershoot(true);
		vec_directions.add(e);
		e.setDirection(UP);
		e.setProbability(prob_next);
		e.setNextposition(6);
		e.setOvershoot(false);
		vec_directions.add(e);
		e.setDirection(UP);
		e.setProbability(prob_overshoot);
		e.setNextposition(5);
		e.setOvershoot(true);
		vec_directions.add(e);
		e.setDirection(NOTHING);
		e.setProbability(prob_stay);
		e.setNextposition(7);
		e.setOvershoot(false);
		vec_directions.add(e);
		vec_States.add(new State(7,vec_directions));
		
		//STATE 8 - moves down, up, up2x, nothing
		vec_directions.clear();
		
		e.setDirection(DOWN);
		e.setProbability(prob_next);
		e.setNextposition(9);
		e.setOvershoot(false);
		vec_directions.add(e);
		e.setDirection(UP);
		e.setProbability(prob_next);
		e.setNextposition(7);
		e.setOvershoot(false);
		vec_directions.add(e);
		e.setDirection(UP);
		e.setProbability(prob_overshoot);
		e.setNextposition(6);
		e.setOvershoot(true);
		vec_directions.add(e);
		e.setDirection(NOTHING);
		e.setProbability(prob_stay);
		e.setNextposition(8);
		e.setOvershoot(false);
		vec_directions.add(e);
		vec_States.add(new State(8,vec_directions));
		
		//STATE 9 - moves left, left2x, up, up2x, nothing
		vec_directions.clear();
		
		e.setDirection(LEFT);
		e.setProbability(prob_next);
		e.setNextposition(10);
		e.setOvershoot(false);
		vec_directions.add(e);
		e.setDirection(LEFT);
		e.setProbability(prob_overshoot);
		e.setNextposition(11);
		e.setOvershoot(true);
		vec_directions.add(e);
		e.setDirection(UP);
		e.setProbability(prob_next);
		e.setNextposition(8);
		e.setOvershoot(false);
		vec_directions.add(e);
		e.setDirection(UP);
		e.setProbability(prob_overshoot);
		e.setNextposition(7);
		e.setOvershoot(true);
		vec_directions.add(e);
		e.setDirection(NOTHING);
		e.setProbability(prob_stay);
		e.setNextposition(9);
		e.setOvershoot(false);
		vec_directions.add(e);
		vec_States.add(new State(9,vec_directions));
		
		//STATE 10 - moves left, left2x, right, nothing
		vec_directions.clear();
		
		e.setDirection(LEFT);
		e.setProbability(prob_next);
		e.setNextposition(11);
		e.setOvershoot(false);
		vec_directions.add(e);
		e.setDirection(LEFT);
		e.setProbability(prob_overshoot);
		e.setNextposition(12);
		e.setOvershoot(true);
		vec_directions.add(e);
		e.setDirection(RIGHT);
		e.setProbability(prob_next);
		e.setNextposition(9);
		e.setOvershoot(false);
		vec_directions.add(e);
		e.setDirection(NOTHING);
		e.setProbability(prob_stay);
		e.setNextposition(10);
		e.setOvershoot(false);
		vec_directions.add(e);
		vec_States.add(new State(10,vec_directions));
		
		//STATE 11 - moves left, left2x, right, rigth2x, nothing
		vec_directions.clear();

		e.setDirection(LEFT);
		e.setProbability(prob_next);
		e.setNextposition(12);
		e.setOvershoot(false);
		vec_directions.add(e);
		e.setDirection(LEFT);
		e.setProbability(prob_overshoot);
		e.setNextposition(14);
		e.setOvershoot(true);
		vec_directions.add(e);
		e.setDirection(RIGHT);
		e.setProbability(prob_next);
		e.setNextposition(10);
		e.setOvershoot(false);
		vec_directions.add(e);
		e.setDirection(RIGHT);
		e.setProbability(prob_overshoot);
		e.setNextposition(9);
		e.setOvershoot(true);
		vec_directions.add(e);
		e.setDirection(NOTHING);
		e.setProbability(prob_stay);
		e.setNextposition(11);
		e.setOvershoot(false);
		vec_directions.add(e);
		vec_States.add(new State(11,vec_directions));
		
		//STATE 12 - moves left, left2x, right, rigth2x, up, nothing
		vec_directions.clear();
		
		e.setDirection(LEFT);
		e.setProbability(prob_next);
		e.setNextposition(14);
		e.setOvershoot(false);
		vec_directions.add(e);
		e.setDirection(LEFT);
		e.setProbability(prob_overshoot);
		e.setNextposition(15);
		e.setOvershoot(true);
		vec_directions.add(e);
		e.setDirection(RIGHT);
		e.setProbability(prob_next);
		e.setNextposition(11);
		e.setOvershoot(false);
		vec_directions.add(e);
		e.setDirection(RIGHT);
		e.setProbability(prob_overshoot);
		e.setNextposition(10);
		e.setOvershoot(true);
		vec_directions.add(e);
		e.setDirection(UP);
		e.setProbability(prob_overshoot);
		e.setNextposition(13);
		e.setOvershoot(true);
		vec_directions.add(e);
		e.setDirection(NOTHING);
		e.setProbability(prob_stay);
		e.setNextposition(12);
		e.setOvershoot(false);
		vec_directions.add(e);
		vec_States.add(new State(12,vec_directions));
		
		//STATE 13 - moves down, nothing
		vec_directions.clear();
		
		e.setDirection(DOWN);
		e.setProbability(prob_next);
		e.setNextposition(12);
		e.setOvershoot(false);
		vec_directions.add(e);
		e.setDirection(NOTHING);
		e.setProbability(prob_stay);
		e.setNextposition(13);
		e.setOvershoot(false);
		vec_directions.add(e);
		vec_States.add(new State(13,vec_directions));
		
		//STATE 14 - moves left, left2x, right, rigth2x, nothing
		vec_directions.clear();
		
		e.setDirection(LEFT);
		e.setProbability(prob_next);
		e.setNextposition(15);
		e.setOvershoot(false);
		vec_directions.add(e);
		e.setDirection(LEFT);
		e.setProbability(prob_overshoot);
		e.setNextposition(16);
		e.setOvershoot(true);
		vec_directions.add(e);
		e.setDirection(RIGHT);
		e.setProbability(prob_next);
		e.setNextposition(12);
		e.setOvershoot(false);
		vec_directions.add(e);
		e.setDirection(RIGHT);
		e.setProbability(prob_overshoot);
		e.setNextposition(11);
		e.setOvershoot(true);
		vec_directions.add(e);
		e.setDirection(NOTHING);
		e.setProbability(prob_stay);
		e.setNextposition(14);
		e.setOvershoot(false);
		vec_directions.add(e);
		vec_States.add(new State(14,vec_directions));
		
		//STATE 15 - moves left, right, rigth2x, nothing
		vec_directions.clear();
		
		e.setDirection(LEFT);
		e.setProbability(prob_next);
		e.setNextposition(16);
		e.setOvershoot(false);
		vec_directions.add(e);
		e.setDirection(RIGHT);
		e.setProbability(prob_next);
		e.setNextposition(14);
		e.setOvershoot(false);
		vec_directions.add(e);
		e.setDirection(RIGHT);
		e.setProbability(prob_overshoot);
		e.setNextposition(12);
		e.setOvershoot(true);
		vec_directions.add(e);
		e.setDirection(NOTHING);
		e.setProbability(prob_stay);
		e.setNextposition(15);
		e.setOvershoot(false);
		vec_directions.add(e);
		vec_States.add(new State(15,vec_directions));
		
		//STATE 16 - moves right, rigth2x, up, up2x, nothing
		vec_directions.clear();
		
		e.setDirection(RIGHT);
		e.setProbability(prob_next);
		e.setNextposition(15);
		e.setOvershoot(false);
		vec_directions.add(e);
		e.setDirection(RIGHT);
		e.setProbability(prob_overshoot);
		e.setNextposition(14);
		e.setOvershoot(true);
		vec_directions.add(e);
		e.setDirection(UP);
		e.setProbability(prob_next);
		e.setNextposition(17);
		e.setOvershoot(false);
		vec_directions.add(e);
		e.setDirection(UP);
		e.setProbability(prob_overshoot);
		e.setNextposition(18);
		e.setOvershoot(true);
		vec_directions.add(e);
		e.setDirection(NOTHING);
		e.setProbability(prob_stay);
		e.setNextposition(16);
		e.setOvershoot(false);
		vec_directions.add(e);
		vec_States.add(new State(16,vec_directions));
		
		//STATE 17 - moves down, up, up2x, nothing
		vec_directions.clear();
		e.setDirection(DOWN);
		e.setProbability(prob_next);
		e.setNextposition(16);
		e.setOvershoot(false);
		vec_directions.add(e);
		e.setDirection(UP);
		e.setProbability(prob_next);
		e.setNextposition(18);
		e.setOvershoot(false);
		vec_directions.add(e);
		e.setDirection(UP);
		e.setProbability(prob_overshoot);
		e.setNextposition(19);
		e.setOvershoot(true);
		vec_directions.add(e);
		e.setDirection(NOTHING);
		e.setProbability(prob_stay);
		e.setNextposition(17);
		e.setOvershoot(false);
		vec_directions.add(e);
		vec_States.add(new State(17,vec_directions));
		
		//STATE 18 - moves down, down2x, up, up2x, nothing
		vec_directions.clear();
		e.setDirection(DOWN);
		e.setProbability(prob_next);
		e.setNextposition(17);
		e.setOvershoot(false);
		vec_directions.add(e);
		e.setDirection(DOWN);
		e.setProbability(prob_overshoot);
		e.setNextposition(16);
		e.setOvershoot(true);
		vec_directions.add(e);
		e.setDirection(UP);
		e.setProbability(prob_next);
		e.setNextposition(19);
		e.setOvershoot(false);
		vec_directions.add(e);
		e.setDirection(UP);
		e.setProbability(prob_overshoot);
		e.setNextposition(20);
		e.setOvershoot(true);
		vec_directions.add(e);
		e.setDirection(NOTHING);
		e.setProbability(prob_stay);
		e.setNextposition(18);
		e.setOvershoot(false);
		vec_directions.add(e);
		vec_States.add(new State(18,vec_directions));
		
		//STATE 19 - moves down, down2x, up, nothing
		vec_directions.clear();
		e.setDirection(DOWN);
		e.setProbability(prob_next);
		e.setNextposition(18);
		e.setOvershoot(false);
		vec_directions.add(e);
		e.setDirection(DOWN);
		e.setProbability(prob_overshoot);
		e.setNextposition(17);
		e.setOvershoot(true);
		vec_directions.add(e);
		e.setDirection(UP);
		e.setProbability(prob_next);
		e.setNextposition(20);
		e.setOvershoot(false);
		vec_directions.add(e);
		e.setDirection(NOTHING);
		e.setProbability(prob_stay);
		e.setNextposition(19);
		e.setOvershoot(false);
		vec_directions.add(e);
		vec_States.add(new State(19,vec_directions));
		
		//STATE 20 - moves right, right2x, down, down2x, nothing
		vec_directions.clear();
		e.setDirection(RIGHT);
		e.setProbability(prob_next);
		e.setNextposition(21);
		e.setOvershoot(false);
		vec_directions.add(e);
		e.setDirection(RIGHT);
		e.setProbability(prob_overshoot);
		e.setNextposition(22);
		e.setOvershoot(true);
		vec_directions.add(e);
		e.setDirection(DOWN);
		e.setProbability(prob_next);
		e.setNextposition(19);
		e.setOvershoot(false);
		vec_directions.add(e);
		e.setDirection(DOWN);
		e.setProbability(prob_overshoot);
		e.setNextposition(18);
		e.setOvershoot(true);
		vec_directions.add(e);
		e.setDirection(NOTHING);
		e.setProbability(prob_stay);
		e.setNextposition(20);
		e.setOvershoot(false);
		vec_directions.add(e);
		vec_States.add(new State(20,vec_directions));
		
		//STATE 21 - moves left, right, rigth2x, nothing
		vec_directions.clear();
		e.setDirection(LEFT);
		e.setProbability(prob_next);
		e.setNextposition(20);
		e.setOvershoot(false);
		vec_directions.add(e);
		e.setDirection(RIGHT);
		e.setProbability(prob_next);
		e.setNextposition(22);
		e.setOvershoot(false);
		vec_directions.add(e);
		e.setDirection(RIGHT);
		e.setProbability(prob_overshoot);
		e.setNextposition(2);
		e.setOvershoot(true);
		vec_directions.add(e);
		e.setDirection(NOTHING);
		e.setProbability(prob_stay);
		e.setNextposition(21);
		e.setOvershoot(false);
		vec_directions.add(e);
		vec_States.add(new State(21,vec_directions));
		
		//STATE 22 - moves left, left2x, right, rigth2x, nothing
		vec_directions.clear();
		e.setDirection(LEFT);
		e.setProbability(prob_next);
		e.setNextposition(21);
		e.setOvershoot(false);
		vec_directions.add(e);
		e.setDirection(LEFT);
		e.setProbability(prob_overshoot);
		e.setNextposition(20);
		e.setOvershoot(true);
		vec_directions.add(e);
		e.setDirection(RIGHT);
		e.setProbability(prob_next);
		e.setNextposition(2);
		e.setOvershoot(false);
		vec_directions.add(e);
		e.setDirection(RIGHT);
		e.setProbability(prob_overshoot);
		e.setNextposition(3);
		e.setOvershoot(true);
		vec_directions.add(e);
		e.setDirection(NOTHING);
		e.setProbability(prob_stay);
		e.setNextposition(22);
		e.setOvershoot(false);
		vec_directions.add(e);
		vec_States.add(new State(22,vec_directions));
		
		//STATE 23 - moves up, up2x, nothing
		vec_directions.clear();
		e.setDirection(UP);
		e.setProbability(prob_next);
		e.setNextposition(2);
		e.setOvershoot(false);
		vec_directions.add(e);
		e.setDirection(UP);
		e.setProbability(prob_overshoot);
		e.setNextposition(1);
		e.setOvershoot(true);
		vec_directions.add(e);
		e.setDirection(NOTHING);
		e.setProbability(prob_stay);
		e.setNextposition(23);
		e.setOvershoot(false);
		vec_directions.add(e);
		vec_States.add(new State(23,vec_directions));

	}

}
