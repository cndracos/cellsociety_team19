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
    private int count = 0;
    /**
     * Initialize what will be displayed and how it will be updated.
     */
    @Override
    public void start (Stage stage) {
                
        myScene = setupGame(990, 720, Color.BLACK);
        stage.setScene(myScene);
        stage.setTitle("practice");
        stage.show();
       
        KeyFrame frame = new KeyFrame(Duration.millis(10000),
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
        
        
        int n =  97;
        int k = 70;
        double[] vals = {4, 20, 5};
        Map<String, double[]> keys = new HashMap<String, double[]>();
        double[] fProb = new double[2];
        fProb[0] = 0.0;
        fProb[1] = 0.7;
        double[] sProb = new double[2];
        sProb[0] = 0.7;
        sProb[1] = 0.8;
        keys.put("FISH", fProb);
        keys.put("SHARK", sProb);
        grid = new WatorGrid(n, k, vals, keys); 

        
       /**int n =  97;
        int k = 70;
        double vals = .5;
        Map<String, double[]> keys = new HashMap<String, double[]>();
        double[] XProb = new double[2];
        XProb[0] = 0.0;
        XProb[1] = 0.35;
        double[] OProb = new double[2];
        OProb[0] = 0.35;
        OProb[1] = 0.7;
        keys.put("X", XProb);
        keys.put("O", OProb);
        grid = new SegreGrid(n, k, vals, keys); **/
    
       //System.out.print(root== null);
                
       grid = new WatorGrid(n, k, vals, keys);

       for (int i = 0; i < n; i++) {
    	   	for (int j = 0; j < k; j++) {
    	   		root.getChildren().add(grid.get(i, j));
    	   	}
       }
       
       return scene;
    }

    
    private void step (double elapsedTime) {
            grid.update();
            count += 1;
            //check if states of all grids in the cell converges or not
            
    }

    private void stopGame(){
        animation.stop();
    }
}




