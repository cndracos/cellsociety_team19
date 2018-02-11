package grid;

import java.util.ArrayList;

import cell.Cell;

public class HexGrid extends Grid {
	private double cellLength;
	private double cellWidth;
	private final int DEFAULT_SPACE = 10;

	public HexGrid(int n, int k, int length, int width) {
		super(n, k);
		getCellSize(length, width);
	}

	@Override
	public void getCellSize(int length, int width) {
		cellLength = (length - 2 * DEFAULT_SPACE) / (this.getRows() * 1.0);
		cellWidth = (width - 2 * DEFAULT_SPACE) / (this.getCols() * 1.0);	
	}

	@Override
	public void addToScreen(Cell c, int n, int k) {
		Double[] coordinates = new Double[12];
		if (n%2==0) {
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
		}
		else {
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
		}
		c.setPosition(coordinates);
	}

	@Override
	public void updateNeighbors(int n, int k, Cell c, String sim) {
		ArrayList<Cell>[][] neighbors = this.getNeighborsArray();
		int cols = this.getCols();
		int rows = this.getRows();
		if (sim.equals("Fire")) {
			
		}
		else if (sim.equals("Wator")) {
			
		}
		else {
			
		}
		
	}
	
}
