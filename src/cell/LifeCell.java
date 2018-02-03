package cell;

import javafx.scene.paint.Color;

/**
 * Class FireCell is inherited from Super class Cell,
 * and is used for Fire Simulation.
 * @author Yameng Liu
 *
 */
public class LifeCell extends Cell{	
	public LifeCell(String currState, double updateProb) {
		super(currState, updateProb);
	}

	@Override
	/**
	 * Update states according to simple rules on course website with probability
	 * @return new state
	 */
	protected String updateByRule() {
		int count = 0;
		for(Cell myNeighbor:myNeighbors){
			count += myNeighbor.getState() == "ALIVE"? 1: 0;
		}
		if(count < 2 || count > 3){
			return "DEAD";
		}
		if(count == 2 || count == 3){
			return "ALIVE";
		}
		if(currState == "DEAD" && count == 3){
			return "ALIVE";
		}
		return currState;
	}

	@Override
	/**
	 * decide specific color of the cell according to specific rules in fire simulation
	 * @param state current state
	 * @return current color of graphics
	 */
	protected Color colorByState(String state) {
		return state == "DEAD" ? Color.BLACK : Color.RED;
	}

}
