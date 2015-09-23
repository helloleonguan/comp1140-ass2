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
    public ArrayList<Point> Corners = new ArrayList<Point>();
    public static void Rotate(ArrayList<Point> piece,int rotation){
        if (rotation > 3) {
            for (Point p : piece) {
                p.x = -(p.x);
            }
        }
        switch (rotation % 4) {
            case 0:
                break;
            case 1:
                for (Point p : piece) {
                    int temp = p.x;
                    p.x = -p.y;
                    p.y = temp;
                }
                break;
            case 2:
                for (Point p : piece) {
                    p.x = -(p.x);
                    p.y = -(p.y);
                }
                break;
            case 3:
                for (Point p : piece) {
                    int temp = p.x;
                    p.x = (p.y);
                    p.y = -temp;
                }
                break;
        }
        return;
    }
    public Tiles(){
        Corners.add(new Point(0,0));
        Corners.add(new Point(19,0));
        Corners.add(new Point(19,19));
        Corners.add(new Point(0,19));
        ArrayList<Point> Piece = new ArrayList<Point>();
        Piece.add(new Point(0,0));
        Pieces.add((ArrayList<Point>) Piece.clone()); //A
        Piece.clear();

        Piece.add(new Point(0, 0));
        Piece.add(new Point(0, 1));
        Pieces.add((ArrayList<Point>) Piece.clone()); //B
        Piece.clear();

        Piece.add(new Point(0, 0));
        Piece.add(new Point(0, 1));
        Piece.add(new Point(0, 2));
        Pieces.add((ArrayList<Point>) Piece.clone()); //C
        Piece.clear();

        Piece.add(new Point(0, 0));
        Piece.add(new Point(0, 1));
        Piece.add(new Point(1, 1));
        Pieces.add((ArrayList<Point>) Piece.clone()); //D
        Piece.clear();

        Piece.add(new Point(0, 0));
        Piece.add(new Point(0, 1));
        Piece.add(new Point(0, 2));
        Piece.add(new Point(0, 3));
        Pieces.add((ArrayList<Point>) Piece.clone()); //E
        Piece.clear();

        Piece.add(new Point(0, 0));
        Piece.add(new Point(0, 1));
        Piece.add(new Point(0, 2));
        Piece.add(new Point(-1, 2));
        Pieces.add((ArrayList<Point>) Piece.clone()); //F
        Piece.clear();

        Piece.add(new Point(0, 0));
        Piece.add(new Point(0, 1));
        Piece.add(new Point(0, 2));
        Piece.add(new Point(1, 1));
        Pieces.add((ArrayList<Point>) Piece.clone()); //G
        Piece.clear();

        Piece.add(new Point(0, 0));
        Piece.add(new Point(0, 1));
        Piece.add(new Point(1, 0));
        Piece.add(new Point(1, 1));
        Pieces.add((ArrayList<Point>) Piece.clone()); //H
        Piece.clear();

        Piece.add(new Point(0, 0));
        Piece.add(new Point(2, 1));
        Piece.add(new Point(1, 0));
        Piece.add(new Point(1, 1));
        Pieces.add((ArrayList<Point>) Piece.clone()); //I
        Piece.clear();

        Piece.add(new Point(0, 0));
        Piece.add(new Point(0, 1));
        Piece.add(new Point(0, 2));
        Piece.add(new Point(0, 3));
        Piece.add(new Point(0, 4));
        Pieces.add((ArrayList<Point>) Piece.clone()); //J
        Piece.clear();

        Piece.add(new Point(0,0));
        Piece.add(new Point(0, 1));
        Piece.add(new Point(0, 2));
        Piece.add(new Point(0, 3));
        Piece.add(new Point(-1, 3));
        Pieces.add((ArrayList<Point>) Piece.clone()); //K
        Piece.clear();

        Piece.add(new Point(0,0));
        Piece.add(new Point(0, 1));
        Piece.add(new Point(0, 2));
        Piece.add(new Point(-1, 2));
        Piece.add(new Point(-1, 3));
        Pieces.add((ArrayList<Point>) Piece.clone()); //L
        Piece.clear();

        Piece.add(new Point(0,0));
        Piece.add(new Point(0, 1));
        Piece.add(new Point(0, 2));
        Piece.add(new Point(-1, 2));
        Piece.add(new Point(-1, 1));
        Pieces.add((ArrayList<Point>) Piece.clone()); //M
        Piece.clear();

        Piece.add(new Point(0, 0));
        Piece.add(new Point(1, 0));
        Piece.add(new Point(1, 1));
        Piece.add(new Point(1, 2));
        Piece.add(new Point(0, 2));
        Pieces.add((ArrayList<Point>) Piece.clone()); //N
        Piece.clear();

        Piece.add(new Point(0, 0));
        Piece.add(new Point(0, 1));
        Piece.add(new Point(0, 2));
        Piece.add(new Point(0, 3));
        Piece.add(new Point(1, 1));
        Pieces.add((ArrayList<Point>) Piece.clone()); //O
        Piece.clear();

        Piece.add(new Point(0,0));
        Piece.add(new Point(0, 1));
        Piece.add(new Point(0, 2));
        Piece.add(new Point(-1, 2));
        Piece.add(new Point(1, 2));
        Pieces.add((ArrayList<Point>) Piece.clone()); //P
        Piece.clear();

        Piece.add(new Point(0,0));
        Piece.add(new Point(0, 1));
        Piece.add(new Point(0, 2));
        Piece.add(new Point(1, 2));
        Piece.add(new Point(2, 2));
        Pieces.add((ArrayList<Point>) Piece.clone()); //Q
        Piece.clear();

        Piece.add(new Point(0, 0));
        Piece.add(new Point(1, 0));
        Piece.add(new Point(1, 1));
        Piece.add(new Point(2, 1));
        Piece.add(new Point(2, 2));
        Pieces.add((ArrayList<Point>) Piece.clone()); //R
        Piece.clear();

        Piece.add(new Point(0, 0));
        Piece.add(new Point(0, 1));
        Piece.add(new Point(1, 1));
        Piece.add(new Point(2, 1));
        Piece.add(new Point(2, 2));
        Pieces.add((ArrayList<Point>) Piece.clone()); //S
        Piece.clear();

        Piece.add(new Point(0,0));
        Piece.add(new Point(0, 1));
        Piece.add(new Point(1, 1));
        Piece.add(new Point(2, 1));
        Piece.add(new Point(1, 2));
        Pieces.add((ArrayList<Point>) Piece.clone()); //T
        Piece.clear();

        Piece.add(new Point(0,0));
        Piece.add(new Point(-1, 1));
        Piece.add(new Point(0, 1));
        Piece.add(new Point(1, 1));
        Piece.add(new Point(0, 2));
        Pieces.add((ArrayList<Point>) Piece.clone()); //U
        Piece.clear();
    }
}
