package comp1140.ass2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Liyang GUAN on 2015/8/26/0026.
 */
public enum Player {

    BLUE,
    YELLOW,
    RED,
    GREEN;

    private ArrayList<String> allTiles = new ArrayList<String>(Arrays.asList("A", "B", "C", "D", "E", "F", "G",
                                                                             "H", "I", "J", "K", "L", "M", "N",
                                                                             "O", "P", "Q", "R", "S", "T", "U"));

    /**
     * Convert a player to an Int to represent the order in the game.
     * @return  an Integer.
     */
    //e.g. to call this method: Player.BLUE.toInt() .

    public int toInt() {
        int i = 0;
        switch (this) {
            case BLUE: i = 0; break;
            case YELLOW: i = 1; break;
            case RED:i = 2; break;
            case GREEN: i = 3; break;
        }
        return  i;
    }

    /**
     * Convert an Int to a Player in the game.
     * @param i An Integer representing the player in the game.
     * @return  a Player..
     */
    public static Player getPlayer(int i) {
        Player p = null;
        switch (i) {
            case 0: p = BLUE; break;
            case 1: p = YELLOW; break;
            case 2: p = RED; break;
            case 3: p = GREEN; break;
        }
        return p;
    }

    /**
     * Parse a string representing a game state and return a list of available pieces for current turn.
     * @param game A string representing the state of the game.
     * @return A list strings representing all remaining tiles for the player. e.g ["A", "C", "H", "U"]
     */
    // e.g to call this method: Player.YELLOW.remainingTiles(game) .
    public String[] remainingTiles(String game) {

        String[] tilesPLaced = game.split("\\s+");
        for (int i = 0; i < tilesPLaced.length; i ++) {
            tilesPLaced[i] = Character.toString(tilesPLaced[i].charAt(0));
        }

        for (int i = 0; i < tilesPLaced.length; i ++) {
            if (i % 4 == this.toInt() && tilesPLaced[i] != ".") {
                allTiles.remove(tilesPLaced[i]);
            }
        }

        String[] result = new String[allTiles.size()];
        for (int i = 0; i < allTiles.size(); i ++) {
            result[i] = allTiles.get(i);
        }

        return result;
    }
}
