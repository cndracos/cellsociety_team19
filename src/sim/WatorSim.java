package sim;

import java.util.HashMap;
import java.util.Random;

import cell.WatorCell;
import grid.Grid;

public class WatorSim extends Sim{
	private double[] vals;
	
	public WatorSim(int n, int k, int length, int width, 
			double[] vals, HashMap<String, double[]> keys, String grid) {
		super(n, k, length, width, keys, grid);
		this.vals = vals;
	}

	@Override
	public void init() {
		//gets the array of probs from the key map
		double[] probF = this.getKeys().get("FISH");
		double[] probS = this.getKeys().get("SHARK");
		//gets the other values for the sim from the vals array
		double fishR = vals[0];
		double sharkR = vals[1];
		double sharkE = vals[2];
		Grid wgrid = this.getGrid();
		Random rand = this.getRand();
		for (int i = 0; i < wgrid.getRows(); i++) {
			for (int j = 0; j < wgrid.getCols(); j++) {
				//creates arbitrary WatorCell to be added
				WatorCell w;
				double randD = rand.nextDouble();
				if (randD >= probF[0] && randD < probF[1]) {
					w = new WatorCell("FISH", fishR, sharkR, sharkE);
					wgrid.add(w, i, j); 
				}
				else if (randD >= probS[0] && randD < probS[1]) {
					w = new WatorCell("SHARK", fishR, sharkR, sharkE);
					wgrid.add(w, i, j); 
				}
				else {
					w = new WatorCell("WATER", fishR, sharkR, sharkE);
					wgrid.add(w, i, j);
				}
				wgrid.updateNeighbors(i, j, w, "Wator");
			}
		}
		wgrid.setNeighbors();
	}	
}
