package cell;

import javafx.scene.paint.Color;

/**
 * Class LifeCell is inherited from Super class Cell,
 * and is used for Game-Of-Life Simulation.
 * @author Yameng Liu
 *
 */
public class LifeCell extends Cell{	
	/**
	 * Constructor of LifeCell class
	 * @param currState current state of cell
	 * @param probCatch this probability will not be used in this class
	 */
	public LifeCell(String currState, double probCatch) {
		super(currState);
	}

	@Override
	/**
	 * Update states according to simple rules on course website with probability
	 * @return new state
	 */
	protected String updateByRule() {
		int count = 0;
		for(Cell myNeighbor:getNeighbors()){
			count += myNeighbor.getState() == "ALIVE"? 1: 0;
		}
		
		//die because of "overpopulation" or "underpopulation"
		if(count < 2 || count > 3){
			return "DEAD";
		}
		//keep alive when is surrounded by 2 or 3 alive neighbors
		if(count == 2 || count == 3){
			return "ALIVE";
		}
		//recover when there are 3 alive neighbors 
		if(getState() == "DEAD" && count == 3){
			return "ALIVE";
		}
		return getState();
	}

	@Override
	/**
	 * decide specific color of the cell according to specific rules in game-of-life simulation
	 * @param state current state
	 * @return current color of graphics
	 */
	protected Color colorByState(String state) {
		return state == "DEAD" ? Color.WHITE : Color.RED;
	}

}
