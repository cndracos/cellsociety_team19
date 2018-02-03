package grid;

import cell.Cell;
import java.util.ArrayList;

public abstract class Grid {
	protected Cell[][] population;
	
	public Grid (int n, int k) {
		population = new Cell[n][k];
	}
	
	public Cell get(int n, int k) {
		return population[n][k];
	}
	
	public void add (Cell c, int n, int k) {
		population[n][k] = c;
		c.addToScreen(n, k);
	}
	
	public abstract ArrayList<Cell> getNeighbors(int n, int k);
	
	public abstract void addNeighbors();
}
