package cell;

import javafx.scene.paint.Color;

public class SegreCell extends Cell{	
	public SegreCell(String currState, double updateProb) {
		super(currState, updateProb);
	}

	@Override
	/**
	 * find the next state to update 
	 */
	public void findState(){
		newState = updateByRule();
	}
	
	@Override
	/**
	 * Update states according to simple rules on course website with probability
	 * @return new state
	 */

	protected String updateByRule() {
		if(currState == "EMPTY"){
			return currState;
		}
		
		int count = 0;
		for (Cell myNeighbor: myNeighbors){
			count += myNeighbor.currState == currState ? 1: 0;
		}
		return "EMPTY";
	}

	@Override
	/**
	 * decide specific color of the cell according to specific rules in fire simulation
	 * @param state current state
	 * @return current color of graphics
	 */
	protected Color colorByState(String state) {
		return state == "EMPTY" ? Color.YELLOW : state == "X" ? Color.GREEN : Color.RED;
	}
}
