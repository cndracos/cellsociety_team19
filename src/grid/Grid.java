package grid;

import cell.Cell;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * 
 * This is the superclass of Grid, 
 * which will be implemented by the subclasses of the specific grids
 * @author charliedracos
 *
 */
public abstract class Grid {
	private Cell[][] population;
	private ArrayList<Cell>[][] neighbors;
	private int rows, cols;
	
	/**
	 * Constructor of grid class
	 * @param n number of rows in the grid
	 * @param k number of columns in the grid
	 * @param length screen length
	 * @width width screen width
	 * @param keys contains mappings of the cell variables as strings in a specific
	 * simulation  (e.g. probTree in Fire, probFish in Wa-Tor) to an upper
	 * and lower bounds of the probability a cell is that type (e.g. 0.0-0.4)
	 */
	public Grid (int n, int k) {
		rows = n;
		cols = k;
		population = new Cell[rows][cols];
		neighbors = new ArrayList[rows][cols];
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				neighbors[i][j] = new ArrayList<Cell>();
			}
		}
	}
	
	/**
	 * compute the width and length of each cell according to cell numbers and screen size
	 * @param length screen length
	 * @param width screen width
	 */
	public abstract void getCellSize(int length,int width);
	
	/**
	 * add the cell to specific position on screen according to its coordinate
	 * @param c cell 
	 * @param n coordinate on x axis
	 * @param k coordinate on y axis
	 */
	public abstract void addToScreen(Cell c, int n, int k);
	
	
	/**
	 * Returns the cell at an index
	 * @param n row index
	 * @param k column index
	 * @return cell at the index
	 */
	public Cell get(int n, int k) {
		return population[n][k];
	}
	/**
	 * returns number of rows
	 * @return rows
	 */
	public int getRows() {
		return rows;
	}
	/**
	 * returns number of columns
	 * @return cols
	 */
	public int getCols() {
		return cols;
	}
	/**
	 * Add a cell to the 2d array at a specific index and adds to the screen
	 * @param c Cell to be added
	 * @param n row index
	 * @param k column index
	 */
	public void add (Cell c, int n, int k) {
		population[n][k] = c;
		addToScreen(c, n, k);
	}
	/**
	 * Goes through all the cells when all are finally added to the grid, 
	 * then gives to them their ArrayList<Cell> neighbors
	 */
	public void setNeighbors() {
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				this.get(i, j).setNeighbors(neighbors[i][j]);
			}
		}
	}
	
	public ArrayList<Cell>[][] getNeighborsArray() {
		return neighbors;
	}
	
	public abstract void updateNeighbors(int n, int k, Cell c, String sim);
}
