package final1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * This class provides some simple methods for I/O on a terminal.
 * 
 * @author ITI, VeriAlg Group
 * @version 2
 */

public final class Terminal {

    /**
     * Reader-object to read new input
     */
    private static BufferedReader in = new BufferedReader(
            new InputStreamReader(System.in));


    /**
     * Private constructor to avoid object generation.
     */
    private Terminal() {

    }


    /**
     * Output a prompt.
     * 
     * @param out
     *            the prompt.
     */
    public static void prompt(String out) {
        // System.out.print("PROMPT");
        System.out.print(out);
    }


    /**
     * Output a string.
     * 
     * @param out
     *            the string.
     */
    public static void println(String out) {
        // System.out.print("PRINTLN");
        System.out.println(out);
    }


    /**
     * Reads a string from the terminal.
     * 
     * @return the input string.
     */
    public static String readln() {
        try {
            return in.readLine();
        } catch (IOException e) {
            throw new Error(e);
        }
    }

}
