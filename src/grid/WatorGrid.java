package grid;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import cell.*;

public class WatorGrid extends Grid {
	private Random rand;
	private double[] vals;

	public WatorGrid(int n, int k, int length, int width, double[] vals, HashMap<String, double[]> keys) {
		super(n, k, length, width, keys);
		this.vals = vals;
		rand = new Random();
		init();
	}
	/**
	 * Initializes the grid by adding the cells to the grid, then calls addNeighbors
	 */
	public void init() {
		//gets the array of probs from the key map
		double[] probF = this.getKeys().get("FISH");
		double[] probS = this.getKeys().get("SHARK");
		//gets the other values for the sim from the vals array
		double fishR = vals[0];
		double sharkR = vals[1];
		double sharkE = vals[2];
		ArrayList<Cell>[][] neighbors = this.getNeighborsArray();
		for (int i = 0; i < this.getRows(); i++) {
			for (int j = 0; j < this.getCols(); j++) {
				WatorCell w;
				double randD = rand.nextDouble();
				if (randD >= probF[0] && randD < probF[1]) {
					w = new WatorCell("FISH", fishR, sharkR, sharkE);
					this.add(w, i, j); 
				}
				else if (randD >= probS[0] && randD < probS[1]) {
					w = new WatorCell("SHARK", fishR, sharkR, sharkE);
					this.add(w, i, j); 
				}
				else {
					w = new WatorCell("WATER", fishR, sharkR, sharkE);
					this.add(w, i, j);
				}
				if (i-1>=0) neighbors[i-1][j].add(w);
					else neighbors[this.getRows() - 1][j].add(w);
				if (j-1>=0) neighbors[i][j-1].add(w);
					else neighbors[i][this.getCols() - 1].add(w);
				if (j+1<this.getCols()) neighbors[i][j+1].add(w);
					else neighbors[i][0].add(w);
				if (i+1<this.getRows()) neighbors[i+1][j].add(w);
					else neighbors[0][j].add(w);
			}
		}
		this.setNeighbors();
	}
}
