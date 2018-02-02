package xmlparser;

/**
 * What could go wrong in reading XML files. Must handle all different cases.
 * @author dylanpowers
 *
 */
public class XMLException extends RuntimeException {
	
	/**
	 * Create an exception based upon a different caught exception
	 */
	public XMLException(Throwable cause) {
		super(cause);
	}
	
	/**
	 * Throw a new exception that reads a given message
	 * @param message message to be read
	 * @param values objects to be used in the message given to the user
	 */
	public XMLException(String message, Object... values) {
		super(String.format(message, values));
	}
}
