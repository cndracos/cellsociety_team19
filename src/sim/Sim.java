package sim;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import grid.Grid;
import grid.SquareGrid;

public abstract class Sim {
	private Map<String, double[]> keys;
	private Grid grid;
	private Random rand;
	
	public Sim (int n, int k, int length, int width, 
			HashMap<String, double[]> keys, String grid) {
		this.keys = keys;
		rand = new Random();
		if (grid.equals("SQUARE")) {
			this.grid = new SquareGrid(n, k, length, width);
		}
		else if (grid.equals("TRIANGLE")) {
			
		}
		else {
			
		}
		init();
	}
	
	public Grid getGrid() {
		return grid;
	}
	
	public Map<String, double[]> getKeys() {
		return keys;
	}
	
	public Random getRand() {
		return rand;
	}
	
	public void update() {
		for (int i = 0; i < grid.getRows(); i++) {
			for (int j = 0; j < grid.getCols(); j++) {
				grid.get(i, j).findState();
			}
		}
		for (int i = 0; i < grid.getRows(); i++) {
			for (int j = 0; j < grid.getCols(); j++) {
				grid.get(i, j).setState();
			}
		}
	}
	
	public abstract void init();
}
