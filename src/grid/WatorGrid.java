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
		for (int i = 0; i < this.getRows(); i++) {
			for (int j = 0; j < this.getCols(); j++) {
				double randD = rand.nextDouble();
				if (randD >= probF[0] && randD < probF[1]) {
					this.add(new WatorCell("FISH", fishR, sharkR, sharkE), i, j); }
				else if (randD >= probS[0] && randD < probS[1]) {
					this.add(new WatorCell("SHARK", fishR, sharkR, sharkE), i, j); }
				else {
					this.add(new WatorCell("WATER", fishR, sharkR, sharkE), i, j);
				}
			}
		}	
		this.addNeighbors();
	}
	/**
	 * Only adds the four direct neighbors, and also has to link an edge cell with the 
	 * corresponding edge cell on the other side of the screen to create the 'taurus'
	 * that represents the Wa-Tor simulation world
	 */
	@Override
	public void addNeighbors() {
			for (int i = 0; i < this.getRows(); i++) {
				for (int j = 0; j < this.getCols(); j++) {
					this.getNeighborsArray()[i][j] = new ArrayList<Cell>();
					if (i-1>=0) this.getNeighborsArray()[i][j].add(this.get(i-1, j));
						else this.getNeighborsArray()[i][j].add(this.get(this.getRows() - 1, j));
					if (j-1>=0) this.getNeighborsArray()[i][j].add(this.get(i, j-1));
						else this.getNeighborsArray()[i][j].add(this.get(i, this.getCols() - 1));
					if (j+1<this.getCols()) this.getNeighborsArray()[i][j].add(this.get(i, j+1));
						else this.getNeighborsArray()[i][j].add(this.get(i, 0));
					if (i+1<this.getRows()) this.getNeighborsArray()[i][j].add(this.get(i+1, j));
						else this.getNeighborsArray()[i][j].add(this.get(0, j));
					this.get(i, j).setNeighbors(this.getNeighborsArray()[i][j]);
				}
			}
	}

}
