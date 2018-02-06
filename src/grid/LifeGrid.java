package grid;

import cell.LifeCell;

import java.util.Map;
import java.util.Random;

public class LifeGrid extends Grid {
	private Random rand;

	public LifeGrid(int n, int k, int length, int width, Map<String, double[]> keys) {
		super(n, k, length, width, keys);
		rand = new Random();
		init();
	}
	/**
	 * Initializes grid by adding cells to the indexes, then calls addNeighbors
	 */
	public void init() {
		double[] probLife = this.getKeys().get("ALIVE");
		for (int i = 0; i < this.getRows(); i++) {
			for (int j = 0; j < this.getCols(); j++) {
					double randD = rand.nextDouble();
					if (randD >= probLife[0] && randD < probLife[1]) 
						this.add(new LifeCell("ALIVE", 0), i, j);
					else this.add(new LifeCell("DEAD", 0), i, j);
				}
			}
		this.addNeighbors();
	}
}

