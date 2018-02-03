package grid;

import cell.Cell;
import java.util.ArrayList;
import java.util.Map;

public abstract class Grid {
	private Cell[][] population;
	private Map<String, double[]> keys;
	private int n;
	private int k;
	
	public Grid (int n, int k, Map<String, double[]> keys) {
		population = new Cell[n][k];
		this.n = n;
		this.k = k;
		this.keys = keys;
	}
	
	public Cell get(int n, int k) {
		return population[n][k];
	}
	
	public Map<String, double[]> getKeys() {
		return keys;
	}
	
	public void add (Cell c, int n, int k) {
		population[n][k] = c;
		c.addToScreen(n, k);
	}
	
	public void update() {
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < k; j++) {
				this.get(i, j).findState();
			}
		}
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < k; j++) {
				this.get(i, j).setState();
			}
		}
	}
	
	public abstract ArrayList<Cell> getNeighbors(int n, int k);
	
	public abstract void addNeighbors();
}

