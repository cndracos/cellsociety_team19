package grid;

import java.util.ArrayList;

import cell.Cell;

public class SquareGrid extends Grid {
	private double cellLength;
	private double cellWidth;
	private final int DEFAULT_SPACE = 10;
	/**
	 * Creates a grid with the classic square cells
	 * @param n number of rows
	 * @param k number of cols
	 * @param length of screen
	 * @param width of screen
	 */
	public SquareGrid(int n, int k, int length, int width) {
		super(n, k);
		double[] size = getCellSize(length, width, DEFAULT_SPACE);
		cellWidth = size[0];
		cellLength = size[1];
	}

	
	/**
	 * adds the four ordered pairs to the cell object
	 */
	public void addToScreen(Cell c, int n, int k) {
		Double[] coordinates = new Double[8];
		coordinates[0] = k*cellWidth + DEFAULT_SPACE;
		coordinates[1] = n*cellLength + DEFAULT_SPACE;
		coordinates[2] = k*cellWidth + DEFAULT_SPACE;
		coordinates[3] = n*cellLength + + cellLength + DEFAULT_SPACE;
		coordinates[4] = k*cellWidth + cellWidth + DEFAULT_SPACE;
		coordinates[5] = n*cellLength + cellLength + DEFAULT_SPACE;
		coordinates[6] = k*cellWidth +  cellWidth + DEFAULT_SPACE;
		coordinates[7] = n*cellLength + DEFAULT_SPACE;
		c.setPosition(coordinates);
	}
	/**
	 * updates neighbors based on the square rules
	 */
	public void updateNeighbors (int n, int k, Cell c, String sim, boolean torus) {
		ArrayList<Cell>[][] neighbors = this.getNeighborsArray();
		int cols = this.getCols();
		int rows = this.getRows();
		//regardless of sim type, will always have direct neighbors
		if (n-1>=0) neighbors[n-1][k].add(c);
		//only wator needs to loop around the screen
		else if (torus) neighbors[rows - 1][k].add(c);
		if (k-1>=0) neighbors[n][k-1].add(c);
		else if (torus) neighbors[n][cols - 1].add(c);
		if (k+1<cols) neighbors[n][k+1].add(c);
		else if (torus) neighbors[n][0].add(c);
		if (n+1<rows) neighbors[n+1][k].add(c);
		else if (torus) neighbors[0][k].add(c);
		//both segre and life use diagonal neighbors, 
		//so add diagonal neighbors just for those sim types
		if (sim.equals("Segregation")||sim.equals("GameOfLife")) {
			if (n-1>=0) {
				if (k-1>=0) 	neighbors[n-1][k-1].add(c);
				else if (torus) neighbors[n-1][cols-1].add(c);
				if (k+1<cols) neighbors[n-1][k+1].add(c);
				else if (torus) neighbors[n-1][0].add(c);
			}
			else if (torus) {
				if (k-1>=0) 	neighbors[rows-1][k-1].add(c);
				else neighbors[rows-1][cols-1].add(c);
				if (k+1<cols) neighbors[rows-1][k+1].add(c);
				else neighbors[rows-1][0].add(c);
			}
			if (n+1<rows) {
				if (k-1>=0) neighbors[n+1][k-1].add(c);
				else if (torus) neighbors[n+1][cols-1].add(c);
				if (k+1<cols) neighbors[n+1][k+1].add(c);
				else if (torus) neighbors[n+1][0].add(c);
			}
			else if (torus) {
				if (k-1>=0) neighbors[0][k-1].add(c);
				else neighbors[0][cols-1].add(c);
				if (k+1<cols) neighbors[0][k+1].add(c);
				else neighbors[0][0].add(c);
			}
		}
	}
}
