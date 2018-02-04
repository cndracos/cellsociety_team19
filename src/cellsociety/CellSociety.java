package cellsociety;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.stage.FileChooser;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.Group;

import xmlparser.*;
import grid.*;

public class CellSociety {
	public static final String DATA_TYPE = "CA";
	// dimensions of the viewing window
	private static final int X_DIMENSION = 800;
	private static final int Y_DIMENSION = 600;
	// color of the viewing window
	private static final Paint BACKGROUND_COLOR = Color.WHITE;
	// for animations
	private static final int FRAMES_PER_SECOND = 60;
	private static final int MILLISECOND_DELAY = 1000 / FRAMES_PER_SECOND;
	private static final double SECOND_DELAY = 1.0 / FRAMES_PER_SECOND;
	// buttons for user control
	private static final Button PAUSE_BUTTON = new Button("Pause");
	private static final Button RESUME_BUTTON = new Button("Resume");
	private static final Button STEP_BUTTON = new Button("Step");

	
	
	// objects that aid with the GUI
	private Timeline timeline;
	private Group root;
	private Grid grid;
	
	/**
	 * Default constructor. Should initialize the grid and handle setting the stage, scene, and animation.
	 * @param stage
	 * @throws FileNotFoundException
	 */
	public CellSociety(Stage stage) {
		// set up grid and cells with SimulationBuilder
		SimulationBuilder sb = new SimulationBuilder(stage);
		grid = sb.build();
		initGrid(grid);
		// stage
		stage.setTitle(sb.getBuildType());
		stage.setScene(setupSimulationWindow(root, X_DIMENSION, Y_DIMENSION, BACKGROUND_COLOR));
		// set to visible
		stage.show();
		// animation specifics
		KeyFrame frame = new KeyFrame(Duration.millis(MILLISECOND_DELAY), e -> step(SECOND_DELAY));
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
	private void step(double elapsedTime) {
		grid.update();
	}
	
	/**
	 * Create the Scene for the simulation (effectively the GUI)
	 */
	private Scene setupSimulationWindow(Group root, int height, int width, Paint background) {
		root = new Group();
		initButtons();
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
	 * Set up the buttons for user control.
	 */
	private void initButtons() {
		PAUSE_BUTTON.setOnAction(__ -> {
			timeline.pause();
		});
		RESUME_BUTTON.setOnAction(__ -> {
			timeline.play();
		});
		STEP_BUTTON.setOnAction(__ -> {
			timeline.pause();
		});
	}

}

