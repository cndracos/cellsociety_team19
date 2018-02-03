import grid.*;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;
import javafx.util.Duration;
import cell.*;

public class practice extends Application{
	
	
	public static void main (String [] args) throws InterruptedException {
		launch(args);
	}
	

    private Scene myScene;
    private int n;
    private int k;
    private Grid grid;

    
    /**
     * Initialize what will be displayed and how it will be updated.
     */
    @Override
    public void start (Stage stage) {
    		
        myScene = setupGame(300, 300, Color.WHITE);
        stage.setScene(myScene);
        stage.setTitle("practice");
        stage.show();
       
        KeyFrame frame = new KeyFrame(Duration.millis(1000),
                                      e -> step(60));
        Timeline animation = new Timeline();
        animation.setCycleCount(Timeline.INDEFINITE);
        animation.getKeyFrames().add(frame);
        animation.play();
    }

  
    private Scene setupGame (int width, int height, Paint background) {
      
        Group root = new Group();
   
        Scene scene = new Scene(root, width, height, background);
        
        n =  9;
		k = 9;
		String state = "TREE";
		
		grid = new FireGrid(n, k);
		
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < k; j++) {
				if (i==5 && j == 5) grid.add(new FireCell("BURNING", .3), i, j);
				else grid.add(new FireCell(state, .3), i, j);
				root.getChildren().add(grid.get(i, j));
			}
		}
		grid.addNeighbors();
		
        
        return scene;
    }

    
    private void step (double elapsedTime) {
    		for (int i = 0; i < n; i++) {
			for (int j = 0; j < k; j++) {
				grid.get(i, j).findState();
			}
		}
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < k; j++) {
				grid.get(i, j).setState();
			}
		}
	}
}




