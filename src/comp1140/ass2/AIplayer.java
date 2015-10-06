package comp1140.ass2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

/**
 * Created by Liyang GUAN on 2015/8/31/0031.
 */
// (All written by Liyang(Leon) )
public class AIplayer {

    final static int NUMBER_OF_TILES = 1;

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
                    if (Legit.legitimateGame(game + " " + tile.get(i) + rotation[j] + position[k])) {
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

    public static void main(String[] args) {
        int[] scores = {23,34,45,16};
        System.out.println(scoreFunction(scores,Player.BLUE));
        System.out.println(getMove("RCCC RBTA SARR SBCR SHDD TBQD RAOO PBFP LBJH LHLH LGNN TAGN JDKI JBRA OHIM UAHK KDGJ KAPH JARK JAFG UADG UALA UASH QAGD QDCL PCIC MEQE MEBL DDKL MDRE TGJQ OHID EBFA QDON PAIR KBGT IBMM SHMO KDDR RCDK GCFO NAPR QCCQ IDAH FHKQ IHRP FATN LDAD NBIP OHJR DBEM FFFB PBMF BASN AAHN DBBP THMC FGTM BBSD AAME OBRB EBNJ ."));
    }

}
