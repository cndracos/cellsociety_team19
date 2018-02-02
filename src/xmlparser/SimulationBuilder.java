package xmlparser;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;

public class SimulationBuilder {
	
	private static final String ACCEPTABLE_EXTENSION = "*.xml";
	private final FileChooser FILE_CHOOSER = buildChooser(ACCEPTABLE_EXTENSION);
	// fields of the XML file that the class XMLFile will need to access
	public static final List<String> DATA_FIELDS = Arrays.asList(new String[] {
			"model",
			"rows", 
			"columns", 
			"probability"
	});
	
	public SimulationBuilder(Stage stage) {
		File XMLFile = FILE_CHOOSER.showOpenDialog(stage);
		if (XMLFile != null) {
			try {
				System.out.println(new XMLParser("simulation"));
			} catch (XMLException e) {
				Alert alert = new Alert(AlertType.ERROR);
				alert.setContentText(e.getMessage());
				alert.showAndWait();
			}
		}
		
		
		
		
	}
	
	
	
	
	private FileChooser buildChooser(String acceptableExtension) {
		FileChooser chooser = new FileChooser();
		chooser.setTitle("Choose a simulation setup file");
		// only start searching in the directory containing the files
		chooser.setInitialDirectory(new File(System.getProperty("user.dir")));
		chooser.getExtensionFilters().setAll(new ExtensionFilter("Text Files", acceptableExtension));
		return chooser;
	}
}

