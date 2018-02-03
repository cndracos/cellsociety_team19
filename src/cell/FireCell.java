package cell;

import java.util.Random;

import javafx.scene.paint.Color;

/**
 * Class FireCell is inherited from Super class Cell,
 * and is used for Fire Simulation.
 * @author Yameng Liu
 *
 */
public class FireCell extends Cell{	
	private Random rand;
	private double probCatch;
	public FireCell(String currState, double probCatch) {
		super(currState);
		this.probCatch = probCatch;
		rand = new Random();
	}

	@Override
	/**
	 * Update states according to simple rules on course website with probability
	 * @return new state
	 */
	protected String updateByRule() {
		if(getState() == "EMPTY"){
			return getState();
		}
		if(getState() == "BURNING"){
			return "EMPTY";
		}
		
		boolean isNeighborsBurned = false;
		for(Cell myNeighbor : getNeighbors()){
			if(myNeighbor.getState() == "BURNING"){
				isNeighborsBurned = true;
				break;
			}
		}
		if(isNeighborsBurned && rand.nextDouble() < probCatch){
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
