package xmlparser;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

/**
 * Class to tell the user when the XML input file contains invalid data.
 * @author dylanpowers
 *
 */
public class SimulationInputException extends RuntimeException {
	// serialization
	private static final long serialVersionUID = 1L;
	private static final String GENERIC_ERROR_MESSAGE = new String("Incorrect input. Please refer to documentation for correct format.\n");
	
	// default constructor
	public SimulationInputException() {
		super(GENERIC_ERROR_MESSAGE);
	}
	
	/**
	 * Create an exception based upon a different thrown cause
	 */
	public SimulationInputException(Throwable cause) {
		super(cause);
	}
	
	/**
	 * This constructor is for when probability values exceed 1
	 */
	
	/**
	 * Create an exception based upon an error message
	 */
	public SimulationInputException(String message, Object... values) {
		super(String.format(message,  values));
		Alert alert = new Alert(AlertType.ERROR);
		alert.setContentText(String.format(message,  values));
		alert.showAndWait();
	}
	
	@Override
	public String getMessage() {
		return GENERIC_ERROR_MESSAGE;
	}
}
