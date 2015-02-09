package final1.algorithm;

/**
 * Used to identify the (disjoint) states of a zero-sum two-player game
 * 
 * @author ITI, VeriAlg Group
 * @version 1
 */
public enum GameState {
    /**
     * There are still possible moves.
     */
    PLAYING,
    
    /**
     * Player 1 won the game.
     */
    PLAYER_1_WON,
    
    /**
     * Player 2 won the game.
     */
    PLAYER_2_WON,
    
    /**
     * There is a draw situation where no more moves are possible.
     */
    DRAW
}
