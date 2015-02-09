package final1;

/**
 * common utility function
 * 
 * @author robin
 * @version 1
 * 
 */
public final class Utility {

    private static final double EPSILON = 1E-14;


    private Utility() {

    }


    /**
     * compare two doubles with an epsilon.
     * taken from Ilias/Markus Iser
     * 
     * @param a
     *            first double
     * @param b
     *            second double
     * @return true if a is greater than b
     */
    public static boolean greaterThan(double a, double b) {
        if (a == b) { // in case of infinity
            return false;
        }
        return a - b > -EPSILON && !(Math.abs(a - b) < EPSILON);
    }


    /**
     * compare two doubles with an epsilon.
     * taken from Ilias/Markus Iser
     * 
     * @param a
     *            first double
     * @param b
     *            second double
     * @return true if a is greater than or equal to b
     */
    public static boolean greaterThanOrEqual(double a, double b) {
        if (a == b) { // in case of infinity
            return true;
        }
        return a - b > -EPSILON;
    }
}
