package comp1140.ass2;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by steveb on 12/08/2015.
 */
public class LegitimateGameTest {

    private static String[] game = {
            "RCCC", // legitimate start
            "RCAA", // illegal start, off board
            "RBTA", // illegal start; must play into blue corner  first
            "SHDD", // illegal start; not placed in corner
            "SHZZ", // illegal start; not on board
            "SZDD", // illegal start; invalid rotation
            "RCCC RBTA SARR SBCR", // legitimate start
            "RCCCRBTASARRSBCR", // legitimate start no spaces
            "RCCC   RBTA SARR SBCR", // legitimate start multiple spaces
            "RCCC RBTA SARR SBCR JACC", // illegal move, overlapping
            "RCCC RBTA SARR SBCR JAED", // illegal move, not touching
            "RCCC RBTA SARR SBCR JADC", // illegal move, adjacent
            "RCCC RBTA SARR SBCR SHDD TBQD RAOO PBFP LBJH LHLH LGNN TAGN JDKI JBRA OHIM UAHK KDGJ KAPH JARK FAFH", // illegal move, green not touching green
            "RCCC RBTA SARR SBCR SHDD TBQD RAOO PBFP LBJH LHLH LGNN TAGN JDKI JBRA OHIM UAHK KDGJ KAPH JARK JAFG UADG UALA UASH QAGD QDCL PCIC MEQE MEBL DDKL MDRE TGJQ OHID EBFA QDON PAIR KBGT IBMM SHMO KDDR RCDK GCFO NAPR QCCQ IDAH FHKQ IHRP FATN LDAD NBIP OHJR DBEM FFFB PBMF BASN AAHN DBBP THMC FGTM BBSD AAME OBRB EBNJ . BBOF MHFC CBJI . . HANR DAHD . . CBMT AAGH . . BBBK . . . AACF ",
    };

    @Test
    public void testOKStart() {
        assertTrue("Incorrectly rejected simple start: " + game[0], BlokGame.legitimateGame(game[0]));
        assertTrue("Incorrectly rejected start with spaces: " + game[6], BlokGame.legitimateGame(game[6]));
        assertTrue("Incorrectly rejected start without spaces: " + game[7], BlokGame.legitimateGame(game[7]));
        assertTrue("Incorrectly rejected start with multiple spaces: " + game[8], BlokGame.legitimateGame(game[8]));
    }

    @Test
    public void testBadStart() {
        assertFalse("Incorrectly accepted off board start: " + game[1], BlokGame.legitimateGame(game[1]));
        assertFalse("Incorrectly accepted start into wrong corner: " + game[2], BlokGame.legitimateGame(game[2]));
        assertFalse("Incorrectly accepted start not in corner: " + game[3], BlokGame.legitimateGame(game[3]));
        assertFalse("Incorrectly accepted off board start: " + game[4], BlokGame.legitimateGame(game[4]));
        assertFalse("Incorrectly accepted start with invalid rotation: " + game[5], BlokGame.legitimateGame(game[5]));
    }

    @Test
    public void testBadMove() {
        assertFalse("Incorrectly accepted overlapping move: " + game[9], BlokGame.legitimateGame(game[9]));
        assertFalse("Incorrectly accepted move that is not touching: " + game[10], BlokGame.legitimateGame(game[10]));
        assertFalse("Incorrectly accepted move that is adjacent: " + game[11], BlokGame.legitimateGame(game[11]));
        assertFalse("Incorrectly accepted move that is not touching: " + game[12], BlokGame.legitimateGame(game[12]));
    }

    @Test
    public void testOK() {
        assertTrue("Incorrectly rejected valid game: " + game[13], BlokGame.legitimateGame(game[13]));
    }
}
