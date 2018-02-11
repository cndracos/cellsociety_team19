package sim;

import java.util.ArrayList;

import java.util.Collections;
import java.util.HashMap;
import java.util.Random;

import cell.SegreCell;
import grid.Grid;

public class SegreSim extends Sim{
	private double satisfied;
	
	public SegreSim(int n, int k, int length, int width, 
			double satisfied, HashMap<String, double[]> keys, String grid) {
		super(n, k, length, width, keys, grid);
		this.satisfied = satisfied;
		init();
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
				sgrid.updateNeighbors(i, j, s, "Segre");
			}
		}
		sgrid.setNeighbors();	
	}
	
	@Override
	public void update() {
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
					System.out.println("not happy");
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
				sgrid.get(i, j).setState();
			}
		}
	}
	
	public String name () {
		return "Segregation";
	}
}
