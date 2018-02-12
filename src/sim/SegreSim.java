package sim;

import java.util.ArrayList;


import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import cell.Cell;
import cell.SegreCell;
import grid.Grid;

public class SegreSim extends Sim{
	private double satisfied;
	private final String[] statesNames = {"EMPTY","X","O"};
	
	/**
	 * Constructor for SegreSim
	 * @param n number of rows
	 * @param k number of cols
	 * @param length of screen
	 * @param width of screen
	 * @param satisfied percentage of similar neighbors needed to satisfy a cells
	 * @param keys values for making random cells
	 * @param grid type of grid
	 */

	public SegreSim(int n, int k, int length, int width, 
			double satisfied, Map<String, double[]> keys, String grid, boolean torus) {
		super(n, k, length, width, keys, grid, torus);
		this.satisfied = satisfied;
		init();
	}

	public String[] getStateNames() {
		return statesNames;
	}

	@Override
	public void init() {
		double[] probX = this.getKeys().get("X");
		double[] probO = this.getKeys().get("O");
		Grid sgrid = this.getGrid();
		Random rand = this.getRand();
		for (int i = 0; i < sgrid.getRows(); i++) {
			for (int j = 0; j < sgrid.getCols(); j++) {
				//makes an arbitrary SegreCell
				SegreCell s;
				double randD = rand.nextDouble();
				if (randD >= probX[0] && randD < probX[1]) {
					s = new SegreCell("X", satisfied);
					sgrid.add(s, i, j);
				}
				else if (randD >= probO[0] && randD < probO[1]) {
					s = new SegreCell("O", satisfied);
					sgrid.add(s, i, j);
				}
				else {
					s = new SegreCell("EMPTY", satisfied);
					sgrid.add(s, i, j);
				}
				sgrid.updateNeighbors(i, j, s, name(), this.getTorus());
			}
		}
		sgrid.setNeighbors();	
	}
	
	@Override
	public Map<String, Double> update() {
		HashMap<String, Double> percentages = new HashMap<String, Double>();
		//creates two ArrayList<SegreCell> to store empty and disatisfied cells
		ArrayList<SegreCell> empty = new ArrayList<SegreCell>();
		ArrayList<SegreCell> disatisfied = new ArrayList<SegreCell>();
		Grid sgrid = this.getGrid();
		for (int i = 0; i < sgrid.getRows(); i++) {
			for (int j = 0; j < sgrid.getCols(); j++) {
				//store the current SegreCell s
				SegreCell s = (SegreCell) sgrid.get(i, j);
				if (s.getState().equals("EMPTY")) {
					empty.add(s);
				}
				else if (!s.isSatisfied()) {
					disatisfied.add(s);
				}
			}
		}
				
		//shuffles the two lists to make the swapping random (and more efficient)
		Collections.shuffle(empty);
		Collections.shuffle(disatisfied);
			
		for (int l = 0; l < Math.min(empty.size(), disatisfied.size()); l++) {
			//swaps the two states, need to getState() disatisfied because we do not know
			//if it is an "X" or an "O"
			empty.get(l).changeState2(disatisfied.get(l).getState());
			disatisfied.get(l).changeState2("EMPTY");
		}
				
		for (int i = 0; i < sgrid.getRows(); i++) {
			for (int j = 0; j < sgrid.getCols(); j++) {
				Cell c = sgrid.get(i, j);
				c.setState();
				if (!percentages.containsKey(c.getState())) {
					percentages.put(c.getState(), 1.0/sgrid.getCols()*sgrid.getRows());
				}
				else {
					percentages.put(c.getState(), 
							(1.0/sgrid.getCols()*sgrid.getRows()) 
							+ percentages.get(c.getState()));
				}
			}
		}
		return percentages;
	}
	
	public String name () {
		return "Segregation";
	}
}
