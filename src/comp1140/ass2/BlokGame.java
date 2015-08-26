package comp1140.ass2;

import java.awt.*;
import java.util.ArrayList;
import java.util.Vector;

/**
 * Created by steveb on 12/08/2015.
 */
public class BlokGame {
    /**
     * Parse a string representing a game state and determine whether it is legitimate.  The game may be in progress
     * (ie incomplete).
     * @param game A string representing the state of the game, as described in the assignment description.
     * @return True if the string represents a legitimate game state, according to the encoding defined in the
     * assignment description and the rules of the game.
     */
    public static boolean legitimateGame(String game) {
        game = game.replaceAll(" ","");
        Tiles tileSet = new Tiles();
        /* FIXME */
        int[] squares = new int[400];
        boolean legit = true;
        int length = game.length();
        int index = 0;
        int encodingpart = 0;
        int turn = 0;
        ArrayList<Point> piece = null;
        int x=0,y = 0;
        while (legit){
            if(index >= length){
                if(encodingpart!=4)
                break;
            }
            switch (encodingpart){
                case 0: //Check if pass otherwise encode piece as ArrayList of points
                    if(game.charAt(index)=='.'){
                        encodingpart = 4;
                        break;
                    }
                    piece = new ArrayList<Point>();
                    for (Point p : tileSet.Pieces.get(game.charAt(index)-'A')){
                        piece.add(new Point(p.x,p.y));
                    }
                    //piece = (ArrayList<Point>) tileSet.Pieces.get(game.charAt(index)-'A').clone();
                    break;
                case 1: //Rotate each square in ArrayList as required
                    if((game.charAt(index)-'A')>3){
                        for (Point p : piece){
                            p.x = -(p.x);
                        }
                    }
                    switch((game.charAt(index)-'A') % 4 ){
                        case 0:
                            break;
                        case 1:
                            for (Point p : piece){
                                int temp = p.x;
                                p.x = -p.y;
                                p.y = temp;
                            }
                            break;
                        case 2:
                            for (Point p : piece){
                                p.x = -(p.x);
                                p.y = -(p.y);
                            }
                            break;
                        case 3:
                            for (Point p : piece){
                                int temp = p.x;
                                p.x = (p.y);
                                p.y = -temp;
                            }
                            break;
                    }
                    break;
                case 2: //Encode horizontal co-ordinate of origin
                    x = game.charAt(index) - 'A';
                    break;
                case 3: //Encode vertical co-ordinate of origin
                    y = game.charAt(index) - 'A';
                    break;
                case 4: //Check for legitimacy of game state
                    ArrayList<Point> absPiece = new ArrayList<Point>();
                    for (Point p : piece){
                        absPiece.add(new Point(p.x + x,p.y+y));
                    }
                    boolean cornerTouch = (index< 20); //If at least one part of the block is diagonal to an owned piece
                    if(index < 20){
                        boolean inCorner = false;
                        for (Point p : absPiece){
                            if (tileSet.Corners.get(turn).x == p.x && tileSet.Corners.get(turn).y == p.y) {
                                inCorner = true;
                            }
                        }
                        if (!inCorner){
                            legit = false;
                            break;
                        }
                    }
                    for (Point p : piece){
                        Point ap = new Point(p.x+x,p.y+y); //Location of individual square on game board
                        if (ap.x < 0 || ap.x > 19 || ap.y < 0 || ap.y > 19){ //If piece is placed out of bounds
                            legit = false;
                            break;
                        }
                        if(squares[(20 * ap.y) + ap.x] !=0){//If placed on occupied square
                            legit = false;
                            break;
                        }
                        if(ap.x>0) {//Check if same colour piece to left
                            if (squares[(20 * (ap.y)) + ap.x - 1] == turn + 1) {
                                legit = false;
                                break;
                            }
                        }
                        if(ap.x<19) {//Check if same colour piece to right
                            if (squares[(20 * (ap.y)) + ap.x + 1] == turn + 1) {
                                legit = false;
                                break;
                            }
                        }
                        if(ap.y>0) {//Check if same colour piece above
                            if (squares[(20 * (ap.y-1)) + ap.x] == turn + 1) {
                                legit = false;
                                break;
                            }
                        }
                        if(ap.y<19) {//Check if same colour piece below
                            if (squares[(20 * (ap.y+1)) + ap.x] == turn + 1) {
                                legit = false;
                                break;
                            }
                        }
                        if(!cornerTouch){
                            if(ap.x>0 && ap.y>0) {//Check if same colour piece to up-left
                                if (squares[(20 * (ap.y - 1)) + ap.x - 1] == turn + 1) {
                                    cornerTouch = true;
                                    break;
                                }
                            }
                            if(ap.x>0 && ap.y<19) {//Check if same colour piece to below-left
                                if (squares[(20 * (ap.y + 1)) + ap.x - 1] == turn + 1) {
                                    cornerTouch = true;
                                    break;
                                }
                            }
                            if(ap.x<19 && ap.y>0) {//Check if same colour piece to up-left
                                if (squares[(20 * (ap.y - 1)) + ap.x + 1] == turn + 1) {
                                    cornerTouch = true;
                                    break;
                                }
                            }
                            if(ap.x>0 && ap.y>0) {//Check if same colour piece to bottom-right
                                if (squares[(20 * (ap.y + 1)) + ap.x + 1] == turn + 1) {
                                    cornerTouch = true;
                                    break;
                                }
                            }
                        }
                    }
                    if(!legit)return false;
                    for (Point p : piece){
                        Point ap = new Point(p.x+x,p.y+y); //Location of individual square on game board
                        squares[(20 * ap.y) + ap.x] = turn + 1; //Store piece on game board
                    }
                    if (!cornerTouch) legit = false;
                    turn = (turn + 1) % 4;
                    encodingpart = -1;
                    index--;
                    break;
                case 5: //Encoding players pass
                    encodingpart = -1;
                    turn = (turn + 1) % 4;
                    break;
            }
            encodingpart++;
            index++;
        }
        return legit;
    }

