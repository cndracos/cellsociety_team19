package sim;

import java.util.HashMap;
import java.util.Random;

import cell.RPSCell;
import grid.Grid;

public class RPSSim extends Sim{
	private double vals;
	private final String[] statesNames = {"WATER","FISH","SHARK"};
	
	public RPSSim(int n, int k, int length, int width, 
			double vals, HashMap<String, double[]> keys, String grid) {
		super(n, k, length, width, keys, grid, true);
		this.vals = vals;
		init();
	}

	public String[] getStateNames() {
		return statesNames;
	}
	
	@Override
	public void init() {
		//gets the array of probs from the key map
		double[] probR = this.getKeys().get("ROCK");
		double[] probP = this.getKeys().get("PAPER");
		//gets the other values for the sim from the vals array
		double health = vals;
		Grid RPSgrid = this.getGrid();
		Random rand = this.getRand();
		for (int i = 0; i < RPSgrid.getRows(); i++) {
			for (int j = 0; j < RPSgrid.getCols(); j++) {
				//creates arbitrary WatorCell to be added
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
				RPSgrid.updateNeighbors(i, j, c, "RPS", true);
			}
		}
		RPSgrid.setNeighbors();
	}	
	
	public String name() {
		return "RPS";
	}
}
