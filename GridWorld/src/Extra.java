import java.util.Random;
import java.util.Vector;

public class Extra {

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


	
}
