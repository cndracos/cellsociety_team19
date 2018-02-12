package cellsociety;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.control.TabPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import xmlparser.SimulationBuilder;

import java.util.Arrays;

import cellsociety.CellSociety;
import sim.Sim;
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
	private static final Button CLEAR_BUTTON = new Button("Clear");
	private static final Button BEGIN_BUTTON = new Button("Begin");
	private static final ComboBox<String> typeChooser = new ComboBox<>(FXCollections.observableList(Arrays.asList(CellSociety.SIMULATIONS)));
	private static final ComboBox<String> shapeChooser = new ComboBox<>(FXCollections.observableList(Arrays.asList(CellSociety.SHAPES)));
	private static final ComboBox<String>locationChooser = new ComboBox<>(FXCollections.observableList(Arrays.asList(CellSociety.LOCATIONS)));
	private static final int BUTTON_WIDTH = 100;
	private static final int BUTTON_HEIGHT = 25;
	private static final int BUTTON_SPACING = 10;
	private static final int GRID_SEPARATION = 20;
	// animation rates and accompanying slider
	private static final double MAX_RATE = 3.0;
	private static final double MIN_RATE = 0.01;
	private static Slider RATE_SLIDER = new Slider(MIN_RATE, MAX_RATE, CellSociety.ANIMATION_RATE);
	private static final Label RATE_CAPTION = new Label(String.format("Animation rate: %.2f s", RATE_SLIDER.getValue()));
	
	private static BorderPane currentRoot;
	private static CellSociety simulation;
	private static GridPane allGrids = new GridPane();
	private static VBox buttonGroup = new VBox(BUTTON_SPACING);
	private static HBox bottom = new HBox(GRID_SEPARATION);
	
	// default constructor
	public GUISetupManager(CellSociety CS) {
		simulation = CS;
		currentRoot = new BorderPane(allGrids, null, null, bottom, buttonGroup);
		init();
	}
	// returns the scene for this GUI
	public Scene getScene() {
		return new Scene(currentRoot, DEFAULT_WIDTH, DEFAULT_HEIGHT, BACKGROUND_COLOR);
	}
	
	/**
	 * Initialize all necessary GUI components. This is done only once (on initialization).
	 */
	private void init() {
		initGridPane();
		initSlider();
		initButtonPlacement();
		initButtonActions();
		initComboBoxes();
		// initialize grid to null because user must pick the grid type to begin with
		initGrid(null);
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
		// add to scene, use a VBox so that the caption can stay with the slider
		VBox sliderGroup = new VBox(BUTTON_SPACING);
		sliderGroup.getChildren().addAll(RATE_SLIDER, RATE_CAPTION);
		bottom.getChildren().add(sliderGroup);
		// set to center alignment on bottom of screen
		bottom.setAlignment(Pos.CENTER);
	}
	
	
	/**
	 * Initializes the GridPane that will hold all of the grids
	 */
	private void initGridPane() {
		allGrids.setVgap(GRID_SEPARATION);
		allGrids.setHgap(GRID_SEPARATION);
		ColumnConstraints CC = new ColumnConstraints(CellSociety.GRID_HEIGHT);
		RowConstraints RC = new RowConstraints(CellSociety.GRID_WIDTH);
		allGrids.getRowConstraints().add(RC);
		allGrids.getColumnConstraints().add(CC);
	}
	
	/**
	 * Set up the buttons for user control.
	 */
	private void initButtonPlacement() {
		// VBox to organize the buttons (on the left
		buttonGroup.setAlignment(Pos.CENTER);
		setButtonProperties(PAUSE_BUTTON);
		setButtonProperties(RESUME_BUTTON); 
		setButtonProperties(STEP_BUTTON);
		setButtonProperties(CLEAR_BUTTON);
		setButtonProperties(BEGIN_BUTTON);
		buttonGroup.getChildren().addAll(PAUSE_BUTTON, RESUME_BUTTON, STEP_BUTTON, CLEAR_BUTTON);
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
		CLEAR_BUTTON.setOnAction(__ -> {
			simulation.clearSims();
			allGrids.getChildren().clear();
		});
		BEGIN_BUTTON.setOnAction(__ -> {
			int[] placement = computeSpacing();
			Node currentGrid = getNodeByIndex(placement[0], placement[1]);
			if (currentGrid != null) allGrids.getChildren().remove(currentGrid);
			
			String filePath = new String("data/" + typeChooser.getValue() + ".xml");
			addGrid(filePath, shapeChooser.getValue());
		});
	}
	
	/**
	 * Initializes the drop-down menu (combo box) that allows the user to choose the type of simulation
	 */
	
	private void initComboBoxes() {
		// choose the type of simulation. Default is fire
		typeChooser.getSelectionModel().selectFirst();
		// choose the shape for the simulation. Default is square
		shapeChooser.getSelectionModel().selectFirst();
		// choose where to put the simulation. Default is top left
		locationChooser.getSelectionModel().selectFirst();
		bottom.getChildren().addAll(typeChooser, shapeChooser, locationChooser, BEGIN_BUTTON);
	}
	
	/**
	 * Initializes this simulation's grid by adding all of the cells to the root.
	 * @param grid the grid object for this simulation
	 */
	private void initGrid(Sim sim) {
		// if grid is null, do nothing
		if (sim != null) {
        		Pane gridPane = new Pane();
        		Label gridLabel = new Label(sim.name());     
        		gridLabel.setAlignment(Pos.CENTER);
        		gridPane.setPrefSize(CellSociety.GRID_WIDTH, CellSociety.GRID_HEIGHT);
        		for (int i = 0; i < sim.getGrid().getRows(); i++) {
        			for (int j = 0; j < sim.getGrid().getCols(); j++) {
        				gridPane.getChildren().add(sim.getGrid().get(i, j));
        			}
        		}
        		int[] placement = computeSpacing();
        		allGrids.add(gridPane, placement[0], placement[1]);
        		simulation.addSim(sim);
		}
	}
	
	/**
	 * Adds another grid to the view (for seeing multiple simulations at once)
	 */
	private void addGrid(String filePath, String shape) {
        	Sim newSim = (new SimulationBuilder(filePath, shape)).build(CellSociety.GRID_WIDTH,  CellSociety.GRID_HEIGHT);
        	initGrid(newSim);
	}
	
	/**
	 * Computes where a new grid needs to be placed within the GridPane. 
	 * There can be a maximum of 4 simulations (grids) running at once.
	 * @return an integer array where the first entry is the row and the second entry is the column
	 */
	private int[] computeSpacing() {
		switch (locationChooser.getValue()) {
		case "Top left":
			return new int[] {0, 0};
		case "Bottom left":
			return new int[] {0, 1};
		case "Top right": 
			return new int[] {1, 0};
		default:
			return new int[] {1, 1};
		}
	}
	
	// finds the node at a certain index of the GridPane
	private Node getNodeByIndex(int row, int col) {
		Node result = null;
		for (Node child : allGrids.getChildren()) {
			if (allGrids.getColumnIndex(child) == row && allGrids.getRowIndex(child) == col) {
				result = child;
				break;
			}
		}
		return result;
	}
}