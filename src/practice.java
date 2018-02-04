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
       
        KeyFrame frame = new KeyFrame(Duration.millis(1000),
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

        int probCatch = 1;
        String state = "TREE";
        
        grid = new SegreGrid(n, k,0.3, null);
        
        for (int i = 0; i < n; i++) {
                for (int j = 0; j < k; j++) {
               
                		int key = rand.nextInt(10);
                        if (key < 4) grid.add(new SegreCell("X", .3), i, j);
                        else if(key < 8)grid.add(new SegreCell("O", 0.3), i, j);
                        else grid.add(new SegreCell("EMPTY", 0.3), i, j);

                        root.getChildren().add(grid.get(i, j));
                }
        }
        grid.addNeighbors();
        /**
        double satisfied = .50;
        Map<String, double[]> keys = new HashMap<String, double[]>();
        double[] Xprob = new double[2];
        Xprob[0] = 0.0;
        Xprob[1] = 0.4;
        double[] Oprob = new double[2];
        Oprob[0] = 0.4;
        Oprob[1] = 0.8;
        keys.put("X", Xprob);
        keys.put("O", Oprob);
**/
                

       
       return scene;
    }

    
    private void step (double elapsedTime) {
    	/**
        boolean isfire = false;
        boolean isFire = false;
        	for (int i = 0; i < n; i++) {
        		for (int j = 0; j < k; j++) {
        			grid.get(i, j).findState();
                    	if(grid.get(i, j).getState() == "BURNING"){
                    		isFire = true;
                        }
                }
            }
        	
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < k; j++) {
                    grid.get(i, j).setState();
                }
            }

            if (!isfire) System.out.println("over");

       grid.update();
                //check if states of all grids in the cell converges or not
                //if(grid.isConverge()){
                  //      stopGame();
                //}
 */
    	grid.update();
    }

    private void stopGame(){
        animation.stop();
    }
}




