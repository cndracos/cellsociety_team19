package cell;

import java.util.ArrayList;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 * This is a super class of Cell, 
 * which will be instantiated by subclasses of specific types of cells.
 * @author Yameng Liu
 *
 */
public abstract class Cell extends Rectangle{
	private ArrayList<Cell> myNeighbors;
	private String currState;
	protected String newState;
	private final int SIZE = 10;
	private final int DEFAULT_DIS = 5;
	/**
	 * Constructor of Cell class
	 * @param currState current state of cell
	 * @param updateProb update probability at which the cell updates
	 */
	public Cell(String currState){
		this.setFill(colorByState(currState));
		this.currState = currState;
		this.newState = currState;
	}
	
	/**
	 * set neighbors array of the cell 
	 * @param neighbors neighbors passed in by grid 
	 */
	public void setNeighbors(ArrayList<Cell> neighbors){
		myNeighbors = neighbors;
	}
	
	/**
	 * find the next state to update 
	 */
	public void findState(){
		newState = updateByRule();
	}
	
	/**
	 * get current state
	 * @return current state
	 */
	public String getState(){
		return currState;
	}
	
	/**
	 * update the state of the cell
	 */
	public void setState(){
		currState = newState;
		this.setFill(colorByState(currState));
	}
	
	public void addToScreen(int n, int k) {
		this.setX(n*SIZE + DEFAULT_DIS);
		this.setY(k*SIZE + DEFAULT_DIS);
		this.setWidth(SIZE);
		this.setHeight(SIZE);
	}
	

	public ArrayList<Cell> getNeighbors(){
		return myNeighbors;
	}

	
	/**
	 * abstract method: instantiate by subclasses according to specific updating rules
	 * @return new state
	 */
	protected abstract String updateByRule();
	
	/**
	 * abstract method: decide graphic color according to specific state
	 * @return color
	 */
	protected abstract Color colorByState(String state);
}
