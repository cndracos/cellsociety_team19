package xmlparser;

import java.io.File;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

import sim.*;

public class SimulationBuilder {
	// the specific file that builds this simulation
	private static File XMLFile;
	// fields of the XML file that the class XMLParser will need to access
	protected static final List<String> DATA_FIELDS = Arrays.asList(new String[] {
			"model",
			"rows", 
			"columns", 
			"probability",
	});
	protected static final String INITIAL_DATA_FIELD = new String("initialStates");
	private static final String ERROR_MESSAGE = new String("Incorrect input of type %s. Please refer to documentation for correct format.\n");
	// name of this specific simulation to build
	private static String simulationName;
	private static String shape = "SQUARE";
	
	// default constructor where shape is SQUARE
	public SimulationBuilder(String filePath) {
		XMLFile = new File(filePath);
	}
	
	// constructor where shape is specified
	public SimulationBuilder(String filePath, String cellshape) {
		this(filePath);
		shape = cellshape;
	}
	
	/**
	 * Build the grid specified by the XMLFile.
	 * @return the built simulation
	 */
	public Sim build(int screenLength, int screenWidth)  {
		if (XMLFile != null) {
			try {
				XMLParser parser = new XMLParser("simulation");
				Map<String, String> gridProperties = parser.getGridProperties(XMLFile);
				// extract all of the information that we need to form the grid
				simulationName = gridProperties.get("model").toString();
				int rows = Integer.parseInt(gridProperties.get("rows").toString());
				int cols = Integer.parseInt(gridProperties.get("columns").toString());
				double[] probability = Stream.of(gridProperties.get("probability").split("\\s+")).mapToDouble(Double::parseDouble).toArray();
				// extract the percentages for the initial states of the cells
				HashMap<String, double[]> initialStates = (HashMap<String, double[]>) parser.getInitialStates(XMLFile);
				// check for correctness
				if (badProbabilities(initialStates)) {
					throw new SimulationInputException(ERROR_MESSAGE, "probability");
				}
				return generateGrid(rows, cols, screenLength,screenWidth,probability, initialStates);
			} catch (XMLException | SimulationInputException e) {
				Alert alert = new Alert(AlertType.ERROR);
				alert.setContentText(e.getMessage());
				alert.showAndWait();
			}
		}
		return null;
	}
	
	// select which type of grid we want to initialize based upon the simulation type
	public Sim generateGrid(int rows, int cols, int screenLength, int screenWidth, double[] probability, HashMap<String, double[]> initialStates) {
		switch (simulationName) {
			case "Fire": 
				// probability[0] only b/c only one probability exists for this simulation
				return new FireSim(rows, cols, screenLength, screenWidth,probability[0], initialStates, shape, false);
			case "Segregation":
				// probability[0] only b/c only one probability exists for this simulation
				return new SegreSim(rows, cols, screenLength, screenWidth, probability[0], initialStates, shape, false);
			case "Wator":
				return new WatorSim(rows, cols, screenLength, screenWidth, probability, initialStates, shape, true);	
			case "Game of Life":
				return new LifeSim(rows, cols, screenLength, screenWidth, initialStates , shape, false);
			case "RPS":
				return new RPSSim(rows, cols, screenLength, screenWidth, probability[0], initialStates, shape);
		}
		// if none of the cases were returned, then there is an issue
		throw new SimulationInputException(ERROR_MESSAGE, "model");
	}
	
	// check if any of the given probabilities are greater than 1 or less than 0
	private boolean badProbabilities(HashMap<String, double[]> initialStates) {
		// initial states
		for (double[] probs : initialStates.values()) {
			for (double prob : probs) {
				if (prob > 1 || prob < 0) {
					return true;
				}
			}
		}
		return false;
	}
	
	// get the name of the simulation that is building
	public String getBuildType() {
		return simulationName;
	}
}

