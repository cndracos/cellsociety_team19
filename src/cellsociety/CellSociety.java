package cellsociety;

import java.io.File;
import java.io.IOException;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.stage.FileChooser;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.Group;

public class CellSociety {
	// title of the window
	private static String simulationType;
	// dimensions of the viewing window
	private static final int X_DIMENSION = 800;
	private static final int Y_DIMENSION = 600;
	// color of the viewing window
	private static final Paint BACKGROUND_COLOR = Color.LIGHTGRAY;
	// for animations
	private static final int FRAMES_PER_SECOND = 60;
	private static final int MILLISECOND_DELAY = 1000 / FRAMES_PER_SECOND;
	private static final double SECOND_DELAY = 1.0 / FRAMES_PER_SECOND;
	
	
	// objects that aid with the GUI
	private Timeline timeline;
	private Group root;
	
	public CellSociety(Stage stage) {
		// stage
		stage.setTitle(simulationType);
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
		
	}
	
	/**
	 * Create the Scene for the simulation (effectively the GUI)
	 */
	private Scene setupSimulationWindow(Group root, int height, int width, Paint background) {
		root = new Group();
		return new Scene(root, height, width, background);
	}
	
	/**
	 * @return the simulation type of this specific instance
	 */
	public String getSimulationType() {
		return simulationType;
	}

}

