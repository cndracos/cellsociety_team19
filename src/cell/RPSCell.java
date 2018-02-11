package cell;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javafx.scene.paint.Color;

/**
 * Class FireCell is inherited from Super class Cell,
 * and is used for Fire Simulation.
 * @author Yameng Liu
 *
 */
public class RPSCell extends Cell{	
	private Random rand;
	private double health;
	private boolean isUpdated;
	private final double HEALTH;
	
	/**
	 * Constructor of FireCell class
	 * @param currState current state of cell
	 * @param probCatch probability at which the cell catches on fire
	 */
	public RPSCell(String currState, double HEALTH) {
		super(currState);
		if(currState.equals("WHITE")) {
			System.out.println("WHITE");
		}
		this.HEALTH = HEALTH;
		health = HEALTH;
		isUpdated = false;
		rand = new Random();
	}

	@Override
	/**
	 * find the next state to update 
	 */
	public void findState(){
		updateByRule();
	}
	
	@Override
	/**
	 * Update states according to simple rules on course website with probability
	 * @return new state
	 */
	protected String updateByRule() {
		//if state is "empty", do nothing
		if(!getState().equals("WHITE")){
			List<RPSCell> opponents = getOpponents();
			int len = opponents.size();
			if(len != 0) {
				int index = rand.nextInt(len);
				RPSCell opponent = opponents.get(index);
				int res = beat(this,opponent);
				//if two cells have different states, a result is achieved, and two cells are updated
				if(res != 0) {
					setUpdated(true);
					opponent.setUpdated(true);
					//if I win
					if(res == 1) {
						setHealth(getHealth() + 1);
						opponent.setHealth(opponent.getHealth() - 1);
					}
					//if opponent wins
					else {
						setHealth(getHealth() - 1);
						opponent.setHealth(opponent.getHealth() + 1);
					}
					//if either I or opponent dies
					if(getHealth() == 0 || opponent.getHealth() == 0) {
						//if I dies
						if(getHealth() == 0) {
							newState = opponent.getState();
							health = HEALTH;
						}
						//if opponent dies
						else {
							opponent.newState = getState();
							opponent.setHealth(HEALTH);
						}
					}
				}
			}
		}
		return newState;
	}

	private List<RPSCell> getOpponents(){
		List<RPSCell> opponents = new ArrayList<>();
		for(Cell myNeighbor : getNeighbors()) {
			if(!myNeighbor.getState().equals("WHITE") && !((RPSCell)myNeighbor).getUpdated()){
				opponents.add((RPSCell)myNeighbor);
			}
		}
		return opponents;
	}
	
	private int beat(Cell me, Cell opponent) {
		if(me.getState() == opponent.getState()) {
			return 0;
		}
		
		//compute whether or not I win
		boolean isWin = me.getState().equals("ROCK") && opponent.getState().equals("SCISSOR")
					 || me.getState().equals("SCISSOR") && opponent.getState().equals("PAPER")
					 || me.getState().equals("PAPER") && opponent.getState().equals("ROCK");
		
		return isWin ? 1 : -1;
	}
	
	/**
	 * check whether or not the cell is updated
	 * @return updated or not
	 */
	public boolean getUpdated() {
		return isUpdated;
	}
	
	/**
	 * change the updated status of cell to given status
	 * @param isUpdated given status
	 */
	public void setUpdated(boolean isUpdated) {
		this.isUpdated = isUpdated;
	}
	
	/**
	 * check whether or not the cell is updated
	 * @return updated or not
	 */
	public double getHealth() {
		return health;
	}
	
	/**
	 * change the updated status of cell to given status
	 * @param isUpdated given status
	 */
	public void setHealth(double health) {
		this.health = health;
	}
	
	@Override
	/**
	 * update the state of the cell, change updated status
	 */
	public void setState(){
		currState = newState;
		this.setFill(colorByState(currState));
		isUpdated = false;
	}

	/**
	 * decide specific color of the cell according to specific rules in fire simulation
	 * @param state current state
	 * @return current color of graphics
	 */
	protected Color colorByState(String state) {
		return state == "ROCK" ? Color.RED : state == "PAPER" ? Color.BLUE : 
			   state == "SCISSOR" ? Color.GREEN : Color.WHITE;
	}

}
