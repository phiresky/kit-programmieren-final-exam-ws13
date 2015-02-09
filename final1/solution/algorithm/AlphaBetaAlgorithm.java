package final1.algorithm;

import final1.Utility;

/**
 * An AI player for a generic game using the alpha beta algorithm in the NegaMax
 * variant.
 * 
 * @author robin
 * @version 1
 * 
 * @param <G>
 *            The Game type
 * @param <M>
 *            The Move type
 */
public class AlphaBetaAlgorithm<G extends Game<M>, M extends Move> implements
        AIPlayer<Move> {

    /**
     * the maximum recursion depth. When this value is reached,
     * {@link Game#evaluateState()} is
     * called instead of recursing further.
     * 
     */
    public static final int SEARCH_DEPTH = 11;
    /**
     * the game in which this AI player acts
     */
    private final G game;
    /** the move that was found on the last search run */
    private M foundMove;


    /**
     * initialize this player
     * 
     * @param game
     *            the game in which this AI player acts
     */
    public AlphaBetaAlgorithm(G game) {
        this.game = game;
    }


    /**
     * recursively evaluate the chance of winning using an Alpha-Beta-Search,
     * saving the best found move to {@link #foundMove}
     * 
     * @param depth
     *            the recursion depth
     * @param maxValue
     *            the initial search value
     * @param beta
     *            the cut off value
     * @return the possibility of winning
     */
    private double recursiveEval(int depth, double alpha, double beta) {
        double maxValue = alpha;
        if (depth > 0 && game.getState() == GameState.PLAYING) {
            for (M move : game.getValidMoves()) {
                game.perform(move);
                double value = -recursiveEval(depth - 1, -beta, -maxValue);
                game.undo();

                if (Utility.greaterThan(value, maxValue)) {
                    maxValue = value;
                    if (depth == SEARCH_DEPTH) {
                        foundMove = move;
                    }
                    if (Utility.greaterThanOrEqual(value, beta)) {
                        break;
                    }
                }
            }
        } else {
            maxValue = game.evaluateState();
        }
        return maxValue;
    }


    @Override
    public M getBestMove() throws GameException {
        foundMove = null;
        recursiveEval(SEARCH_DEPTH, Double.NEGATIVE_INFINITY,
                Double.POSITIVE_INFINITY);
        if (foundMove != null) {
            return foundMove;
        } else {
            throw new GameException("No move found");
        }
    }


    /**
     * get the game in which this player acts
     * 
     * @return the game
     */
    protected G getGame() {
        return game;
    }

}
