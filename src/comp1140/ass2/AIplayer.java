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
        String[] tilesPLaced = game.split("\\s+");
        Player currentPlayer = Player.getPlayer(tilesPLaced.length % 4);
        int[] currentScores = BlokGame.scoreGame(game);

        String[] tile = currentPlayer.remainingTiles(game);
        String[] rotation = {"A", "B", "C", "D", "E", "F", "G", "H"};
        String[] position = new String[400];

        for (int i = 0; i < 400; i++) {
            position[i] = Character.toString((char) ('A' + i % 20)) + Character.toString((char) ('A' + i / 20));
        }

        ArrayList<String> candidates = new ArrayList<>();

        for (int i = 0; i < tile.length; i++) {
            for (int j = 0; j < rotation.length; j++) {
                for (int k = 0; k < position.length; k++) {
                    if (Legit.checkLegitForTile(game, tile[i] + rotation[j] + position[k])) {
                        candidates.add(tile[i] + rotation[j] + position[k]);
                    }
                }
            }
        }

        String result;

        // greedy solution.
        if (candidates.size() == 0)
            result = ".";
        else {
            Collections.sort(candidates);
            Collections.reverse(candidates);
            result = candidates.get(0);
        }

        return " "+result;
    }

}
