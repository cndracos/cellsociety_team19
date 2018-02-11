package sim;

import java.util.HashMap;
import java.util.Random;

import cell.LifeCell;
import grid.Grid;

public class LifeSim extends Sim{

	public LifeSim(int n, int k, int length, int width, HashMap<String, double[]> keys, String grid) {
		super(n, k, length, width, keys, grid);
		init();
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
					lgrid.updateNeighbors(i, j, l, "Life");
				}
			}
		lgrid.setNeighbors();
	}
	
	public String name() {
		return "GameofLife";
	}
}
