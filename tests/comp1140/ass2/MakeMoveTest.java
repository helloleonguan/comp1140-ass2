package comp1140.ass2;

import org.junit.Test;

import static org.junit.Assert.assertTrue;

/**
 * Created by steveb on 12/08/2015.
 */
public class MakeMoveTest {

    private static String[] game = {
            "RCCC",
            "RCCC RBTA SARR SBCR",
            "RCCC RBTA SARR SBCR SHDD TBQD RAOO PBFP LBJH LHLH LGNN TAGN JDKI JBRA",
            "RCCC RBTA SARR SBCR SHDD TBQD RAOO PBFP LBJH LHLH LGNN TAGN JDKI JBRA OHIM UAHK KDGJ KAPH JARK JAFG UADG UALA UASH QAGD QDCL PCIC MEQE MEBL DDKL MDRE TGJQ",
            "RCCC RBTA SARR SBCR SHDD TBQD RAOO PBFP LBJH LHLH LGNN TAGN JDKI JBRA OHIM UAHK KDGJ KAPH JARK JAFG UADG UALA UASH QAGD QDCL PCIC MEQE MEBL DDKL MDRE TGJQ OHID EBFA QDON PAIR KBGT IBMM SHMO KDDR RCDK GCFO NAPR QCCQ IDAH FHKQ IHRP FATN LDAD NBIP OHJR DBEM FFFB PBMF BASN AAHN DBBP THMC FGTM BBSD AAME",
            "RCCC RBTA SARR SBCR SHDD TBQD RAOO PBFP LBJH LHLH LGNN TAGN JDKI JBRA OHIM UAHK KDGJ KAPH JARK JAFG UADG UALA UASH QAGD QDCL PCIC MEQE MEBL DDKL MDRE TGJQ OHID EBFA QDON PAIR KBGT IBMM SHMO KDDR RCDK GCFO NAPR QCCQ IDAH FHKQ IHRP FATN LDAD NBIP OHJR DBEM FFFB PBMF BASN AAHN DBBP THMC FGTM BBSD AAME OBRB",
            "RCCC RBTA SARR SBCR SHDD TBQD RAOO PBFP LBJH LHLH LGNN TAGN JDKI JBRA OHIM UAHK KDGJ KAPH JARK JAFG UADG UALA UASH QAGD QDCL PCIC MEQE MEBL DDKL MDRE TGJQ OHID EBFA QDON PAIR KBGT IBMM SHMO KDDR RCDK GCFO NAPR QCCQ IDAH FHKQ IHRP FATN LDAD NBIP OHJR DBEM FFFB PBMF BASN AAHN DBBP THMC FGTM BBSD AAME OBRB EBNJ .",
            "RCCC RBTA SARR SBCR SHDD TBQD RAOO PBFP LBJH LHLH LGNN TAGN JDKI JBRA OHIM UAHK KDGJ KAPH JARK JAFG UADG UALA UASH QAGD QDCL PCIC MEQE MEBL DDKL MDRE TGJQ OHID EBFA QDON PAIR KBGT IBMM SHMO KDDR RCDK GCFO NAPR QCCQ IDAH FHKQ IHRP FATN LDAD NBIP OHJR DBEM FFFB PBMF BASN AAHN DBBP THMC FGTM BBSD AAME OBRB EBNJ . BBOF MHFC CBJI . . HANR DAHD . . CBMT AAGH . . ",
    };

    @Test
    public void testOKStart() {
        String move = BlokGame.makeMove(game[0]);
        assertTrue("Invalid move: " + game[0] + " " + move, BlokGame.legitimateGame(game[0] + move));
    }

    @Test
    public void testOKSecondMove() {
        String move = BlokGame.makeMove(game[1]);
        assertTrue("Invalid move: " + game[1] + " " + move, BlokGame.legitimateGame(game[1] + move));
    }

    @Test
    public void testOKMove() {
        String move = BlokGame.makeMove(game[2]);
        assertTrue("Invalid move: " + game[2] + " " + move, BlokGame.legitimateGame(game[2] + move));
        move = BlokGame.makeMove(game[3]);
        assertTrue("Invalid move: " + game[3] + " " + move, BlokGame.legitimateGame(game[3] + move));
        move = BlokGame.makeMove(game[4]);
        assertTrue("Invalid move: " + game[4] + " " + move, BlokGame.legitimateGame(game[4] + move));
    }

    @Test
    public void testNoMove() {
        String move = BlokGame.makeMove(game[5]);
        assertTrue("Invalid move returned when no move possible: " + game[5] + " " + move, BlokGame.legitimateGame(game[5] + move));
    }

    @Test
    public void testEndgame() {
        String move = BlokGame.makeMove(game[6]);
        assertTrue("Invalid move: " + game[6] + " " + move, BlokGame.legitimateGame(game[6] + move));
        move = BlokGame.makeMove(game[7]);
        assertTrue("Invalid move returned: " + game[7] + " " + move, BlokGame.legitimateGame(game[7] + move));
    }
}
