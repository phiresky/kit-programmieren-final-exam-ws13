package final1.algorithm;


/**
 * A player for a game that does moves
 * 
 * @author robin
 * @version 1
 * @param <M>
 *            type of move
 */
public interface Player<M extends Move> {

    /**
     * get the move this player wants to make
     * 
     * @return the move
     */
    public M getMove();
}
