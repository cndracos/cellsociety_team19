package sim;

import java.util.HashMap;

import java.util.Map;
import java.util.Random;

import cell.Cell;
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
	private boolean torus;
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
		Map<String, double[]> keys, String grid, boolean torus) {
		this.keys = (HashMap<String, double[]>) keys;
		rand = new Random();
		this.torus = torus;
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
	 * @return the boolean if there is a torus simulation
	 */
	public boolean getTorus() {
		return torus;
	}
	/**
	 * Iterates twice through the grid, once using the states to
	 * update all cells, then setting the cells to that state
	 * once all have been updated
	 */
	public Map<String, Double> update() {
		HashMap<String, Double> percentages = new HashMap<String, Double>();
		for (int i = 0; i < grid.getRows(); i++) {
			for (int j = 0; j < grid.getCols(); j++) {
				grid.get(i, j).findState();
			}
		}
		for (int i = 0; i < grid.getRows(); i++) {
			for (int j = 0; j < grid.getCols(); j++) {
				Cell c = grid.get(i, j);
				c.setState();
				if (!percentages.containsKey(c.getState())) {
					percentages.put(c.getState(), 1.0/grid.getCols()*grid.getRows());
				}
				else {
					percentages.put(c.getState(), 
							(1.0/grid.getCols()*grid.getRows()) 
							+ percentages.get(c.getState()));
				}
			}
		}
		return percentages;
	}
	/**
	 * initializer for any  simulation
	 */
	public abstract void init();
	/**
	 * @return name of the sim being run
	 */
	public abstract String name();
}
