package cellsociety;

import java.io.FileNotFoundException;
import java.util.ArrayList;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.scene.layout.BorderPane;

import xmlparser.*;
import grid.*;

/**
 * Class containing all of the necessary data to run a simulation and have the user interact with it.
 * @author dylanpowers
 *
 */
public class CellSociety {
	public static final String DATA_TYPE = "CA";
	protected static final String[] SIMULATIONS = {
			"Wator",
			"Fire",
			"GameOfLife",
			"Segregation"
	};
	// dimensions of the viewing window
	protected static final int X_DIMENSION = 420;
	protected static final int Y_DIMENSION = 520;
	protected static final int GRID_WIDTH = X_DIMENSION - 100;
	protected static final int GRID_HEIGHT = Y_DIMENSION - 50;
	// the offset when the user would like to add multiple simulations at once
	// for animations
	protected static double ANIMATION_RATE = 1.0;
	// objects that aid with the GUI
	private Timeline timeline;
	private BorderPane root = new BorderPane();
	private ArrayList<Grid> grids = null;
	private Stage simStage;
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
		simStage = stage;
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
		for (Grid gr : grids)
			gr.update();
	}
	
	/**
	 * Resets the simulation to the beginning.
	 */
	public void newSimulation() {
		simStage.close();
		new CellSociety(new Stage());
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
	public ArrayList<Grid> grid() {
		return grids;
	}
	
	public void setGrid(Grid newGrid) {
		grids.add(newGrid);
	}
	
	public Timeline getTimeline() {
		return timeline;
	}
	
	public BorderPane getRoot() {
		return root;
	}
	
	// set a new animation rate
	public void setRate(double newRate) {
		ANIMATION_RATE = newRate;
	}
}

