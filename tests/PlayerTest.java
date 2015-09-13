import comp1140.ass2.Player;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by Liyang GUAN on 13/09/15.
 * This test class is designed to test the functionality of all methods in the Player class.
 */

public class PlayerTest {
    private static String[] game = {
            "",
            "RCCC",
            "RCCC RBTA SARR SBCR",
            "RCCC RBTA SARR SBCR SHDD TBQD RAOO PBFP LBJH LHLH LGNN TAGN JDKI JBRA OHIM UAHK KDGJ KAPH JARK JAFG UADG UALA UASH QAGD QDCL PCIC MEQE MEBL DDKL MDRE TGJQ OHID EBFA QDON PAIR KBGT IBMM SHMO KDDR RCDK GCFO NAPR QCCQ IDAH FHKQ IHRP FATN LDAD NBIP OHJR DBEM FFFB PBMF BASN AAHN DBBP THMC FGTM BBSD AAME OBRB EBNJ . BBOF MHFC CBJI . . HANR DAHD . . CBMT AAGH . . BBBK . . . AACF"
    };

    public static String toString(String[] string_list) {
        String result = "";
        for (int i = 0; i < string_list.length; i ++) {
            result = result + string_list[i];
        }
        return result;
    }

    //Tests for toInt() method.
    @Test
    public void testToInt () {
        assertTrue("Expected: 0 but got: "+ Player.BLUE.toInt(), Player.BLUE.toInt() == 0);
        assertTrue("Expected: 1 but got: "+ Player.YELLOW.toInt(), Player.YELLOW.toInt() == 1);
        assertTrue("Expected: 2 but got: "+ Player.RED.toInt(), Player.RED.toInt() == 2);
        assertTrue("Expected: 3 but got: "+ Player.GREEN.toInt(), Player.GREEN.toInt() == 3);

    }
    //Tests for getPlayer(int i) method.
    @Test
    public void testGetPlayer () {
        assertTrue("Expected: Player.BLUE but got: "+ Player.getPlayer(0),Player.getPlayer(0) == Player.BLUE);
        assertTrue("Expected: Player.YELLOW but got: "+ Player.getPlayer(1),Player.getPlayer(1) == Player.YELLOW);
        assertTrue("Expected: Player.RED but got: "+ Player.getPlayer(2),Player.getPlayer(2) == Player.RED);
        assertTrue("Expected: Player.GREEN but got: "+ Player.getPlayer(3),Player.getPlayer(3) == Player.GREEN);
    }

    //Tests for remainingTiles(String game) method
    @Test
    public void testRemainingTiles () {
        assertTrue("Expected: ABCDEFGHIJKLMNOPQRSTU but got: " + PlayerTest.toString(Player.BLUE.remainingTiles(game[0])),PlayerTest.toString(Player.BLUE.remainingTiles(game[0])).equals("ABCDEFGHIJKLMNOPQRSTU"));
        assertTrue("Expected: ABCDEFGHIJKLMNOPQRSTU but got: " + PlayerTest.toString(Player.YELLOW.remainingTiles(game[0])),PlayerTest.toString(Player.YELLOW.remainingTiles(game[0])).equals("ABCDEFGHIJKLMNOPQRSTU"));
        assertTrue("Expected: ABCDEFGHIJKLMNOPQRSTU but got: " + PlayerTest.toString(Player.RED.remainingTiles(game[0])), PlayerTest.toString(Player.RED.remainingTiles(game[0])).equals("ABCDEFGHIJKLMNOPQRSTU"));
        assertTrue("Expected: ABCDEFGHIJKLMNOPQRSTU but got: " + PlayerTest.toString(Player.GREEN.remainingTiles(game[0])),PlayerTest.toString(Player.GREEN.remainingTiles(game[0])).equals("ABCDEFGHIJKLMNOPQRSTU"));

        assertFalse("Should not contain R for BLUE player", PlayerTest.toString(Player.BLUE.remainingTiles(game[1])).contains("R"));
        assertTrue("Expected: ABCDEFGHIJKLMNOPQSTU but got: " + PlayerTest.toString(Player.BLUE.remainingTiles(game[1])), PlayerTest.toString(Player.BLUE.remainingTiles(game[1])).equals("ABCDEFGHIJKLMNOPQSTU"));

        assertTrue("Expected: ABCDEFGHIJKLMNOPQSTU but got: " + PlayerTest.toString(Player.YELLOW.remainingTiles(game[2])), PlayerTest.toString(Player.YELLOW.remainingTiles(game[2])).equals("ABCDEFGHIJKLMNOPQSTU"));
        assertTrue("Expected: ABCDEFGHIJKLMNOPQRTU but got: " + PlayerTest.toString(Player.RED.remainingTiles(game[2])), PlayerTest.toString(Player.RED.remainingTiles(game[2])).equals("ABCDEFGHIJKLMNOPQRTU"));
        assertTrue("Expected: ABCDEFGHIJKLMNOPQRTU but got: " + PlayerTest.toString(Player.GREEN.remainingTiles(game[2])),PlayerTest.toString(Player.GREEN.remainingTiles(game[2])).equals("ABCDEFGHIJKLMNOPQRTU"));

        assertFalse("Should not contain T for YELLOW player", PlayerTest.toString(Player.YELLOW.remainingTiles(game[3])).contains("T"));
        assertFalse("Should not contain J for RED player",  PlayerTest.toString(Player.RED.remainingTiles(game[3])).contains("J"));
        assertFalse("Should not contain P for GREEN player",  PlayerTest.toString(Player.GREEN.remainingTiles(game[3])).contains("P"));
        assertFalse("Should not contain E for BLUE player", PlayerTest.toString(Player.BLUE.remainingTiles(game[3])).contains("E"));
        assertFalse("Should not contain A for BLUE player", PlayerTest.toString(Player.BLUE.remainingTiles(game[3])).contains("A"));

    }
}
