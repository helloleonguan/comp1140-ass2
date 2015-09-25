package comp1140.ass2;
import comp1140.ass2.BlokGUI;
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
        assertTrue("Expected true, as 'ABCD' only contains letters but got "+BlokGUI.isOnlyAlpha("ABCD"),BlokGUI.isOnlyAlpha("ABCD"));
        assertTrue("Expected true, as '' no numbers/symbols but got "+BlokGUI.isOnlyAlpha(""),BlokGUI.isOnlyAlpha(""));
        assertTrue("Expected true, as 'XYZAR' only contains letters but got "+BlokGUI.isOnlyAlpha("XYZAR"),BlokGUI.isOnlyAlpha("XYZAR"));
        assertFalse("Expected false, as '22222' contains only numbers but got " + BlokGUI.isOnlyAlpha("22222"), BlokGUI.isOnlyAlpha("22222"));
        assertFalse("Expected false, as 'BY!%' contains non-alphebtical symbols but got "+BlokGUI.isOnlyAlpha("BY!%"),BlokGUI.isOnlyAlpha("BY!%"));
        assertFalse("Expected false, as 'T@3$' contains numbers and non-alphabetical symbols but got "+BlokGUI.isOnlyAlpha("T@3$"),BlokGUI.isOnlyAlpha("T@3$"));
    }

    @Test
    public void testisLengthOnlyFour() {
        assertTrue("Expected true, has 'ABCD' only four characters but got"+BlokGUI.isLengthOnlyFour("ABCD"),BlokGUI.isLengthOnlyFour("ABCD"));
        assertTrue("Expected true, has 'A BC' only four characters but got"+BlokGUI.isLengthOnlyFour("A BC"),BlokGUI.isLengthOnlyFour("A BC"));
        assertFalse("Expected false, as 'ABC' has three characters but got" + BlokGUI.isLengthOnlyFour("ABC"), BlokGUI.isLengthOnlyFour("ABC"));
        assertFalse("Expected false, as 'ABCDE' has five characters but got"+BlokGUI.isLengthOnlyFour("ABCDE"),BlokGUI.isLengthOnlyFour("ABCDE"));
        assertFalse("Expected false, as '' has no characters but got"+BlokGUI.isLengthOnlyFour(""),BlokGUI.isLengthOnlyFour(""));


    }

    @Test
    public void testisValidEncoding() {
        assertTrue("Expected true as ABCD is a valid game piece, but got "+BlokGUI.isValidEncoding("ABCD"),BlokGUI.isValidEncoding("ABCD"));
        assertTrue("Expected true as RCCC is a valid game piece, but got "+BlokGUI.isValidEncoding("RCCC"),BlokGUI.isValidEncoding("RCCC"));
        assertTrue("Expected true as OHIM is a valid game piece, but got "+BlokGUI.isValidEncoding("OHIM"),BlokGUI.isValidEncoding("OHIM"));
        assertFalse("Expected false, as V is not a valid polynomio but got" + BlokGUI.isValidEncoding("VCCC"), BlokGUI.isValidEncoding("VCCC"));
        assertFalse("Expected false, as K is not a valid rotation but got"+BlokGUI.isValidEncoding("VKCC"),BlokGUI.isValidEncoding("VKCC"));
        assertFalse("Expected false, as (20,21) are not valid coordinates for the board, but got"+BlokGUI.isValidEncoding("VKUT"),BlokGUI.isValidEncoding("VKUT"));
        assertFalse("Expected false, as (21,20) are not valid coordinates for the board, but got"+BlokGUI.isValidEncoding("VKTU"),BlokGUI.isValidEncoding("VKTU"));
    }

}
