package cellsociety;


import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {
	
	@Override
	public void start(Stage stage) throws InterruptedException {
		CellSociety cs = new CellSociety(stage);
	}
	
	// launch the simulation
	public static void main(String[] args) {
		launch(args);
	}

}
