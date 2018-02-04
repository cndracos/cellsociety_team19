package grid;

import java.util.ArrayList;
import java.util.Map;
import java.util.Random;

import cell.*;

public class WatorGrid extends Grid {
	private int fishR;
	private int sharkR;
	private int sharkE;
	private Random rand;

	public WatorGrid(int n, int k, int fishR, int sharkR, int sharkE, Map<String, double[]> keys) {
		super(n, k, keys);
		this.fishR = fishR;
		this.sharkR = sharkR;
		this.sharkE = sharkE;
	}
	
	public void init() {
		double[] probF = this.getKeys().get("Fish");
		double[] probS = this.getKeys().get("Shark");
		for (int i = 0; i < this.getRows(); i++) {
			for (int j = 0; j < this.getCols(); j++) {
				double randD = rand.nextDouble();
				if (randD >= probF[0] && randD < probF[1]) 
					this.add(new WatorCell("FISH", fishR, sharkR, sharkE), i, j);
				else if (randD >= probS[0] && randD < probS[1])
					this.add(new WatorCell("SHARK", fishR, sharkR, sharkE), i, j);
				else {
					this.add(new WatorCell("WATER", fishR, sharkR, sharkE), i, j);
				}
			}
		}	
		this.addNeighbors();
	}
	
	@Override
	public void addNeighbors() {
			for (int i = 0; i < this.getRows(); i++) {
				for (int j = 0; j < this.getCols(); j++) {
					this.getNeighborsArray()[i][j] = new ArrayList<Cell>();
					if (i-1>=0) this.getNeighborsArray()[i][j].add(this.get(i-1, j));
					else this.getNeighborsArray()[i][j].add(this.get(this.getRows(), j));
					if (j-1>=0) this.getNeighborsArray()[i][j].add(this.get(i, j-1));
					else this.getNeighborsArray()[i][j].add(this.get(i, this.getCols()));
					if (j+1<this.getCols()) this.getNeighborsArray()[i][j].add(this.get(i, j+1));
					else this.getNeighborsArray()[i][j].add(this.get(i, 0));
					if (i+1<this.getRows()) this.getNeighborsArray()[i][j].add(this.get(i+1, j));
					else this.getNeighborsArray()[i][j].add(this.get(0, j));
					this.get(i, j).setNeighbors(this.getNeighborsArray()[i][j]);
				}
			}
	}

}
