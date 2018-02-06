package grid;

import cell.Cell;
import cell.FireCell;

import java.util.ArrayList;
import java.util.Map;
import java.util.Random;

public class FireGrid extends Grid {
	private double probCatch;
	private Random rand;

	public FireGrid(int n, int k, int length, int width, double probCatch, Map<String, double[]> keys) {
		super(n, k, length, width, keys);
		this.probCatch = probCatch;
		rand = new Random();
		init();
	}
	/**
	 * Adds only the four direct neighbors (above, below, left, right)
	 */
	@Override
	public void addNeighbors() {
		for (int i = 0; i < this.getRows(); i++) {
			for (int j = 0; j < this.getCols(); j++) {
				this.getNeighborsArray()[i][j] = new ArrayList<Cell>();
				if (i-1>=0) this.getNeighborsArray()[i][j].add(this.get(i-1, j));
				if (j-1>=0) this.getNeighborsArray()[i][j].add(this.get(i, j-1));
				if (j+1<this.getCols()) this.getNeighborsArray()[i][j].add(this.get(i, j+1));
				if (i+1<this.getRows()) this.getNeighborsArray()[i][j].add(this.get(i+1, j));
				this.get(i, j).setNeighbors(this.getNeighborsArray()[i][j]);
			}
		}
	}
	/**
	 * Initializes by adding the cells to the grid index and then calls addNeighbors, 
	 * also gives the simulation a border of empty cells, as specified by the 
	 * simulation description
	 */
	public void init() {
		double[] probFire = this.getKeys().get("BURNING");
		for (int i = 0; i < this.getRows(); i++) {
			for (int j = 0; j < this.getCols(); j++) {
				//if you are on the border, auto-make an empty cell
				if (i == 0 || j== 0 || i == this.getRows()-1 || j == this.getCols()-1) 
					this.add(new FireCell("EMPTY", probCatch), i, j);
				else {
					double randD = rand.nextDouble();
					if (randD >= probFire[0] && randD < probFire[1]) 
						this.add(new FireCell("BURNING", probCatch), i, j);
					else this.add(new FireCell("TREE", probCatch), i, j);
				}
			}
		}
		this.addNeighbors();
	}
}
