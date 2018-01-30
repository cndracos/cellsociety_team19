package cell;

import java.util.Random;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 * This is a super class of Cell, 
 * which will be instantiated by subclasses of specific types of cells.
 * @author Yameng Liu
 *
 */
public abstract class Cell extends Rectangle{
	protected Cell[] myNeighbors;
	protected String currState;
	protected String newState;
	protected double updateProb;
	protected Random rand;
	
	/**
	 * Constructor of Cell class
	 * @param currState current state of cell
	 * @param updateProb update probability at which the cell updates
	 */
	public Cell(String currState, double updateProb){
		this.setFill(colorByState(currState));
		this.currState = currState;
		this.updateProb = updateProb;
		rand = new Random();
	}
	
	/**
	 * set neighbors array of the cell 
	 * @param neighbors neighbors passed in by grid 
	 */
	public void setNeighbors(Cell[] neighbors){
		myNeighbors = neighbors;
	}
	
	/**
	 * find the next state to update 
	 */
	public void findState(){
		newState = updateByRule();
	}
	
	/**
	 * update the state and graphics of the cell
	 */
	public void setState(){
		currState = newState;
		this.setFill(colorByState(currState));
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
