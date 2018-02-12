package cellsociety;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.stage.Stage;
import javafx.util.Duration;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import sim.Sim;

/**
 * Class containing all of the necessary data to run a simulation and have the user interact with it.
 * @author dylanpowers
 *
 */
@XmlRootElement(name = "data")
public class CellSociety {
	public static final String DATA_TYPE = "CA";
	protected static final String[] SIMULATIONS = {
			"Fire",
			"Wator",
			"GameOfLife",
			"Segregation",
			"RPS"
	};
	protected static final String[] SHAPES = {
			"SQUARE",
			"TRIANGLE",
			"HEXAGON"
	};
	protected static final String[] LOCATIONS = {
			"Top left",
			"Top right",
			"Bottom left",
			"Bottom right"
	};
	// dimensions of the viewing window
	protected static final int X_DIMENSION = 840;
	protected static final int Y_DIMENSION =840;
	protected static final int GRID_WIDTH = 300;
	protected static final int GRID_HEIGHT = 300;
	private static Map<String, Double> cellData = new HashMap<String, Double>();
	// the offset when the user would like to add multiple simulations at once
	// for animations
	protected static double ANIMATION_RATE = 1.0;
	// objects that aid with the GUI
	private Timeline timeline;
	private ArrayList<Sim> sims = new ArrayList<Sim>();
	private GUISetupManager GSM;
	
	/**
	 * Default constructor. Should initialize the grid and handle setting the stage, scene, and animation.
	 * @param stage
	 * @throws InterruptedException 
	 * @throws FileNotFoundException
	 */
	public CellSociety(Stage stage) {
		// set up grid and cells with SimulationBuilder
		GSM = new GUISetupManager(this);
		// stage
		stage.setTitle(DATA_TYPE);
		stage.setScene(GSM.getScene());
		// set to visible
		stage.show();
		// animation specifics
		KeyFrame frame = new KeyFrame(Duration.millis(ANIMATION_RATE * 1000), e -> step());
		timeline = new Timeline();
		timeline.getKeyFrames().add(frame);
		timeline.setCycleCount(Timeline.INDEFINITE);
		timeline.play();
	}
	
	/**
	 * This is called by the class Application every frame
	 * Each frame, cells and grid are updated in their respective classes,
	 * and the GUI is updated.
	 */
	public void step() {
		for (Sim sim : sims) {
			cellData = sim.update();
		}
	}
	
	/**
	 * Initializes the animation and plays it.
	 */
	public void changeAnimationRate() {
		timeline.stop();
		timeline.getKeyFrames().setAll(new KeyFrame(Duration.millis(ANIMATION_RATE * 1000), e -> step()));
		timeline.play();
	}
	
	// returns the grid for this simulation
	public ArrayList<Sim> sims() {
		return sims;
	}
	
	public void addSim(Sim newSim) {
		sims.add(newSim);
	}
	
	// gets number of simulations currently running
	public int numSimulations() {
		return sims.size();
	}
	
	public Timeline getTimeline() {
		return timeline;
	}
	
	// clears all simulations
	public void clearSims() {
		sims.clear();
	}
	
	// set a new animation rate
	public void setRate(double newRate) {
		ANIMATION_RATE = newRate;
	}
	
	public String[] getCellNames(int index) {
		return sims.get(index).getStateNames();
	}
	
	public Map<String, Double> getCellData() {
		return cellData;
	}
}

