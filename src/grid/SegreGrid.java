package grid;

import cell.Cell;
import cell.SegreCell;

import java.util.ArrayList;
import java.util.Map;
import java.util.Random;

public class SegreGrid extends Grid {
	private ArrayList<Cell>[][] neighbors;
	private double satisfied;
	private int n;
	private int k;
	private Random rand;

	public SegreGrid(int n, int k, double satisfied, Map<String, double[]> keys) {
		super(n, k, keys);
		neighbors = new ArrayList[n][k];
		this.n = n;
		this.k = k;
		this.satisfied = satisfied;
		rand = new Random();
		init();
	}
	
	public ArrayList<Cell> getNeighbors(int n, int k){
		return neighbors[n][k];
	}
	
	public void addNeighbors() {
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < k; j++) {
				neighbors[i][j] = new ArrayList<Cell>();
				if (i-1>=0) {
					neighbors[i][j].add(this.get(i-1, j));
					if (j-1>=0) {
						neighbors[i][j].add(this.get(i-1, j-1));
						neighbors[i][j].add(this.get(i, j-1));
					}
					if (j+1<k) {
						neighbors[i][j].add(this.get(i-1, j+1));
						neighbors[i][j].add(this.get(i, j+1));
					}
				}
				if (i+1<n) {
					neighbors[i][j].add(this.get(i+1, j));
					if (j-1>=0) neighbors[i][j].add(this.get(i+1, j-1));
					if (j+1<k) neighbors[i][j].add(this.get(i+1, j+1));
				}
				this.get(n, k).setNeighbors(neighbors[n][k]);
			}
		}
	}
	
	public void init() {
		double[] probX = this.getKeys().get("X");
		double[] probO = this.getKeys().get("O");
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < k; j++) {
				double randD = rand.nextDouble();
				if (randD > probX[0] && randD <= probX[1]) 
					this.add(new SegreCell("X", satisfied), i, j);
				else if (randD > probO[0] && randD <= probO[1])
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
		ArrayList<Cell> empty = new ArrayList<Cell>();
		ArrayList<Cell> disatisfied = new ArrayList<Cell>();
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < k; j++) {
				SegreCell s = (SegreCell) this.get(i, j);
				if (s.getState().equals("EMPTY")) {
					empty.add(s);
				}
				else if (!s.isSatisfied()) {
					disatisfied.add(s);
				}
			}
		}
		
		for (int l = 0; l < Math.min(empty.size(), disatisfied.size()); l++) {
			empty.get(l).changeState2(disatisfied.get(l).getState());
			disatisfied.get(l).changeState2("EMPTY");
		}
		
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < k; j++) {
				this.get(i, j).setState();
			}
		}
	}
}