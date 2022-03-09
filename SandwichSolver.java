
package problemset1;

import java.util.Scanner;
import java.util.ArrayList;
import java.util.BitSet;

public class SandwichSolver {
	static int smallestSet;
	static int numIslands;
	static BitSet[] Paths;
	static ArrayList<Integer> necesaryIslands;

	SandwichSolver(int numIsland, int NumConnections, BitSet[] BitValues) {
		numIslands = numIsland;
		smallestSet = numIsland;
		Paths = BitValues;
		necesaryIslands = new ArrayList<Integer>();
		for (int i = 1; i <= numIslands; i++) {
			necesaryIslands.add(i);
		}

	}

	ArrayList<Integer> getIslands() {
		return necesaryIslands;
	}

	void SandwichBackTracking() {
		SandwichRecurssion(0, new BitSet(numIslands), new BitSet(numIslands));
	}

	void SandwichRecurssion(int position, BitSet current, BitSet islandsInSet) {

		
		BitSet potentialIslands = new BitSet();
		potentialIslands.or(islandsInSet);
		potentialIslands.or(Paths[position]);
		if (current.cardinality() + 1 >= smallestSet) {
			return;
		}
		if (potentialIslands.cardinality() == numIslands) {
			ArrayList<Integer> islands = new ArrayList<Integer>();

			for (int i = 0; i < numIslands; i++) {
				if (current.get(i)) {
					islands.add(i + 1);
				}
			}
			islands.add(position + 1);
			necesaryIslands = islands;
			smallestSet = islands.size();
			return;

		}
		if (position + 1 == numIslands) {
			return;
		}
		BitSet currentandSet = new BitSet();
		currentandSet.or(current);
		currentandSet.set(position);
		if(Paths[position].cardinality() == 1) {
			SandwichRecurssion(position+1, currentandSet, potentialIslands);
			return;
		}

		if (potentialIslands.cardinality() <= islandsInSet.cardinality()) {
			SandwichRecurssion(position + 1, current, islandsInSet);
			return;
		}

		
		
		SandwichRecurssion(position + 1, currentandSet, potentialIslands);
		SandwichRecurssion(position + 1, current, islandsInSet);

	}

	public static void main(String[] args) {
		Scanner myobj = new Scanner(System.in);

		int N = myobj.nextInt();
		int R = myobj.nextInt();

		BitSet[] BitValues = new BitSet[N];
		for (int i = 0; i < N; i++) {
			BitValues[i] = new BitSet();
		}
		for (int i = 0; i < R; i++) {
			int Next1 = myobj.nextInt();
			int Next2 = myobj.nextInt();
			BitValues[Next1 - 1].set(Next2 - 1);
			BitValues[Next2 - 1].set(Next1 - 1);

		}
		for (int i = 0; i < N; i++) {
			BitValues[i].set(i);
		}

		SandwichSolver sandwichShops = new SandwichSolver(N, R, BitValues);
		sandwichShops.SandwichBackTracking();
		ArrayList<Integer> islands = sandwichShops.getIslands();
		System.out.println(islands.size());
		for (int isl : islands) {
			System.out.print(isl + " ");
		}
		myobj.close();

	}

}
