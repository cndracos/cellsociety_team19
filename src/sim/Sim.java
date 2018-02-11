package sim;

import java.util.HashMap;

import java.util.Map;
import java.util.Random;

import grid.*;

/**
 * This is the superclass of any simulation, which runs the simulation
 * based on the sim specified. It creates the corresponding grid which
 * interacts with its subclasses
 * @author charliedracos
 *
 */

public abstract class Sim {
	private HashMap<String, double[]> keys;
	private Grid grid;
	private Random rand;
	/**
	 * Constructor for the sim superclass
	 * @param n number of rows
	 * @param k number of cols
	 * @param length of screen
	 * @param width of screen
	 * @param keys the values to be used in a specific simulation
	 * @param grid the type of grid to be created
	 */
	public Sim (int n, int k, int length, int width, 
		Map<String, double[]> keys, String grid) {
		this.keys = (HashMap<String, double[]>) keys;
		rand = new Random();
		if (grid.equals("SQUARE")) {
			this.grid = new SquareGrid(n, k, length, width);
		}
		else if (grid.equals("TRIANGLE")) {
			this.grid = new TriangleGrid(n, k, length, width);
		}
		else {
			this.grid = new HexGrid(n, k, length, width);
		}
	}
	/**
	 * @return the grid in the simulation
	 */
	public Grid getGrid() {
		return grid;
	}
	/**
	 * @return the keys of values for a sim
	 */
	public Map<String, double[]> getKeys() {
		return keys;
	}
	/**
	 * @return the rand that makes random values in generating cells
	 */
	public Random getRand() {
		return rand;
	}
	/**
	 * Iterates twice through the grid, once using the states to
	 * update all cells, then setting the cells to that state
	 * once all have been updated
	 */
	public void update() {
		for (int i = 0; i < grid.getRows(); i++) {
			for (int j = 0; j < grid.getCols(); j++) {
				grid.get(i, j).findState();
			}
		}
		for (int i = 0; i < grid.getRows(); i++) {
			for (int j = 0; j < grid.getCols(); j++) {
				grid.get(i, j).setState();
			}
		}
	}
	/**
	 * initializer for any  simulation
	 */
	public abstract void init();
	
	public abstract String name();
}
