import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Random;
import java.util.Vector;
import java.util.concurrent.TimeUnit;

public class SARSA_Class {

	private static Vector<State> vec_States;
	private long startTime;
	private int iterations;
	private double alpha;
	private double epsilon;
	private static Vector SarsaValues = new Vector<>();
	private static double gamma;

	public SARSA_Class(Vector<State> States_vec, double ga, int it, double al, double ep) {
		// TODO Auto-generated constructor stub
		startTime = System.currentTimeMillis();
		vec_States = States_vec;
		gamma = ga;
		iterations = it;
		alpha = al;
		epsilon = ep;
		
		SARSA(iterations, alpha, epsilon);
		long stopTime = System.currentTimeMillis();
		long elapsedTime = stopTime - startTime;
		String aux = String.format("%d min, %d sec", TimeUnit.MILLISECONDS.toMinutes(elapsedTime),
				TimeUnit.MILLISECONDS.toSeconds(elapsedTime)
						- TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(elapsedTime)));
		System.out.println("");
		System.out.println("Execution time: " + aux);

		CreateFileWithData();
	}

	private static void CreateFileWithData() {
		// TODO Auto-generated method stub
		PrintWriter writer;
		try {
			writer = new PrintWriter("SARSA_results.txt", "UTF-8");

			for (int i = 0; i < SarsaValues.size(); i++) {
				writer.println("" + SarsaValues.get(i));
			}

			writer.close();

		} catch (FileNotFoundException | UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private static void SARSA(int iterations, double alpha, double epsilon) {
		// TODO Auto-generated method stub

		/*
		 * 1. initialize Q(s,a) to small random values for all states and
		 * actions 2.observe state s 3.pick random action from actions and do it
		 * 4.observe next state, s' and reward r 5.Q(s,a)<-- (1-alpha)Q(s,a) +
		 * alpha (r + gamma* Q(s',policy(s')); 6. Go to step 2
		 */

		Vector<QLearning> SARSA_Q = new Vector<QLearning>();
		Vector<Integer> vecVisitedStates_SARSA = new Vector<Integer>();

		Vector<Policy> vecPolicy_SARSA = new Vector<Policy>();

		for (int i = 0; i < vec_States.size(); i++) {// initialize Q(s,a)
			Vector<Directions> directions = vec_States.get(i).getDirections();
			int s = vec_States.get(i).getPosition();

			Directions aux;
			for (int j = 0; j < directions.size(); j++) {
				QLearning Q = new QLearning(s, directions.get(j), directions.get(j).getNextposition(), 0.0);
				SARSA_Q.add(Q);
			}
			// vecPolicy_SARSA.add(new Policy(s, -1));
		}

		RunPolicy(SARSA_Q, vecPolicy_SARSA);

		System.out.println("");
		System.out.println("Initial Policy");

		PrintStates(vecPolicy_SARSA);

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
			if (i == 1) {
				actual_state = 0;// actual state = 0 -> state 1
			}
			while (reachGoal) {// reach the goal state for this episode
				if (actual_state == 13 - 1) {
					reachGoal = false;
				}
				Vector<Directions> dir_st = vec_States.get(actual_state).getDirections();

				if (!vecVisitedStates_SARSA.contains(actual_state)) {
					vecVisitedStates_SARSA.add(actual_state);
				}

				int Direction_Sarsa = GetAction(epsilon, actual_state, dir_st, SARSA_Q, vecPolicy_SARSA);
				int direction_sarsa = Direction_Sarsa;

				Directions actionSARSA = dir_st.get(direction_sarsa);
				QLearning new_valSARSA = SARSA_State_Action(alpha, actual_state, actionSARSA, SARSA_Q, vecPolicy_SARSA);


				double[] aux = Search(vec_States.get(actual_state), actionSARSA, SARSA_Q);
				int index = (int) aux[0];
				SARSA_Q.set(index, new_valSARSA);
				System.out.println("_______ Q(" + (actual_state + 1) + "," + actionSARSA.toString() + ") = "
						+ new_valSARSA.getQL());
				actual_state = new_valSARSA.getNext() - 1;

				if (!reachGoal) {
					try {
						actual_state = vecVisitedStates_SARSA.get(ran.nextInt(vecVisitedStates_SARSA.size() - 1));
					} catch (java.lang.ArrayIndexOutOfBoundsException e) {
						// TODO Auto-generated catch block
						actual_state = 0;
					}
				}
				RunPolicy(SARSA_Q, vecPolicy_SARSA);
			}
			System.out.println("");
			System.out.println("Updated Policy SARSA");
			PrintStates(vecPolicy_SARSA);
			System.out.println("");

			System.out.println("");
			System.out.println("Q-function values SARSA");

			SarsaValues.add(LearningOptimalPolicy(SARSA_Q, vecPolicy_SARSA));
		}

	}


	private static QLearning SARSA_State_Action(double alpha, int st, Directions ac, Vector<QLearning> SARSA_Q,
			Vector<Policy> vecPolicy) {
		// TODO Auto-generated method stub
		QLearning new_LearnedQsa = null;
		State state = vec_States.get(st), next_st;
		double[] aux_ = Search(state, ac, SARSA_Q);
		QLearning actual_LearnedQsa = SARSA_Q.get((int) aux_[0]);
		// first position its the SARSA index and the second its the
		// Q(s'.a') for that index

		int nextstate = 0;
		String action = ac.toString();
		 double r = state.getReward();

		Vector<Directions> dir = state.getDirections();
		for (int i = 0; i < dir.size(); i++) {
			if (action.compareTo(dir.get(i).toString()) == 0) {
				nextstate = dir.get(i).getNextposition() - 1;
				break;
			}
		}

		next_st = vec_States.get(nextstate);

		int action_policy = 0;
		for (int i = nextstate; i < vecPolicy.size(); i++) {
			// if (vecPolicy.get(i).getState() == next_st.getPosition()) {
			action_policy = vecPolicy.get(i).getDirection();
			break;
			// }
		}

		double QSA = 0.0, QSlineA_policy = 0.0;

		for (int i = nextstate; i < SARSA_Q.size(); i++) {
			if (SARSA_Q.get(i).getState() == next_st.getPosition() && (SARSA_Q.get(i).getAction()
					.getDirection() == action_policy)) {
				QSlineA_policy = SARSA_Q.get(i).getQL();
				break;
			}
		}

		// Qsa = (1-alpha)*Qsa + alpha (r+gamma*Qs'policy(s')
		QSA = (1 - alpha) * actual_LearnedQsa.getQL() + alpha * (r + (gamma * QSlineA_policy));

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

	private static void RunPolicy(Vector<QLearning> sARSA_Q, Vector<Policy> vecPolicy_SARSA) {
		// TODO Auto-generated method stub

		for (int i = 0; i < vec_States.size(); i++) {
			int aux = 0;
			State state = vec_States.get(i);
			int direction = 0;
			double maxQ = 0;
			for (int j = 0; j < sARSA_Q.size(); j++) {
				QLearning sarsa = sARSA_Q.get(j);
				if (state.getPosition() == sarsa.getState()) {

					if (aux == 0) {
						maxQ = sarsa.getQL();
						aux++;
					} else {
						maxQ = Math.max(maxQ, sarsa.getQL());
					}
					if (maxQ == sarsa.getQL()) {
						direction = sarsa.getAction().getDirection();
					}
				}
			}
			if (vecPolicy_SARSA.size() == vec_States.size()) {
				vecPolicy_SARSA.set(i, new Policy(state.getPosition(), direction));
			} else {
				vecPolicy_SARSA.add(new Policy(state.getPosition(), direction));

			}
		}

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

	private static int GetAction(double epsilon, int actual_state, Vector<Directions> dir_st,
			Vector<QLearning> learning, Vector<Policy> Policy) {
		// TODO Auto-generated method stub
		Random ran = new Random();
		int direction = -1;
		double prob_rand = ran.nextDouble();
		int dir = -1;
		if (prob_rand < epsilon) {
			try {
				dir = ran.nextInt(dir_st.size() - 1);
				System.out.println("random direction = " + dir);
			} catch (java.lang.ArrayIndexOutOfBoundsException e) {
				// TODO Auto-generated catch block
				dir = 0;
			}
		} else {
			for (int i = 0; i < Policy.size(); i++) {
				if (Policy.get(i).getState() == actual_state + 1) {
					direction = Policy.get(i).getDirection();
					break;
				}
			}

			int index = 0;
			double[] policy_high = { -1, -1, -1, -1 };
			for (int d = 0; d < dir_st.size(); d++) {
				if (dir_st.get(d).getDirection() == direction) {
					for (int i = 0; i < learning.size(); i++) {
						if (learning.get(i).getState() == actual_state + 1
								&& learning.get(i).getAction().getDirection() == direction
								&& learning.get(i).getNext() == dir_st.get(d).getNextposition()) {
							policy_high[index] = learning.get(i).getQL();
							index++;
							break;
						}
					}
					policy_high[index] = d;
					index++;
				}
			}

			if (policy_high[2] != -1) {
				double max = Math.max(policy_high[0], policy_high[2]);
				if (max == policy_high[0]) {
					dir = (int) policy_high[1];
				} else {
					dir = (int) policy_high[3];
				}
			} else {
				dir = (int) policy_high[1];
			}
		}
		return dir;

	}

	private static Vector<Double> LearningOptimalPolicy(Vector<QLearning> learning, Vector<Policy> vecPolicy) {
		// TODO Auto-generated method stub
		Vector<Double> aux = new Vector<Double>();

		for (int j = 0; j < vecPolicy.size(); j++) {
			Policy policy = vecPolicy.get(j);
			double sum_qls = 0;
			for (int i = 0; i < learning.size(); i++) {
				QLearning ql = learning.get(i);
				if (policy.getState() == ql.getState() && policy.getDirection() == ql.getAction().getDirection()) {
					sum_qls = Math.max(sum_qls, ql.getQL());
				}
			}
			aux.add(sum_qls);

		}
		PrintStates(aux);
		return aux;
	}

}
