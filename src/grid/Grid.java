package grid;

import cell.Cell;
import java.util.ArrayList;
import java.util.Map;

public abstract class Grid {
	private Cell[][] population;
	private ArrayList<Cell>[][] neighbors;
	private Map<String, double[]> keys;
	private int rows;
	private int cols;
	
	public Grid (int n, int k, Map<String, double[]> keys) {
		rows = n;
		cols = k;
		population = new Cell[rows][cols];
		neighbors = new ArrayList[n][k];
		this.keys = keys;
	}
	
	public Cell get(int n, int k) {
		return population[n][k];
	}
	
	public ArrayList<Cell>[][] getNeighborsArray() {
		return neighbors;
	}
	
	public Map<String, double[]> getKeys() {
		return keys;
	}
	
	public int getRows() {
		return rows;
	}
	
	public int getCols() {
		return cols;
	}
	
	public void add (Cell c, int n, int k) {
		population[n][k] = c;
		c.addToScreen(n, k);
	}
	
	public void update() {
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				this.get(i, j).findState();
			}
		}
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				this.get(i, j).setState();
			}
		}
	}
	
	public void addNeighbors() {
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				neighbors[i][j] = new ArrayList<Cell>();
				if (i-1>=0) {
					neighbors[i][j].add(this.get(i-1, j));
					if (j-1>=0) {
						neighbors[i][j].add(this.get(i-1, j-1));
						neighbors[i][j].add(this.get(i, j-1));
					}
					if (j+1<cols) {
						neighbors[i][j].add(this.get(i-1, j+1));
						neighbors[i][j].add(this.get(i, j+1));
					}
				}
				if (i+1<rows) {
					neighbors[i][j].add(this.get(i+1, j));
					if (j-1>=0) neighbors[i][j].add(this.get(i+1, j-1));
					if (j+1<cols) neighbors[i][j].add(this.get(i+1, j+1));
				}
				this.get(i, j).setNeighbors(neighbors[i][j]);
			}
		}
	}
	
	public ArrayList<Cell> getNeighbors(int n, int k) {
		return neighbors[n][k];
	}
}