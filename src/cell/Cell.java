package cell;

import java.util.ArrayList;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;

/**
 * This is a super class of Cell, 
 * which will be instantiated by subclasses of specific types of cells.
 * @author Yameng Liu
 *
 */
public abstract class Cell extends Polygon{
	private ArrayList<Cell> myNeighbors;
	protected String currState;
	protected String newState;
	
	/**
	 * Constructor of Cell class
	 * @param currState current state of cell
	 * @param updateProb probability at which the cell updates
	 */
	public Cell(String currState){
		this.setFill(colorByState(currState));
		this.currState = currState;
		this.newState = currState;
	}
	
	/**
	 * Set the position of polygon according to an array of x and y coordinates
	 * @param coordinates array of x and y coordinates of all points
	 */
	public void setPosition(Double[] coordinates) {
		this.getPoints().addAll(coordinates);
	}
	
	
	/**
	 * Set neighbors array of the cell 
	 * Since the neighbors are constant during program, store them initially for the convinience of accessing them
	 * @param neighbors neighbors passed in by grid 
	 */
	public void setNeighbors(ArrayList<Cell> neighbors){
		myNeighbors = neighbors;
	}
	
	/**
	 * find the next state to update to 
	 * call by grid class
	 */
	public void findState(){
		newState = updateByRule();
	}
	
	/**
	 * Get current state
	 * @return current state
	 */
	public String getState(){
		return currState;
	}
	
	/**
	 * Update the state and graphics of the cell
	 */
	public void setState(){
		currState = newState;
		this.setFill(colorByState(currState));
	}

	/**
	 * Get neighbors list of the cell
	 */
	public ArrayList<Cell> getNeighbors(){
		return myNeighbors;
	}

	
	/**
	 * abstract method: instantiate by subclasses according to specific updating rules
	 * If one cell is replaced by another, just replace the currStates and parameters
	 * If two cells are exchanged, exchange their parameters and currStates
	 * If one cell moves to another cells, replace the target cell with current cell's state and parameters,
	 * and update the current state's new state and new parameters
	 * @return new state
	 */
	protected abstract String updateByRule();
	
	/**
	 * abstract method: decide graphic color according to specific state
	 * @return color
	 */
	protected abstract Color colorByState(String state);
}
