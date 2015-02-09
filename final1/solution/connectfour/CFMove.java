package final1.connectfour;

import final1.algorithm.Move;

/**
 * A "Move" for a Connect Four game. Contains the coordinates where a piece is
 * put.
 * 
 * @author robin
 * @version 1
 * 
 */
public class CFMove implements Move {

    private final int x;


    /**
     * Initialize a new move with the given coordinates
     * 
     * @param x
     *            the column
     */
    public CFMove(int x) {
        this.x = x;
    }


    /**
     * @return the column
     */
    public int getX() {
        return x;
    }
}