    /**
     * Parse a string representing a game state and return a score for the game.  The game may be in progress
     * (ie incomplete), in which case score should reflect the score at that time (if no further moves were made).
     * @param game A string representing the state of the game, as described in the assignment description.
     * @return An array of four integers reflecting the score for the game.   The scores should be given in the playing
     * order specified in the rules of the game, and the scores should be made according to the rules.
     */
    public static int[] scoreGame(String game) {
        Tiles tile = new Tiles();

        int[] scores = new int[4];
        String[] tilesPLaced = game.split("\\s+");
        int n = 0;
        final int totalScore = 89; // best score if one placed all tiles on board.
        for (String s : tilesPLaced) {
            if (s.charAt(0) != '.') {
                scores[n % 4] += tile.Pieces.get(convertToIndex(Character.toString(s.charAt(0)))).size();
            }

            if (79<n && n<84) {
                if (scores[n%4] == totalScore) // placed all tiles.
                    scores[n%4] += 15;
                if (s.charAt(0) == 'A') // monomino tile bonus.
                    scores[n%4] += 5;
            }
            n++;
        }

        for (int i = 0; i < 4; i ++) {
            scores[i] -= 89; // convert the score according to the rules.
        }
        return scores;
    }

    private static int convertToIndex (String s) {
        int num = 0;
        switch (s) {
            case "A" : num = 0 ; break;
            case "B" : num = 1 ; break;
            case "C" : num = 2 ; break;
            case "D" : num = 3 ; break;
            case "E" : num = 4 ; break;
            case "F" : num = 5 ; break;
            case "G" : num = 6 ; break;
            case "H" : num = 7 ; break;
            case "I" : num = 8 ; break;
            case "J" : num = 9 ; break;
            case "K" : num = 10 ; break;
            case "L" : num = 11 ; break;
            case "M" : num = 12 ; break;
            case "N" : num = 13 ; break;
            case "O" : num = 14 ; break;
            case "P" : num = 15 ; break;
            case "Q" : num = 16 ; break;
            case "R" : num = 17 ; break;
            case "S" : num = 18 ; break;
            case "T" : num = 19 ; break;
            case "U" : num = 20 ; break;
        }
        return num;
    }

    /**
     * Parse a string representing a game state and return a valid next move.  If no move is possible, return a pass ('.')
     * @param game A string representing the state of the game, as described in the assignment description.
     * @return A four-character string representing the next move.
     */
    public static String makeMove(String game) {
        /* FIXME */
        /*given a game state, we need a function that lists all possible locations where a player can place a block
        * this includes space and availability of blocks, but also that it is touching the corner of the player's other blocks
        * since we don't need to make a good move, we should just choose any move at random, or the "biggest possible block */
        return null;
    }
}
