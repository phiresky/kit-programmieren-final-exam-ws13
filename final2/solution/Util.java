package final2;

import java.util.Collections;
import java.util.Set;
import java.util.WeakHashMap;

/**
 * Utility functions
 * 
 * @author robin
 * @version 1
 */
public final class Util {
    private Util() {
        // unused
    }

    /**
     * print an error message with the prefix "Error, " to stderr, then either
     * exits the program or continues, depending on {@link Main#ERRORS_FATAL}
     * 
     * @param message
     *            the error message
     */
    public static void handleError(String message) {
        System.err.println("Error, " + message);
        if (Main.ERRORS_FATAL) {
            // throw new RuntimeException(message);
            System.err.println("Error occured, aborting");
            System.exit(1);
        }
    }

    /**
     * Create a set of weak references to objects of type T. Needed because Java
     * does not have an internal type for weak sets, only weak maps.
     * (HashSet uses a HashMap internally in java anyways)
     * 
     * @return a set of weak references
     * @param <T>
     *            the type of the set
     * @formatter:off
     */
    public static <T> Set<T> createWeakSet() {
        return Collections.newSetFromMap(new WeakHashMap<T, Boolean>());
    }

}
