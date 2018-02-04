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

import java.util.Random;
import java.util.HashMap;
import java.util.Map;
import cell.*;

public class practice extends Application{
        
        
        public static void main (String [] args) throws InterruptedException {
                launch(args);
        }
        

    private Scene myScene;
    private int n;
    private int k;
    private Grid grid;
    private Timeline animation;
    private Random rand;
    /**
     * Initialize what will be displayed and how it will be updated.
     */
    @Override
    public void start (Stage stage) {
                
        myScene = setupGame(320, 320, Color.BLACK);
        stage.setScene(myScene);
        stage.setTitle("practice");
        stage.show();
       
        KeyFrame frame = new KeyFrame(Duration.millis(100),
                                      e -> step(60));
        animation = new Timeline();
        animation.setCycleCount(Timeline.INDEFINITE);
        animation.getKeyFrames().add(frame);
        animation.play();
    }

  
    private Scene setupGame (int width, int height, Paint background) {
      
        Group root = new Group();
   
        Scene scene = new Scene(root, width, height, background);
        rand = new Random();
        n =  31;
        k = 31;

        
        double satisfied = .25;
        Map<String, double[]> keys = new HashMap<String, double[]>();
        double[] Xprob = new double[2];
        Xprob[0] = 0.0;
        Xprob[1] = 0.4;
        double[] Oprob = new double[2];
        Oprob[0] = 0.4;
        Oprob[1] = 0.8;
        keys.put("X", Xprob);
        keys.put("O", Oprob);

                
       grid = new SegreGrid(n, k, satisfied, keys);
       for (int i = 0; i < n; i++) {
    	   	for (int j = 0; j < k; j++) {
    	   		root.getChildren().add(grid.get(i, j));
    	   	}
       }
       
       return scene;
    }

    
    private void step (double elapsedTime) {
            grid.update();
                
            //check if states of all grids in the cell converges or not
            //if(grid.isConverge()){
            //      stopGame();
            //}
    }

    private void stopGame(){
        animation.stop();
    }
}




