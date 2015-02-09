package final1.connectfour;

import final1.Terminal;
import final1.algorithm.AlphaBetaAlgorithm;
import final1.algorithm.GameException;
import final1.algorithm.Player;

/**
 * an AI player for the Connect Four game using {@link AlphaBetaAlgorithm}
 * 
 * @author robin
 * @version 1
 * 
 */
public class CFAIPlayer extends AlphaBetaAlgorithm<CFGame, CFMove> implements
        Player<CFMove> {

    private final int id;


    /**
     * initialize a new AI player
     * 
     * @param game
     *            the game in which this player acts
     * @param id
     *            the player id, must be > 0
     */
    public CFAIPlayer(CFGame game, int id) {
        super(game);
        this.id = id;
    }


    @Override
    public CFMove getMove() {
        CFMove move = null;
        try {
            move = getBestMove();
        } catch (GameException e) {
            // no move was found that has a larger rating than -Inf
            // i.e. Every possible move results in losing
            // so move the first possible move
            move = getGame().getValidMoves().get(0);
        }
        Terminal.prompt("Player " + id + ": ");
        Terminal.println("" + move.getX());
        return move;
    }
}
