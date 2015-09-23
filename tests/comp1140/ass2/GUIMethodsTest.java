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
        assertTrue("Expected true, as ABCD only contains letters but got "+BlokGUI.isOnlyAlpha("ABCD"),BlokGUI.isOnlyAlpha("ABCD"));
        assertTrue("Expected true, as AAAA only contains letters but got "+BlokGUI.isOnlyAlpha("AAAA"),BlokGUI.isOnlyAlpha("AAAA"));
        assertTrue("Expected true, as XYZAR only contains letters but got "+BlokGUI.isOnlyAlpha("XYZAR"),BlokGUI.isOnlyAlpha("XYZAR"));
        assertFalse("Expected false, as 22222 contains only numbers but got " + BlokGUI.isOnlyAlpha("22222"), BlokGUI.isOnlyAlpha("22222"));
        assertFalse("Expected false, as BY!% contains non-alphebtical symbols but got "+BlokGUI.isOnlyAlpha("BY!%"),BlokGUI.isOnlyAlpha("BY!%"));
        assertFalse("Expected false, as T@3$ contains numbers and non-alphabetical symbols but got "+BlokGUI.isOnlyAlpha("T@3$"),BlokGUI.isOnlyAlpha("T@3$"));
    }

}
