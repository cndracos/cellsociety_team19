package cell;

import java.util.ArrayList;
import java.util.Random;

import javafx.scene.paint.Color;

/**
 * Class FireCell is inherited from Super class Cell,
 * and is used for Fire Simulation.
 * @author Yameng Liu
 *
 */
public class WatorCell extends Cell{
	private final double FISH_R;
	private final double SHARK_R;
	private final double SHARK_E;
	private Random rand;
	public double fishMoves;
	public double sharkMoves;
	public double sharkE;
	private boolean isUpdated;
	private final int fishE = 1;

	/**
	 * Constructor of WatorCell class
	 * @param currState current state of cell
	 * @param fishR number of cells the fish needs to survive to be able to survive
	 * @param sharkR number of cells the shark needs to survive to be able to survive
	 * @param sharkE initial energy amount of shark
	 */
	public WatorCell(String currState,double fishR,double sharkR,double sharkE){
		super(currState);
		FISH_R = fishR;
		SHARK_R = sharkR;
		SHARK_E = sharkE;
		isUpdated = false;
		setParam(currState);
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
		//if the cell is a fish
		if(getState().equals("FISH")){
			ArrayList<WatorCell> waters = getTypes("WATER");
			WatorCell nextCell = null;
			
			//if there is an unoccupied free cell, move to it
			if(waters.size() > 0){
				fishMoves += 1;
				int key = rand.nextInt(waters.size());
				nextCell = waters.get(key);
				
				//else just swim to the free water
				this.newState ="WATER";
				nextCell.newState = "FISH";
				nextCell.fishMoves = this.fishMoves;
				
				//reproduce fish
				if(fishMoves == FISH_R){
					this.newState = "FISH";
					nextCell.fishMoves = 0;
				}
				
				//update changed cells
				isUpdated = true;
				nextCell.setUpdated(true);
				this.fishMoves = 0;
			}
		}
		
		//if current cell is a shark
		else if(getState().equals("SHARK")){
			ArrayList<WatorCell> fishs = getTypes("FISH");
			ArrayList<WatorCell> waters = getTypes("WATER");
			WatorCell nextCell = null;
			
			//if a shark consumed all energy, it dies
			sharkE -= 1;
			
			if(sharkE <= 0){
				this.newState = "WATER";
				this.sharkMoves = 0;
				this.sharkE = 0;

				isUpdated = true;
				System.out.println("energy after: "+sharkE);
				return newState;
			}
			
			//if shark is eligible to move
			if(fishs.size() > 0 || waters.size() > 0){
				sharkMoves += 1;
				//if there is a fish, devour it
				if(fishs.size() > 0){
					sharkE += fishE;	
					int key = rand.nextInt(fishs.size());
					nextCell = fishs.get(key);
					this.newState ="WATER";
					nextCell.newState = "SHARK";
					nextCell.sharkE = this.sharkE;
					nextCell.sharkMoves = this.sharkMoves;
				}
				
				//otherwise if there is an unoccupied free cell, move to it
				else if(waters.size() > 0){
					int key = rand.nextInt(waters.size());
					nextCell = waters.get(key);
					this.newState = "WATER";
					nextCell.newState = "SHARK";
					nextCell.sharkE = this.sharkE;
					nextCell.sharkMoves = this.sharkMoves;
				}
				
				//change updated status of cells,reduce shark's energy
				isUpdated = true;
				nextCell.setUpdated(true);
				
				//reproduce shark
				if(sharkMoves == SHARK_R && nextCell != null && sharkE >= SHARK_E / 2.0){
					this.newState = "SHARK";
					this.sharkE = SHARK_E;
					nextCell.sharkMoves = 0;
				}
				else {
					this.sharkE = 0;
				}
				
				this.sharkMoves = 0;
			}
		}
		return newState;
	}
	
	/**
	 * get not updated neighbors cells of given types
	 * @param type given type
	 * @return found neighbor lists
	 */
	private ArrayList<WatorCell> getTypes(String type){
		ArrayList<WatorCell> typeArray = new ArrayList<WatorCell>();
		for(Cell myNeighbor : getNeighbors()){
			if(myNeighbor.getState() == type && !((WatorCell)myNeighbor).getUpdated()){
				typeArray.add((WatorCell)myNeighbor);
			}
		}
		return typeArray;
	}
	
	private void setParam(String currState) {
		if(currState == "FISH") {
			this.fishMoves = 0;
		}
		else if(currState == "SHARK") {
			this.sharkMoves = 0;
			this.sharkE = SHARK_E;
		}
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
	
	@Override
	/**
	 * update the state of the cell, change updated status
	 */
	public void setState(){
		currState = newState;
		this.setFill(colorByState(currState));
		isUpdated = false;
	}

	@Override
	/**
	 * decide specific color of the cell according to specific rules in fire simulation
	 * @param state current state
	 * @return current color of graphics
	 */
	protected Color colorByState(String state) {
		return state == "WATER" ? Color.BLUE : state == "FISH" ? Color.GREEN : Color.YELLOW;
	}
}
