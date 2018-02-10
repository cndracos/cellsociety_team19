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
	private Map<String, double[]> keys;
	private int rows, cols;
	private int screenLength, screenWidth;
	private double cellLength, cellWidth;
	private final int DEFAULT_SPACE = 10;
	
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
	public Grid (int n, int k, int length, int width, HashMap<String, double[]> keys) {
		rows = n;
		cols = k;
		population = new Cell[rows][cols];
		neighbors = new ArrayList[rows][cols];
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				neighbors[i][j] = new ArrayList<Cell>();
			}
		}
		getCellSize(length, width);
		this.keys = keys;
	}
	
	/**
	 * compute the width and length of each cell according to cell numbers and screen size
	 * @param length screen length
	 * @param width screen width
	 */
	private void getCellSize(int length,int width) {
		screenLength = length - 2 * DEFAULT_SPACE;
		screenWidth = width - 2 * DEFAULT_SPACE;
		cellLength = screenLength / (rows * 1.0);
		cellWidth = screenWidth / (cols * 1.0);
	}
	
	/**
	 * add the cell to specific position on screen according to its coordinate
	 * @param c cell 
	 * @param n coordinate on x axis
	 * @param k coordinate on y axis
	 */
	private void addToScreen(Cell c, int n, int k) {
		c.setX(n*cellLength + DEFAULT_SPACE);
		c.setY(k*cellWidth + DEFAULT_SPACE);
		c.setWidth(cellWidth);
		c.setHeight(cellLength);
	}
	
	
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
	 * returns the map of cell types and probabilities
	 * @return keys map
	 */
	public Map<String, double[]> getKeys() {
		return keys;
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
	 * Parses through the whole grid and finds the state of cell and its neighbors,
	 * then parses again using those values to set the new states
	 */
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
	
	public void updateNeighbors (int n, int k, Cell c, String sim) {
		if (sim.equals("Fire")) {
			if (n-1>=0) neighbors[n-1][k].add(c);
			if (k-1>=0) neighbors[n][k-1].add(c);
			if (k+1<cols) neighbors[n][k+1].add(c);
			if (n+1<rows) neighbors[n+1][k].add(c);	
		}
		else if (sim.equals("Wator")) {
			if (n-1>=0) neighbors[n-1][k].add(c);
				else neighbors[rows - 1][k].add(c);
			if (k-1>=0) neighbors[n][k-1].add(c);
				else neighbors[n][cols - 1].add(c);
			if (k+1<cols) neighbors[n][k+1].add(c);
				else neighbors[n][0].add(c);
			if (n+1<rows) neighbors[n+1][k].add(c);
				else neighbors[0][k].add(c);
		}
		else {
			if (n-1>=0) {
				if (k-1>=0) 	neighbors[n-1][k-1].add(c);
				if (k+1<cols) neighbors[n-1][k+1].add(c);
			}
			if (n+1<rows) {
				neighbors[n+1][k].add(c);
				if (k-1>=0) neighbors[n+1][k-1].add(c);
				if (k+1<cols) neighbors[n+1][k+1].add(c);
			}
			if (k-1>=0) neighbors[n][k-1].add(c);
			if (k+1<cols) neighbors[n][k+1].add(c);
		}
	}
}
