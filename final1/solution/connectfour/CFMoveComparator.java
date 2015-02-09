package final1.connectfour;

import java.util.Comparator;

/**
 * A Comparator for ConnectFourMoves. Compares Moves according to the rules
 * specified in the assignment:
 * 1. Positive threats in the column
 * 2. Number of generally possible 4-rows crossing the coordinates of the move.
 * 3. Left to right
 * 
 * @author robin
 * @version 1
 */
public class CFMoveComparator implements Comparator<CFMove> {

    /**
     * number of 4-rows possible in principle at the POSSIBLE_COUNT[y][x]
     * position
     */
    private static final byte[][] POSSIBLE_COUNT = {{3, 4, 5, 7, 5, 4, 3},
            {4, 6, 8, 10, 8, 6, 4}, {5, 8, 11, 13, 11, 8, 5},
            {5, 8, 11, 13, 11, 8, 5}, {4, 6, 8, 10, 8, 6, 4},
            {3, 4, 5, 7, 5, 4, 3}};
    private final byte[] threatAt;
    private final CFGame game;


    /**
     * initialize a new MoveComparator with the current game state
     * 
     * @param game
     *            the game to use
     */
    public CFMoveComparator(CFGame game) {
        this.game = game;
        threatAt = new byte[CFGameField.COL_COUNT];
    }


    /**
     * update the threat cache for this comparator. Call this after every move
     * in the game
     */
    public void updateThreats() {
        for (int i = 0; i < threatAt.length; i++) {
            threatAt[i] = 0;
        }
        int currentPlayer = game.getCurrentPlayer();

        CFGameField field = game.getGameField();
        for (CFThreat t : game.getThreats()) {
            if (t.getPlayer() == currentPlayer) {
                if (field.nextPutY(t.getX()) >= t.getY()) {
                    threatAt[t.getX()] = 1;
                }
            }
        }
    }


    @Override
    public int compare(CFMove first, CFMove second) {
        int x1 = first.getX();
        int x2 = second.getX();
        int y1 = game.getGameField().nextPutY(x1);
        int y2 = game.getGameField().nextPutY(x2);

        int rating = threatAt[x2] - threatAt[x1];
        if (rating == 0) {
            rating = POSSIBLE_COUNT[y2][x2] - POSSIBLE_COUNT[y1][x1];
            if (rating == 0) {
                rating = x1 - x2;
            }
        }

        return rating;
    }
}
