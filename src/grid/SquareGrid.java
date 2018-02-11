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
		getCellSize(length, width);
	}

	public void getCellSize(int length,int width) {
		cellLength = (length - 2 * DEFAULT_SPACE) / (this.getRows() * 1.0);
		cellWidth = (width - 2 * DEFAULT_SPACE) / (this.getCols() * 1.0);
	}
	/**
	 * adds the four ordered pairs to the cell object
	 */
	public void addToScreen(Cell c, int n, int k) {
		Double[] coordinates = new Double[8];
		coordinates[0] = n*cellLength + DEFAULT_SPACE;
		coordinates[1] = k*cellWidth + DEFAULT_SPACE;
		coordinates[2] = n*cellLength + cellLength + DEFAULT_SPACE;
		coordinates[3] = k*cellWidth + DEFAULT_SPACE;
		coordinates[4] = n*cellLength + DEFAULT_SPACE;
		coordinates[5] = k*cellWidth + cellWidth + DEFAULT_SPACE;
		coordinates[6] = n*cellLength +  cellLength + DEFAULT_SPACE;
		coordinates[7] = k*cellWidth + cellWidth + DEFAULT_SPACE;
		c.setPosition(coordinates);
	}
	/**
	 * updates neighbors based on the square rules
	 */
	public void updateNeighbors (int n, int k, Cell c, String sim) {
		ArrayList<Cell>[][] neighbors = this.getNeighborsArray();
		int cols = this.getCols();
		int rows = this.getRows();
		if (n-1>=0) neighbors[n-1][k].add(c);
		else if (sim.equals("Wator")) neighbors[rows - 1][k].add(c);
		if (k-1>=0) neighbors[n][k-1].add(c);
		else if (sim.equals("Wator")) neighbors[n][cols - 1].add(c);
		if (k+1<cols) neighbors[n][k+1].add(c);
		else if (sim.equals("Wator")) neighbors[n][0].add(c);
		if (n+1<rows) neighbors[n+1][k].add(c);
		else if (sim.equals("Wator")) neighbors[0][k].add(c);
		if (sim.equals("Segre")||sim.equals("Life")) {
			if (n-1>=0) {
				if (k-1>=0) 	neighbors[n-1][k-1].add(c);
				if (k+1<cols) neighbors[n-1][k+1].add(c);
			}
			if (n+1<rows) {
				if (k-1>=0) neighbors[n+1][k-1].add(c);
				if (k+1<cols) neighbors[n+1][k+1].add(c);
			}
		}
	}
}
