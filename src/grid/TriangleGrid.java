package grid;

import java.util.ArrayList;

import cell.Cell;

public class TriangleGrid extends Grid {
	private double cellWidth;
	private double cellHeight;
	private final int DEFAULT_SPACE = 10;
	/**
	 * Grid that contains triangle cells
	 * @param n number of rows
	 * @param k number of cols
	 * @param length of screen
	 * @param width of screen
	 */
	public TriangleGrid(int n, int k, int length, int width) {
		super(n, k);
		getCellSize(length, width);
	}

	@Override
	public void getCellSize(int length, int width) {
		cellWidth = (length - 2 * DEFAULT_SPACE) / (this.getRows() * 1.0);
		cellHeight = (width - 2 * DEFAULT_SPACE) / (this.getCols() * 1.0);	
	}
	/**
	 * Adds the three ordered pairs of coordinates for the corners of the 
	 * triangle, and has two cases for whether a triangle is facing up or down
	 */
	@Override
	public void addToScreen(Cell c, int n, int k) {
		Double[] coordinates = new Double[6];
		boolean facingUp = (n+k)%2==0;
		coordinates[0] = n*cellWidth + cellWidth/2 + DEFAULT_SPACE;
		coordinates[2] = n*cellWidth + DEFAULT_SPACE;
		coordinates[4] = n*cellWidth + cellWidth + DEFAULT_SPACE;
		if (facingUp) {
			coordinates[1] = k*cellHeight + DEFAULT_SPACE;
			coordinates[3] = k*cellHeight + cellHeight + DEFAULT_SPACE;
			coordinates[5] = k*cellHeight + cellHeight + DEFAULT_SPACE;
		}
		else {
			coordinates[1] = k*cellHeight + cellHeight + DEFAULT_SPACE; 
			coordinates[3] = k*cellHeight + DEFAULT_SPACE; 
			coordinates[5] =  k*cellHeight + DEFAULT_SPACE; 
		}
		c.setPosition(coordinates);
	}
	/**
	 * updates neighbors based on triangle rules, any 
	 * triangle is shares a corner with is a neight (up to 12)
	 */
	@Override
	public void updateNeighbors(int n, int k, Cell c, String sim, boolean torus) {
		ArrayList<Cell>[][] neighbors = this.getNeighborsArray();
		//triangles have different direct neighbors based on their directions
		boolean facingUp = n+k%2==0;
		int cols = this.getCols();
		int rows = this.getRows();
		//once again always get the direct neighbors
		if (k-1>=0) neighbors[n][k-1].add(c);
		//and loop if it is a wator sim
		else if (sim.equals("Wator")||torus)neighbors[n][cols-1].add(c);
		if (k+1<cols) neighbors[n][k+1].add(c);
		else if (sim.equals("Wator")||torus) neighbors[n][0].add(c);
		if (facingUp) 
			if (n+1<rows) neighbors[n+1][k].add(c); 
			else if (sim.equals("Wator")||torus) neighbors[0][k].add(c); 
		else {
			if (n-1>=0) neighbors[n-1][k].add(c);
			else if (sim.equals("Wator")||torus) neighbors[rows-1][k].add(c);
		}
		//if segre or life, go ahead and get all twelve neighbors
		if (sim.equals("Segre") || sim.equals("Life")){
			if (k-2>=0) neighbors[n][k-2].add(c);
			else if (torus) {
				if (k-1>=0) neighbors[n][cols-1].add(c);
				else neighbors[n][cols-2].add(c);
			}
			if (k+2<cols) neighbors[n][k+2].add(c);
			else if (torus) {
				if (k+1<cols) neighbors[n][0].add(c);
				else neighbors[n][1].add(c);
			}
			if (n-1>=0) {
				if (facingUp) neighbors[n-1][k].add(c);
				if (k-1>=0) neighbors[n-1][k-1].add(c);
				else if (torus) neighbors[n-1][cols-1].add(c);
				if (k+1<cols) neighbors[n-1][k+1].add(c);
				else if (torus) neighbors[n-1][0].add(c);
				if (!facingUp) {
					if (k-2>=0) neighbors[n-1][k-2].add(c);
					else if (torus) {
						if (k-1>=0) neighbors[n-1][cols-1].add(c);
						else neighbors[n-1][cols-2].add(c);
					}
					if (k+2<cols) neighbors[n-1][k+2].add(c);
					else if (torus) {
						if (k+1<cols) neighbors[n-1][0].add(c);
						else neighbors[n-1][1].add(c);
					}
				}
			}
			else if (torus) {
				if (facingUp) neighbors[rows-1][k].add(c);
				if (k-1>=0) neighbors[rows-1][k-1].add(c);
				else neighbors[rows-1][cols-1].add(c);
				if (k+1<cols) neighbors[rows-1][k+1].add(c);
				else neighbors[rows-1][0].add(c);
				if (!facingUp) {
					if (k-2>=0) neighbors[rows-1][k-2].add(c);
					else {
						if (k-1>=0) neighbors[rows-1][cols-1].add(c);
						else neighbors[rows-1][cols-2].add(c);
					}
					if (k+2<cols) neighbors[rows-1][k+2].add(c);
					else {
						if (k+1<cols) neighbors[rows-1][0].add(c);
						else neighbors[rows-1][1].add(c);
					}
				}
			}
			if (n+1<rows) {
				if (!facingUp) neighbors[n+1][k].add(c);
				if (k-1>=0) neighbors[n+1][k-1].add(c);
				else if (torus) neighbors[n+1][cols-1].add(c);
				if (k+1<cols) neighbors[n+1][k+1].add(c);
				else if (torus) neighbors[n+1][0].add(c);
				if (facingUp) {
					if (k-2>=0) neighbors[n+1][k-2].add(c);
					else if (torus) {
						if (k-1>=0) neighbors[n+1][cols-1].add(c);
						else neighbors[n+1][cols-2].add(c);
					}
					if (k+2<cols) neighbors[n+1][k+2].add(c);
					else if (torus) {
						if (k+1<cols) neighbors[n+1][0].add(c);
						else neighbors[n+1][1].add(c);
					}
				}
			}
			else if (torus) {
				if (!facingUp) neighbors[0][k].add(c);
				if (k-1>=0) neighbors[0][k-1].add(c);
				else neighbors[0][cols-1].add(c);
				if (k+1<cols) neighbors[0][k+1].add(c);
				else neighbors[0][0].add(c);
				if (facingUp) {
					if (k-2>=0) neighbors[0][k-2].add(c);
					else {
						if (k-1>=0) neighbors[0][cols-1].add(c);
						else neighbors[0][cols-2].add(c);
					}
					if (k+2<cols) neighbors[0][k+2].add(c);
					else {
						if (k+1<cols) neighbors[0][0].add(c);
						else neighbors[0][1].add(c);
					}
				}
			}
		}
	}
}