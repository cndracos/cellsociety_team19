package grid;

import cell.Cell;
import cell.SegreCell;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;
import java.util.Random;

public class SegreGrid extends Grid {
	private double satisfied;
	private Random rand;

	public SegreGrid(int n, int k, int length, int width, double satisfied, Map<String, double[]> keys) {
		super(n, k, length, width, keys);
		this.satisfied = satisfied;
		rand = new Random();
		init();
	}

	public void init() {
		double[] probX = this.getKeys().get("X");
		double[] probO = this.getKeys().get("O");
		for (int i = 0; i < this.getRows(); i++) {
			for (int j = 0; j < this.getCols(); j++) {
				double randD = rand.nextDouble();
				if (randD >= probX[0] && randD < probX[1]) 
					this.add(new SegreCell("X", satisfied), i, j);
				else if (randD >= probO[0] && randD < probO[1])
					this.add(new SegreCell("O", satisfied), i, j);
				else {
					this.add(new SegreCell("EMPTY", satisfied), i, j);
				}
			}
		}
		
		this.addNeighbors();
    }

	
	@Override
	public void update() {
		ArrayList<SegreCell> empty = new ArrayList<SegreCell>();
		ArrayList<SegreCell> disatisfied = new ArrayList<SegreCell>();
		for (int i = 0; i < this.getRows(); i++) {
			for (int j = 0; j < this.getCols(); j++) {
				SegreCell s = (SegreCell) this.get(i, j);
				if (s.getState().equals("EMPTY")) {
					empty.add(s);
				}
				else if (!s.isSatisfied()) {
					disatisfied.add(s);
				}
			}
		}
		
		Collections.shuffle(empty);
		Collections.shuffle(disatisfied);
		
		for (int l = 0; l < Math.min(empty.size(), disatisfied.size()); l++) {
			empty.get(l).changeState2(disatisfied.get(l).getState());
			disatisfied.get(l).changeState2("EMPTY");
		}
		
		for (int i = 0; i < this.getRows(); i++) {
			for (int j = 0; j < this.getCols(); j++) {
				this.get(i, j).setState();
			}
		}
	}
}
