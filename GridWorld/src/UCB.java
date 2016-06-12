import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Vector;

public class UCB {

	private BufferedReader fr;

	private Vector Sequences = new Vector<>();
	int[] ActionsS1 = { 0, 0, 0, 0, 0 };
	int[] ActionsS2 = { 0, 0, 0, 0, 0 };

	public UCB() {
		// TODO Auto-generated constructor stub
		LoadSequences();
		CalculateScore();
	}

	private void CalculateScore() {
		// TODO Auto-generated method stub
		for (int i = 0; i < Sequences.size(); i++) {
			int[] Actions = { 0, 0, 0, 0, 0 };
			Vector<Integer> aux = (Vector<Integer>) Sequences.get(i);
			for (int j = 0; j < 100; j++) {// number of samples in each sequence
				if (aux.get(j) == 1) {
					Actions[0] = Actions[0] + 1;
				} else if (aux.get(j) == 2) {
					Actions[1] = Actions[1] + 1;
				} else if (aux.get(j) == 3) {
					Actions[2] = Actions[2] + 1;
				} else if (aux.get(j) == 4) {
					Actions[3] = Actions[3] + 1;
				} else if (aux.get(j) == 5) {
					Actions[4] = Actions[4] + 1;
				}
			}
			if (i == 0) {
				ActionsS1 = Actions;
				System.out.println(ActionsS1.toString());

			} else {
				ActionsS2 = Actions;
				System.out.println(ActionsS2.toString());

			}
			
		}
	}

	private void LoadSequences() {
		// TODO Auto-generated method stub
		try {
			fr = new BufferedReader(new InputStreamReader(new FileInputStream(new File("./seqs.csv"))));
			String line = "";
			Vector<Integer> vecSi_one = new Vector<Integer>();
			Vector<Integer> vecSi_two = new Vector<Integer>();
			int aux = 1;
			while ((line = fr.readLine()) != null) {
				line = line.replaceAll("\\s+", "");
				System.out.println(line.toString());
				String[] parts = line.split(",");
				// System.out.println(parts.toString());
				int[] vals = new int[parts.length];
				for (int i = 0; i < parts.length; i++) {
					vals[i] = Integer.parseInt(parts[i]);
					if (aux == 1) {
						vecSi_one.add(Integer.parseInt(parts[i]));
					} else {
						vecSi_two.add(Integer.parseInt(parts[i]));

					}
				}
				aux++;
			}
			// System.out.println(vecSi_one.toString());
			// System.out.println(vecSi_two.toString());
			Sequences.add(vecSi_one);
			Sequences.add(vecSi_two);
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
