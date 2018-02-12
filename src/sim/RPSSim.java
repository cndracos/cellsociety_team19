package sim;

import java.util.HashMap;
import java.util.Random;

import cell.RPSCell;
import grid.Grid;

/**
 * RPS simulation class which inherits from super class Sim.java
 * This simulation simulated the Rock, Paper and Scissor game
 * Each cell represents one of three states, and play game with a randomly chosen neighbor at each step
 * If the cell loses the game, it loses 1 health points; otherwise, it gets 1 health points
 * Once a cell has 0 health points, it will be replaced by the neighbor which beats it
 * @param vals initial health point for each cell
 * @param statesNames array of all states names in this simulation
 * @author Yameng Liu
 */
public class RPSSim extends Sim{
	private double vals;
	private final String[] statesNames = {"WATER","FISH","SHARK"};
	
	/** 
	 * Constructor of the RPSSim class
	 * @param n number of rows
	 * @param k number of columns
	 * @param length length of screen
	 * @param width width of screen
	 * @param vals initial health point for each cell
	 * @param keys map containing initial generation probability for each state
	 * @param grid grid of simulation
	 */
	public RPSSim(int n, int k, int length, int width, 
			double vals, HashMap<String, double[]> keys, String grid) {
		super(n, k, length, width, keys, grid, true);
		this.vals = vals;
		init();
	}

	/**
	 * get all states name
	 * @return all states name
	 */
	public String[] getStateNames() {
		return statesNames;
	}
	
	/** 
	 * Initialization of the grid with cells of different states
	 */
	@Override
	public void init() {
		//gets probability of different states from the key map and initial health value
		double[] probR = this.getKeys().get("ROCK");
		double[] probP = this.getKeys().get("PAPER");
		double[] probS = this.getKeys().get("SCISSOR");
		double health = vals;
		
		//Get the grid for simulation
		Grid RPSgrid = this.getGrid();
		Random rand = this.getRand();
		for (int i = 0; i < RPSgrid.getRows(); i++) {
			for (int j = 0; j < RPSgrid.getCols(); j++) {
				//creates arbitrary RPSCell to be added following specific probability
				RPSCell c;
				double randD = rand.nextDouble();
				if (randD >= probR[0] && randD < probR[1]) {
					c = new RPSCell("ROCK", health);
					RPSgrid.add(c, i, j); 
				}
				else if (randD >= probP[0] && randD < probP[1]) {
					c = new RPSCell("PAPER", health);
					RPSgrid.add(c, i, j); 
				}
				else{
					c = new RPSCell("SCISSOR", health);
					RPSgrid.add(c, i, j);
				}
				//update the neighbor array of the grid
				RPSgrid.updateNeighbors(i, j, c, "RPS", true);
			}
		}
		//send the neighbors array to each cell
		RPSgrid.setNeighbors();
	}	
	
	/**
	 * get the name of simulation
	 * @return name of simulation
	 */
	public String name() {
		return "RPS";
	}
}
