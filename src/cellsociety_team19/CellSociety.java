package cellsociety_team19;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import javafx.animation.Timeline;
import javafx.stage.Stage;
import javafx.stage.FileChooser;
import javafx.scene.Scene;
import javafx.scene.Group;

public class CellSociety {

	private static final int X_DIMENSION = 800;
	private static final int Y_DIMENSION = 600;
	private static String simulationType;
	
	
	
	
	private Group myRoot;
	
	public CellSociety(Stage stage) {
	}
	
	/**
	 * @return the simulation type of this specific instance
	 */
	public String getSimulationType() {
		return simulationType;
	}

}

