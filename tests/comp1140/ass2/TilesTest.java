package comp1140.ass2;

import org.junit.Assert;
import org.junit.Test;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

import static org.junit.Assert.*;

/**
 * Created by Jack on 23/09/2015.
 */
public class TilesTest {

    @Test
    public void testRotate() throws Exception {
        Tiles testTiles = new Tiles();
        ArrayList<Point> Piece;
        Random ng = new Random();
        for (ArrayList<Point> piece : testTiles.Pieces){
            int size = piece.size();
            Tiles.Rotate(piece, ng.nextInt(7));
            assert (piece.size()==size);
        }
        
    }
}