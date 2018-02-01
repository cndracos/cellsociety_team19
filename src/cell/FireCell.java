package cell;

import javafx.scene.paint.Color;

/**
 * Class FireCell is inherited from Super class Cell,
 * and is used for Fire Simulation.
 * @author Yameng Liu
 *
 */
public class FireCell extends Cell{	
	public FireCell(String currState, double updateProb) {
		super(currState, updateProb);
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
		if(currState == "BURNING"){
			return "EMPTY";
		}
		
		boolean isNeighborsBurned = false;
		for(Cell myNeighbor : myNeighbors){
			if(myNeighbor.currState == "BURNING"){
				isNeighborsBurned = true;
				break;
			}
		}
		if(isNeighborsBurned && rand.nextDouble() < updateProb){
			return "BURNING";
		}
		return "TREE";
	}

	@Override
	/**
	 * decide specific color of the cell according to specific rules in fire simulation
	 * @param state current state
	 * @return current color of graphics
	 */
	protected Color colorByState(String state) {
		return state == "EMPTY" ? Color.YELLOW : state == "TREE" ? Color.GREEN : Color.RED;
	}

}
