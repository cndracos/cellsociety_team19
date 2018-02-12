package cell;

import javafx.scene.paint.Color;

/**
 * Class LifeCell is inherited from Super class Cell,
 * and is used for Segregation Simulation.
 * @author Yameng Liu
 *
 */
public class SegreCell extends Cell{
	private double satisfied;
	
	/**
	 * Constructor of SegreCell class
	 * Assign initial state of the cell by calling constructor of super class
	 * Assign initial value of satisfied: the proportion of same states as current cell among neighbors above which a cell is defined as "satisfied"
	 * @param currState current state of cell
	 * @param satisfied threshold based on which to decide whether or not a cell is satisfied
	 */
	public SegreCell(String currState, double satisfied) {
		super(currState);
		this.satisfied = satisfied;
	}

	@Override
	/**
	 * find the next state to update to 
	 * call by grid class
	 */
	public void findState(){
		updateByRule();
	}
	
	@Override
	/**
	 * Update states according to simple rules on course website with probability
	 * Do nothing here
	 * @return new state
	 */
	protected String updateByRule() {
		return "null";
	}

	/**
	 * Determine whether the cell is satisfied or not by checking 
	 * the proportion of same states as current cell among neighbors
	 * @return satisfied or not
	 */
	public boolean isSatisfied(){
		double count = 0;
		for (Cell myNeighbor: getNeighbors()){
			count += myNeighbor.getState() == getState() ? 1: 0;
		}
		return count/getNeighbors().size() < satisfied ? false : true;
	}
	
	/**
	 * Change new state to given state
	 * This function is called by grid class for states exchange
	 * @param newState new state to be changed to
	 */
	public void changeState2(String newState){
		this.newState = newState;
	}
	
	@Override
	/**
	 * decide specific color of the cell according to specific rules in fire simulation
	 * @param state current state
	 * @return current color of graphics
	 */
	protected Color colorByState(String state) {
		return state == "EMPTY" ? Color.WHITE : state == "X" ? Color.GREEN : Color.RED;
	}
}
