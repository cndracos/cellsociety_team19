package cell;

import java.util.ArrayList;
import java.util.Random;
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
	protected ArrayList<Cell> myNeighbors;
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
	 * update the state of the cell
	 */
	public void setState(){
		currState = newState;
		this.setFill(colorByState(currState));
	}
	
	public void addToScreen(int n, int k) {
		this.setX(n*10 + 5);
		this.setY(k*10 + 5);
		this.setWidth(10);
		this.setHeight(10);
	}
	
	
	/**
	 * update the graphics of the cell
	 */
	public void setGUI(){
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
