package xmlparser;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

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
	
	public SimulationBuilder(Stage stage) {
		FILE_CHOOSER = buildChooser(ACCEPTABLE_EXTENSION);
		XMLFile = FILE_CHOOSER.showOpenDialog(stage);
	}
	
	// build the grid
	public Grid build() {
		if (XMLFile != null) {
			try {
				XMLParser parser = new XMLParser("simulation");
				Map<String, String> gridProperties = parser.getGridProperties(XMLFile);
				// extract all of the information that we need to form the grid
				String simulationType = gridProperties.get("model").toString();
				int rows = Integer.parseInt(gridProperties.get("rows").toString());
				int cols = Integer.parseInt(gridProperties.get("columns").toString());
				double probability = Double.parseDouble(gridProperties.get("probability").toString());
				// extract the percentages for the initial states of the cells
				Map<String, double[]> initialStates = parser.getInitialStates(XMLFile);
				return generateGrid(simulationType, rows, cols, probability, initialStates);
			} catch (XMLException e) {
				Alert alert = new Alert(AlertType.ERROR);
				alert.setContentText(e.getMessage());
				alert.showAndWait();
			}
		}
	}
	
	// select which type of grid we want to initialize based upon the simulation type
	public Grid generateGrid(String simulationType, int rows, int cols, double probability, Map<String, double[]> initialStates) {
		switch (simulationType) {
			case "Fire": 
				return new FireGrid(rows, cols, probability, initialStates);
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
}

