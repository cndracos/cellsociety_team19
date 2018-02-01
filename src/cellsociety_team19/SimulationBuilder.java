package cellsociety_team19;

import java.io.File;

import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;

public class SimulationBuilder {

	private static final String ACCEPTABLE_EXTENSION = "*.xml";
	private final FileChooser FILE_CHOOSER = buildChooser(ACCEPTABLE_EXTENSION);
	
	
	
	
	
	
	private FileChooser buildChooser(String acceptableExtension) {
		FileChooser chooser = new FileChooser();
		chooser.setTitle("Choose a simulation setup file");
		// only start searching in the directory containing the files
		chooser.setInitialDirectory(new File(System.getProperty("user.dir")));
		chooser.getExtensionFilters().setAll(new ExtensionFilter("Text Files", acceptableExtension));
		return chooser;
	}
}

