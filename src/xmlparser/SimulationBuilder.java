package xmlparser;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;

import grid.*;

public class SimulationBuilder {
	
	private static final String ACCEPTABLE_EXTENSION = "*.xml";
	private static FileChooser FILE_CHOOSER;
	// the specific file that builds this simulation
	private static File XMLFile;
	// fields of the XML file that the class XMLParser will need to access
	public static final List<String> DATA_FIELDS = Arrays.asList(new String[] {
			"model",
			"rows", 
			"columns", 
			"probability",
	});
	public static final String INITIAL_DATA_FIELD = new String("initialStates");
	// name of this specific simulation to build
	private static String simulationName;
	
	// constructor
	public SimulationBuilder(Stage stage) {
		FILE_CHOOSER = buildChooser(ACCEPTABLE_EXTENSION);
		XMLFile = FILE_CHOOSER.showOpenDialog(stage);
	}
	
	/**
	 * Build the grid specified by the XMLFile.
	 * @return the built grid
	 * @throws FileNotFoundException
	 */
	public Grid build() {
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
				Map<String, double[]> initialStates = parser.getInitialStates(XMLFile);
				return generateGrid(rows, cols, probability, initialStates);
			} catch (XMLException e) {
				Alert alert = new Alert(AlertType.ERROR);
				alert.setContentText(e.getMessage());
				alert.showAndWait();
			}
		}
		return null;
		
	}
	
	// select which type of grid we want to initialize based upon the simulation type
	public Grid generateGrid(int rows, int cols, double[] probability, Map<String, double[]> initialStates) {
		switch (simulationName) {
			
			case "Fire": 
				// probability[0] only b/c only one probability exists for this simulation
				return new FireGrid(rows, cols, probability[0], initialStates);
			case "Segregation":
				// probability[0] only b/c only one probability exists for this simulation
				return new SegreGrid(rows, cols, probability[0], initialStates);
			case "Wator":
				return new WatorGrid(rows, cols, probability, initialStates);
			default:
				return new LifeGrid(rows, cols, initialStates);
		}
			
			
	}
	
	// make the file chooser that will be displayed to the user
	private FileChooser buildChooser(String acceptableExtension) {
		FileChooser chooser = new FileChooser();
		chooser.setTitle("Choose a simulation setup file");
		// only start searching in the directory containing the files
		chooser.setInitialDirectory(new File(System.getProperty("user.dir")));
		chooser.getExtensionFilters().setAll(new ExtensionFilter("Text Files", acceptableExtension));
		return chooser;
	}
	
	// get the name of the simulation that is building
	public String getBuildType() {
		return simulationName;
	}
}

