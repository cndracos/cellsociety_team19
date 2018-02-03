package grid;

import cell.Cell;
import cell.LifeCell;

import java.util.ArrayList;
import java.util.Map;
import java.util.Random;

public class LifeGrid extends Grid {
	private ArrayList<Cell>[][] neighbors;
	private int n;
	private int k;
	private Random rand;

	public LifeGrid(int n, int k, Map<String, double[]> keys) {
		super(n, k, keys);
		neighbors = new ArrayList[n][k];
		this.n = n;
		this.k = k;
		rand = new Random();
		init();
	}
	
	public ArrayList<Cell> getNeighbors(int n, int k){
		return neighbors[n][k];
	}
	
	public void addNeighbors() {
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < k; j++) {
				neighbors[i][j] = new ArrayList<Cell>();
				if (i-1>=0) {
					neighbors[i][j].add(this.get(i-1, j));
					if (j-1>=0) {
						neighbors[i][j].add(this.get(i-1, j-1));
						neighbors[i][j].add(this.get(i, j-1));
					}
					if (j+1<k) {
						neighbors[i][j].add(this.get(i-1, j+1));
						neighbors[i][j].add(this.get(i, j+1));
					}
				}
				if (i+1<n) {
					neighbors[i][j].add(this.get(i+1, j));
					if (j-1>=0) neighbors[i][j].add(this.get(i+1, j-1));
					if (j+1<k) neighbors[i][j].add(this.get(i+1, j+1));
				}
				this.get(n, k).setNeighbors(neighbors[n][k]);
			}
		}
	}
	
	public void init() {
		double[] probLife = this.getKeys().get("ALIVE");
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < k; j++) {
					double randD = rand.nextDouble();
					if (randD > probLife[0] && randD <= probLife[1]) 
						this.add(new LifeCell("ALIVE", 0), i, j);
					else this.add(new LifeCell("DEAD", 0), i, j);
				}
				//root.getChildren().add(this.get(i, j));
			}
		this.addNeighbors();
	}
}

