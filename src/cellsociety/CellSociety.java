package cellsociety;

import java.io.FileNotFoundException;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Insets;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.Group;

import xmlparser.*;
import grid.*;

public class CellSociety {
	public static final String DATA_TYPE = "CA";
	// dimensions of the viewing window
	private static final int X_DIMENSION = 420;
	private static final int Y_DIMENSION = 520;
	private static final int BUTTON_INDENT = 20;
	// color of the viewing window
	private static final Paint BACKGROUND_COLOR = Color.BLACK;
	// for animations
	private static double MILLISECOND_DELAY = 1000;
	// buttons for user control
	private static final Button PAUSE_BUTTON = new Button("Pause");
	private static final Button RESUME_BUTTON = new Button("Resume");
	private static final Button STEP_BUTTON = new Button("Step");
	private static final Button NEW_BUTTON = new Button("New");
	// objects that aid with the GUI
	private Timeline timeline;
	private Group root = new Group();
	private Grid grid;
	private Stage simStage;
	
	/**
	 * Default constructor. Should initialize the grid and handle setting the stage, scene, and animation.
	 * @param stage
	 * @throws InterruptedException 
	 * @throws FileNotFoundException
	 */
	public CellSociety(Stage stage) {
		// set up grid and cells with SimulationBuilder
		SimulationBuilder sb = new SimulationBuilder(stage);
		grid = sb.build(X_DIMENSION, Y_DIMENSION - 100);
		// stage
		simStage = stage;
		stage.setTitle(sb.getBuildType());
		stage.setScene(setupSimulationWindow(root, X_DIMENSION, Y_DIMENSION, BACKGROUND_COLOR));
		initGrid(grid);
		// set to visible
		stage.show();
		initButtons();
		// animation specifics
		KeyFrame frame = new KeyFrame(Duration.millis(MILLISECOND_DELAY), e -> step());
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
	private void step() {
		grid.update();
	}
	
	/**
	 * Create the Scene for the simulation (effectively the GUI)
	 */
	private Scene setupSimulationWindow(Group root, int height, int width, Paint background) {
		return new Scene(root, height, width, background);
	}
	
	/**
	 * Initializes this simulation's grid by adding all of the cells to the root.
	 * @param grid the grid object for this simulation
	 */
	private void initGrid(Grid grid) {
		for (int i = 0; i < grid.getRows(); i++) {
			for (int j = 0; j < grid.getCols(); j++) {
				root.getChildren().add(grid.get(i, j));
			}
		}
	}
	
	/**
	 * Resets the simulation to the beginning.
	 */
	private void newSimulation() {
		simStage.close();
		new CellSociety(new Stage());
	}
	
	/**
	 * Set up the buttons for user control.
	 */
	private void initButtons() {
		// to pause the simulation
		PAUSE_BUTTON.setOnAction(__ -> {
			timeline.pause();
		});
		placeButton(PAUSE_BUTTON, BUTTON_INDENT, Y_DIMENSION - 100);
		
		// to resume the simulation
		RESUME_BUTTON.setOnAction(__ -> {
			timeline.play();
		});
		placeButton(RESUME_BUTTON, BUTTON_INDENT, Y_DIMENSION - 75); 
		
		// to switch the simulation to step mode (on first press)
		// subsequent presses will step throught the simulation
		STEP_BUTTON.setOnAction(__ -> {
			timeline.pause();
			step();
		});
		//STEP_BUTTON.setLayoutX(BUTTON_SEPARATION + RESUME_BUTTON.getLayoutX() + RESUME_BUTTON.getWidth());
		placeButton(STEP_BUTTON, BUTTON_INDENT, Y_DIMENSION - 50);
		
		// to change the simulation to a different one
		NEW_BUTTON.setOnAction(__ -> {
			newSimulation();
		});
		
		//NEW_BUTTON.setLayoutX(BUTTON_SEPARATION + STEP_BUTTON.getLayoutX() + STEP_BUTTON.getWidth());
		placeButton(NEW_BUTTON, BUTTON_INDENT, Y_DIMENSION - 25);
		root.getChildren().addAll(PAUSE_BUTTON, RESUME_BUTTON, STEP_BUTTON, NEW_BUTTON);
	}
	
	/**
	 * Initializes the animation and plays it.
	 */
	public void changeAnimationRate() {
		timeline.stop();
		timeline.getKeyFrames().setAll(new KeyFrame(Duration.millis(MILLISECOND_DELAY), e -> step()));
		timeline.play();
	}
	
	/**
	 * Formats the button to be placed at a proper location and sized correctly.
	 */
	private void placeButton(Button button, int xCoord, int yCoord) {
		button.setPadding(new Insets(0, 0, 0, 0));
		button.setPrefSize(20, 100);
		button.setMinWidth(100);
		button.setMaxHeight(25);
		button.setMinHeight(20);
		button.relocate(xCoord,  yCoord);
	}

}

