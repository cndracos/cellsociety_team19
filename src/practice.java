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
import sim.SegreSim;
import sim.Sim;

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
    private Sim sim;
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
       
        KeyFrame frame = new KeyFrame(Duration.millis(500),
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
        
<<<<<<< HEAD
        
        /**int n =  97;
        int k = 70;
        double[] vals = {4, 20, 2};
        Map<String, double[]> keys = new HashMap<String, double[]>();
        double[] fProb = new double[2];
        fProb[0] = 0.0;
        fProb[1] = 0.3;
        double[] sProb = new double[2];
        sProb[0] = 0.0;
        sProb[1] = 0.0;
        keys.put("FISH", fProb);
        keys.put("SHARK", sProb);
        grid = new WatorGrid(n, k, 970, 700, vals, (HashMap<String, double[]>) keys); **/
=======
        int n =  100;
        int k = 100;
        double[] vals = {5, 20, 10};
        HashMap<String, double[]> keys = new HashMap<String, double[]>();
        double[] fProb = new double[2];
        fProb[0] = 0.0;
        fProb[1] = 0.5;
        double[] sProb = new double[2];
        sProb[0] = 0.5;
        sProb[1] = 0.6;
        keys.put("FISH", fProb);
        keys.put("SHARK", sProb);
        grid = new WatorGrid(n, k, 990, 720, vals, keys); 
>>>>>>> 3103b84991d9f835a1989b9dee9092ae623c92f5

        
       int n =  97;
        int k = 70;
        double vals = .25;
        Map<String, double[]> keys = new HashMap<String, double[]>();
        double[] XProb = new double[2];
        XProb[0] = 0.0;
        XProb[1] = 0.35;
        double[] OProb = new double[2];
        OProb[0] = 0.35;
        OProb[1] = 0.7;
        keys.put("X", XProb);
        keys.put("O", OProb);
        sim = new SegreSim(n, k, 970, 700, vals, (HashMap<String, double[]>) keys, "SQUARE"); 
    
       //System.out.print(root== null);
                
<<<<<<< HEAD
      // grid = new WatorGrid(n, k, vals, keys);
=======
       //grid = new WatorGrid(n, k, vals, keys);
>>>>>>> 3103b84991d9f835a1989b9dee9092ae623c92f5

       for (int i = 0; i < n; i++) {
    	   	for (int j = 0; j < k; j++) {
    	   		root.getChildren().add(sim.getGrid().get(i, j));
    	   	}
       }
       
       return scene;
    }

    
    private void step (double elapsedTime) {
            sim.update();
            count += 1;
            //check if states of all grids in the cell converges or not
            
    }

    private void stopGame(){
        animation.stop();
    }
}




