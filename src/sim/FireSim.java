package sim;

import java.util.HashMap;
import java.util.Random;

import cell.FireCell;
import grid.Grid;

public class FireSim extends Sim {
	private double probCatch;

	public FireSim(int n, int k, int length, int width, 
			double probCatch, HashMap<String, double[]> keys, String grid) {
		super(n, k, length, width, keys, grid);
		this.probCatch = probCatch;
		init();
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
				fgrid.updateNeighbors(i, j, f, "Fire");
			}
		}
	    fgrid.setNeighbors();	
	}
}
