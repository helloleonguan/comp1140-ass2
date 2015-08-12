package comp1140.ass2;

import org.junit.Test;

import static org.junit.Assert.assertTrue;

/**
 * Created by steveb on 12/08/2015.
 */
public class ScoreGameTest {
    private static String[] game = {
            "RCCC RBTA SARR SBCR SHDD TBQD RAOO PBFP LBJH LHLH LGNN TAGN JDKI JBRA OHIM UAHK KDGJ KAPH JARK JAFG UADG UALA UASH QAGD QDCL PCIC MEQE MEBL DDKL MDRE TGJQ OHID EBFA QDON PAIR KBGT IBMM SHMO KDDR RCDK GCFO NAPR QCCQ IDAH FHKQ IHRP FATN LDAD NBIP OHJR DBEM FFFB PBMF BASN AAHN DBBP THMC FGTM BBSD AAME OBRB EBNJ . BBOF MHFC CBJI . . HANR DAHD . . CBMT AAGH . . BBBK . . . AACF",
    };

    @Test
    public void testScore() {
        int[] score  = BlokGame.scoreGame(game[0]);
        assertTrue("No score returned", score != null);
        assertTrue("Score array should be length 4, but is "+score.length, score.length == 4);
        assertTrue("Invalid score: (" + score[0] + ", " + score[1] + ", " + score[2] + ", " + score[3] + "), see official rules for scores for this game. for game (expected 20, -8, -24, -20)", (score[0] == 20 && score[1] == -8 && score[2] == -24 && score [3] == -20));
    }
}
