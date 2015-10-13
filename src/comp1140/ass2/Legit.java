package comp1140.ass2;

/**
 * Created by Liyang(Leon) Guan on 2015/10/3.
 */

// (All written by Liyang(Leon) )
//This is a class to check the legitimacy of the string representation of the game.
import java.awt.*;
import java.util.ArrayList;

public class Legit {
    public static Tiles mytiles;

    /**
    * Increment the turn.
    * @param currentTurn An int representing the current turn.
    * @return An int representing the next turn.
    * */
    public static int incrementTurn(int currentTurn) {
        currentTurn++;
        if (currentTurn == 5) {
            currentTurn = 1;
        }
        return currentTurn;
    }

    /**
     * Parse a string representing a game state and determine whether it is legitimate.
     * @param game A string representing the state of the game, as described in the assignment description.
     * @return  True if the string represents a legitimate game state. Otherwise, it returns false.
     * */
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

    /**
     * Given a game state, check the legitimacy for the adding tile. It is more efficient for filtering legitimate moves.
     * @param tempBoard An int[400] representing the current game state..
     * @param tile A string representation of the tile you want to add to the current board.
     * @param initial A boolean determine whether it is at the start of the game. (i.e. Have all 4 players place their first tiles? If yea, then it is not the start of the game.)
     * @param turn An int indicating whose turn is it now..
     * @return True if the tile is legitimate. Otherwise, it returns false.
     * */
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

    /**
     * Add a tile to our current board (a 20*20 2-dimensional array).
     * @param tile The tile you want to add to the board.
     * @param b The current board.
     * @param turn An int indicating whose turn is it now.
     * @return The board with the tile on it.
     * */
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

    /**
     * Parse a 4-letter string to a int array which stores the 4 key info about the tile.
     * @param tile The string representation of the tile you want to parse.
     * @return An array of 4 ints OR null if it is not a tile.
     * */
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

    /**
     * Check whether a tile is a starting tile or not (i.e. the first tile each player places on the board).
     * @param tile The string representation of the tile.
     * @return True if it is a starting tile. Otherwise, it returns false.
     * */
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

    /**
     * Check whether all little square cells of a tile are on board or not.
     * @param tile The string representation of the tile.
     * @return True if they are all on board. Otherwise, it returns false.
     * */
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

    /**
     * Check a whether a tile has overlapping with other tiles on board.
     * @param tile The string representation of the tile.
     * @param currentBoard The current state of the board.
     * @return True if there is no overlapping. Otherwise, it returns false.
     * */
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

    /**
     * Find all the adjacent (those squares have edge-to-edge contact with the tile) coordinates around a Tile.
     * @param points The coordinates of a tile.
     * @return An arraylist containing all adjacent coordinates.
     * */
    public static ArrayList<Point> findAdjacents(ArrayList<Point> points) {
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

    /**
     * Find all the corner (those squares have only corner-to-corner contact with the tile) coordinates around a Tile.
     * @param points The coordinates of a tile.
     * @return An arraylist containing all corner coordinates.
     * */
    public static ArrayList<Point> findCorners (ArrayList<Point> points) {
        ArrayList<Point> adjacents = findAdjacents(points);
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

    /**
     * Check whether a tile has corner contact with other tiles that share the same color.
     * @param tile The string representation of the tile.
     * @param currentBoard The current state of the board.
     * @param turn An int indicating whose turn is it now.
     * @return True, if there is corner contact with same color tile(s). Otherwise, it returns false.
     * */
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

    /**
     * Check whether a tile has cedge contact with other tiles that share the same color.
     * @param tile The string representation of the tile.
     * @param currentBoard The current state of the board.
     * @param turn An int indicating whose turn is it now.
     * @return True, if there is no edge contact with same color tile(s). Otherwise, it returns false.
     * */
    public static boolean noEdgeContactWithSameColor (String tile, int[] currentBoard, int turn){
        boolean flag = true;
        int[] codes = analyseTile(tile);
        if (codes == null) {
            return true;
        }
        Tiles.Rotate(mytiles.Pieces.get(codes[0]), codes[1]);
        ArrayList<Point> adjacents = findAdjacents(mytiles.Pieces.get(codes[0]));

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
