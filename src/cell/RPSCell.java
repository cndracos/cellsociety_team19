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
	 * Constructor of FireCell class:
	 * Assign initial state of the cell by calling constructor of super class
	 * Assign value of full health points and each cell's initial health points, initi
	 * isUpdated is true if the cell has computed and decided on the next state to update to, false otherwise
	 * initialize rand
	 * @param currState current state of cell
	 * @param probCatch probability at which the cell catches on fire
	 */
	public RPSCell(String currState, double HEALTH) {
		super(currState);
		this.HEALTH = HEALTH;
		health = HEALTH;
		isUpdated = false;
		rand = new Random();
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
	 * @return new state
	 */
	protected String updateByRule() {
		//get current state's non-updated neighbors
		List<RPSCell> opponents = getOpponents();
		int len = opponents.size();
		//if there is a non-updated neighbors
		if(len != 0) {
			//get a random non-updated neighbor as opponent of current cell to play the RPS game
			int index = rand.nextInt(len);
			RPSCell opponent = opponents.get(index);
			int res = beat(this,opponent);
			//if the result is not a tie, two cells needs to be updated
			if(res != 0) {
				setUpdated(true);
				opponent.setUpdated(true);
				//if current cell win, current cell's health point + 1, opponent' health point - 1
				if(res == 1) {
					setHealth(getHealth() + 1);
					opponent.setHealth(opponent.getHealth() - 1);
				}
				//if opponent wins, current cell's health point - 1, opponent' health point + 1
				else {
					setHealth(getHealth() - 1);
					opponent.setHealth(opponent.getHealth() + 1);
				}
				//if either current cell or opponent dies
				if(getHealth() == 0 || opponent.getHealth() == 0) {
					//if current cell dies, replace current cell with opponent
					if(getHealth() == 0) {
						newState = opponent.getState();
						health = HEALTH;
					}
					//if opponent dies, replace opponent cell with current cell
					else {
						opponent.newState = getState();
						opponent.setHealth(HEALTH);
					}
				}
			}
		}
		return newState;
	}

	/**
	 * Get the opponents list of current cells, which consists of all non-updated neighbors
	 * @return list of opponents
	 */
	private List<RPSCell> getOpponents(){
		List<RPSCell> opponents = new ArrayList<>();
		for(Cell myNeighbor : getNeighbors()) {
			if(!((RPSCell)myNeighbor).getUpdated()){
				opponents.add((RPSCell)myNeighbor);
			}
		}
		return opponents;
	}
	
	/**
	 * This method takes in two states and compute who wins the RPS game
	 * @param me state of current cell
	 * @param opponent state of opponent
	 * @return 0: tie; 1: current cell wins; -1: current cell loses
	 */
	private int beat(Cell me, Cell opponent) {
		//if two states are the same, the result is tie
		if(me.getState() == opponent.getState()) {
			return 0;
		}
		
		//if two states are not the same, the result if computed by RPS rules
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
	 * check the current health point
	 * @return current health point
	 */
	public double getHealth() {
		return health;
	}
	
	/**
	 * change the health point to given value
	 * @param given value of health point to change to
	 */
	public void setHealth(double health) {
		this.health = health;
	}
	
	@Override
	/**
	 * update the current state and graphics of the cell, change updated status
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
