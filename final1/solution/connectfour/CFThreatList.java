package final1.connectfour;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Stack;

/**
 * A list of all threats in the game, contains a history for full game course
 * reconstruction / undoing moves and faster and more memory efficient recursion
 * 
 * @author robin
 * @version 1
 */
public class CFThreatList implements Iterable<CFThreat> {

    /** the field in which the threats happen */
    private final CFGameField field;
    private final ArrayList<CFThreat> list;
    /** number of threats that were added in the nth move */
    private final Stack<Integer> addedCounts;


    /**
     * initialize a new threat list
     * 
     * @param game
     *            the game in which this threat
     */
    public CFThreatList(CFGame game) {
        field = game.getGameField();
        list = new ArrayList<CFThreat>(40);
        addedCounts = new Stack<Integer>();
    }


    /**
     * add new threats caused by a move. Must be called after the actual move is
     * executed.
     * 
     * @param move
     *            the move
     * @param player
     *            the player who did the move
     */
    public void addNewThreats(CFMove move, int player) {
        int x = move.getX();
        int y = field.nextPutY(x) + 1;
        int count = 0;
        count += addThreatsNear(x, y, 1, 0, player); // horizontal
        count += addThreatsNear(x, y, 0, 1, player); // vertical
        count += addThreatsNear(x, y, 1, 1, player); // diagonal1
        count += addThreatsNear(x, y, 1, -1, player); // diagonal2
        addedCounts.push(count);
    }


    /**
     * undo the last move
     */
    public void undoMove() {
        int count = addedCounts.pop();
        while (count-- > 0) {
            list.remove(list.size() - 1);
        }
    }


    /**
     * add all threats from the middle point x,y in the direction xadd,yadd.
     * Example: {@code
     * M = (x,y),             player just put a piece here
     * R = (x,y)+(xadd,yadd)  so it will be checked if he has a piece here
     * L = (x,y)-(xadd,yadd)  and here
     * T = threat position    then threats will be added here
     * | |T| | | | | |
     * | | |L| | | | |
     * | | | |M| | | |
     * | | | | |R| | |
     * | | | | | |T| |
     * }
     * 
     * @param x
     *            coordinate of the middle position
     * @param y
     *            coordinate of the middle position
     * @param xadd
     *            -1 or +1 direction
     * @param yadd
     *            -1 or +1
     */
    private int addThreatsNear(int x, int y, int xadd, int yadd, int player) {
        boolean l1 = field.get(x - xadd * 1, y - yadd * 1) == player;
        boolean r1 = field.get(x + xadd * 1, y + yadd * 1) == player;
        boolean l2 = field.get(x - xadd * 2, y - yadd * 2) == player;
        boolean r2 = field.get(x + xadd * 2, y + yadd * 2) == player;
        boolean l3 = field.get(x - xadd * 3, y - yadd * 3) == player;
        boolean r3 = field.get(x + xadd * 3, y + yadd * 3) == player;
        int count = 0;

        // |X|M|X|T|
        // | |M|X|T|X
        if (r1 && l1) {
            count += threatAdd(x + xadd * 2, y + yadd * 2, player);
            count += threatAdd(x - xadd * 2, y - yadd * 2, player);
        }


        if (r1 && r3) {
            count += threatAdd(x + xadd * 2, y + yadd * 2, player);
        }
        // gap: |X|M|T|X|
        // 3right: |M|T|X|X|
        else if (r2 && (r3 || l1)) {
            count += threatAdd(x + xadd, y + yadd, player);
        }
        // 2right: |T|M|X|X|T|
        else if (r1 && r2) {
            count += threatAdd(x + xadd * 3, y + yadd * 3, player);
            count += threatAdd(x - xadd, y - yadd, player);
        }

        // |X|M|X|T|
        // | |M|X|T|X
        if (l1 && l3) {
            count += threatAdd(x - xadd * 2, y - yadd * 2, player);
        }
        // gap: |X|M|T|X|
        // 3right: |M|T|X|X|
        else if (l2 && (l3 || r1)) {
            count += threatAdd(x - xadd, y - yadd, player);
        }
        // 2right: |T|M|X|X|T|
        else if (l1 && l2) {
            count += threatAdd(x - xadd * 3, y - yadd * 3, player);
            count += threatAdd(x + xadd, y + yadd, player);
        }

        return count;
    }


    /**
     * Creates a new threat at the position and adds it to the list, if it is
     * not already in it
     * 
     * @param x
     *            coordinate
     * @param y
     *            coordinate
     * @param player
     *            player id
     * @return
     *         the amount of threats added (0 or 1)
     */
    private int threatAdd(int x, int y, int player) {
        int count = 0;
        if (field.inbound(x, y)) {
            CFThreat t = new CFThreat(x, y, player);
            if (!list.contains(t)) {
                list.add(t);
                count++;
            }
        }
        return count;
    }


    @Override
    public Iterator<CFThreat> iterator() {
        return list.iterator();
    }


    @Override
    public String toString() {
        String s = "TL:";
        // s += list.size();
        for (CFThreat t : this) {
            s += "(" + t.getPlayer() + ":" + t.getX() + ":" + t.getY() + "),";
        }
        return s;
    }
}
