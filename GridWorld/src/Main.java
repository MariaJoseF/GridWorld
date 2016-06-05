import java.util.Iterator;
import java.util.Random;
import java.util.Vector;
import java.util.stream.DoubleStream;

import javax.swing.text.html.parser.ParserDelegator;

public class Main {
	private static double LoseReward = 0;
	private static double GoalReward = 0;
	private static double gamma = 0.95;
	static Vector<State> vec_States = new Vector<State>();
	private static Vector<State> Policy_star;

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		InitializeGrid();
		// begin();
		PrintStates();
		// Print(vec_States);

		//StartMDP();

		 StartQ_Learning(5000, 0.05, 0.2);
	}

	private static void StartQ_Learning(int iterations, double alpha, double epsilon) {
		// TODO Auto-generated method stub

		/*
		 * 1. initialize Q(s,a) to small random values for all states and
		 * actions 2.observe state s 3.pick random action from actions and do it
		 * 4.observe next state, s' and reward r 5.Q(s,a)<-- (i-alpha)Q(s,a) +
		 * alpha (r + gamma) max Q(s',a'); 6. Go to step 2
		 */

		Vector<QLearning> Qlearning = new Vector<QLearning>();
		Vector<Integer> vecVisitedStates = new Vector<Integer>();

		Vector<Policy> vecPolicy = new Vector<Policy>();

		for (int i = 0; i < vec_States.size(); i++) {// initialize Q(s,a)
			Vector<Directions> directions = vec_States.get(i).getDirections();
			int s = vec_States.get(i).getPosition();
			for (int j = 0; j < directions.size(); j++) {
				QLearning Q = new QLearning(s, directions.get(j), directions.get(j).getNextposition(), 0.0);
				Qlearning.add(Q);
			}
			vecPolicy.add(new Policy(s, -1));
		}

		System.out.println("");
		System.out.println("Initial Policy");

		PrintStates(vecPolicy);

		/*
		 * EPISODE - corresponds to explore each state until reach the goal. A
		 * episode consist of a agent move from the initial state to the goal
		 * state. Each time the agent reaches the goal state it starts a new
		 * episode
		 */
		int actual_state = 0;
		Random ran = new Random();
		for (int i = 1; i <= iterations; i++) {
			System.out.println("");
			System.out.println(">>>>>>>>>>>>>>>>>  Iteration: " + i + "  >>>>>>>>>>>>>>>>>");
			boolean reachGoal = true;
			vecVisitedStates = new Vector<Integer>();
			if (i == 1) {
				actual_state = 0;// actual state = 0 -> state 1
			}
			while (reachGoal) {// reach the goal state for this episode
				if (actual_state == 13 - 1) {
					reachGoal = false;
				}
				Vector<Directions> dir_st = vec_States.get(actual_state).getDirections();

				if (!vecVisitedStates.contains(actual_state)) {
					vecVisitedStates.add(actual_state);
				}

				int direction = -1;
				double max_action = 0;
				double prob_rand = ran.nextDouble();

				if (prob_rand < epsilon) {
					try {
						direction = ran.nextInt(dir_st.size() - 1);
						System.out.println("random direction = " + direction);
					} catch (java.lang.ArrayIndexOutOfBoundsException e) {
						// TODO Auto-generated catch block
						direction = 0;
					}
				} else {
					int aux = 0;
					Directions aux_direction = null;
					for (int j = 0; j < Qlearning.size(); j++) {
						if (Qlearning.get(j).getState() == actual_state + 1) {
							if (aux == 0) {
								max_action = Qlearning.get(j).getQL();
								aux++;
							} else {
								max_action = Math.max(max_action, Qlearning.get(j).getQL());
							}
							if (max_action == Qlearning.get(j).getQL()) {
								aux_direction = Qlearning.get(j).getAction();
								// System.out.println("direction= " +
								// aux_direction.getDirection());
							}
						}
					}
					for (int j = 0; j < dir_st.size(); j++) {
						if (dir_st.get(j) == aux_direction) {
							vecPolicy.set(actual_state, new Policy(actual_state + 1, dir_st.get(j).getDirection()));
							direction = j;
						}
					}
				}
				Directions action = dir_st.get(direction);
				QLearning new_valQsa = Q_State_Action(alpha, actual_state, action, Qlearning);
				double[] aux = Search(vec_States.get(actual_state), action, Qlearning);
				int index = (int) aux[0];
				Qlearning.set(index, new_valQsa);
				System.out.println(
						"_______ Q(" + (actual_state + 1) + "," + action.toString() + ") = " + new_valQsa.getQL());
				actual_state = new_valQsa.getNext() - 1;

				if (!reachGoal) {
					try {
						actual_state = vecVisitedStates.get(ran.nextInt(vecVisitedStates.size() - 1));
					} catch (java.lang.ArrayIndexOutOfBoundsException e) {
						// TODO Auto-generated catch block
						actual_state = 0;
					}
				}
			}
			System.out.println("");
			System.out.println("Updated Policy");
			PrintStates(vecPolicy);
			System.out.println("");
		}

		System.out.println("");
		System.out.println("Q-function values");

		QlearningOptimalPolicy(Qlearning, vecPolicy);

	}

	private static void QlearningOptimalPolicy(Vector<QLearning> qlearning, Vector<Policy> vecPolicy) {
		// TODO Auto-generated method stub
		Vector<QLearning> aux = new Vector<QLearning>();

		for (int j = 0; j < vecPolicy.size(); j++) {
			Policy policy = vecPolicy.get(j);

			for (int i = 0; i < qlearning.size(); i++) {
				QLearning ql = qlearning.get(i);

				if (policy.getState() == ql.getState() && policy.getDirection() == ql.getAction().getDirection()) {
					aux.add(ql);
					break;
				}
			}
		}

		PrintStates(aux);

	}

	private static QLearning Q_State_Action(double alpha, int st, Directions ac, Vector<QLearning> qlearning) {
		// TODO Auto-generated method stub
		QLearning new_LearnedQsa = null;
		State state = vec_States.get(st), next_st;
		double[] aux_ = Search(state, ac, qlearning);
		QLearning actual_LearnedQsa = qlearning.get((int) aux_[0]);

		// first position its the qlearning index and the second its the
		// Q(s'.a') for that index
		int nextstate = 0;
		String action = ac.toString();
		double r = state.getReward();
		Vector<Directions> Next_Directions;

		// state = vec_States.get(st);
		Vector<Directions> dir = state.getDirections();
		for (int i = 0; i < dir.size(); i++) {
			if (action.compareTo(dir.get(i).toString()) == 0) {
				nextstate = dir.get(i).getNextposition() - 1;
				break;
			}
		}

		next_st = vec_States.get(nextstate);

		Next_Directions = next_st.getDirections();

		double maxQnext = 0;
		for (int j = 0; j < Next_Directions.size(); j++) {
			double aux[] = Search(next_st, Next_Directions.get(j), qlearning);
			maxQnext = Math.max(maxQnext, aux[1]);// first position its the
													// qlearning index and the
													// second its the Q(s'.a')
													// for that index
		}

		// Qsa = (1-alpha)Qsa + alpha (r+gamma*maxQs'a')
		double QSA = 0.0;
		QSA = (1 - alpha) * actual_LearnedQsa.getQL() + alpha * (r + (gamma * maxQnext));

		double _aux = QSA;
		_aux = Math.round(_aux * 100);
		_aux = _aux / 100;// 2 decimal places
		QSA = _aux;

		new_LearnedQsa = new QLearning(st + 1, ac, nextstate + 1, QSA);
		return new_LearnedQsa;
	}

	private static double[] Search(State state, Directions action, Vector<QLearning> qlearning) {
		double QL_next[] = null;
		// TODO Auto-generated method stub
		for (int i = 0; i < qlearning.size(); i++) {
			Directions ac = qlearning.get(i).getAction();
			int st = qlearning.get(i).getState();
			int next = qlearning.get(i).getNext();

			if (ac.toString() == action.toString() && st == state.getPosition() && next == action.getNextposition()) {
				double aux[] = { i, qlearning.get(i).getQL() };
				QL_next = aux;
				break;
			}

		}
		return QL_next;
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
		int iteration = 0;
		while (iteration < 20) {
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
			iteration++;
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
			} //// Não sei se é preciso comentar este bloco de if

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
