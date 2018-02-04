package cell;


import javafx.scene.paint.Color;

/**
 * Class FireCell is inherited from Super class Cell,
 * and is used for Fire Simulation.
 * @author Yameng Liu
 *
 */
public class LifeCell extends Cell{	
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
		if(count < 2 || count > 3){
			return "DEAD";
		}
		if(count == 2 || count == 3){
			return "ALIVE";
		}
		if(getState() == "DEAD" && count == 3){
			return "ALIVE";
		}
		return getState();
	}

	@Override
	/**
	 * decide specific color of the cell according to specific rules in fire simulation
	 * @param state current state
	 * @return current color of graphics
	 */
	protected Color colorByState(String state) {
		return state == "DEAD" ? Color.WHITE : Color.RED;
	}

}
