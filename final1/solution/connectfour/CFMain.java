package final1.connectfour;

import final1.Terminal;
import final1.algorithm.GameState;

/**
 * The main class for the Connect Four game.
 * Takes the number of players as an argument and the game input from stdin.
 * 
 * @author robin
 * @version 1
 * 
 */
public final class CFMain {

    /** call main method instead */
    private CFMain() {

    }


    /**
     * Begin main program. Takes exactly one argument: the number of human
     * players.
     * 0 means computer vs computer,
     * 1 means human vs computer,
     * 2 means human vs human.
     * 
     * @param args
     *            the command line arguments
     */
    public static void main(String[] args) {
        if (args.length == 1) {
            parsePlayerCount(args[0]);
        } else {
            Terminal.println("Error, invalid argument; excepted human player count (0,1,2)");
            System.exit(1);
        }
    }


    /**
     * Try to parse the player count from a string.
     * Start the game if the count is valid, exit with an error otherwise.
     * 
     * @param countString
     *            the unparsed player count.
     */
    private static void parsePlayerCount(String countString) {
        int humanPlayerCount = -1;
        boolean inputValid = false;
        try {
            humanPlayerCount = Integer.parseInt(countString);
            if (humanPlayerCount >= 0 && humanPlayerCount <= 2) {
                inputValid = true;
            }
        } catch (NumberFormatException e) {
            inputValid = false;
        }

        if (inputValid) {
            startGame(humanPlayerCount);
        } else {
            Terminal.println("Error, invalid player count");
            System.exit(1);
        }
    }


    /**
     * Begin the ConnectFour game.
     * 
     * @param playerCount
     *            the number of human players.
     */
    private static void startGame(int playerCount) {
        CFGame game = new CFGame(playerCount);

        // long start = System.nanoTime();
        Terminal.println(game.getGameField().toString());
        while (game.getState() == GameState.PLAYING) {
            game.perform(game.nextTurn());
            Terminal.println(game.getGameField().toString());
        }
        switch (game.getState()) {
            case DRAW:
                Terminal.println("Draw!");
            break;
            case PLAYER_1_WON:
                Terminal.println("Player 1 won the game!");
            break;
            case PLAYER_2_WON:
                Terminal.println("Player 2 won the game!");
            break;
            default:
            break;
        }
        // System.out.println((System.nanoTime() - start) / 1000000);
        System.exit(0);
    }
}
