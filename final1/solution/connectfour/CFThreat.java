package final1.connectfour;

/**
 * A Threat in the Connect Four game.
 * A Threat is a position where, when the player puts a piece, he has a
 * complete 4-row and thus wins.
 * 
 * @author robin
 * @version 1
 * 
 */
public class CFThreat {

    private final int x;
    private final int y;
    private final int player;


    /**
     * create a new threat object
     * 
     * @param x
     *            column
     * @param y
     *            row
     * @param player
     *            the player who this threat belongs to
     */
    public CFThreat(int x, int y, int player) {
        this.x = x;
        this.y = y;
        this.player = player;
    }


    /**
     * @return the row
     */
    public int getY() {
        return y;
    }


    /**
     * @return the column
     */
    public int getX() {
        return x;
    }


    /**
     * 
     * @return the player who did this threat
     */
    public int getPlayer() {
        return player;
    }


    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + player;
        result = prime * result + x;
        result = prime * result + y;
        return result;
    }


    @Override
    public boolean equals(Object obj) {
        boolean equal = false;
        if (this == obj) {
            equal = true;
        } else if (obj != null && getClass() == obj.getClass()) {
            CFThreat other = (CFThreat) obj;
            if (player == other.player && x == other.x && y == other.y) {
                equal = true;
            }
        } else {
            equal = false;
        }
        return equal;
    }
}
