package cell;

import java.util.ArrayList;
import java.util.Random;

import javafx.scene.paint.Color;

public class WatorCell extends Cell{
	private String currState;
	private double fishR;
	private double sharkR;
	private double sharkE;
	private Random rand;
	private final int fishE = 7;
	
	public WatorCell(String currState,double fishR,double sharkR,double sharkE){
		super(currState);
		this.fishR = fishR;
		this.sharkR = sharkR;
		this.sharkE = sharkE;
		rand = new Random();
	}
	
	@Override
	/**
	 * find the next state to update 
	 */
	public void findState(){
		//System.out.println("enter find state");
		updateByRule();
	}
	
	@Override
	/**
	 * Update states according to simple rules on course website with probability
	 * @return new state
	 */
	protected String updateByRule() {
		if(getState().equals("FISH")){
			//System.out.print("enter fish");
			ArrayList<WatorCell> waters = getTypes("WATER");
			//if there is an unoccupied free cell, move to it
			if(waters.size() > 0){
				int key = rand.nextInt(waters.size());
				this.newState ="WATER";
				waters.get(key).newState = "FISH";
			}
		}
		
		else if(getState().equals("SHARK")){
			//System.out.print("enter shark");
			ArrayList<WatorCell> fishs = getTypes("FISH");
			ArrayList<WatorCell> waters = getTypes("WATER");
			//if there is a fish, devour it
			if(fishs.size() > 0){
				int key = rand.nextInt(fishs.size());
				this.newState ="WATER";
				fishs.get(key).newState = "SHARK";
				sharkE += fishE;
				sharkE -= 1;
			}
			//if there is an unoccupied free cell, move to it
			else if(waters.size() > 0){
				int key = rand.nextInt(waters.size());
				this.newState = "WATER";
				waters.get(key).newState = "SHARK";
				sharkE -= 1;
			}
			//if a shark consumed all energy, it dies
			if(sharkE == 0){
				this.newState = "WATER";
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
	
	@Override
	protected Color colorByState(String state) {
		return state == "WATER" ? Color.BLUE : state == "FISH" ? Color.GREEN : Color.YELLOW;
	}
}
