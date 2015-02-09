package final1.connectfour;

import java.util.ArrayList;
import java.util.List;

/**
 * the representation of the game field for a connect four game.
 * 
 * @author robin
 * @version 1
 * 
 */
public class CFGameField {

    /**
     * The number of rows the game field has. Determined by the Connect Four
     * rules.
     */
    public static final int ROW_COUNT = 6;
    /**
     * the number of columns the game field has. Determined by the Connect Four
     * rules.
     */
    public static final int COL_COUNT = 7;

    /**
     * The in-memory representation for the field.
     * The first coordinate is y for easier output.
     * The player-id is directly written into this array, 0 is reserved for
     * empty.
     */
    private final byte[][] field;
    /** the y-positions of the uppermost pieces */
    private final int[] stackHeight;


    /**
     * Initialize a new, empty game field with the standard Connect Four size.
     */
    public CFGameField() {
        field = new byte[ROW_COUNT][COL_COUNT];
        stackHeight = new int[COL_COUNT];
    }


    /**
     * Get the id of the player occupying the field at the given coordinates.
     * 
     * @param x
     *            the column
     * @param y
     *            the row
     * @return the player id at the coordinates, 0 when the position is empty,
     *         -1 when it is out of bounds
     */
    public int get(int x, int y) {
        if (inbound(x, y)) {
            return field[y][x];
        } else {
            return -1;
        }
    }


    /**
     * checks if the given coordinates are within the game field
     * 
     * @param x
     *            the column to check
     * @param y
     *            the row to check
     * @return if the coordinates are inbound
     */
    public boolean inbound(int x, int y) {
        return !(x < 0 || y < 0 || x >= COL_COUNT || y >= ROW_COUNT);
    }


    /**
     * checks if the given column exists
     * 
     * @param x
     *            the column to check
     * 
     * @return if the column exists
     */
    public boolean inbound(int x) {
        return !(x < 0 || x >= COL_COUNT);
    }


    /**
     * Set the value at the given coordinates to val if possible.
     * If the coordinates are out of bounds, do nothing.
     * 
     * @param x
     *            the column
     * @param player
     *            the player id
     */
    public void put(int x, int player) {
        int y = nextPutY(x);
        if (inbound(x, y)) {
            field[y][x] = (byte) player;
            stackHeight[x]++;
        }
    }


    /**
     * remove the uppermost piece in the specified column
     * 
     * @param x
     *            the column
     */
    public void remove(int x) {
        if (inbound(x) && stackHeight[x] > 0) {
            field[nextPutY(x) + 1][x] = 0;
            stackHeight[x]--;
        }
    }


    /**
     * Retrieve a list of all possible moves for the current setting.
     * If the list is empty, the game is over.
     * 
     * @return the list of all possible moves
     */
    public List<CFMove> getValidMoves() {
        List<CFMove> validMoves = new ArrayList<CFMove>(COL_COUNT);
        for (int x = 0; x < COL_COUNT; x++) {
            if (nextPutY(x) >= 0) {
                validMoves.add(new CFMove(x));
            }
        }
        return validMoves;
    }


    /**
     * check if this game field is completely full resulting in a draw
     * 
     * @return true if the play field is full
     */
    public boolean isFull() {
        for (int height : stackHeight) {
            if (height != ROW_COUNT) { return false; }
        }
        return true;
    }


    /**
     * get the topmost occupied position at the given column.
     * 0 means the stack is full, ROW_COUNT means the stack is empty.
     * 
     * @param x
     *            the column
     * @return the topmost occupied row at this column
     */
    public int nextPutY(int x) {
        return ROW_COUNT - stackHeight[x] - 1;
    }


    /**
     * Get the visual representation of this field.
     * Columns are seperated with |.
     * Empty space is represented by " ", players are represented by their id.
     * Example:
     * | | | | | | | |
     * | | | | | | | |
     * | | | |2| | | |
     * | | | |1|2| | |
     * | | | |2|1| | |
     * | | |1|1|2| |1|
     * 
     * @return a String representing this game field
     */
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (byte[] row : field) {
            for (byte val : row) {
                sb.append("|" + (val == 0 ? " " : val));
            }
            sb.append("|\n");
        }
        return sb.toString();
    }
}
