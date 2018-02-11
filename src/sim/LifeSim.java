package sim;

import java.util.Map;
import java.util.Random;

import cell.LifeCell;
import grid.Grid;

public class LifeSim extends Sim{

	private final String[] statesNames = {"ALIVE","DEAD"};
	

	/**
	 * Constructs the lifesim
	 * @param n number of rows
	 * @param k number of cols
	 * @param length of screen
	 * @param width of screen
	 * @param keys values to be used when making cells
	 * @param grid type of grid to be used
	 */
	public LifeSim(int n, int k, int length, int width, Map<String, double[]> keys, 
			String grid, boolean torus) {
		super(n, k, length, width, keys, grid, torus);
		init();
	}
	
	public String[] getStateNames() {
		return statesNames;
	}
	
	@Override
	public void init() {
		double[] probLife = this.getKeys().get("ALIVE");
		Grid lgrid = this.getGrid();
		Random rand = this.getRand();
		for (int i = 0; i < lgrid.getRows(); i++) {
			for (int j = 0; j < lgrid.getCols(); j++) {
					double randD = rand.nextDouble();
					//makes an arbitrary LifeCell
					LifeCell l;
					if (randD >= probLife[0] && randD < probLife[1]) {
						l = new LifeCell("ALIVE", 0);
						lgrid.add(l, i, j);
					}
					else {
						l = new LifeCell("DEAD", 0);
						lgrid.add(l, i, j);
					}
					lgrid.updateNeighbors(i, j, l, "Life", this.getTorus());
				}
			}
		lgrid.setNeighbors();
	}

	public String name() {
		return "GameOfLife";
	}
}
