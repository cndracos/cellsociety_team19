package grid;

import java.util.ArrayList;

import cell.Cell;

public class HexGrid extends Grid {
	private double cellHeight;
	private double cellWidth;
	private final int DEFAULT_SPACE = 50;

	public HexGrid(int n, int k, int length, int width) {
		super(n, k);
		getCellSize(length, width);
	}

	@Override
	public void getCellSize(int length, int width) {
		cellHeight = (length - 2 * DEFAULT_SPACE) / (this.getRows() * 3.0 / 4 + 0.25);
		cellWidth = (width - 2 * DEFAULT_SPACE) / (this.getCols() * 1.0 + 0.5);
	}

	@Override
	public void addToScreen(Cell c, int n, int k) {
		Double[] coordinates = new Double[12];
		if (n % 2 == 0) {
			coordinates[0] = k*cellWidth + cellWidth + DEFAULT_SPACE + 10;
			coordinates[1] = n*cellHeight + DEFAULT_SPACE;
			coordinates[2] = k*cellWidth + 3 * cellWidth/2 + DEFAULT_SPACE + 10;
			coordinates[3] = n*cellHeight + cellHeight/4 + DEFAULT_SPACE;
			coordinates[4] = k*cellWidth + 3 * cellWidth/2 + DEFAULT_SPACE + 10;
			coordinates[5] = n*cellHeight + 3*cellHeight/4 + DEFAULT_SPACE;
			coordinates[6] = k*cellWidth + cellWidth + DEFAULT_SPACE + 10;
			coordinates[7] = (n + 1)*cellHeight + DEFAULT_SPACE;
			coordinates[8] = k*cellWidth + DEFAULT_SPACE + 10;
			coordinates[9] = n*cellHeight + 3*cellHeight/4 + DEFAULT_SPACE;
			coordinates[10] = k*cellWidth + DEFAULT_SPACE + 10;
			coordinates[11] = n*cellHeight + cellHeight/4 + DEFAULT_SPACE;
		}
		else {
			coordinates[0] = k*cellWidth + 3 * cellWidth/2 + DEFAULT_SPACE;
			coordinates[1] = n*cellHeight - cellHeight/4 + DEFAULT_SPACE;
			coordinates[2] = k*cellWidth + 2 * cellWidth + DEFAULT_SPACE;
			coordinates[3] = n*cellHeight + DEFAULT_SPACE;
			coordinates[4] = k*cellWidth + 2 * cellWidth + DEFAULT_SPACE;
			coordinates[5] = n*cellHeight + cellHeight/2 + DEFAULT_SPACE;
			coordinates[6] = k*cellWidth + 3 * cellWidth/2 + DEFAULT_SPACE;
			coordinates[7] = (n + 1)*cellHeight -cellHeight/4 + DEFAULT_SPACE;
			coordinates[8] = k*cellWidth + cellWidth/2 + DEFAULT_SPACE;
			coordinates[9] = n*cellHeight + cellHeight/2 + DEFAULT_SPACE;
			coordinates[10] = k*cellWidth + cellWidth / 2 + DEFAULT_SPACE;
			coordinates[11] = n*cellHeight + DEFAULT_SPACE;
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
