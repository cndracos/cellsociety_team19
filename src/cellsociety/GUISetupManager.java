package cellsociety;

import grid.Grid;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import xmlparser.SimulationBuilder;

import java.util.Arrays;

import cellsociety.CellSociety;
/**
 * Class that sets up the GUI to prepare for running simulations.
 * @author dylanpowers
 *
 */
public class GUISetupManager {
	private static final int DEFAULT_WIDTH = CellSociety.X_DIMENSION;
	private static final int DEFAULT_HEIGHT = CellSociety.Y_DIMENSION;
	// color of the viewing window
	private static final Paint BACKGROUND_COLOR = Color.BLACK;
	// buttons and properties
	private static final Button PAUSE_BUTTON = new Button("Pause");
	private static final Button RESUME_BUTTON = new Button("Resume");
	private static final Button STEP_BUTTON = new Button("Step");
	private static final Button RESET_BUTTON = new Button("Reset");
	private static final ComboBox<String> CHOOSER = new ComboBox<>(FXCollections.observableList(Arrays.asList(CellSociety.SIMULATIONS)));
	private static final int BUTTON_WIDTH = 100;
	private static final int BUTTON_HEIGHT = 25;
	private static final int BUTTON_SPACING = 10;
	private static final int GRID_SEPARATION = 50;
	// animation rates
	private static final double MAX_RATE = 3.0;
	private static final double MIN_RATE = 0.01;
	private static Slider RATE_SLIDER = new Slider(MIN_RATE, MAX_RATE, CellSociety.ANIMATION_RATE);
	private static final Label RATE_CAPTION = new Label(String.format("Animation rate: %.2f s", RATE_SLIDER.getValue()));
	
	private static BorderPane currentRoot;
	private static CellSociety simulation;
	private static HBox allGrids = new HBox(GRID_SEPARATION);
	
	// default constructor
	public GUISetupManager(CellSociety CS) {
		simulation = CS;
		currentRoot = simulation.getRoot();
		init();
		// center initialized without a simulation (empty)
		// drop-down menu chooses the type of simulation
		// then runs game loop where drop down menu is always active
		// add all non-center elements
	}
	
	public Scene getScene() {
		return new Scene(currentRoot, DEFAULT_WIDTH, DEFAULT_HEIGHT, BACKGROUND_COLOR);
	}
	
	// initialize all necessary GUI components
	private void init() {
		initSlider();
		initButtonPlacement();
		initButtonActions();
		initComboBox();
		initGrid(null);
		currentRoot.setBottom(CHOOSER);
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
		// labels
		RATE_CAPTION.setTextFill(Color.BLACK);
		// how the slider will change the rate of animation
		RATE_SLIDER.valueProperty().addListener(new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> ov,
					Number oldValue, Number newValue) {
				simulation.setRate(newValue.doubleValue());
				RATE_CAPTION.setText(String.format("Animation rate: %.2f s", newValue));
				simulation.changeAnimationRate();
			}
		});
		// add to scene
		currentRoot.getChildren().addAll(RATE_SLIDER, RATE_CAPTION);
	}
	
	
	/**
	 * Set up the buttons for user control.
	 */
	private void initButtonPlacement() {
		// VBox to organize the buttons (on the left
		VBox buttonGroup = new VBox(BUTTON_SPACING);
		buttonGroup.setAlignment(Pos.CENTER_LEFT);
		setButtonProperties(PAUSE_BUTTON);
		setButtonProperties(RESUME_BUTTON); 
		setButtonProperties(STEP_BUTTON);
		setButtonProperties(RESET_BUTTON);
		buttonGroup.getChildren().addAll(PAUSE_BUTTON, RESUME_BUTTON, STEP_BUTTON, RESET_BUTTON);
		currentRoot.setLeft(buttonGroup);
	}
	
	/**
	 * Formats the button to be placed at a proper location and sized correctly.
	 */
	private void setButtonProperties(Button button) {
		button.setMinWidth(BUTTON_WIDTH);
		button.setMaxHeight(BUTTON_HEIGHT);
		button.setMinHeight(BUTTON_HEIGHT - 5);
	}
	
	/**
	 * Initializes actions for all of the buttons, and adds them to the root.
	 */
	private void initButtonActions() {
		// to pause the simulation
		PAUSE_BUTTON.setOnAction(__ -> {
			simulation.getTimeline().pause();
		});
		// to switch the simulation to step mode (on first press)
		// subsequent presses will step throughout the simulation
		STEP_BUTTON.setOnAction(__ -> {
			simulation.getTimeline().pause();
			simulation.step();
		});
		// to resume the simulation
		RESUME_BUTTON.setOnAction(__ -> {
			simulation.getTimeline().play();
		});	
		// to reset the simulation to a new random configuration
		RESET_BUTTON.setOnAction(__ -> {
			addGrid("data/Fire.xml", false);
		});
	}
	
	/**
	 * Initializes the drop-down menu (combo box) that allows the user to choose the type of simulation
	 */
	
	private void initComboBox() {
		CHOOSER.valueProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> ov, String oldValue, String newValue) {
				String filePath = new String("data/" + newValue + ".xml");
				addGrid(filePath, true);
			}
		});
	}
	
	/**
	 * Initializes this simulation's grid by adding all of the cells to the root.
	 * @param grid the grid object for this simulation
	 */
	private void initGrid(Grid grid) {
		// if grid is null, do nothing
		if (grid != null) {
        		Pane gridPane = new Pane();
        		gridPane.setPrefSize(CellSociety.GRID_WIDTH, CellSociety.GRID_HEIGHT);
        		for (int i = 0; i < grid.getRows(); i++) {
        			for (int j = 0; j < grid.getCols(); j++) {
        				gridPane.getChildren().add(grid.get(i, j));
        			}
        		}
        		allGrids.getChildren().add(gridPane);
		}
	}
	
	/**
	 * Adds another grid to the view (for seeing multiple simulations at once)
	 */
	private void addGrid(String filePath, boolean flag) {
		if (!flag) {
        		allGrids = new HBox(GRID_SEPARATION);
        		Grid newGrid = (new SimulationBuilder(filePath)).build(CellSociety.GRID_WIDTH,  CellSociety.GRID_HEIGHT);
        		simulation.setGrid(newGrid);
        		initGrid(newGrid);
        		currentRoot.setCenter(allGrids);
		} else {
			Grid newGrid = (new SimulationBuilder(filePath)).build(CellSociety.GRID_WIDTH,  CellSociety.GRID_HEIGHT);
    			simulation.setGrid(newGrid);
    			initGrid(newGrid);
		}
	}
	
}
