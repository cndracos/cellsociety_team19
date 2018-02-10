package grid;

import cell.Cell;
import cell.LifeCell;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class LifeGrid extends Grid {
	private Random rand;

	public LifeGrid(int n, int k, int length, int width, HashMap<String, double[]> keys) {
		super(n, k, length, width, keys);
		rand = new Random();
		init();
	}
	/**
	 * Initializes grid by adding cells to the indexes, then calls adds neighbors
	 * to neighbor ArrayLists around it, then calls setNeighbors of finalized grid
	 */
	public void init() {
		double[] probLife = this.getKeys().get("ALIVE");
		for (int i = 0; i < this.getRows(); i++) {
			for (int j = 0; j < this.getCols(); j++) {
					double randD = rand.nextDouble();
					//makes an arbitrary LifeCell
					LifeCell l;
					if (randD >= probLife[0] && randD < probLife[1]) {
						l = new LifeCell("ALIVE", 0);
						this.add(l, i, j);
					}
					else {
						l = new LifeCell("DEAD", 0);
						this.add(l, i, j);
					}
					this.updateNeighbors(i, j, l, "Life");
				}
			}
		this.setNeighbors();
	}
}