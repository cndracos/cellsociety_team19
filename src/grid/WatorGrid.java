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
	 * Initializes the grid by adding the cells to the grid, then calls add 
	 * cell to ArrayList<Cell> neighbors around it, then sets neighbors when all
	 * cells are added
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
				//creates arbitrary WatorCell to be added
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
				this.updateNeighbors(i, j, w, "Wator");
			}
		}
		this.setNeighbors();
	}
}
