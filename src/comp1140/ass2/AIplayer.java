package comp1140.ass2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

/**
 * Created by Liyang GUAN on 2015/8/31/0031.
 */
// (All written by Liyang(Leon) )
public class AIplayer {

    public static int scoreFunction(int[] scores, Player player) {
        int promisingValue = 0;

        for (int i = 0; i < 4; i++) {
            if (i == player.toInt()) {
                promisingValue += scores[i];
            } else {
                promisingValue -= scores[i];
            }
        }
        return promisingValue;
    }

    public static String getMove(String game) {
        int turn = 1;
        String[] tilesPLaced = game.split("\\s+");
        int[] board0 = new int[400];

        for (String s: tilesPLaced) {
            Legit.draw(s, board0,turn);
            turn = Legit.incrementTurn(turn);
        }

        Player currentPlayer = Player.getPlayer(tilesPLaced.length % 4);

        // Only choose a certian amount of Pieces.
        ArrayList<String> tile = new ArrayList<>();
        String[] leftTiles = currentPlayer.remainingTiles(game);
        for (String s : leftTiles) {
            tile.add(s);
        }

        String[] rotation = {"A", "B", "C", "D", "E", "F", "G", "H"};
        String[] position = new String[400];


        for (int i = 0; i < 400; i++) {
            position[i] = Character.toString((char) ('A' + i % 20)) + Character.toString((char) ('A' + i / 20));
        }

        ArrayList<String> candidates = new ArrayList<>();

        for (int i = 0; i < tile.size(); i++) {
            for (int j = 0; j < rotation.length; j++) {
                for (int k = 0; k < position.length; k++) {

                    if ( Legit.checkLegitForTile(board0,tile.get(i) + rotation[j] + position[k],tilesPLaced.length < 4, turn) ) {
                        if ((tile.get(i) + rotation[j] + position[k]).equals("UHAS")) {

                            System.out.println(Legit.checkLegitForTile(board0, tile.get(i) + rotation[j] + position[k], tilesPLaced.length < 4, turn));
                        }
                        candidates.add(tile.get(i) + rotation[j] + position[k]);
                    }
                }
            }
        }

        if (candidates.size() == 0) {
            return " .";
        }
        String result;

        // greedy solution.

            Collections.sort(candidates);
            Collections.reverse(candidates);
            result = candidates.get(0);

        return " "+result;
    }

}
