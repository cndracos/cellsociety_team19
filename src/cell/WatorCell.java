package cell;

import java.util.ArrayList;
import java.util.Random;

import javafx.scene.paint.Color;

public class WatorCell extends Cell{
	private String currState;
	private int fishR;
	private int sharkR;
	private int sharkE;
	private Random rand;
	private final int fishE = 7;
	
	public WatorCell(String currState,int fishR,int sharkR,int sharkE){
		super(currState);
		this.fishR = fishR;
		this.sharkR = sharkR;
		this.sharkE = sharkE;
		rand = new Random();
	}
	
	@Override
	/**
	 * Update states according to simple rules on course website with probability
	 * @return new state
	 */
	protected String updateByRule() {
		if(currState == "FISH"){
			ArrayList<WatorCell> waters = getTypes("WATER");
			//if there is an unoccupied free cell, move to it
			if(waters.size() > 0){
				int key = rand.nextInt(waters.size());
				this.setNewState("WATER");
				waters.get(key).setNewState(currState);
			}
		}
		
		else if(currState == "SHARK"){
			ArrayList<WatorCell> fishs = getTypes("FISH");
			ArrayList<WatorCell> waters = getTypes("WATER");
			//if there is a fish, devour it
			if(fishs.size() > 0){
				int key = rand.nextInt(fishs.size());
				this.setNewState("WATER");
				fishs.get(key).setNewState(currState);
				sharkE += fishE;
				sharkE -= 1;
			}
			//if there is an unoccupied free cell, move to it
			else if(waters.size() > 0){
				int key = rand.nextInt(fishs.size());
				this.setNewState("WATER");
				fishs.get(key).setNewState(currState);
				sharkE -= 1;
			}
			//if a shark consumed all energy, it dies
			if(sharkE == 0){
				this.setNewState("WATER");
			}
		}
		
		return newState;
	}
	
	private ArrayList<WatorCell> getTypes(String type){
		ArrayList<WatorCell> typeArray = new ArrayList<WatorCell>();
		for(Cell myNeighbor : getNeighbors()){
			if(myNeighbor.getState() == type){
				typeArray.add((WatorCell)myNeighbor);
			}
		}
		return typeArray;
	}
	
	private void setNewState(String newState){
		this.newState = newState;
	}
	
	@Override
	protected Color colorByState(String state) {
		return state == "WATER" ? Color.WHITE : state == "FISH" ? Color.GREEN : Color.BLUE;
	}
}
