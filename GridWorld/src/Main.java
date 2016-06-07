import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Random;
import java.util.Vector;
import java.util.concurrent.TimeUnit;


public class Main {
	private static double LoseReward = 0;
	private static double GoalReward = 0;
	private static double gamma = 0;
	static Vector<State> vec_States = new Vector<State>();
	private static Vector<State> Policy_star;
	

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		InitializeGrid();
		// begin();
		PrintStates();
		// Print(vec_States);

		// StartMDP(5);

		// 
		gamma = 0.95;
		int iterations = 5000;
		double alpha = 0.05;
		double epsilon = 0.2;

		new SARSA_Class(vec_States, gamma, iterations, alpha, epsilon);
		//new Qlearning_class(vec_States, gamma, iterations, alpha, epsilon);	
		
	}



	private static void begin() {
		// TODO Auto-generated method stub
		// Possible directions
		int UP = 1;
		int DOWN = 2;
		int LEFT = 3;
		int RIGHT = 4;
		int NOTHING = 5;
		double prob_next = 0.8;
		double prob_stay = 0.10;

		LoseReward = -1;
		GoalReward = 1;
		gamma = 0.99;

		Vector<Directions> vec_directions = new Vector<Directions>();
		Directions e = new Directions();

		// STATE 1 - move down, right
		e.setDirection(DOWN);
		e.setProbability(prob_stay);
		e.setNextposition(2);
		e.setOvershoot(false);
		vec_directions.add(e);
		e = new Directions();
		e.setDirection(RIGHT);
		e.setProbability(prob_next);
		e.setNextposition(2);
		e.setOvershoot(true);
		vec_directions.add(e);
		e = new Directions();
		vec_States.add(new State(1, vec_directions, -0.02));
		vec_directions = new Vector<Directions>();

		// STATE 2 - move left, right and nothing
		e.setDirection(LEFT);
		e.setProbability(prob_stay);
		e.setNextposition(1);
		e.setOvershoot(false);
		vec_directions.add(e);
		e = new Directions();
		e.setDirection(RIGHT);
		e.setProbability(prob_next);
		e.setNextposition(3);
		e.setOvershoot(false);
		vec_directions.add(e);
		e = new Directions();
		e.setDirection(NOTHING);
		e.setProbability(prob_stay);
		e.setNextposition(2);
		e.setOvershoot(false);
		vec_directions.add(e);
		e = new Directions();
		vec_States.add(new State(2, vec_directions, -0.02));
		vec_directions = new Vector<Directions>();

		// STATE 3 - move left, right down, and nothing
		e.setDirection(LEFT);
		e.setProbability(prob_stay);
		e.setNextposition(2);
		e.setOvershoot(false);
		vec_directions.add(e);
		e = new Directions();
		e.setDirection(RIGHT);
		e.setProbability(prob_next);
		e.setNextposition(4);
		e.setOvershoot(false);
		vec_directions.add(e);
		e = new Directions();
		e.setDirection(DOWN);
		e.setProbability(prob_stay);
		e.setNextposition(2);
		e.setOvershoot(false);
		vec_directions.add(e);
		e = new Directions();
		vec_States.add(new State(3, vec_directions, -0.02));
		vec_directions = new Vector<Directions>();

		// STATE 4 - move left, down
		e.setDirection(LEFT);
		e.setProbability(prob_next);
		e.setNextposition(3);
		e.setOvershoot(false);
		vec_directions.add(e);
		e = new Directions();
		e.setDirection(DOWN);
		e.setProbability(prob_stay);
		e.setNextposition(7);
		e.setOvershoot(false);
		vec_directions.add(e);
		e = new Directions();
		vec_States.add(new State(4, vec_directions, GoalReward));
		vec_directions = new Vector<Directions>();

		// STATE 5 - move up down, and nothing
		e.setDirection(UP);
		e.setProbability(prob_stay);
		e.setNextposition(1);
		e.setOvershoot(false);
		vec_directions.add(e);
		e = new Directions();
		e.setDirection(DOWN);
		e.setProbability(prob_next);
		e.setNextposition(8);
		e.setOvershoot(false);
		vec_directions.add(e);
		e = new Directions();
		e.setDirection(NOTHING);
		e.setProbability(prob_stay);
		e.setNextposition(5);
		e.setOvershoot(false);
		vec_directions.add(e);
		e = new Directions();
		vec_States.add(new State(5, vec_directions, -0.02));
		vec_directions = new Vector<Directions>();

		// STATE 6 - move up down, right,and nothing
		e.setDirection(UP);
		e.setProbability(prob_stay);
		e.setNextposition(3);
		e.setOvershoot(false);
		vec_directions.add(e);
		e = new Directions();
		e.setDirection(DOWN);
		e.setProbability(prob_stay);
		e.setNextposition(10);
		e.setOvershoot(false);
		vec_directions.add(e);
		e = new Directions();
		e.setDirection(RIGHT);
		e.setProbability(prob_next);
		e.setNextposition(7);
		e.setOvershoot(false);
		vec_directions.add(e);
		e = new Directions();
		e.setDirection(NOTHING);
		e.setProbability(prob_stay);
		e.setNextposition(6);
		e.setOvershoot(false);
		vec_directions.add(e);
		e = new Directions();
		vec_States.add(new State(6, vec_directions, -0.02));
		vec_directions = new Vector<Directions>();

		// STATE 7 - move up down, right,and nothing
		e.setDirection(UP);
		e.setProbability(prob_next);
		e.setNextposition(4);
		e.setOvershoot(false);
		vec_directions.add(e);
		e = new Directions();
		e.setDirection(DOWN);
		e.setProbability(prob_stay);
		e.setNextposition(11);
		e.setOvershoot(false);
		vec_directions.add(e);
		e = new Directions();
		e.setDirection(LEFT);
		e.setProbability(prob_stay);
		e.setNextposition(6);
		e.setOvershoot(false);
		vec_directions.add(e);
		e = new Directions();
		vec_States.add(new State(7, vec_directions, LoseReward));
		vec_directions = new Vector<Directions>();

		// STATE 8 - move up down, right,and nothing
		e.setDirection(UP);
		e.setProbability(prob_stay);
		e.setNextposition(5);
		e.setOvershoot(false);
		vec_directions.add(e);
		e = new Directions();
		e.setDirection(RIGHT);
		e.setProbability(prob_next);
		e.setNextposition(9);
		e.setOvershoot(false);
		vec_directions.add(e);
		e = new Directions();
		vec_States.add(new State(8, vec_directions, -0.02));
		vec_directions = new Vector<Directions>();

		// STATE 9 - move up down, right,and nothing
		e.setDirection(NOTHING);
		e.setProbability(prob_stay);
		e.setNextposition(9);
		e.setOvershoot(false);
		vec_directions.add(e);
		e = new Directions();
		e.setDirection(RIGHT);
		e.setProbability(prob_next);
		e.setNextposition(10);
		e.setOvershoot(false);
		vec_directions.add(e);
		e = new Directions();
		e.setDirection(LEFT);
		e.setProbability(prob_stay);
		e.setNextposition(8);
		e.setOvershoot(false);
		vec_directions.add(e);
		e = new Directions();
		vec_States.add(new State(9, vec_directions, -0.02));
		vec_directions = new Vector<Directions>();

		// STATE 10 - move up down, right,and nothing
		e.setDirection(UP);
		e.setProbability(prob_next);
		e.setNextposition(6);
		e.setOvershoot(false);
		vec_directions.add(e);
		e = new Directions();
		e.setDirection(RIGHT);
		e.setProbability(prob_stay);
		e.setNextposition(11);
		e.setOvershoot(false);
		vec_directions.add(e);
		e = new Directions();
		e.setDirection(LEFT);
		e.setProbability(prob_stay);
		e.setNextposition(9);
		e.setOvershoot(false);
		vec_directions.add(e);
		e = new Directions();
		vec_States.add(new State(10, vec_directions, -0.02));
		vec_directions = new Vector<Directions>();

		// STATE 11 - move up down, right,and nothing
		e.setDirection(UP);
		e.setProbability(prob_next);
		e.setNextposition(7);
		e.setOvershoot(false);
		vec_directions.add(e);
		e = new Directions();
		e.setDirection(LEFT);
		e.setProbability(prob_stay);
		e.setNextposition(10);
		e.setOvershoot(false);
		vec_directions.add(e);
		e = new Directions();
		vec_States.add(new State(11, vec_directions, -0.02));
		vec_directions = new Vector<Directions>();

	}

	private static void StartMDP(int iteration) {
		// TODO Auto-generated method stub
		Vector<Double> Vstar_line = new Vector<Double>();
		Vector<String> Policy_star = new Vector<String>();
		for (int i = 0; i < vec_States.size(); i++) {// initialize V_line
			Vstar_line.add(0.0);
			Policy_star.add("");
		}
		System.out.println("");
		System.out.println("V(0)");
		PrintStates(Vstar_line);
		// Print(Vstar_line);
		Vector<Double> Vstar = new Vector<Double>();

		Vstar = Vstar_line;

		for (int k = 1; k <= iteration; k++) {
			for (int i = 0; i < vec_States.size(); i++) {// initialize V_line
				double[] aux = Vstar_line(i, Vstar);
				Vstar_line.set(i, aux[0]);
				Double d = new Double(aux[1]);
				int aux_double = d.intValue();
				String d_value = "";
				switch (aux_double) {
				case 1:
					d_value = "U";
					break;
				case 2:
					d_value = "D";
					break;
				case 3:
					d_value = "L";
					break;
				case 4:
					d_value = "R";
					break;
				}

				if (d_value == "" && aux_double == LoseReward) {
					d_value = "" + LoseReward + "";
				} else if (d_value == "" && aux_double == GoalReward) {
					d_value = "" + GoalReward + "";
				}

				Policy_star.set(i, d_value);
			}

			System.out.println("");
			System.out.println("");
			System.out.println("V(" + iteration + ")");
			PrintStates(Vstar_line);
			// Print(Vstar_line);
		}
		System.out.println("");
		PrintStates(Policy_star);
		// Print(Policy_star);
		/*
		 * System.out.println(""); System.out.println("Gamma = " + gamma);
		 */
	}

	private static double[] Vstar_line(int i, Vector<Double> v) {
		// implementing Q(s,a) = Reward + Gamma * Q(s'a')
		// TODO Auto-generated method stub

		double aux;
		int policy = 0;
		aux = vec_States.get(i).getReward(); // if its the last states

		if ((aux == LoseReward) || (aux == GoalReward)) {
			policy = (int) aux;
		} else {
			double max = 0;
			double maxUp = 0.0, maxDown = 0.0, maxRight = 0.0,
					maxLeft = 0.0/* , maxNothing = 0.0 */;
			int left = 3, right = 4, up = 1, nothing = 5, down = 2;
			for (int j = 0; j < vec_States.get(i).getDirections().size(); j++) {
				double prob = vec_States.get(i).getDirections().get(j).getProbability();
				int nextstate = vec_States.get(i).getDirections().get(j).getNextposition();
				int diretion = vec_States.get(i).getDirections().get(j).getDirection();

				if (diretion == left) {
					maxLeft = maxLeft + (prob * v.elementAt(nextstate - 1));
				} else if (diretion == right) {
					maxRight = maxRight + (prob * v.elementAt(nextstate - 1));
				} else if (diretion == down) {
					maxDown = maxDown + (prob * v.elementAt(nextstate - 1));
				} else if (diretion == up) {
					maxUp = maxUp + (prob * v.elementAt(nextstate - 1));
				} else { // direction == nothing
					maxLeft = maxLeft + (prob * v.elementAt(nextstate - 1));
					maxRight = maxRight + (prob * v.elementAt(nextstate - 1));
					maxDown = maxDown + (prob * v.elementAt(nextstate - 1));
					maxUp = maxUp + (prob * v.elementAt(nextstate - 1));
				}
			}

			// Calculates the Utility not the Q*(s,a)

			max = Math.max(max, maxLeft);
			max = Math.max(max, maxRight);
			max = Math.max(max, maxUp);
			max = Math.max(max, maxDown);

			if ((max == maxLeft && max != 0) || (max == 0.0 && maxLeft < 0.0)) {
				policy = left;
			} else if ((max == maxRight && max != 0) || (max == 0.0 && maxRight < 0.0)) {
				policy = right;
			} else if ((max == maxDown && max != 0) || (max == 0.0 && maxDown < 0.0)) {
				policy = down;
			} else if ((max == maxUp && max != 0) || (max == 0.0 && maxDown < 0.0)) {
				policy = up;
			}

			aux = vec_States.get(i).getReward() + gamma * max;
			double _aux = aux;
			_aux = Math.round(_aux * 100);
			_aux = _aux / 100;// 2 decimal places
			aux = _aux;
		}
		double a[] = { aux, policy };

		return a;
	}

	public static void PrintStates(Vector v_Pi) {
		// TODO Auto-generated method stub
		int[][] st = { { -1, -1, -1, 1, -1, -1, -1 }, { 20, 21, 22, 2, 3, 4, 5 }, { 19, -1, -1, 23, -1, -1, 6 },
				{ 18, -1, -1, -1, -1, -1, 7 }, { 17, -1, -1, 13, -1, -1, 8 }, { 16, 15, 14, 12, 11, 10, 9 } };
		int pos;

		for (int i = 0; i < 6; i++) {
			for (int j = 0; j < 7; j++) {
				pos = st[i][j];
				if (pos == -1) {
					System.out.print(String.format("%15s", ""));
				} else {
					System.out.print(String.format("%15s", v_Pi.get(pos - 1).toString()));
				}
			}
			System.out.println("");
		}
	}

	public static void PrintStates() {
		// TODO Auto-generated method stub
		int[][] st = { { -1, -1, -1, 1, -1, -1, -1 }, { 20, 21, 22, 2, 3, 4, 5 }, { 19, -1, -1, 23, -1, -1, 6 },
				{ 18, -1, -1, -1, -1, -1, 7 }, { 17, -1, -1, 13, -1, -1, 8 }, { 16, 15, 14, 12, 11, 10, 9 } };
		int pos;

		for (int i = 0; i < 6; i++) {
			for (int j = 0; j < 7; j++) {
				pos = st[i][j];
				if (pos == -1) {
					System.out.print(String.format("%30s", ""));
				} else {
					System.out.print(String.format("%30s", vec_States.get(pos - 1).toString()));
				}
			}
			System.out.println("");
		}
	}

	private static void Print(Vector v_Pi) {
		// TODO Auto-generated method stub
		int[][] st = { { 1, 2, 3, 4 }, { 5, -1, 6, 7 }, { 8, 9, 10, 11 } };
		int pos;

		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 4; j++) {
				pos = st[i][j];
				if (pos == -1) {
					System.out.print(String.format("%17s", ""));
				} else {
					System.out.print(String.format("%17s", v_Pi.get(pos - 1).toString()));
				}
			}
			System.out.println("");
		}
	}

	@SuppressWarnings("null")
	private static void InitializeGrid() {
		// TODO Auto-generated method stub

		// Possible directions
		int UP = 1;
		int DOWN = 2;
		int LEFT = 3;
		int RIGHT = 4;
		int NOTHING = 5;
		double prob_next = 0.75;
		double prob_stay = 0.10;
		double prob_overshoot = 0.15;

		LoseReward = -100;
		GoalReward = 10;
		gamma = 0.99;

		Vector<Directions> vec_directions = new Vector<Directions>();
		Directions e = new Directions();

		// STATE 1 - move down, down2x and nothing
		e.setDirection(DOWN);
		e.setProbability(prob_next);
		e.setNextposition(2);
		e.setOvershoot(false);
		vec_directions.add(e);
		e = new Directions();
		e.setDirection(DOWN);
		e.setProbability(prob_overshoot);
		e.setNextposition(23);
		e.setOvershoot(true);
		vec_directions.add(e);
		e = new Directions();
		e.setDirection(NOTHING);
		e.setProbability(prob_stay);
		e.setNextposition(1);
		e.setOvershoot(false);
		vec_directions.add(e);
		e = new Directions();
		vec_States.add(new State(1, vec_directions, 0));

		// STATE 2 - moves up, down, left, left2x, right, right2x, nothing
		vec_directions = new Vector<Directions>();

		e.setDirection(DOWN);
		e.setProbability(prob_next);
		e.setNextposition(23);
		e.setOvershoot(false);
		vec_directions.add(e);
		e = new Directions();
		e.setDirection(UP);
		e.setProbability(prob_next);
		e.setNextposition(1);
		e.setOvershoot(false);
		vec_directions.add(e);
		e = new Directions();
		e.setDirection(LEFT);
		e.setProbability(0.75);
		e.setNextposition(22);
		e.setOvershoot(false);
		vec_directions.add(e);
		e = new Directions();
		e.setDirection(RIGHT);
		e.setProbability(prob_next);
		e.setNextposition(3);
		e.setOvershoot(false);
		vec_directions.add(e);
		e = new Directions();
		e.setDirection(LEFT);
		e.setProbability(prob_overshoot);
		e.setNextposition(21);
		e.setOvershoot(true);
		vec_directions.add(e);
		e = new Directions();
		e.setDirection(RIGHT);
		e.setProbability(prob_overshoot);
		e.setNextposition(4);
		e.setOvershoot(true);
		vec_directions.add(e);
		e = new Directions();
		e.setDirection(NOTHING);
		e.setProbability(prob_stay);
		e.setNextposition(2);
		e.setOvershoot(false);
		vec_directions.add(e);
		e = new Directions();
		e = new Directions();
		vec_States.add(new State(2, vec_directions, 0));

		// STATE 3 - moves left, left2x, right, right2x, nothing
		vec_directions = new Vector<Directions>();

		e.setDirection(LEFT);
		e.setProbability(prob_next);
		e.setNextposition(2);
		e.setOvershoot(false);
		vec_directions.add(e);
		e = new Directions();
		e.setDirection(RIGHT);
		e.setProbability(prob_next);
		e.setNextposition(4);
		e.setOvershoot(false);
		vec_directions.add(e);
		e = new Directions();
		e.setDirection(LEFT);
		e.setProbability(prob_overshoot);
		e.setNextposition(22);
		e.setOvershoot(true);
		vec_directions.add(e);
		e = new Directions();
		e.setDirection(RIGHT);
		e.setProbability(prob_overshoot);
		e.setNextposition(5);
		e.setOvershoot(true);
		vec_directions.add(e);
		e = new Directions();
		e.setDirection(NOTHING);
		e.setProbability(prob_stay);
		e.setNextposition(3);
		e.setOvershoot(false);
		vec_directions.add(e);
		e = new Directions();
		vec_States.add(new State(3, vec_directions, 0));

		// STATE 4 - moves left, left2x, right, nothing
		vec_directions = new Vector<Directions>();

		e.setDirection(LEFT);
		e.setProbability(prob_next);
		e.setNextposition(3);
		e.setOvershoot(false);
		vec_directions.add(e);
		e = new Directions();
		e.setDirection(LEFT);
		e.setProbability(prob_overshoot);
		e.setNextposition(2);
		e.setOvershoot(true);
		vec_directions.add(e);
		e = new Directions();
		e.setDirection(RIGHT);
		e.setProbability(prob_next);
		e.setNextposition(5);
		e.setOvershoot(false);
		vec_directions.add(e);
		e = new Directions();
		e.setDirection(NOTHING);
		e.setProbability(prob_stay);
		e.setNextposition(4);
		e.setOvershoot(false);
		vec_directions.add(e);
		e = new Directions();
		vec_States.add(new State(4, vec_directions, 0));

		// STATE 5 - moves left, left2x, down, down2x, nothing
		vec_directions = new Vector<Directions>();

		e.setDirection(LEFT);
		e.setProbability(prob_next);
		e.setNextposition(4);
		e.setOvershoot(false);
		vec_directions.add(e);
		e = new Directions();
		e.setDirection(LEFT);
		e.setProbability(prob_overshoot);
		e.setNextposition(3);
		e.setOvershoot(true);
		vec_directions.add(e);
		e = new Directions();
		e.setDirection(DOWN);
		e.setProbability(prob_next);
		e.setNextposition(6);
		e.setOvershoot(false);
		vec_directions.add(e);
		e = new Directions();
		e.setDirection(DOWN);
		e.setProbability(prob_overshoot);
		e.setNextposition(7);
		e.setOvershoot(true);
		vec_directions.add(e);
		e = new Directions();
		e.setDirection(NOTHING);
		e.setProbability(prob_stay);
		e.setNextposition(5);
		e.setOvershoot(false);
		vec_directions.add(e);
		e = new Directions();
		vec_States.add(new State(5, vec_directions, 0));

		// STATE 6 - moves down, down2x, up, nothing
		vec_directions = new Vector<Directions>();

		e.setDirection(DOWN);
		e.setProbability(prob_next);
		e.setNextposition(7);
		e.setOvershoot(false);
		vec_directions.add(e);
		e = new Directions();
		e.setDirection(DOWN);
		e.setProbability(prob_overshoot);
		e.setNextposition(8);
		e.setOvershoot(true);
		vec_directions.add(e);
		e = new Directions();
		e.setDirection(UP);
		e.setProbability(prob_next);
		e.setNextposition(5);
		e.setOvershoot(false);
		vec_directions.add(e);
		e = new Directions();
		e.setDirection(NOTHING);
		e.setProbability(prob_stay);
		e.setNextposition(6);
		e.setOvershoot(false);
		vec_directions.add(e);
		e = new Directions();
		vec_States.add(new State(6, vec_directions, 0));

		// STATE 7 - moves down, down2x, up, up2x, nothing
		vec_directions = new Vector<Directions>();
		e.setDirection(DOWN);
		e.setProbability(prob_next);
		e.setNextposition(8);
		e.setOvershoot(false);
		vec_directions.add(e);
		e = new Directions();
		e.setDirection(DOWN);
		e.setProbability(prob_overshoot);
		e.setNextposition(9);
		e.setOvershoot(true);
		vec_directions.add(e);
		e = new Directions();
		e.setDirection(UP);
		e.setProbability(prob_next);
		e.setNextposition(6);
		e.setOvershoot(false);
		vec_directions.add(e);
		e = new Directions();
		e.setDirection(UP);
		e.setProbability(prob_overshoot);
		e.setNextposition(5);
		e.setOvershoot(true);
		vec_directions.add(e);
		e = new Directions();
		e.setDirection(NOTHING);
		e.setProbability(prob_stay);
		e.setNextposition(7);
		e.setOvershoot(false);
		vec_directions.add(e);
		e = new Directions();
		vec_States.add(new State(7, vec_directions, 0));

		// STATE 8 - moves down, up, up2x, nothing
		vec_directions = new Vector<Directions>();

		e.setDirection(DOWN);
		e.setProbability(prob_next);
		e.setNextposition(9);
		e.setOvershoot(false);
		vec_directions.add(e);
		e = new Directions();
		e.setDirection(UP);
		e.setProbability(prob_next);
		e.setNextposition(7);
		e.setOvershoot(false);
		vec_directions.add(e);
		e = new Directions();
		e.setDirection(UP);
		e.setProbability(prob_overshoot);
		e.setNextposition(6);
		e.setOvershoot(true);
		vec_directions.add(e);
		e = new Directions();
		e.setDirection(NOTHING);
		e.setProbability(prob_stay);
		e.setNextposition(8);
		e.setOvershoot(false);
		vec_directions.add(e);
		e = new Directions();
		vec_States.add(new State(8, vec_directions, 0));

		// STATE 9 - moves left, left2x, up, up2x, nothing
		vec_directions = new Vector<Directions>();

		e.setDirection(LEFT);
		e.setProbability(prob_next);
		e.setNextposition(10);
		e.setOvershoot(false);
		vec_directions.add(e);
		e = new Directions();
		e.setDirection(LEFT);
		e.setProbability(prob_overshoot);
		e.setNextposition(11);
		e.setOvershoot(true);
		vec_directions.add(e);
		e = new Directions();
		e.setDirection(UP);
		e.setProbability(prob_next);
		e.setNextposition(8);
		e.setOvershoot(false);
		vec_directions.add(e);
		e = new Directions();
		e.setDirection(UP);
		e.setProbability(prob_overshoot);
		e.setNextposition(7);
		e.setOvershoot(true);
		vec_directions.add(e);
		e = new Directions();
		e.setDirection(NOTHING);
		e.setProbability(prob_stay);
		e.setNextposition(9);
		e.setOvershoot(false);
		vec_directions.add(e);
		e = new Directions();
		vec_States.add(new State(9, vec_directions, 0));

		// STATE 10 - moves left, left2x, right, nothing
		vec_directions = new Vector<Directions>();

		e.setDirection(LEFT);
		e.setProbability(prob_next);
		e.setNextposition(11);
		e.setOvershoot(false);
		vec_directions.add(e);
		e = new Directions();
		e.setDirection(LEFT);
		e.setProbability(prob_overshoot);
		e.setNextposition(12);
		e.setOvershoot(true);
		vec_directions.add(e);
		e = new Directions();
		e.setDirection(RIGHT);
		e.setProbability(prob_next);
		e.setNextposition(9);
		e.setOvershoot(false);
		vec_directions.add(e);
		e = new Directions();
		e.setDirection(NOTHING);
		e.setProbability(prob_stay);
		e.setNextposition(10);
		e.setOvershoot(false);
		vec_directions.add(e);
		e = new Directions();
		vec_States.add(new State(10, vec_directions, 0));

		// STATE 11 - moves left, left2x, right, rigth2x, nothing
		vec_directions = new Vector<Directions>();

		e.setDirection(LEFT);
		e.setProbability(prob_next);
		e.setNextposition(12);
		e.setOvershoot(false);
		vec_directions.add(e);
		e = new Directions();
		e.setDirection(LEFT);
		e.setProbability(prob_overshoot);
		e.setNextposition(14);
		e.setOvershoot(true);
		vec_directions.add(e);
		e = new Directions();
		e.setDirection(RIGHT);
		e.setProbability(prob_next);
		e.setNextposition(10);
		e.setOvershoot(false);
		vec_directions.add(e);
		e = new Directions();
		e.setDirection(RIGHT);
		e.setProbability(prob_overshoot);
		e.setNextposition(9);
		e.setOvershoot(true);
		vec_directions.add(e);
		e = new Directions();
		e.setDirection(NOTHING);
		e.setProbability(prob_stay);
		e.setNextposition(11);
		e.setOvershoot(false);
		vec_directions.add(e);
		e = new Directions();
		vec_States.add(new State(11, vec_directions, 0));

		// STATE 12 - moves left, left2x, right, rigth2x, up, nothing
		vec_directions = new Vector<Directions>();

		e.setDirection(LEFT);
		e.setProbability(prob_next);
		e.setNextposition(14);
		e.setOvershoot(false);
		vec_directions.add(e);
		e = new Directions();
		e.setDirection(LEFT);
		e.setProbability(prob_overshoot);
		e.setNextposition(15);
		e.setOvershoot(true);
		vec_directions.add(e);
		e = new Directions();
		e.setDirection(RIGHT);
		e.setProbability(prob_next);
		e.setNextposition(11);
		e.setOvershoot(false);
		vec_directions.add(e);
		e = new Directions();
		e.setDirection(RIGHT);
		e.setProbability(prob_overshoot);
		e.setNextposition(10);
		e.setOvershoot(true);
		vec_directions.add(e);
		e = new Directions();
		e.setDirection(UP);
		e.setProbability(prob_next);
		e.setNextposition(13);
		e.setOvershoot(false);
		vec_directions.add(e);
		e = new Directions();
		e.setDirection(NOTHING);
		e.setProbability(prob_stay);
		e.setNextposition(12);
		e.setOvershoot(false);
		vec_directions.add(e);
		e = new Directions();
		vec_States.add(new State(12, vec_directions, 0));

		// STATE 13 - moves down, nothing
		vec_directions = new Vector<Directions>();

		e.setDirection(DOWN);
		e.setProbability(prob_next);
		e.setNextposition(12);
		e.setOvershoot(false);
		vec_directions.add(e);
		e = new Directions();
		e.setDirection(NOTHING);
		e.setProbability(prob_stay);
		e.setNextposition(13);
		e.setOvershoot(false);
		vec_directions.add(e);
		e = new Directions();
		vec_States.add(new State(13, vec_directions, GoalReward));

		// STATE 14 - moves left, left2x, right, rigth2x, nothing
		vec_directions = new Vector<Directions>();

		e.setDirection(LEFT);
		e.setProbability(prob_next);
		e.setNextposition(15);
		e.setOvershoot(false);
		vec_directions.add(e);
		e = new Directions();
		e.setDirection(LEFT);
		e.setProbability(prob_overshoot);
		e.setNextposition(16);
		e.setOvershoot(true);
		vec_directions.add(e);
		e = new Directions();
		e.setDirection(RIGHT);
		e.setProbability(prob_next);
		e.setNextposition(12);
		e.setOvershoot(false);
		vec_directions.add(e);
		e = new Directions();
		e.setDirection(RIGHT);
		e.setProbability(prob_overshoot);
		e.setNextposition(11);
		e.setOvershoot(true);
		vec_directions.add(e);
		e = new Directions();
		e.setDirection(NOTHING);
		e.setProbability(prob_stay);
		e.setNextposition(14);
		e.setOvershoot(false);
		vec_directions.add(e);
		e = new Directions();
		vec_States.add(new State(14, vec_directions, 0));

		// STATE 15 - moves left, right, rigth2x, nothing
		vec_directions = new Vector<Directions>();

		e.setDirection(LEFT);
		e.setProbability(prob_next);
		e.setNextposition(16);
		e.setOvershoot(false);
		vec_directions.add(e);
		e = new Directions();
		e.setDirection(RIGHT);
		e.setProbability(prob_next);
		e.setNextposition(14);
		e.setOvershoot(false);
		vec_directions.add(e);
		e = new Directions();
		e.setDirection(RIGHT);
		e.setProbability(prob_overshoot);
		e.setNextposition(12);
		e.setOvershoot(true);
		vec_directions.add(e);
		e = new Directions();
		e.setDirection(NOTHING);
		e.setProbability(prob_stay);
		e.setNextposition(15);
		e.setOvershoot(false);
		vec_directions.add(e);
		e = new Directions();
		vec_States.add(new State(15, vec_directions, 0));

		// STATE 16 - moves right, rigth2x, up, up2x, nothing
		vec_directions = new Vector<Directions>();

		e.setDirection(RIGHT);
		e.setProbability(prob_next);
		e.setNextposition(15);
		e.setOvershoot(false);
		vec_directions.add(e);
		e = new Directions();
		e.setDirection(RIGHT);
		e.setProbability(prob_overshoot);
		e.setNextposition(14);
		e.setOvershoot(true);
		vec_directions.add(e);
		e = new Directions();
		e.setDirection(UP);
		e.setProbability(prob_next);
		e.setNextposition(17);
		e.setOvershoot(false);
		vec_directions.add(e);
		e = new Directions();
		e.setDirection(UP);
		e.setProbability(prob_overshoot);
		e.setNextposition(18);
		e.setOvershoot(true);
		vec_directions.add(e);
		e = new Directions();
		e.setDirection(NOTHING);
		e.setProbability(prob_stay);
		e.setNextposition(16);
		e.setOvershoot(false);
		vec_directions.add(e);
		e = new Directions();
		vec_States.add(new State(16, vec_directions, 0));

		// STATE 17 - moves down, up, up2x, nothing
		vec_directions = new Vector<Directions>();
		e.setDirection(DOWN);
		e.setProbability(prob_next);
		e.setNextposition(16);
		e.setOvershoot(false);
		vec_directions.add(e);
		e = new Directions();
		e.setDirection(UP);
		e.setProbability(prob_next);
		e.setNextposition(18);
		e.setOvershoot(false);
		vec_directions.add(e);
		e = new Directions();
		e.setDirection(UP);
		e.setProbability(prob_overshoot);
		e.setNextposition(19);
		e.setOvershoot(true);
		vec_directions.add(e);
		e = new Directions();
		e.setDirection(NOTHING);
		e.setProbability(prob_stay);
		e.setNextposition(17);
		e.setOvershoot(false);
		vec_directions.add(e);
		e = new Directions();
		vec_States.add(new State(17, vec_directions, 0));

		// STATE 18 - moves down, down2x, up, up2x, nothing
		vec_directions = new Vector<Directions>();
		e.setDirection(DOWN);
		e.setProbability(prob_next);
		e.setNextposition(17);
		e.setOvershoot(false);
		vec_directions.add(e);
		e = new Directions();
		e.setDirection(DOWN);
		e.setProbability(prob_overshoot);
		e.setNextposition(16);
		e.setOvershoot(true);
		vec_directions.add(e);
		e = new Directions();
		e.setDirection(UP);
		e.setProbability(prob_next);
		e.setNextposition(19);
		e.setOvershoot(false);
		vec_directions.add(e);
		e = new Directions();
		e.setDirection(UP);
		e.setProbability(prob_overshoot);
		e.setNextposition(20);
		e.setOvershoot(true);
		vec_directions.add(e);
		e = new Directions();
		e.setDirection(NOTHING);
		e.setProbability(prob_stay);
		e.setNextposition(18);
		e.setOvershoot(false);
		vec_directions.add(e);
		e = new Directions();
		vec_States.add(new State(18, vec_directions, 0));

		// STATE 19 - moves down, down2x, up, nothing
		vec_directions = new Vector<Directions>();
		e.setDirection(DOWN);
		e.setProbability(prob_next);
		e.setNextposition(18);
		e.setOvershoot(false);
		vec_directions.add(e);
		e = new Directions();
		e.setDirection(DOWN);
		e.setProbability(prob_overshoot);
		e.setNextposition(17);
		e.setOvershoot(true);
		vec_directions.add(e);
		e = new Directions();
		e.setDirection(UP);
		e.setProbability(prob_next);
		e.setNextposition(20);
		e.setOvershoot(false);
		vec_directions.add(e);
		e = new Directions();
		e.setDirection(NOTHING);
		e.setProbability(prob_stay);
		e.setNextposition(19);
		e.setOvershoot(false);
		vec_directions.add(e);
		e = new Directions();
		vec_States.add(new State(19, vec_directions, 0));

		// STATE 20 - moves right, right2x, down, down2x, nothing
		vec_directions = new Vector<Directions>();
		e.setDirection(RIGHT);
		e.setProbability(prob_next);
		e.setNextposition(21);
		e.setOvershoot(false);
		vec_directions.add(e);
		e = new Directions();
		e.setDirection(RIGHT);
		e.setProbability(prob_overshoot);
		e.setNextposition(22);
		e.setOvershoot(true);
		vec_directions.add(e);
		e = new Directions();
		e.setDirection(DOWN);
		e.setProbability(prob_next);
		e.setNextposition(19);
		e.setOvershoot(false);
		vec_directions.add(e);
		e = new Directions();
		e.setDirection(DOWN);
		e.setProbability(prob_overshoot);
		e.setNextposition(18);
		e.setOvershoot(true);
		vec_directions.add(e);
		e = new Directions();
		e.setDirection(NOTHING);
		e.setProbability(prob_stay);
		e.setNextposition(20);
		e.setOvershoot(false);
		vec_directions.add(e);
		e = new Directions();
		vec_States.add(new State(20, vec_directions, 0));

		// STATE 21 - moves left, right, rigth2x, nothing
		vec_directions = new Vector<Directions>();
		e.setDirection(LEFT);
		e.setProbability(prob_next);
		e.setNextposition(20);
		e.setOvershoot(false);
		vec_directions.add(e);
		e = new Directions();
		e.setDirection(RIGHT);
		e.setProbability(prob_next);
		e.setNextposition(22);
		e.setOvershoot(false);
		vec_directions.add(e);
		e = new Directions();
		e.setDirection(RIGHT);
		e.setProbability(prob_overshoot);
		e.setNextposition(2);
		e.setOvershoot(true);
		vec_directions.add(e);
		e = new Directions();
		e.setDirection(NOTHING);
		e.setProbability(prob_stay);
		e.setNextposition(21);
		e.setOvershoot(false);
		vec_directions.add(e);
		e = new Directions();
		vec_States.add(new State(21, vec_directions, 0));

		// STATE 22 - moves left, left2x, right, rigth2x, nothing
		vec_directions = new Vector<Directions>();
		e.setDirection(LEFT);
		e.setProbability(prob_next);
		e.setNextposition(21);
		e.setOvershoot(false);
		vec_directions.add(e);
		e = new Directions();
		e.setDirection(LEFT);
		e.setProbability(prob_overshoot);
		e.setNextposition(20);
		e.setOvershoot(true);
		vec_directions.add(e);
		e = new Directions();
		e.setDirection(RIGHT);
		e.setProbability(prob_next);
		e.setNextposition(2);
		e.setOvershoot(false);
		vec_directions.add(e);
		e = new Directions();
		e.setDirection(RIGHT);
		e.setProbability(prob_overshoot);
		e.setNextposition(3);
		e.setOvershoot(true);
		vec_directions.add(e);
		e = new Directions();
		e.setDirection(NOTHING);
		e.setProbability(prob_stay);
		e.setNextposition(22);
		e.setOvershoot(false);
		vec_directions.add(e);
		e = new Directions();
		vec_States.add(new State(22, vec_directions, 0));

		// STATE 23 - moves up, up2x, nothing
		vec_directions = new Vector<Directions>();
		e.setDirection(UP);
		e.setProbability(prob_next);
		e.setNextposition(2);
		e.setOvershoot(false);
		vec_directions.add(e);
		e = new Directions();
		e.setDirection(UP);
		e.setProbability(prob_overshoot);
		e.setNextposition(1);
		e.setOvershoot(true);
		vec_directions.add(e);
		e = new Directions();
		e.setDirection(NOTHING);
		e.setProbability(prob_stay);
		e.setNextposition(23);
		e.setOvershoot(false);
		vec_directions.add(e);
		e = new Directions();
		vec_States.add(new State(23, vec_directions, LoseReward));

	}

}
