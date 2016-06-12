import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Vector;
import java.util.concurrent.TimeUnit;

public class MDP {
	private static Vector<VstarLine> Vstar_line = new Vector<VstarLine>();
	private static Vector<State> vec_States = new Vector<State>();
	private static Vector MDPValues = new Vector<>();
	private static double gamma;
	private long startTime;

	public MDP(Vector<State> States_vec, int iteration, double ga) {
		// TODO Auto-generated constructor stub
		startTime = System.currentTimeMillis();
		vec_States = States_vec;
		gamma = ga;
		StartMDP(iteration);
		long stopTime = System.currentTimeMillis();
		long elapsedTime = stopTime - startTime;
		String aux = String.format("%d min, %d sec", TimeUnit.MILLISECONDS.toMinutes(elapsedTime),
				TimeUnit.MILLISECONDS.toSeconds(elapsedTime)
						- TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(elapsedTime)));
		System.out.println("");
		System.out.println("Execution time: " + aux);

		CreateFileWithData();
	}

	private static void StartMDP(int iteration) {
		// TODO Auto-generated method stub
		Vector<String> Policy_star = new Vector<String>();
		for (int i = 0; i < vec_States.size(); i++) {// initialize V_line
			Vstar_line.add(new VstarLine(vec_States.get(i).getPosition(), 0.0, 0.0));
			Policy_star.add("-");
		}

		System.out.println("");
		System.out.println("V(0)");
		PrintStates(Vstar_line);

		System.out.println("");
		PrintStates(Policy_star);
		// Vector<Double> Vstar = new Vector<Double>();

		// Vstar = Vstar_line;

		for (int k = 1; k <= iteration; k++) {
			for (int i = 0; i < vec_States.size(); i++) {
				// initialize V_line
				double[] aux = Vstar_line(i, Vstar_line);
				Vstar_line.get(i).setActualValueV(aux[0]);
				// Vstar_line.set(i, );
				/*
				 * Double d = new Double(aux[1]); int aux_double = d.intValue();
				 */
				String d_value = "";
				switch (Vstar_line.get(i).getDirection()) {
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
				case 0:
					d_value = "-";
					break;
				}

				Policy_star.set(i, d_value);
			}

			System.out.println("");
			System.out.println("");
			System.out.println("V(" + k + ")");
			PrintStates(Vstar_line);
			System.out.println("");
			PrintStates(Policy_star);
			
			MDPValues.add(FillMDP());

		}
	}

	private static Vector<Double> FillMDP() {
		// TODO Auto-generated method stub
		Vector<Double> aux_Vstar_Line = new Vector<Double>();
		for (int i = 0; i < Vstar_line.size(); i++) {
			aux_Vstar_Line.add(Vstar_line.get(i).getActualValueV());	
		}
		return aux_Vstar_Line;
	}

	private static void CreateFileWithData() {
		// TODO Auto-generated method stub
		PrintWriter writer;
		try {
			writer = new PrintWriter("MDP_results.txt", "UTF-8");

			for (int i = 0; i < MDPValues.size(); i++) {
				writer.println("" + MDPValues.get(i));
			}

			writer.close();

		} catch (FileNotFoundException | UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private static double[] Vstar_line(int i, Vector<VstarLine> vstar_line) {
		// implementing Q(s,a) = Reward + Gamma * Q(s'a')
		// TODO Auto-generated method stub
		// int st = 0;
		double actualValueV = 0;
		int left = 3, right = 4, up = 1, down = 2;
		int policy = 0;
		double[][] max_ = new double[5][2];
		Vector<DirectionVstarLine> VS = new Vector<DirectionVstarLine>();
		for (int j = 0; j < vec_States.get(i).getDirections().size(); j++) {
			double prob = vec_States.get(i).getDirections().get(j).getProbability();
			int nextstate = vec_States.get(i).getDirections().get(j).getNextposition();
			int diretion = vec_States.get(i).getDirections().get(j).getDirection();

			if (diretion == left) {
				max_[0][0] = max_[0][0] + (prob * vstar_line.get(nextstate - 1).getActualValueV());
				max_[0][1] = left;
			} else if (diretion == right) {
				max_[1][0] = max_[1][0] + (prob * vstar_line.get(nextstate - 1).getActualValueV());
				max_[1][1] = right;
			} else if (diretion == up) {
				max_[2][0] = max_[2][0] + (prob * vstar_line.get(nextstate - 1).getActualValueV());
				max_[2][1] = up;
			} else if (diretion == down) {
				max_[3][0] = max_[3][0] + (prob * vstar_line.get(nextstate - 1).getActualValueV());
				max_[3][1] = down;
			} else {// direction = stay
				max_[4][0] = (prob * vstar_line.get(nextstate - 1).getActualValueV());
				max_[4][1] = 5;
				// System.out.println("");
			}
		}
		for (int j = 0; j < 5; j++) {
			if (max_[j][1] != 0.0) {
				double _aux = max_[j][0];
				_aux = Math.round(_aux * 100);
				_aux = _aux / 100;// 2 decimal

				VS.add(new DirectionVstarLine((int) max_[j][1], _aux));
			}
		}

		if (vec_States.get(i).getPosition() == 23) {
			System.out.println("");
		}
		vstar_line.get(i).setDirections(VS);

		policy = vstar_line.get(i).getDirection();

		actualValueV = vec_States.get(i).getReward() + gamma * vstar_line.get(i).getMaxVal();

		double _aux = actualValueV;
		_aux = Math.round(_aux * 100);
		_aux = _aux / 100;// 2 decimal
							// places
		actualValueV = _aux;

		double a[] = { actualValueV, policy };

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
	/*
	 * private static void RunPolicy(Vector<VstarLine> vecVstarline,
	 * Vector<Policy> vecPolicy) { // TODO Auto-generated method stub
	 * 
	 * for (int i = 0; i < vec_States.size(); i++) { int aux = 0; State state =
	 * vec_States.get(i); int direction = 0; double maxQ = 0; for (int j = 0; j
	 * < vecVstarline.size(); j++) { VstarLine vstar_line = vecVstarline.get(j);
	 * if (state.getPosition() == vstar_line.getState()) {
	 * 
	 * if (aux == 0) { maxQ = vstar_line.getValueV(); aux++; } else { maxQ =
	 * Math.max(maxQ, vstar_line.getValueV()); } if (maxQ ==
	 * vstar_line.getValueV()) { direction =
	 * vstar_line.getAction().getDirection(); } } }
	 * 
	 * if (vecPolicy.size() == vec_States.size()) { vecPolicy.set(i, new
	 * Policy(state.getPosition(), direction)); } else { vecPolicy.add(new
	 * Policy(state.getPosition(), direction));
	 * 
	 * } }
	 * 
	 * }
	 */

}
