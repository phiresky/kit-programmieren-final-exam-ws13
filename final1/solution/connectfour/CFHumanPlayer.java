package final1.connectfour;

import final1.Terminal;
import final1.algorithm.Player;

/**
 * A Human Connect Four Player taking input from the terminal.
 * 
 * @author robin
 * @version 1
 * 
 */
public class CFHumanPlayer implements Player<CFMove> {

    /**
     * The id of this player used for printing his "name".
     */
    private final int id;
    private final CFGameField gameField;


    /**
     * create a new Human player.
     * 
     * @param game
     *            the game in which this player lives.
     * @param id
     *            the id of the player.
     */
    public CFHumanPlayer(CFGame game, int id) {
        this.gameField = game.getGameField();
        this.id = id;
    }


    @Override
    public CFMove getMove() {
        Terminal.prompt("Player " + id + ": ");
        String s = Terminal.readln();
        int x;
        try {
            x = Integer.parseInt(s);
        } catch (NumberFormatException e) {
            x = -1;
        }
        CFMove move;
        if (gameField.inbound(x) && gameField.nextPutY(x) >= 0) {
            move = new CFMove(x);
        } else {
            Terminal.println("Error, Player " + id + ": " + s);
            move = getMove();
        }
        return move;
    }
}
