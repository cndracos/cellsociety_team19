package cellsociety;

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
import javafx.scene.layout.GridPane;
import javafx.scene.control.TabPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import xmlparser.SimulationBuilder;

import java.util.ArrayList;
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
	private static final Button RESET_BUTTON = new Button("Reset");
	private static final ComboBox<String> typeChooser = new ComboBox<>(FXCollections.observableList(Arrays.asList(CellSociety.SIMULATIONS)));
	private static final ComboBox<String> shapeChooser = new ComboBox<>(FXCollections.observableList(Arrays.asList(CellSociety.SHAPES)));
	private static final int BUTTON_WIDTH = 100;
	private static final int BUTTON_HEIGHT = 25;
	private static final int BUTTON_SPACING = 10;
	private static final int GRID_SEPARATION = 50;
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
		// center initialized without a simulation (empty)
		// drop-down menu chooses the type of simulation
		// then runs game loop where drop down menu is always active
		// add all non-center elements
	}
	
	public Scene getScene() {
		return new Scene(currentRoot, DEFAULT_WIDTH, DEFAULT_HEIGHT, BACKGROUND_COLOR);
	}
	
	/**
	 * Initialize all necessary GUI components. This is done only once (on initialization).
	 */
	private void init() {
		allGrids.setVgap(GRID_SEPARATION);
		allGrids.setHgap(GRID_SEPARATION);
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
	 * Set up the buttons for user control.
	 */
	private void initButtonPlacement() {
		// VBox to organize the buttons (on the left
		buttonGroup.setAlignment(Pos.CENTER);
		setButtonProperties(PAUSE_BUTTON);
		setButtonProperties(RESUME_BUTTON); 
		setButtonProperties(STEP_BUTTON);
		setButtonProperties(RESET_BUTTON);
		buttonGroup.getChildren().addAll(PAUSE_BUTTON, RESUME_BUTTON, STEP_BUTTON, RESET_BUTTON);
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
			ArrayList<Sim> copy = new ArrayList<Sim>(simulation.sims());
			simulation.clearSims();
			allGrids.getChildren().clear();
			for (Sim sim : copy) {
				String filePath = new String("data/" + sim.name() + ".xml");
				addGrid(filePath);
			}
		});
	}
	
	/**
	 * Initializes the drop-down menu (combo box) that allows the user to choose the type of simulation
	 */
	
	private void initComboBoxes() {
		// choose the type of simulation
		typeChooser.valueProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> ov, String oldValue, String newValue) {
				String filePath = new String("data/" + newValue + ".xml");
				addGrid(filePath);
			}
		});
		// choose the shape for simulations. Default is square
		shapeChooser.getSelectionModel().selectFirst();
		shapeChooser.valueProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> ov, String oldValue, String newValue) {
				
			}
		});
		bottom.getChildren().addAll(typeChooser, shapeChooser);
	}
	
	/**
	 * Initializes this simulation's grid by adding all of the cells to the root.
	 * @param grid the grid object for this simulation
	 */
	private void initGrid(Sim sim) {
		// if grid is null, do nothing
		if (sim != null) {
			VBox gridEncapsulator = new VBox(BUTTON_SPACING);
        		Pane gridPane = new Pane();
        		Label gridLabel = new Label(sim.name());     
        		gridLabel.setAlignment(Pos.CENTER);
        		gridPane.setPrefSize(CellSociety.GRID_WIDTH, CellSociety.GRID_HEIGHT);
        		for (int i = 0; i < sim.getGrid().getRows(); i++) {
        			for (int j = 0; j < sim.getGrid().getCols(); j++) {
        				gridPane.getChildren().add(sim.getGrid().get(i, j));
        			}
        		}
        		gridEncapsulator.getChildren().addAll(gridLabel, gridPane);
        		int[] placement = computeSpacing();
        		allGrids.add(gridEncapsulator, placement[0], placement[1]);
        		simulation.addSim(sim);
		}
	}
	
	/**
	 * Adds another grid to the view (for seeing multiple simulations at once)
	 */
	private void addGrid(String filePath) {
        	Sim newSim = (new SimulationBuilder(filePath)).build(CellSociety.GRID_WIDTH,  CellSociety.GRID_HEIGHT);
        	initGrid(newSim);
	}
	
	/**
	 * Computes where a new grid needs to be placed within the GridPane. 
	 * There can be a maximum of 4 simulations (grids) running at once.
	 * @return an integer array where the first entry is the row and the second entry is the column
	 */
	private int[] computeSpacing() {
		switch (simulation.numSimulations()) {
		case 0:
			return new int[] {0, 0};
		case 1:
			return new int[] {0, 1};
		case 2: 
			return new int[] {1, 0};
		default:
			return new int[] {1, 1};
		}
	}
}