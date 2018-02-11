package sim;

import java.util.Map;
import java.util.Random;

import cell.FireCell;
import grid.Grid;

public class FireSim extends Sim {
	private double probCatch;
	private final String[] statesNames = {"EMPTY","BURNING","TREE"};
	
	/**
	 * Constructor for a firesim
	 * @param n number of rows
	 * @param k number of cols
	 * @param length of screen
	 * @param width of screen
	 * @param probCatch probability of a tree catching fire
	 * @param keys values for making random cells
	 * @param grid type of grid
	 */
	public FireSim(int n, int k, int length, int width, 
			double probCatch, Map<String, double[]> keys, String grid, boolean torus) {
		super(n, k, length, width, keys, grid, torus);
		this.probCatch = probCatch;
		init();
	}

	public String[] getStateNames() {
		return statesNames;
	}
	
	
	@Override
	public void init() {
		double[] probFire = this.getKeys().get("BURNING");
		Grid fgrid = this.getGrid();
		Random rand = this.getRand();
		for (int i = 0; i < fgrid.getRows(); i++) {
			for (int j = 0; j < fgrid.getCols(); j++) {
				//makes an arbitrary FireCell
				FireCell f;
				//if you are on the border, auto-make an empty cell
				if (i == 0 || j== 0 || i == fgrid.getRows()-1 || j == fgrid.getCols()-1) {
					f = new FireCell("EMPTY", probCatch);
					fgrid.add(f, i, j);	
				}
				else {
					double randD = rand.nextDouble();
					if (randD >= probFire[0] && randD < probFire[1]) {
						f = new FireCell("BURNING", probCatch);
						fgrid.add(f, i, j);
					}
					else {
						f = new FireCell("TREE", probCatch);
						fgrid.add(f, i, j);
					}
				}
				fgrid.updateNeighbors(i, j, f, "Fire", this.getTorus());
			}
		}
	    fgrid.setNeighbors();	
	}

	public String name() {
		return "Fire";
	}
}
