package final1.connectfour;

import java.util.Collections;
import java.util.List;
import java.util.Stack;

import final1.algorithm.Game;
import final1.algorithm.GameState;
import final1.algorithm.Player;

/**
 * The classic Connect Four game. Inputs are read from stdin, status and prompts
 * are output to stdout
 * 
 * @author robin
 * @version 1
 */
public class CFGame implements Game<CFMove> {

    private final CFGameField gameField;
    /**
     * A stack of the history of all moves from the beginning of the game.
     */
    private final Stack<CFMove> moves;
    /**
     * A list of {@link CFThreat}s, this List has reversible functions, so moves
     * can be undone without completely regenerating the list
     */
    private final CFThreatList threats;
    /**
     * The first player. This player always begins.
     */
    private final Player<CFMove> player1;
    private final Player<CFMove> player2;

    private final CFMoveComparator moveComparer;

    /**
     * The player who's turn it currently is.
     */
    private int currentPlayerTurn = 1;

    /**
     * the current game state
     * calculated after each move for better performance
     */
    private GameState state = GameState.PLAYING;


    /**
     * Initialize a new Connect Four game with the specified amount of human
     * players.
     * 
     * @param playerCount
     *            The number of human players. Depending on this, either human
     *            or AI players are initialized. The human player always comes
     *            first.
     */
    public CFGame(int playerCount) {
        gameField = new CFGameField();
        if (playerCount >= 1) {
            player1 = new CFHumanPlayer(this, 1);
        } else {
            player1 = new CFAIPlayer(this, 1);
        }
        if (playerCount >= 2) {
            player2 = new CFHumanPlayer(this, 2);
        } else {
            player2 = new CFAIPlayer(this, 2);
        }
        moves = new Stack<CFMove>();
        threats = new CFThreatList(this);
        moveComparer = new CFMoveComparator(this);
    }


    @Override
    public List<CFMove> getValidMoves() {
        List<CFMove> list = getGameField().getValidMoves();
        Collections.sort(list, moveComparer);
        return list;
    }


    /**
     * Locally evaluates the current state of the game with respect
     * to the player who has the turn.
     * This implementation searches for {@link CFThreat}s and evaluates them
     * based
     * on a logarithmic scale of their distance from happening.
     * 
     * @return a local evaluation of the current game state
     */
    @Override
    public double evaluateState() {
        double evaluation = 0;
        int player = getCurrentPlayer();
        if (state == GameState.PLAYING) {
            for (CFThreat t : threats) {
                int danger = gameField.nextPutY(t.getX()) + 1 - t.getY();
                if (danger > 0) { // not yet reached
                    double currating = 1d / Math.log(danger + 1);
                    if (t.getPlayer() == currentPlayerTurn) {
                        evaluation += currating;
                    } else {
                        evaluation -= currating;
                    }
                }
            }
        } else if (state == GameState.PLAYER_1_WON && player == 1
                || state == GameState.PLAYER_2_WON && player == 2) {
            evaluation = Double.POSITIVE_INFINITY;
        } else if (state == GameState.PLAYER_1_WON && player == 2
                || state == GameState.PLAYER_2_WON && player == 1) {
            evaluation = Double.NEGATIVE_INFINITY;
        }
        return evaluation;
    }


    /**
     * Performs the given move for the player who has the turn.
     * 
     * The move must have been checked for validity with
     * {@link CFGameField#moveValid()}
     * 
     * @param move
     *            The move to perform.
     */
    @Override
    public void perform(CFMove move) {
        getGameField().put(move.getX(), getCurrentPlayer());
        moves.push(move);
        threats.addNewThreats(move, getCurrentPlayer());
        currentPlayerTurn = getOtherPlayer();
        moveComparer.updateThreats();
        updateState();
    }


    @Override
    public void undo() {
        CFMove move = moves.pop();
        threats.undoMove();
        getGameField().remove(move.getX());
        currentPlayerTurn = getOtherPlayer();
        state = GameState.PLAYING; // if there was a move to be done, the
                                   // previous state was always PLAYING
    }


    @Override
    public CFMove nextTurn() {
        if (getCurrentPlayer() == 1) {
            return player1.getMove();
        } else {
            return player2.getMove();
        }
    }


    /**
     * Get the game field.
     * 
     * @return the game field.
     */
    public CFGameField getGameField() {
        return gameField;
    }


    /**
     * Get the player who's turn it currently is.
     * 
     * @return the players id.
     */
    public int getCurrentPlayer() {
        return currentPlayerTurn;
    }


    /**
     * Get the player that is not currently active.
     * 
     * @return the id of the player
     */
    public int getOtherPlayer() {
        return getCurrentPlayer() % 2 + 1; // swap 1<=>2
    }


    @Override
    public GameState getState() {
        return state;
    }


    /**
     * recalculate the game state
     * call once after each move
     */
    private void updateState() {
        for (CFThreat t : threats) {
            int player = t.getPlayer();
            int field = gameField.get(t.getX(), t.getY());
            if (player == field) { // if the player has put a piece where there
                                   // was a threat
                if (player == 1) {
                    state = GameState.PLAYER_1_WON;
                } else {
                    state = GameState.PLAYER_2_WON;
                }
                break;
            }
        }
        if (state == GameState.PLAYING && getGameField().isFull()) {
            state = GameState.DRAW;
        }
    }


    /**
     * 
     * @return the list of all threats
     */
    public CFThreatList getThreats() {
        return threats;
    }
}
