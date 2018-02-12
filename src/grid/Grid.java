package grid;

import cell.Cell;
import java.util.ArrayList;
/**
 * 
 * This is the superclass of Grid, 
 * which will be implemented by the subclasses of the specific grids
 * @author charliedracos
 *
 */
public abstract class Grid {
	private Cell[][] population;
	private ArrayList<Cell>[][] neighbors;
	private int rows, cols;
	
	/**
	 * Constructor of grid class
	 * @param n number of rows in the grid
	 * @param k number of columns in the grid
	 * 
	 */
	public Grid (int n, int k) {
		rows = n;
		cols = k;
		population = new Cell[rows][cols];
		neighbors = new ArrayList[rows][cols];
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				neighbors[i][j] = new ArrayList<Cell>();
			}
		}
	}

	
	/**
	 * add the cell to specific position on screen according to its coordinate
	 * @param c cell 
	 * @param n which row is it in
	 * @param k which column it is in
	 */
	public abstract void addToScreen(Cell c, int n, int k);
	
	
	/**
	 * Returns the cell at an index
	 * @param n row index
	 * @param k column index
	 * @return cell at the index
	 */
	public Cell get(int n, int k) {
		return population[n][k];
	}
	/**
	 * returns number of rows
	 * @return rows
	 */
	public int getRows() {
		return rows;
	}
	
	/**
	 * Add a cell to the 2d array at a specific index and adds to the screen
	 * @param c Cell to be added
	 * @param n row index
	 * @param k column index
	 * @author Yameng Liu
	 */
	public double[] getCellSize(int length,int height, double DEFAULT_SPACE) {
		double[] size = new double[2];
		size[0] = (length - 2 * DEFAULT_SPACE) / (this.getCols() * 1.0);//cell width
		size[1] = (height - 2 * DEFAULT_SPACE) / (this.getRows() * 1.0);//cell height
		return size;
	}
	
	/**
	 * returns number of columns
	 * @return cols
	 */
	public int getCols() {
		return cols;
	}
	
	/**
	 * Add a cell to the 2d array at a specific index and adds to the screen
	 * @param c Cell to be added
	 * @param n row index
	 * @param k column index
	 * @author Yameng Liu
	 */
	public void add (Cell c, int n, int k) {
		population[n][k] = c;
		addToScreen(c, n, k);
	}
	/**
	 * Goes through all the cells when all are finally added to the grid, 
	 * then gives to them their ArrayList<Cell> neighbors
	 */
	public void setNeighbors() {
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				this.get(i, j).setNeighbors(neighbors[i][j]);
			}
		}
	}
	/**
	 * returns the 2d neighbors array for the subclasses to use
	 * @return neighbors
	 */
	public ArrayList<Cell>[][] getNeighborsArray() {
		return neighbors;
	}
	/**
	 * update the neighbors around the cell c at index n, k, dependent on the sim
	 * @param n row index
	 * @param k column index
	 * @param c cell to be added to neighbor arrays
	 * @param sim type of simulation
	 */
	public abstract void updateNeighbors(int n, int k, Cell c, String sim, boolean torus);
}
