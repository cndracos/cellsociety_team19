package grid;

import cell.Cell;
import java.util.ArrayList;

public class LifeGrid extends Grid {
	private ArrayList<Cell>[][] neighbors;
	private int n;
	private int k;

	public LifeGrid(int n, int k) {
		super(n, k);
		neighbors = new ArrayList[n][k];
		this.n = n;
		this.k = k;
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
}
