package grid;

import cell.Cell;
import cell.SegreCell;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class SegreGrid extends Grid {
	private double satisfied;
	private Random rand;

	public SegreGrid(int n, int k, int length, int width, double satisfied, HashMap<String, double[]> keys) {
		super(n, k, length, width, keys);
		this.satisfied = satisfied;
		rand = new Random();
		init();
	}
	/**
	 * Initializes grid by adding cells to indexes, the calls addNeighbors
	 */
	public void init() {
		double[] probX = this.getKeys().get("X");
		double[] probO = this.getKeys().get("O");
		ArrayList<Cell>[][] neighbors = this.getNeighborsArray();
		for (int i = 0; i < this.getRows(); i++) {
			for (int j = 0; j < this.getCols(); j++) {
				SegreCell s;
				double randD = rand.nextDouble();
				if (randD >= probX[0] && randD < probX[1]) {
					s = new SegreCell("X", satisfied);
					this.add(s, i, j);
				}
				else if (randD >= probO[0] && randD < probO[1]) {
					s = new SegreCell("O", satisfied);
					this.add(s, i, j);
				}
				else {
					s = new SegreCell("EMPTY", satisfied);
					this.add(s, i, j);
				}
				if (i-1>=0) {
					neighbors[i-1][j].add(s);
					if (j-1>=0) 	neighbors[i-1][j-1].add(s);
					if (j+1<this.getCols()) neighbors[i-1][j+1].add(s);
				}
				if (i+1<this.getRows()) {
					neighbors[i+1][j].add(s);
					if (j-1>=0) neighbors[i+1][j-1].add(s);
					if (j+1<this.getCols()) neighbors[i+1][j+1].add(s);
				}
				if (j-1>=0) neighbors[i][j-1].add(s);
				if (j+1<this.getCols()) neighbors[i][j+1].add(s);
			}
		}
		this.setNeighbors();
    }
	/**
	 * Updates the cells on screen, but only looks for disatisfied cells and empty cells, 
	 * then shuffles them for randomness, then runs a loop of the smaller amount of
	 * the two types of cells, and swaps them. 
	 * Then finally sets the new states of the cells
	 */
	@Override
	public void update() {
		//creates two ArrayList<SegreCell> to store empty and disatisfied cells
		ArrayList<SegreCell> empty = new ArrayList<SegreCell>();
		ArrayList<SegreCell> disatisfied = new ArrayList<SegreCell>();
		for (int i = 0; i < this.getRows(); i++) {
			for (int j = 0; j < this.getCols(); j++) {
				//store the current SegreCell s
				SegreCell s = (SegreCell) this.get(i, j);
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
		
		for (int i = 0; i < this.getRows(); i++) {
			for (int j = 0; j < this.getCols(); j++) {
				this.get(i, j).setState();
			}
		}
	}
}
