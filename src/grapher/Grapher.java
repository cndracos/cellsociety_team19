package grapher;

import cellsociety.CellSociety;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.NumberAxis;

/**
 * Class for creating a graph of states throughout the simulation
 * @author dylanpowers
 *
 */
public class Grapher {
	
	private static NumberAxis xAxis;
	private static NumberAxis yAxis;
	
	private static LineChart<Number, Number> stateChart;
	private static int numIterations = 0;
	
	// default constructor
	public Grapher(CellSociety CS) {
		xAxis = new NumberAxis();
		yAxis = new NumberAxis();
		yAxis.setLabel("Percentage of cells");
		stateChart = new LineChart<Number, Number>(xAxis, yAxis);
		
	}
	
	// called when the graph needs to be updated
	public void update() {
		numIterations++;
	}
	
	public XYChart.Series<Number, Number> addNewSeries(String name) {
		XYChart.Series<Number, Number> series = new XYChart.Series<>();
		series.setName(name);
		return series;
	}
}
