package grid;

import cell.Cell;
import java.util.ArrayList;

public class FireGrid extends Grid {
	private ArrayList<Cell>[][] neighbors;

	public FireGrid(int n, int k) {
		super(n, k);
		neighbors = new ArrayList[n][k];
	}
	
	public ArrayList<Cell> getNeighbors(int n, int k){
		return neighbors[n][k];
	}
	
	public void addNeighbors (int n, int k) {
		neighbors[n][k] = new ArrayList<Cell>();
		if (n-1>=0) neighbors[n][k].add(this.get(n-1, k));
		if (k-1>=0) neighbors[n][k].add(this.get(n, k-1));
		if (k+1<neighbors.length) neighbors[n][k].add(this.get(n, k+1));
		if (n+1<neighbors[0].length) neighbors[n][k].add(this.get(n+1, k));
	}
}
