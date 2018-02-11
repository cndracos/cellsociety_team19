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
import sim.RPSSim;
import sim.SegreSim;
import sim.Sim;
import sim.WatorSim;

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
                
        myScene = setupGame(970, 700, Color.BLACK);
        stage.setScene(myScene);
        stage.setTitle("practice");
        stage.show();
       
        KeyFrame frame = new KeyFrame(Duration.millis(250),
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
        /*
        int n =  97;
        int k = 70;
        double[] vals = {4, 20, 10};
        Map<String, double[]> keys = new HashMap<String, double[]>();
        double[] fProb = new double[2];
        fProb[0] = 0.0;
        fProb[1] = 0.3;
        double[] sProb = new double[2];
        sProb[0] = 0.4;
        sProb[1] = 0.5;
        keys.put("FISH", fProb);
        keys.put("SHARK", sProb);
        //grid = new WatorGrid(n, k, 970, 700, vals, (HashMap<String, double[]>) keys);**/

        
/**       int n =  97;
        int k = 70;
        double vals = .625;
        Map<String, double[]> keys = new HashMap<String, double[]>();
        double[] XProb = new double[2];
        XProb[0] = 0.0;
        XProb[1] = 0.0;
        double[] OProb = new double[2];
        OProb[0] = 0.3;
        OProb[1] = 0.9;
        keys.put("X", XProb);
        keys.put("O", OProb);
        sim = new SegreSim(n, k, 970, 700, vals, (HashMap<String, double[]>) keys, "TRIANGLE", true); 
    **/
        int n =  97;
        int k = 70;
        int vals = 10;
        Map<String, double[]> keys = new HashMap<String, double[]>();
        double[] RProb = new double[2];
        RProb[0] = 0.0;
        RProb[1] = 0.3;
        double[] PProb = new double[2];
        PProb[0] = 0.3;
        PProb[1] = 0.7;
        double[] SProb = new double[2];
        SProb[0] = 0.7;
        SProb[1] = 1.0;
        keys.put("ROCK", RProb);
        keys.put("PAPER", PProb);
        keys.put("SCISSOR",SProb);
        sim = new RPSSim(n, k, 970, 700, vals, (HashMap<String, double[]>) keys, "SQUARE"); 
        
       //System.out.print(root== null);
                
      //grid = new SegreGrid(n, k, 970, 700, vals, keys);

       for (int i = 0; i < n; i++) {
    	   	for (int j = 0; j < k; j++) {
    	   		root.getChildren().add(sim.getGrid().get(i, j));
    	   	}
       }
       
       return scene;
    }

    
    private void step (double elapsedTime) {
            sim.update();
            //count += 1;
            //check if states of all grids in the cell converges or not
            
    }

    private void stopGame(){
        animation.stop();
    }
}




