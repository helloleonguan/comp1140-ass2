package comp1140.ass2;
/**
 * Created by Liyang(Leon) Guan on 2015/10/3.
 */

import java.awt.*;
import java.util.ArrayList;

public class Legit {
    public static int turn = 1;
    static int[] board = new int[400];
    static Tiles mytiles = new Tiles();

    public static boolean legitimateGame(String game) {
        boolean legit = false;
        String[] tilesPLaced = game.split("\\s+");
        if (tilesPLaced.length <= 4) {
            for (int i = 0; i < tilesPLaced.length; i++) {
                legit = isStarter(tilesPLaced[i]) && onBoard(tilesPLaced[i]);
                if (legit) {
                    draw(tilesPLaced[i]);
                } else {
                    break;
                }
            }
        } else {
            boolean start = isStarter(tilesPLaced[0]) && isStarter(tilesPLaced[1]) && isStarter(tilesPLaced[2]) && isStarter(tilesPLaced[3])
                    && onBoard(tilesPLaced[0]) && onBoard(tilesPLaced[1]) && onBoard(tilesPLaced[2]) && onBoard(tilesPLaced[3]);
            if (start) {
                draw(tilesPLaced[0]);
                draw(tilesPLaced[1]);
                draw(tilesPLaced[2]);
                draw(tilesPLaced[3]);
            }
            for (int i = 4; i < tilesPLaced.length; i++) {
                legit = start && onBoard(tilesPLaced[i]) && noOverlapping(tilesPLaced[i]) &&
                        cornerContactWithSameColor(tilesPLaced[i]) && noEdgeContactWithSameColor(tilesPLaced[i]);

                if (legit) {
                    draw(tilesPLaced[i]);
                } else {
                    break;
                }
            }

        }
        //clear static objects
        turn = 1;
        for (int i =0; i< 400; i++) {
            board[i] =0;
        }
        return legit;
    }

    public static int[] draw(String tile) {
        int[] codes = analyseTile(tile);
        if (codes == null) {
            turn++;
            if (turn == 5) {
                turn = 1;
            }
            return board;
        }
        Tiles.Rotate(mytiles.Pieces.get(codes[0]), codes[1]);

        for (Point p:mytiles.Pieces.get(codes[0])) {
            int pos = codes[2] + codes[3] * 20 + p.x + p.y*20;
            board[pos] = turn;
        }

        turn++;
        if (turn == 5) {
            turn = 1;
        }
        mytiles = new Tiles();
        return board;
    }

    public static int[] analyseTile(String tile) {
        int[] codes = new int[4];
        if (tile.equals(".")) {
            return null;
        } else {
            codes[0] = tile.charAt(0) - 'A';
            codes[1] = tile.charAt(1) - 'A';
            codes[2] = tile.charAt(2) - 'A';
            codes[3] = tile.charAt(3) - 'A';
        }
        return codes;
    }

    public static boolean isStarter(String tile) {
        boolean flag = false;
        int[] codes = analyseTile(tile);
        if (codes == null) {
            return true;
        }
        Tiles.Rotate(mytiles.Pieces.get(codes[0]), codes[1]);

        for (Point p:mytiles.Pieces.get(codes[0])) {
            int pos = codes[2] + codes[3] * 20 + p.x + p.y*20;
            if (pos == 0 || pos == 19 || pos == 380 || pos == 399 ) {
                flag = true;
                break;
            }
        }
        mytiles = new Tiles();
        return flag;
    }

    public static boolean onBoard (String tile) {
        boolean flag = true;
        int[] codes = analyseTile(tile);
        if (codes == null) {
            return true;
        }
        Tiles.Rotate(mytiles.Pieces.get(codes[0]), codes[1]);

        for (Point p:mytiles.Pieces.get(codes[0])) {
            if (codes[2]+p.x > 19 || codes[2]+p.x < 0 || codes[3]+p.y > 19 || codes[3] < 0) {
                return false;
            }
            int pos = codes[2] + codes[3] * 20 + p.x + p.y*20;
            if (pos < 0 || pos > 399 ) {
                flag = false;
                break;
            }
        }
        mytiles = new Tiles();
        return flag;
    }

