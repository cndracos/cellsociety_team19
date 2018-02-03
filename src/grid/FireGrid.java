package grid;

import cell.Cell;
import cell.FireCell;

import java.util.ArrayList;
import java.util.Random;

public class FireGrid extends Grid {
	private ArrayList<Cell>[][] neighbors;
	private double probCatch;
	private double probFire;
	private Random rand;
	private int n;
	private int k;

	public FireGrid(int n, int k, double probCatch, double probFire) {
		super(n, k);
		neighbors = new ArrayList[n][k];
		this.probCatch = probCatch;
		this.probFire = probFire;
		this.n = n;
		this.k = k;
		rand = new Random();
	}
	
	public ArrayList<Cell> getNeighbors(int n, int k){
		return neighbors[n][k];
	}
	
	public void addNeighbors() {
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < k; j++) {
				neighbors[i][j] = new ArrayList<Cell>();
				if (i-1>=0) neighbors[i][j].add(this.get(i-1, j));
				if (j-1>=0) neighbors[i][j].add(this.get(i, j-1));
				if (j+1<k) neighbors[i][j].add(this.get(i, j+1));
				if (i+1<n) neighbors[i][j].add(this.get(i+1, j));
				this.get(i, j).setNeighbors(neighbors[i][j]);
			}
		}
	}
	
	public void init() {
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < k; j++) {
				if (i == 0 || j== 0 || i == n || j == k) 
					this.add(new FireCell("EMPTY", probCatch), i, j);
				else {
					if (rand.nextDouble() < probFire) this.add(new FireCell("BURNING", probCatch), i, j);
					else this.add(new FireCell("TREE", probCatch), i, j);
				}
				//root.getChildren().add(this.get(i, j));
			}
		}
		this.addNeighbors();
	}
}
