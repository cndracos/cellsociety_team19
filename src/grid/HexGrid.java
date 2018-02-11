package grid;

import java.util.ArrayList;

import cell.Cell;

public class HexGrid extends Grid {
	private double cellHeight;
	private double cellWidth;
	private final int DEFAULT_SPACE = 10;
	/**
	 * A grid with Hexagon cells
	 * @param n number of rows
	 * @param k number of cols
	 * @param length the height of the screen
	 * @param width the width of the screen
	 */
	public HexGrid(int n, int k, int length, int width) {
		super(n, k);
		double[] size = getCellSize(length, width, DEFAULT_SPACE);
		cellWidth = size[0];
		cellHeight = size[1];
	}

	@Override
	public double[] getCellSize(int length,int height, double DEFAULT_SPACE) {
		double[] size = new double[2];
		size[0] = (length - 2 * DEFAULT_SPACE) / (this.getCols() + 0.5);//cell width
		size[1] = (height - 2 * DEFAULT_SPACE) / (3 * this.getRows() / 4.0 + 0.25);//cell height
		return size;
	}
	
	/**
	 * add a hexagon cell to the screen, uses six points, with 
	 * a coordinate pair to tell the cell where on the screen to go
	 */
	@Override
	public void addToScreen(Cell c, int n, int k) {
		Double[] coordinates = new Double[12];
		if (n % 2 == 0) {
			coordinates[0] = k*cellWidth + cellWidth/2 + DEFAULT_SPACE;
			coordinates[1] = n*cellHeight + DEFAULT_SPACE;
			coordinates[2] = k*cellWidth + cellWidth + DEFAULT_SPACE;
			coordinates[3] = n*cellHeight + cellHeight/4 + DEFAULT_SPACE;
			coordinates[4] = k*cellWidth + cellWidth + DEFAULT_SPACE;
			coordinates[5] = n*cellHeight + 3*cellHeight/4 + DEFAULT_SPACE;
			coordinates[6] = k*cellWidth + cellWidth/2 + DEFAULT_SPACE;
			coordinates[7] = (n + 1)*cellHeight + DEFAULT_SPACE;
			coordinates[8] = k*cellWidth - cellWidth/2 + DEFAULT_SPACE;
			coordinates[9] = n*cellHeight + 3*cellHeight/4 + DEFAULT_SPACE;
			coordinates[10] = k*cellWidth - cellWidth/2 + DEFAULT_SPACE;
			coordinates[11] = n*cellHeight + cellHeight/4 + DEFAULT_SPACE;
		}
		else {
			coordinates[0] = k*cellWidth + cellWidth + DEFAULT_SPACE;
			coordinates[1] = n*cellHeight - cellHeight/4 + DEFAULT_SPACE;
			coordinates[2] = k*cellWidth + 3 * cellWidth/2 + DEFAULT_SPACE;
			coordinates[3] = n*cellHeight + DEFAULT_SPACE;
			coordinates[4] = k*cellWidth + 3 * cellWidth/2 + DEFAULT_SPACE;
			coordinates[5] = n*cellHeight + cellHeight/2 + DEFAULT_SPACE;
			coordinates[6] = k*cellWidth + cellWidth + DEFAULT_SPACE;
			coordinates[7] = (n + 1)*cellHeight - cellHeight/4 + DEFAULT_SPACE;
			coordinates[8] = k*cellWidth + DEFAULT_SPACE;
			coordinates[9] = n*cellHeight + cellHeight/2 + DEFAULT_SPACE;
			coordinates[10] = k*cellWidth + DEFAULT_SPACE;
			coordinates[11] = n*cellHeight + DEFAULT_SPACE;
		}
		c.setPosition(coordinates);
	}
	/**
	 * updates the neighbors around a given cell c according to hexagon rules
	 */
	@Override
	public void updateNeighbors(int n, int k, Cell c, String sim, boolean torus) {
		ArrayList<Cell>[][] neighbors = this.getNeighborsArray();
		int cols = this.getCols();
		int rows = this.getRows();
		//if it is an even row, the index directly above it is
		//actually its top left neighbors, and top right of odd row,
		//so we have to account for this
		boolean even = n%2==0;
		//here all neighbors are direct neighbors, so we always
		//look for all six neighbors
		if (k-1>=0) neighbors[n][k-1].add(c);
		//and per usual, loop the screen if wator sim
		else if (torus) neighbors[n][cols-1].add(c);
		if (k+1<cols) neighbors[n][k+1].add(c);
		else if (torus) neighbors[n][0].add(c);
		if (n-1>=0) {
			neighbors[n-1][k].add(c);
			if (even) {
				if (k-1>=0) neighbors[n-1][k-1].add(c);
				else if (torus) neighbors[n-1][cols-1].add(c);
			}
			else {
				if (k+1<cols) neighbors[n-1][k+1].add(c);
				else if (torus) neighbors[n-1][0].add(c);
			}
		}
		else if (torus){
			neighbors[rows-1][k].add(c);
			if (even) {
				if (k-1>=0) neighbors[rows-1][k-1].add(c);
				else neighbors[rows-1][cols-1].add(c);
			}
			else {
				if (k+1<cols) neighbors[rows-1][k+1].add(c);
				else neighbors[rows-1][0].add(c);
			}
		}
		if (n+1<rows) {
			neighbors[n+1][k].add(c);
			if (even) {
				if (k-1>=0) neighbors[n+1][k-1].add(c);
				else if (torus) neighbors[n+1][cols-1].add(c);
			}
			else {
				if (k+1<cols) neighbors[n+1][k+1].add(c);
				else if (torus) neighbors[n+1][0].add(c);
			}
		}
		else if (torus){
			neighbors[0][k].add(c);
			if (even) {
				if (k-1>=0) neighbors[0][k-1].add(c);
				else neighbors[0][cols-1].add(c);
			}
			else {
				if (k+1<cols) neighbors[0][k+1].add(c);
				else neighbors[0][0].add(c);
			}
		}
	}
}	