    public static boolean noOverlapping (String tile) {
        boolean flag = true;
        int[] codes = analyseTile(tile);
        if (codes == null) {
            return true;
        }
        Tiles.Rotate(mytiles.Pieces.get(codes[0]), codes[1]);

        for (Point p:mytiles.Pieces.get(codes[0])) {
            if (codes[2]+p.x > 19 || codes[2]+p.x < 0 || codes[3]+p.y > 19 || codes[3] < 0) {
                continue;
            }
            int pos = codes[2] + codes[3] * 20 + p.x + p.y * 20;
            if (pos < 0 || pos > 399)
                continue;
            if (board[pos] != 0) {
                flag = false;
                break;
            }
        }

        mytiles = new Tiles();
        return flag;
    }

    public static ArrayList<Point> findAdjacent (ArrayList<Point> points) {
        ArrayList<Point> adjacents = new ArrayList<>();
        for (Point p: points) {
            if ((!points.contains(new Point(p.x+1,p.y))) && (!adjacents.contains(new Point(p.x+1,p.y)))) {
                adjacents.add(new Point(p.x+1, p.y));
            }
            if ((!points.contains(new Point(p.x-1,p.y))) && (!adjacents.contains(new Point(p.x-1,p.y)))) {
                adjacents.add(new Point(p.x-1, p.y));
            }
            if ((!points.contains(new Point(p.x,p.y+1))) && (!adjacents.contains(new Point(p.x,p.y+1)))) {
                adjacents.add(new Point(p.x, p.y+1));
            }
            if ((!points.contains(new Point(p.x,p.y-1))) && (!adjacents.contains(new Point(p.x,p.y-1)))) {
                adjacents.add(new Point(p.x, p.y-1));
            }
        }
        return adjacents;
    }

    public static ArrayList<Point> findCorners (ArrayList<Point> points) {
        ArrayList<Point> adjacents = findAdjacent(points);
        ArrayList<Point> corners = new ArrayList<>();
        for (Point p: points) {
            if ((!points.contains(new Point(p.x-1,p.y-1))) && (!adjacents.contains(new Point(p.x-1,p.y-1))) && (!corners.contains(new Point(p.x-1,p.y-1)))) {
                corners.add(new Point(p.x-1,p.y-1));
            }
            if ((!points.contains(new Point(p.x+1,p.y+1))) && (!adjacents.contains(new Point(p.x+1,p.y+1))) && (!corners.contains(new Point(p.x+1,p.y+1)))) {
                corners.add(new Point(p.x+1,p.y+1));
            }
            if ((!points.contains(new Point(p.x+1,p.y-1))) && (!adjacents.contains(new Point(p.x+1,p.y-1))) && (!corners.contains(new Point(p.x+1,p.y-1)))) {
                corners.add(new Point(p.x+1,p.y-1));
            }
            if ((!points.contains(new Point(p.x-1,p.y+1))) && (!adjacents.contains(new Point(p.x-1,p.y+1))) && (!corners.contains(new Point(p.x-1,p.y+1)))) {
                corners.add(new Point(p.x-1,p.y+1));
            }
        }
        return corners;
    }

    public static boolean cornerContactWithSameColor (String tile) {
        boolean flag = false;
        int[] codes = analyseTile(tile);
        if (codes == null) {
            return true;
        }
        Tiles.Rotate(mytiles.Pieces.get(codes[0]), codes[1]);
        ArrayList<Point> corners = findCorners(mytiles.Pieces.get(codes[0]));

        for (Point p: corners) {
            if (codes[2]+p.x > 19 || codes[2]+p.x < 0 || codes[3]+p.y > 19 || codes[3] < 0) {
                continue;
            }
            int pos = codes[2] + codes[3] * 20 + p.x + p.y * 20;
            if (pos < 0 || pos > 399)
                continue;
            if (board[pos] == turn) {
                flag = true;
                break;
            }
        }

        mytiles = new Tiles();
        return flag;
    }

    public static boolean noEdgeContactWithSameColor (String tile){
        boolean flag = true;
        int[] codes = analyseTile(tile);
        if (codes == null) {
            return true;
        }
        Tiles.Rotate(mytiles.Pieces.get(codes[0]), codes[1]);
        ArrayList<Point> adjacents = findAdjacent(mytiles.Pieces.get(codes[0]));

        for (Point p: adjacents) {
            if (codes[2]+p.x > 19 || codes[2]+p.x < 0 || codes[3]+p.y > 19 || codes[3] < 0) {
                continue;
            }
            int pos = codes[2] + codes[3] * 20 + p.x + p.y * 20;
            if (pos < 0 || pos > 399)
                continue;
            if (board[pos] == turn) {
                flag = false;
                break;
            }
        }

        mytiles = new Tiles();
        return flag;
    }
}
