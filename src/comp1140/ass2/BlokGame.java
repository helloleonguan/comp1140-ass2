package comp1140.ass2;

/**
 * Created by steveb on 12/08/2015.
 */
public class BlokGame {

    /**
     * Parse a string representing a game state and determine whether it is legitimate.  The game may be in progress
     * (ie incomplete).
     * @param game A string representing the state of the game, as described in the assignment description.
     * @return True if the string represents a legitimate game state, according to the encoding defined in the
     * assignment description and the rules of the game.
     */
    public static boolean legitimateGame(String game) {
        /* FIXME */
        return false;
    }

    /**
     * Parse a string representing a game state and return a score for the game.  The game may be in progress
     * (ie incomplete), in which case score should reflect the score at that time (if no further moves were made).
     * @param game A string representing the state of the game, as described in the assignment description.
     * @return An array of four integers reflecting the score for the game.   The scores should be given in the playing
     * order specified in the rules of the game, and the scores should be made according to the rules.
     */
    public static int[] scoreGame(String game) {
        int[] scores = new int[4];
        String[] tilesPLaced = game.split("\\s+");
        int n = 0;
        final int totalScore = 89; // best score if one placed all tiles on board.
        for (String s : tilesPLaced) {
            if (s.charAt(0) != '.') {
                // scores[n%4] +=  || getScore for each tile -- Character.toString(s.charAt(0));
            }

            if (79<n && n<84) {
                if (scores[n%4] == totalScore) // placed all tiles.
                    scores[n%4] += 15;
                if (s.charAt(0) == 'A') // monomino tile bonus.
                    scores[n%4] += 5;
            }
            n++;
        }

        for (int i = 0; i < 4; i ++) {
            scores[i] -= 89; // convert the score according to the rules.
        }
        return scores;
    }

    /**
     * Parse a string representing a game state and return a valid next move.  If no move is possible, return a pass ('.')
     * @param game A string representing the state of the game, as described in the assignment description.
     * @return A four-character string representing the next move.
     */
    public static String makeMove(String game) {
        /* FIXME */
        return null;
    }
}
