package final1.algorithm;

/**
 * This interface is for algorithm-implementations that calculate optimal moves
 * for an AI-Player in generic two-player games.
 *
 * @author ITI, VeriAlg Group
 * @version 1
 *
 * @param <M> The type of move the game uses.
 */
public interface AIPlayer<M extends Move> {
    /**
     * Calculates and returns the estimated best move for the active AI-player.
     * @return the estimated best move for the active player.
     * @throws GameException when no move is possible.
     */
    public M getBestMove() throws GameException;
}
