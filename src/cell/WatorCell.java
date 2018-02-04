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
	private int fishMoves;
	private int sharkMoves;
	private final int fishE = 7;
	
	public WatorCell(String currState,double fishR,double sharkR,double sharkE){
		super(currState);
		this.fishR = fishR;
		this.sharkR = sharkR;
		this.sharkE = sharkE;
		int fishMoves = 0;
		int sharkMoves = 0;
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
			ArrayList<WatorCell> waters = getTypes("WATER");
			WatorCell nextCell = null;
			//if there is an unoccupied free cell, move to it
			if(waters.size() > 0){
				//System.out.println("fish moves");
				int key = rand.nextInt(waters.size());
				nextCell = waters.get(key);
				this.newState ="WATER";
				nextCell.newState = "FISH";
				fishMoves += 1;
				//reproduce fish
				if(fishMoves == fishR){
					//System.out.println("fish reproduces");
					this.newState = "FISH";
					fishMoves = 0;
				}
			}
		}
		
		else if(getState().equals("SHARK")){
			ArrayList<WatorCell> fishs = getTypes("FISH");
			ArrayList<WatorCell> waters = getTypes("WATER");
			WatorCell nextCell = null;
			//if it is eligible to move
			if(fishs.size() > 0 || waters.size() > 0){
				//if there is a fish, devour it
				if(fishs.size() > 0){
					System.out.println("shark devour fish");
					int key = rand.nextInt(fishs.size());
					nextCell = fishs.get(key);
					this.newState ="WATER";
					nextCell.newState = "SHARK";
					sharkE += fishE;
					
				}
				//if there is an unoccupied free cell, move to it
				else if(waters.size() > 0){
					System.out.println("shark moves");
					int key = rand.nextInt(waters.size());
					nextCell = waters.get(key);
					this.newState = "WATER";
					nextCell.newState = "SHARK";
				}
				
				sharkE -= 1;
				sharkMoves += 1;
				
				//if a shark consumed all energy, it dies
				if(sharkE == 0){
					System.out.println("shark dies");
					this.newState = "WATER";
				}
				//reproduce shark
				else if(sharkMoves == sharkR && nextCell != null){
					System.out.println("shark reproduces");
					this.newState = "SHARK";
					sharkMoves = 0;
				}
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
