package grid;

import java.util.ArrayList;

import cell.Cell;

public class SquareGrid extends Grid {
	private double cellLength;
	private double cellWidth;
	private final int DEFAULT_SPACE = 10;

	public SquareGrid(int n, int k, int length, int width) {
		super(n, k);
		getCellSize(length, width);
	}
	
	public void getCellSize(int length,int width) {
		cellLength = (length - 2 * DEFAULT_SPACE) / (this.getRows() * 1.0);
		cellWidth = (width - 2 * DEFAULT_SPACE) / (this.getCols() * 1.0);
	}
	
	public void addToScreen(Cell c, int n, int k) {
		c.setX(n*cellLength + DEFAULT_SPACE);
		c.setY(k*cellWidth + DEFAULT_SPACE);
		c.setWidth(cellWidth);
		c.setHeight(cellLength);
	}
	
	public void updateNeighbors (int n, int k, Cell c, String sim) {
		ArrayList<Cell>[][] neighbors = this.getNeighborsArray();
		int cols = this.getCols();
		int rows = this.getRows();
		if (sim.equals("Fire")) {
			if (n-1>=0) neighbors[n-1][k].add(c);
			if (k-1>=0) neighbors[n][k-1].add(c);
			if (k+1<cols) neighbors[n][k+1].add(c);
			if (n+1<rows) neighbors[n+1][k].add(c);	
		}
		else if (sim.equals("Wator")) {
			if (n-1>=0) neighbors[n-1][k].add(c);
				else neighbors[rows - 1][k].add(c);
			if (k-1>=0) neighbors[n][k-1].add(c);
				else neighbors[n][cols - 1].add(c);
			if (k+1<cols) neighbors[n][k+1].add(c);
				else neighbors[n][0].add(c);
			if (n+1<rows) neighbors[n+1][k].add(c);
				else neighbors[0][k].add(c);
		}
		else {
			if (n-1>=0) {
				if (k-1>=0) 	neighbors[n-1][k-1].add(c);
				if (k+1<cols) neighbors[n-1][k+1].add(c);
			}
			if (n+1<rows) {
				neighbors[n+1][k].add(c);
				if (k-1>=0) neighbors[n+1][k-1].add(c);
				if (k+1<cols) neighbors[n+1][k+1].add(c);
			}
			if (k-1>=0) neighbors[n][k-1].add(c);
			if (k+1<cols) neighbors[n][k+1].add(c);
		}
	}

}
