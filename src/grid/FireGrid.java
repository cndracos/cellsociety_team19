package grid;

import cell.Cell;
import cell.FireCell;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class FireGrid extends Grid {
	private double probCatch;
	private Random rand;

	public FireGrid(int n, int k, int length, int width, double probCatch, HashMap<String, double[]> keys) {
		super(n, k, length, width, keys);
		this.probCatch = probCatch;
		rand = new Random();
		init();
	}
	
	/**
	 * Initializes by adding the cells to the grid index and then adds the cell
	 * to the ArrayList<Cell> of neighbors around it,  
	 * also gives the simulation a border of empty cells, as specified by the 
	 * simulation description
	 * Then calls setNeighbor of the superclass to add finalized neighbors to each cell
	 */
	public void init() {
		double[] probFire = this.getKeys().get("BURNING");
		for (int i = 0; i < this.getRows(); i++) {
			for (int j = 0; j < this.getCols(); j++) {
				//makes an arbitrary FireCell
				FireCell f;
				//if you are on the border, auto-make an empty cell
				if (i == 0 || j== 0 || i == this.getRows()-1 || j == this.getCols()-1) {
					f = new FireCell("EMPTY", probCatch);
					this.add(f, i, j);	
				}
				else {
					double randD = rand.nextDouble();
					if (randD >= probFire[0] && randD < probFire[1]) {
						f = new FireCell("BURNING", probCatch);
						this.add(f, i, j);
					}
					else {
						f = new FireCell("TREE", probCatch);
						this.add(f, i, j);
					}
				}
				this.updateNeighbors(i, j, f, "Fire");
			}
		}
	    this.setNeighbors();
	}
}
