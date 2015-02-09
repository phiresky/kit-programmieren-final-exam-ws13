package final1.algorithm;

import java.util.List;

/**
 * This interface is for implementations of zero-sum two-player Games.
 * 
 * @author ITI, VeriAlg Group
 * @version 3
 * 
 * @param <M>
 *            The type of move the game uses.
 */
public interface Game<M extends Move> {

    /**
     * Calculates and returns the list of possible moves
     * among which the active player can choose.
     * If the game is over (one player has won) or no move
     * is possible the list is empty.
     * 
     * @return the list of possible moves.
     */
    public List<M> getValidMoves();


    /**
     * Locally evaluates the current state of the game with respect
     * to the active player.
     * 
     * @return a local evaluation of the current game state.
     */
    public double evaluateState();


    /**
     * Performs the given move for the active player.
     * 
     * @param move
     *            The move to perform.
     */
    public void perform(M move);


    /**
     * Restores the game state before the last turn.
     */
    public void undo();


    /**
     * Returns the next move. If the active player is human,
     * the next move will be asked for via the user-interface.
     * If the active player is artificial, the next move will be calculated
     * by the AI algorithm. A subsequent call to {@link #perform(M move)
     * perform} is needed to execute the move.
     * 
     * @return the next move to perform.
     */
    public M nextTurn();


    /**
     * Returns the game-state.
     * 
     * @return one of the four possible game-states.
     */
    public GameState getState();
}
