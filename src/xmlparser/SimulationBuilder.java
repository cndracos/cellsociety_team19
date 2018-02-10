package xmlparser;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

import grid.*;

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
	// name of this specific simulation to build
	private static String simulationName;
	
	// constructor
	public SimulationBuilder(String filePath) {
		XMLFile = new File(filePath);
	}
	
	/**
	 * Build the grid specified by the XMLFile.
	 * @return the built grid
	 * @throws FileNotFoundException
	 */
	public Grid build(int screenLength, int screenWidth) {
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
				return generateGrid(rows, cols, screenLength,screenWidth,probability, initialStates);
			} catch (XMLException e) {
				Alert alert = new Alert(AlertType.ERROR);
				alert.setContentText(e.getMessage());
				alert.showAndWait();
			}
		}
		return null;
		
	}
	
	// select which type of grid we want to initialize based upon the simulation type
	public Grid generateGrid(int rows, int cols, int screenLength, int screenWidth, double[] probability, HashMap<String, double[]> initialStates) {
		switch (simulationName) {
			case "Fire": 
				// probability[0] only b/c only one probability exists for this simulation
				return new FireGrid(rows, cols, screenLength, screenWidth,probability[0], initialStates);
			case "Segregation":
				// probability[0] only b/c only one probability exists for this simulation
				return new SegreGrid(rows, cols, screenLength, screenWidth,probability[0], initialStates);
			case "Wator":
				return new WatorGrid(rows, cols, screenLength, screenWidth,probability,initialStates);	
			default:
				return new LifeGrid(rows, cols, screenLength, screenWidth,initialStates);
		}
			
			
	}
	
	// get the name of the simulation that is building
	public String getBuildType() {
		return simulationName;
	}
}

