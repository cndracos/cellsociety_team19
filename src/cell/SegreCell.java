package cell;

import javafx.scene.paint.Color;

public class SegreCell extends Cell{
	private double probCatch;
	public SegreCell(String currState, double probCatch) {
		super(currState);
		this.probCatch = probCatch;
	}

	@Override
	/**
	 * Update states according to simple rules on course website with probability
	 * @return new state
	 */
	protected String updateByRule() {
		return "null";
	}

	public boolean isSatisfied(){
		int count = 0;
		for (Cell myNeighbor: getNeighbors()){
			count += myNeighbor.getState() == getState() ? 1: 0;
		}
		return count/getNeighbors().size() < probCatch ? false : true;
	}
	
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
