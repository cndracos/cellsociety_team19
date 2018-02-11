package grid;

import java.util.ArrayList;

import cell.Cell;

public class HexGrid extends Grid {
	private double cellLength;
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
		getCellSize(length, width);
	}

	@Override
	public void getCellSize(int length, int width) {
		cellLength = (length - 2 * DEFAULT_SPACE) / (this.getRows() * 1.0);
		cellWidth = (width - 2 * DEFAULT_SPACE) / (this.getCols() * 1.0);	
	}
	/**
	 * add a hexagon cell to the screen, uses six points, with 
	 * a coordinate pair to tell the cell where on the screen to go
	 */
	@Override
	public void addToScreen(Cell c, int n, int k) {
		Double[] coordinates = new Double[12];
		//boolean even = n%2==0;
		//if (even) //if it is an even row, put cells more aligned to the left 
		//{
			coordinates[0] = n*cellLength + cellLength/2 + DEFAULT_SPACE;
			coordinates[1] = k*cellWidth + DEFAULT_SPACE;
			coordinates[2] = n*cellLength + cellLength + DEFAULT_SPACE;
			coordinates[3] = k*cellWidth + cellWidth/3 + DEFAULT_SPACE;
			coordinates[4] = n*cellLength + cellLength + DEFAULT_SPACE;
			coordinates[5] = k*cellWidth + 2*cellWidth/3 + DEFAULT_SPACE;
			coordinates[6] = n*cellLength + cellLength/2 + DEFAULT_SPACE;
			coordinates[7] = k*cellWidth + cellWidth + DEFAULT_SPACE;
			coordinates[8] = n*cellLength + DEFAULT_SPACE;
			coordinates[9] = k*cellWidth + 2*cellWidth/3 + DEFAULT_SPACE;
			coordinates[10] = n*cellLength + DEFAULT_SPACE;
			coordinates[11] = k*cellWidth + cellWidth/3 + DEFAULT_SPACE;
		//}
		/**else //shift over the odd rows to make the cells interlock
		{
			coordinates[0] = n*cellLength + cellLength + DEFAULT_SPACE;
			coordinates[1] = k*cellWidth - (1/3)*cellWidth + DEFAULT_SPACE;
			coordinates[2] = n*cellLength + (3/2)*cellLength + DEFAULT_SPACE;
			coordinates[3] = k*cellWidth  + DEFAULT_SPACE;
			coordinates[4] = n*cellLength + (3/2)*cellLength + DEFAULT_SPACE;
			coordinates[5] = k*cellWidth + (1/3)*cellWidth + DEFAULT_SPACE;
			coordinates[6] = n*cellLength + cellLength + DEFAULT_SPACE;
			coordinates[7] = k*cellWidth + (2/3)*cellWidth + DEFAULT_SPACE;
			coordinates[8] = n*cellLength + .5*cellLength + DEFAULT_SPACE;
			coordinates[9] = k*cellWidth + (1/3)*cellWidth + DEFAULT_SPACE;
			coordinates[10] = n*cellLength + .5*cellLength + DEFAULT_SPACE;
			coordinates[11] = k*cellWidth  + DEFAULT_SPACE;
		}**/
		c.setPosition(coordinates); //sets to cell to its coordinates
	}
	/**
	 * updates the neighbors around a given cell c according to hexagon rules
	 */
	@Override
	public void updateNeighbors(int n, int k, Cell c, String sim) {
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
		else if (sim.equals("Wator")) neighbors[n][cols-1].add(c);
		if (k+1<cols) neighbors[n][k+1].add(c);
		else if (sim.equals("Wator")) neighbors[n][0].add(c);
		if (n-1>=0) {
			neighbors[n-1][k].add(c);
			if (even) {
				if (k-1>=0) neighbors[n-1][k-1].add(c);
				else if (sim.equals("Wator")) neighbors[n-1][cols-1].add(c);
			}
			else {
				if (k+1<cols) neighbors[n-1][k+1].add(c);
				else if (sim.equals("Wator")) neighbors[n-1][0].add(c);
			}
		}
		else if (sim.equals("Wator")){
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
				else if (sim.equals("Wator")) neighbors[n+1][cols-1].add(c);
			}
			else {
				if (k+1<cols) neighbors[n+1][k+1].add(c);
				else if (sim.equals("Wator")) neighbors[n+1][0].add(c);
			}
		}
		else if (sim.equals("Wator")){
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