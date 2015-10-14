package comp1140.ass2;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by Faizan on 23/09/2015, to test some of the functions I made to be used for the GUI (not the GUI itself, but
 * functions that are used by it)
 */
public class GUIMethodsTest {
    @Test
    public void testisOnlyAlpha() {
        assertTrue("Expected true, as 'ABCD' only contains letters but got "+PlayBoard.isOnlyAlpha("ABCD"),PlayBoard.isOnlyAlpha("ABCD"));
        assertTrue("Expected true, as '' no numbers/symbols but got "+PlayBoard.isOnlyAlpha(""),PlayBoard.isOnlyAlpha(""));
        assertTrue("Expected true, as 'XYZAR' only contains letters but got "+PlayBoard.isOnlyAlpha("XYZAR"),PlayBoard.isOnlyAlpha("XYZAR"));
        assertFalse("Expected false, as '22222' contains only numbers but got " + PlayBoard.isOnlyAlpha("22222"), PlayBoard.isOnlyAlpha("22222"));
        assertFalse("Expected false, as 'BY!%' contains non-alphebtical symbols but got "+PlayBoard.isOnlyAlpha("BY!%"),PlayBoard.isOnlyAlpha("BY!%"));
        assertFalse("Expected false, as 'T@3$' contains numbers and non-alphabetical symbols but got "+PlayBoard.isOnlyAlpha("T@3$"),PlayBoard.isOnlyAlpha("T@3$"));
    }

    @Test
    public void testisLengthOnlyFour() {
        assertTrue("Expected true, has 'ABCD' only four characters but got"+PlayBoard.isLengthOnlyFour("ABCD"),PlayBoard.isLengthOnlyFour("ABCD"));
        assertTrue("Expected true, has 'A BC' only four characters but got"+PlayBoard.isLengthOnlyFour("A BC"),PlayBoard.isLengthOnlyFour("A BC"));
        assertFalse("Expected false, as 'ABC' has three characters but got" + PlayBoard.isLengthOnlyFour("ABC"), PlayBoard.isLengthOnlyFour("ABC"));
        assertFalse("Expected false, as 'ABCDE' has five characters but got"+PlayBoard.isLengthOnlyFour("ABCDE"),PlayBoard.isLengthOnlyFour("ABCDE"));
        assertFalse("Expected false, as '' has no characters but got"+PlayBoard.isLengthOnlyFour(""),PlayBoard.isLengthOnlyFour(""));


    }

    @Test
    public void testisValidEncoding() {
        assertTrue("Expected true as ABCD is a valid game piece, but got "+PlayBoard.isValidEncoding("ABCD"),PlayBoard.isValidEncoding("ABCD"));
        assertTrue("Expected true as RCCC is a valid game piece, but got "+PlayBoard.isValidEncoding("RCCC"),PlayBoard.isValidEncoding("RCCC"));
        assertTrue("Expected true as OHIM is a valid game piece, but got "+PlayBoard.isValidEncoding("OHIM"),PlayBoard.isValidEncoding("OHIM"));
        assertFalse("Expected false, as V is not a valid polynomio but got" + PlayBoard.isValidEncoding("VCCC"), PlayBoard.isValidEncoding("VCCC"));
        assertFalse("Expected false, as K is not a valid rotation but got"+PlayBoard.isValidEncoding("VKCC"),PlayBoard.isValidEncoding("VKCC"));
        assertFalse("Expected false, as (20,21) are not valid coordinates for the board, but got"+PlayBoard.isValidEncoding("VKUT"),PlayBoard.isValidEncoding("VKUT"));
        assertFalse("Expected false, as (21,20) are not valid coordinates for the board, but got"+PlayBoard.isValidEncoding("VKTU"),PlayBoard.isValidEncoding("VKTU"));
    }

}
