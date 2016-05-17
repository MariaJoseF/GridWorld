import java.util.Iterator;
import java.util.Random;
import java.util.Vector;

public class Main {
	static Vector<State> vec_States = new Vector<State>();

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		InitializeGrid();
		PrintStates(vec_States);
		StartGame();
		MPD();
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

		//	MPD(pos);

			finished = ((pos == WinState) || (pos == LoseState));
			pos = nextpos;

		}
	}

	private static void MPD() {// Markov Decision Process
		// TODO Auto-generated method stub

		Vector<Double> V_Pi = new Vector<Double>();
		Vector<Double> aux = new Vector<Double>();
		// V_Pi.add(V_Pi(state));

		for (int i = 0; i < 23; i++) {// initialize all V(s) = 0;
			V_Pi.add(0.0);
		}
		System.out.println("V(0)");
		PrintStates(V_Pi);
		
		int teste = 1;
		while (teste < 10) {
			for (int i = 0; i < 23; i++) {// next iterations
				Vector<Directions> direct = vec_States.get(i).getDirections();
				int reward = vec_States.get(i).getReward();
				double gama = 0.95;
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
				v_pi = v_pi/100;// 2 decimal places
				
				
				aux.add(v_pi);
			}

			V_Pi= new Vector<>();// delete all values from previous iteration
			V_Pi = aux;// add to V_Pi the values of the new iteration
			aux = new Vector<>();// delete all values from previous iteration
			System.out.println("");
			System.out.println("V("+teste+")");
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

	private static Double V_Pi(int state) {

		// TODO Auto-generated method stub
		Vector<Directions> direct = vec_States.get(state - 1).getDirections();
		int reward = vec_States.get(state - 1).getReward();
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
		e.setProbability(prob_next);
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
