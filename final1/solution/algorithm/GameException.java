package final1.algorithm;

/**
 * a general game exception
 * 
 * @author robin
 * @version 1
 */
public class GameException extends Exception {

    /** needed for serialization */
    private static final long serialVersionUID = -6111865820630702350L;


    /**
     * initialize a new exception
     * 
     * @param message
     *            the message to print before the stack trace
     */
    public GameException(String message) {
        super(message);
    }
}
