package comp1140.ass2;

import java.awt.*;
import java.util.ArrayList;

/**
 * Created by Jack on 23/08/2015.
 * To use, create instance of Tiles, the to get nth tile (Indexed at 0), access Pieces.get(n) member
 * eg. Tiles myTiles = new Tiles();
 *     ArrayList<Point> Tile = myTiles.get(3);
 * This would return an ArrayList of points like:
 *   #
 *   ##
 */
public class Tiles {
    public ArrayList<ArrayList<Point>> Pieces = new ArrayList<ArrayList<Point>>();
    public Tiles(){
        ArrayList<Point> Piece = new ArrayList<Point>();
        Piece.add(new Point(0,0));
        Pieces.add((ArrayList<Point>) Piece.clone());
        Piece.clear();

        Piece.add(new Point(0, 0));
        Piece.add(new Point(0, 1));
        Pieces.add((ArrayList<Point>) Piece.clone());
        Piece.clear();

        Piece.add(new Point(0, 0));
        Piece.add(new Point(0, 1));
        Piece.add(new Point(0, 2));
        Pieces.add((ArrayList<Point>) Piece.clone());
        Piece.clear();

        Piece.add(new Point(0, 0));
        Piece.add(new Point(0, 1));
        Piece.add(new Point(1, 1));
        Pieces.add((ArrayList<Point>) Piece.clone());
        Piece.clear();

        Piece.add(new Point(0, 0));
        Piece.add(new Point(0, 1));
        Piece.add(new Point(0, 2));
        Piece.add(new Point(0, 3));
        Pieces.add((ArrayList<Point>) Piece.clone());
        Piece.clear();

        Piece.add(new Point(0, 0));
        Piece.add(new Point(0, 1));
        Piece.add(new Point(0, 2));
        Piece.add(new Point(-1, 2));
        Pieces.add((ArrayList<Point>) Piece.clone());
        Piece.clear();

        Piece.add(new Point(0, 0));
        Piece.add(new Point(0, 1));
        Piece.add(new Point(0, 2));
        Piece.add(new Point(1, 1));
        Pieces.add((ArrayList<Point>) Piece.clone());
        Piece.clear();

        Piece.add(new Point(0, 0));
        Piece.add(new Point(0, 1));
        Piece.add(new Point(1, 0));
        Piece.add(new Point(1, 1));
        Pieces.add((ArrayList<Point>) Piece.clone());
        Piece.clear();

        Piece.add(new Point(0, 0));
        Piece.add(new Point(2, 1));
        Piece.add(new Point(1, 0));
        Piece.add(new Point(1, 1));
        Pieces.add((ArrayList<Point>) Piece.clone());
        Piece.clear();

        Piece.add(new Point(0, 0));
        Piece.add(new Point(0, 1));
        Piece.add(new Point(0, 2));
        Piece.add(new Point(0, 3));
        Piece.add(new Point(0, 4));
        Pieces.add((ArrayList<Point>) Piece.clone());
        Piece.clear();

        Piece.add(new Point(0,0));
        Piece.add(new Point(0, 1));
        Piece.add(new Point(0, 2));
        Piece.add(new Point(0, 3));
        Piece.add(new Point(-1, 3));
        Pieces.add((ArrayList<Point>) Piece.clone());
        Piece.clear();

        Piece.add(new Point(0,0));
        Piece.add(new Point(0, 1));
        Piece.add(new Point(0, 2));
        Piece.add(new Point(-1, 2));
        Piece.add(new Point(-1, 3));
        Pieces.add((ArrayList<Point>) Piece.clone());
        Piece.clear();

        Piece.add(new Point(0,0));
        Piece.add(new Point(0, 1));
        Piece.add(new Point(0, 2));
        Piece.add(new Point(-1, 2));
        Piece.add(new Point(-1, 1));
        Pieces.add((ArrayList<Point>) Piece.clone());
        Piece.clear();

        Piece.add(new Point(0, 0));
        Piece.add(new Point(1, 0));
        Piece.add(new Point(1, 1));
        Piece.add(new Point(1, 2));
        Piece.add(new Point(0, 2));
        Pieces.add((ArrayList<Point>) Piece.clone());
        Piece.clear();

        Piece.add(new Point(0, 0));
        Piece.add(new Point(0, 1));
        Piece.add(new Point(0, 2));
        Piece.add(new Point(0, 3));
        Piece.add(new Point(1, 1));
        Pieces.add((ArrayList<Point>) Piece.clone());
        Piece.clear();

        Piece.add(new Point(0,0));
        Piece.add(new Point(0, 1));
        Piece.add(new Point(0, 2));
        Piece.add(new Point(-1, 2));
        Piece.add(new Point(1, 2));
        Pieces.add((ArrayList<Point>) Piece.clone());
        Piece.clear();

        Piece.add(new Point(0,0));
        Piece.add(new Point(0, 1));
        Piece.add(new Point(0, 2));
        Piece.add(new Point(1, 2));
        Piece.add(new Point(2, 2));
        Pieces.add((ArrayList<Point>) Piece.clone());
        Piece.clear();

        Piece.add(new Point(0, 0));
        Piece.add(new Point(1, 0));
        Piece.add(new Point(1, 1));
        Piece.add(new Point(2, 1));
        Piece.add(new Point(2, 2));
        Pieces.add((ArrayList<Point>) Piece.clone());
        Piece.clear();

        Piece.add(new Point(0, 0));
        Piece.add(new Point(0, 1));
        Piece.add(new Point(1, 1));
        Piece.add(new Point(2, 1));
        Piece.add(new Point(2, 2));
        Pieces.add((ArrayList<Point>) Piece.clone());
        Piece.clear();

        Piece.add(new Point(0,0));
        Piece.add(new Point(0, 1));
        Piece.add(new Point(1, 1));
        Piece.add(new Point(2, 1));
        Piece.add(new Point(1, 2));
        Pieces.add((ArrayList<Point>) Piece.clone());
        Piece.clear();

        Piece.add(new Point(0,0));
        Piece.add(new Point(-1, 1));
        Piece.add(new Point(0, 1));
        Piece.add(new Point(1, 1));
        Piece.add(new Point(0, 2));
        Pieces.add((ArrayList<Point>) Piece.clone());
        Piece.clear();
    }
}
