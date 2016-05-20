import java.util.Iterator;
import java.util.Random;
import java.util.Vector;

import javax.swing.text.html.parser.ParserDelegator;

public class Main {
	static Vector<State> vec_States = new Vector<State>();
	private static Vector<State> Policy_star;

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		InitializeGrid();
		// begin();
		PrintStates();
		// Print(vec_States);

		StartMDP();
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
		vec_States.add(new State(1, vec_directions, -0.04));
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
		vec_States.add(new State(2, vec_directions, -0.04));
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
		vec_States.add(new State(3, vec_directions, -0.04));
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
		vec_States.add(new State(4, vec_directions, 1));
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
		vec_States.add(new State(5, vec_directions, -0.04));
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
		vec_States.add(new State(6, vec_directions, -0.04));
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
		vec_States.add(new State(7, vec_directions, -1));
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
		vec_States.add(new State(8, vec_directions, -0.04));
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
		vec_States.add(new State(9, vec_directions, -0.04));
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
		vec_States.add(new State(10, vec_directions, -0.04));
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
		vec_States.add(new State(11, vec_directions, -0.04));
		vec_directions = new Vector<Directions>();

	}

	private static void StartMDP() {
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
		int teration = 0;
		while (teration < 20) {
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
				case 5:
					d_value = "N";
					break;
				}
				Policy_star.set(i, d_value);
			}
			teration++;
			System.out.println("");
			System.out.println("");
			System.out.println("V(" + teration + ")");
			PrintStates(Vstar_line);
			// Print(Vstar_line);
		}
		System.out.println("");
		PrintStates(Policy_star);
	}

	private static double[] Vstar_line(int i, Vector<Double> v) {
		// TODO Auto-generated method stub
		double gamma = 0.95;
		double aux;
		int policy = 0;
		if ((vec_States.get(i).getPosition() == 23) || (vec_States.get(i).getPosition() == 13)) {
			aux = vec_States.get(i).getReward(); // if its the last states
		} else {
			double max = 0;
			double maxUp = 0.0, maxDown = 0.0, maxRight = 0.0, maxLeft = 0.0, maxNothing = 0.0;
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
				} else { // dirrection == nothing
					maxNothing = prob * v.elementAt(nextstate - 1);
				}
			}
			max = Math.max(max, maxLeft);
			max = Math.max(max, maxRight);
			max = Math.max(max, maxUp);
			max = Math.max(max, maxDown);
			max = Math.max(max, maxNothing);

			if (max == maxLeft
					|| (maxLeft < 0.0 && maxDown == 0.0 && maxUp == 0.0 && maxNothing == 0.0 && maxRight == 0.0)) {
				policy = left;
			} else if (max == maxRight
					|| (maxLeft == 0.0 && maxDown == 0.0 && maxUp == 0.0 && maxNothing == 0.0 && maxRight < 0.0)) {
				policy = right;
			} else if (max == maxDown
					|| (maxLeft == 0.0 && maxDown < 0.0 && maxUp == 0.0 && maxNothing == 0.0 && maxRight == 0.0)) {
				policy = down;
			} else if (max == maxUp
					|| (maxLeft == 0.0 && maxDown == 0.0 && maxUp < 0.0 && maxNothing == 0.0 && maxRight == 0.0)) {
				policy = up;
			} else if (max == maxNothing
					|| (maxLeft == 0.0 && maxDown == 0.0 && maxUp == 0.0 && maxNothing < 0.0 && maxRight == 0.0)) {
				policy = nothing;
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

	private static void StartGame() {
		// TODO Auto-generated method stub
		int WinState = 13;// State 13 robot wins
		int LoseState = 23;// State 23 robot loses
		int pos = 1;// begin in the 1State
		Random randomGenerator = new Random();
		String direction = "";
		int randomDirection = 0;
		boolean finished = false;
		while (finished != true) {

			int randomOvershoot = randomGenerator.nextInt(2);

			int nextpos = pos;
			Vector<Directions> directions = vec_States.get(pos - 1).getDirections();

			int numdirectionsavailable = directions.size() - 1;
			randomDirection = randomGenerator.nextInt(numdirectionsavailable);

			boolean isOvershoot = false;
			nextpos = directions.get(randomDirection).getNextposition();

			System.out.println("S" + pos + " " + directions.get(randomDirection).toString());

			if (pos == WinState) {
				System.out.println("_______WIN_______");
			} else if (pos == LoseState) {
				System.out.print("GAME OVER !!!!!");
			}

			// MPD(pos);

			finished = ((pos == WinState) || (pos == LoseState));
			pos = nextpos;

		}
	}

	private static void MPD(double valueGama) {// Markov Decision Process
		// TODO Auto-generated method stub

		Vector<Double> V_Pi = new Vector<Double>();
		Vector<Double> aux = new Vector<Double>();
		// V_Pi.add(V_Pi(state));

		for (int i = 0; i < vec_States.size(); i++) {// initialize all V(s) = 0;
			V_Pi.add(0.0);
		}
		System.out.println("V(0)");
		PrintStates(V_Pi);

		int teste = 1;
		while (teste < 10) {
			for (int i = 0; i < vec_States.size(); i++) {// next iterations
				Vector<Directions> direct = vec_States.get(i).getDirections();
				double reward = vec_States.get(i).getReward();
				double gama = valueGama;
				double v_pi = 0.0;
				double SumProbabilities = 0;
				for (int j = 0; j < direct.size(); j++) {
					double direction_probabilty = direct.get(j).getProbability();
					int nextposition = direct.get(j).getNextposition() - 1;// vector
																			// with
																			// V(s)
																			// starts
																			// in
																			// 0
					SumProbabilities = SumProbabilities + (direction_probabilty * V_Pi.get(nextposition));
				}

				v_pi = reward + gama * SumProbabilities;

				v_pi = Math.round(v_pi * 100);
				v_pi = v_pi / 100;// 2 decimal places

				aux.add(v_pi);
			}

			V_Pi = new Vector<>();// delete all values from previous iteration
			V_Pi = aux;// add to V_Pi the values of the new iteration
			aux = new Vector<>();// delete all values from previous iteration
			System.out.println("");
			System.out.println("V(" + teste + ")");
			PrintStates(V_Pi);
			teste++;
		}

	}

	private static void PrintStates(Vector v_Pi) {
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
					System.out.print(String.format("%30s", v_Pi.get(pos - 1).toString()));
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

	private static Double V_Pi(int state) {

		// TODO Auto-generated method stub
		Vector<Directions> direct = vec_States.get(state - 1).getDirections();
		double reward = vec_States.get(state - 1).getReward();
		double gama = 0.95;
		double v_pi = 0.0;
		double SumProbabilities = 0;
		for (int i = 0; i < direct.size(); i++) {
			double direction_probabilty = direct.get(i).getProbability();
			int nextposition = direct.get(i).getNextposition();
			SumProbabilities = SumProbabilities + direction_probabilty * V_Pi(nextposition);
		}

		v_pi = reward + gama * SumProbabilities;

		return v_pi;
	}

	private static void PrintStates() {
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
		e.setProbability(prob_overshoot);
		e.setNextposition(13);
		e.setOvershoot(true);
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
		vec_States.add(new State(13, vec_directions, 10));

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
		vec_States.add(new State(23, vec_directions, -100));

	}

}
