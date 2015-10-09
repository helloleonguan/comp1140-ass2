package comp1140.ass2;
/**
 * Created by Liyang(Leon) Guan on 2015/10/3.
 */

import java.awt.*;
import java.util.ArrayList;
// (All written by Liyang(Leon) )
public class Legit {
    public static Tiles mytiles;

    public static int incrementTurn(int currentTurn) {
        currentTurn++;
        if (currentTurn == 5) {
            currentTurn = 1;
        }
        return currentTurn;
    }

    public static boolean legitimateGame(String game) {
        mytiles = new Tiles();
        int turn = 1;
        int[] board = new int[400];
        boolean legit = false;
        String[] tilesPLaced = game.split("\\s+");
        if (tilesPLaced.length <= 4) {
            for (int i = 0; i < tilesPLaced.length; i++) {
                legit = isStarter(tilesPLaced[i]) && onBoard(tilesPLaced[i]) && noOverlapping(tilesPLaced[i],board);
                if (legit) {
                    draw(tilesPLaced[i], board, turn);
                    turn = incrementTurn(turn);
                } else {
                    break;
                }
            }
        } else {
            boolean start = isStarter(tilesPLaced[0]) && isStarter(tilesPLaced[1]) && isStarter(tilesPLaced[2]) && isStarter(tilesPLaced[3])
                    && onBoard(tilesPLaced[0]) && onBoard(tilesPLaced[1]) && onBoard(tilesPLaced[2]) && onBoard(tilesPLaced[3]);
            if (start) {
                draw(tilesPLaced[0], board, turn);
                turn = incrementTurn(turn);
                draw(tilesPLaced[1], board, turn);
                turn = incrementTurn(turn);
                draw(tilesPLaced[2], board, turn);
                turn = incrementTurn(turn);
                draw(tilesPLaced[3], board, turn);
                turn = incrementTurn(turn);
            }
            for (int i = 4; i < tilesPLaced.length; i++) {
                legit = start && onBoard(tilesPLaced[i]) && noOverlapping(tilesPLaced[i],board) &&
                        cornerContactWithSameColor(tilesPLaced[i],board,turn) && noEdgeContactWithSameColor(tilesPLaced[i], board,turn);

                if (legit) {
                    draw(tilesPLaced[i], board, turn);
                    turn = incrementTurn(turn);
                } else {
                    break;
                }
            }

        }
        return legit;
    }

    // It takes in a int[400] as a board, the tile you want to place and a boolean indicates whether it is a initial board
    // (i.e. Have all 4 players place their first tiles. OR whether there are less than 4 tiles already on board).
    public static boolean checkLegitForTile (int[] tempBoard, String tile, boolean initial, int turn) {
        mytiles = new Tiles();
        boolean result;
        if (initial) {
            result = isStarter(tile) && onBoard(tile) && noOverlapping(tile, tempBoard);
        } else {
            result = onBoard(tile) && noEdgeContactWithSameColor(tile, tempBoard, turn) && noOverlapping(tile, tempBoard) && cornerContactWithSameColor(tile, tempBoard, turn);
        }
        return result;
    }


    public static int[] draw(String tile, int[] b, int turn) {
        mytiles = new Tiles();
        int[] codes = analyseTile(tile);
        if (codes == null) {
            return b;
        }
        Tiles.Rotate(mytiles.Pieces.get(codes[0]), codes[1]);

        for (Point p:mytiles.Pieces.get(codes[0])) {
            int pos = codes[2] + codes[3] * 20 + p.x + p.y*20;
            b[pos] = turn;
        }
        mytiles = new Tiles();
        return b;
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

    public static boolean noOverlapping (String tile, int[] currentBoard) {
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
            if (currentBoard[pos] != 0) {
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

    public static boolean cornerContactWithSameColor (String tile, int[] currentBoard, int turn) {
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
            if (currentBoard[pos] == turn) {
                flag = true;
                break;
            }
        }

        mytiles = new Tiles();
        return flag;
    }

    public static boolean noEdgeContactWithSameColor (String tile, int[] currentBoard, int turn){
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
            if (currentBoard[pos] == turn) {
                flag = false;
                break;
            }
        }

        mytiles = new Tiles();
        return flag;
    }
}
