package cellsociety;

import grid.Grid;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import xmlparser.SimulationBuilder;
/**
 * Class that sets up the GUI to prepare for running simulations.
 * @author dylanpowers
 *
 */
public class GUISetupManager {
	private static final int DEFAULT_WIDTH = 420;
	private static final int DEFAULT_HEIGHT = 520;
	private static final int BUTTON_WIDTH = 100;
	private static final int BUTTON_HEIGHT = 25;
	
	// animation rates
	private static final double MAX_RATE = 3.0;
	private static final double MIN_RATE = 0.01;
	private static double ANIMATION_RATE = 1.0;
	private static Slider RATE_SLIDER = new Slider(MIN_RATE, MAX_RATE, ANIMATION_RATE);
	private static final Label RATE_CAPTION = new Label(String.format("Animation rate: %.2f s", RATE_SLIDER.getValue()));
	private static BorderPane currentRoot;
	
	// default constructor
	public GUISetupManager(BorderPane root) {
		currentRoot = root;
		// center initialized without a simulation (empty)
		// drop-down menu chooses the type of simulation
		// then runs game loop where drop down menu is always active
		// add all non-center elements
	}
	
	private void buildCenter(Stage stage) {
		// initialize grid with a simulation builder
		// put grid in the center of the borderpane
	}
	
	
	/**
	 * Initializes the slider that will concurrently change the rate of animation.
	 */
	private void initSlider() {
		// slider itself
		RATE_SLIDER.setMajorTickUnit(0.5);
		RATE_SLIDER.setMinorTickCount(2);
		RATE_SLIDER.setBlockIncrement(0.25);
		RATE_SLIDER.setShowTickLabels(true);
		RATE_SLIDER.setShowTickMarks(true);
		RATE_SLIDER.setLayoutX(DEFAULT_WIDTH / 2);
		RATE_SLIDER.setLayoutY(DEFAULT_HEIGHT - 70);
		RATE_SLIDER.valueProperty().addListener(new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> ov,
					Number oldValue, Number newValue) {
				ANIMATION_RATE = newValue.doubleValue();
				RATE_CAPTION.setText(String.format("Animation rate: %.2f s", newValue));
				//changeAnimationRate();
			}
		});
		// labels
		RATE_CAPTION.setLayoutX(DEFAULT_WIDTH / 2);
		RATE_CAPTION.setLayoutY(DEFAULT_HEIGHT - 30);
		RATE_CAPTION.setTextFill(Color.WHITE);
		// add to scene
		currentRoot.getChildren().addAll(RATE_SLIDER, RATE_CAPTION);
	}
	
	/**
	 * Formats the button to be placed at a proper location and sized correctly.
	 */
	private void placeButton(Button button, int xCoord, int yCoord) {
		button.setPadding(new Insets(0, 0, 0, 0));
		button.setMinWidth(100);
		button.setMaxHeight(25);
		button.setMinHeight(20);
		button.relocate(xCoord,  yCoord);
	}
	
	private Slider getSlider() {
		return RATE_SLIDER;
	}
	
}
