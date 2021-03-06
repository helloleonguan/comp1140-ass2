package comp1140.ass2;

import javafx.application.Application;
import javafx.geometry.HPos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.*;
import javafx.scene.shape.Rectangle;
import javafx.scene.input.KeyCode;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

public class PlayBoard extends Application {
    //Put only global variables for the GAME here
    final static double CELL_LENGTH = 25;

    ArrayList<Cell> board = new ArrayList<>();
    ArrayList<ArrayList<Tile>> Players = new ArrayList<>();
    String game = "";
    Tiles gameTiles = new Tiles();
    int currentTurn;
    int consecutivePass = 0;
    Scoreboard scores = new Scoreboard();
    String game_mode = "";
    boolean[] isHuman = new boolean[4];
    MessageContainer messageBox = new MessageContainer(25,615,"");
    int fourth_player = 2; // This is only for 3-player game mode.

    //Put all inner classes here, and those events inside the object.
    //Class for the MessageBox to present the info to the user. (Written by Liyang(Leon))
    class MessageContainer extends Text {
        String current_msg = "";

        public MessageContainer (double x, double y, String initial_msg) {
            this.setWrappingWidth(300);
            this.setFont(Font.font("Calibri", FontWeight.NORMAL, 18));
            this.setFill(Color.BROWN);
            this.setLayoutX(x);
            this.setLayoutY(y);
            this.setText(initial_msg);
        }

        public void renew(String msg) {
            this.setText(msg);
        }
    }

    //Class for the scoreboard presented at the scene. (Written by Faizan and Liyang(Leon))
    class Scoreboard extends GridPane{
        int blue_score;
        int yellow_score;
        int red_score;
        int green_score;
        Text blueScore;
        Text yellowScore;
        Text redScore;
        Text greenScore;

        public Scoreboard () {
            this.blue_score = 0;
            this.yellow_score = 0;
            this.red_score = 0;
            this.green_score = 0;

            this.setHgap(5);
            this.setVgap(5);
            ColumnConstraints column1 = new ColumnConstraints(100);
            ColumnConstraints column2 = new ColumnConstraints(100);
            ColumnConstraints column3 = new ColumnConstraints(100);
            ColumnConstraints column4 = new ColumnConstraints(100);
            this.getColumnConstraints().addAll(column1, column2, column3, column4);

            Text blueLabel = new Text("BLUE");
            blueLabel.setFill(Color.BLUE);
            blueLabel.setFont(new Font(18));
            this.blueScore = new Text(Integer.toString(this.blue_score));
            this.blueScore.setFont(new Font(16));

            Text yellowLabel = new Text("YELLOW");
            yellowLabel.setFill(Color.YELLOW);
            yellowLabel.setFont(new Font(18));
            this.yellowScore = new Text(Integer.toString(this.yellow_score));
            this.yellowScore.setFont(new Font(16));

            Text redLabel = new Text("RED");
            redLabel.setFill(Color.RED);
            redLabel.setFont(new Font(18));
            this.redScore = new Text(Integer.toString(this.red_score));
            this.redScore.setFont(new Font(16));

            Text greenLabel = new Text("GREEN");
            greenLabel.setFill(Color.GREEN);
            greenLabel.setFont(new Font(18));
            this.greenScore = new Text(Integer.toString(this.green_score));
            this.greenScore.setFont(new Font(16));

            GridPane.setHalignment(blueLabel, HPos.LEFT);
            this.add(blueLabel, 0, 0);
            this.add(blueScore, 0, 1);

            GridPane.setHalignment(yellowLabel, HPos.LEFT);
            this.add(yellowLabel, 1, 0);
            this.add(yellowScore, 1, 1);

            GridPane.setHalignment(redLabel, HPos.LEFT);
            this.add(redLabel, 2, 0);
            this.add(redScore, 2, 1);

            GridPane.setHalignment(greenLabel, HPos.LEFT);
            this.add(greenLabel, 3, 0);
            this.add(greenScore, 3, 1);
        }

        /**
         * update the scores for each player.
         * @param score1 score for the blue player.
         * @param score2 score for the yellow player.
         * @param score3 score for the red player.
         * @param score4 score for the green player.
        .*/
        public void update(int score1, int score2, int score3, int score4) {
            this.blue_score = score1+89;
            this.yellow_score =score2+89;
            this.red_score = score3+89;
            this.green_score = score4+89;

            this.blueScore.setText(Integer.toString(this.blue_score));
            this.yellowScore.setText(Integer.toString(this.yellow_score));
            this.redScore.setText(Integer.toString(this.red_score));
            this.greenScore.setText(Integer.toString(this.green_score));
        }
    }

    //Class for each individual little square cell. (Written by Liyang(Leon))
    class Cell extends Rectangle {
        public Cell (double x, double y, double length) {
            this.setX(x);
            this.setY(y);
            this.setWidth(length);
            this.setHeight(length);
        }
    }

    //Tile represents a single game piece, possessing an orientation, location, owner and shape. (Written by Jack and Liyang(Leon))
    class Tile extends Group {
        private PlayBoard board;
        int owner; //Index of enum of players
        public boolean played;
        public boolean flipped; //Whether flipped along Y axis
        ArrayList<Cell> tile;
        double original_x;
        double original_y;
        int shape_encoding;
        int rotation_encoding;
        int positionX_encoding;
        int positionY_encoding;
        String encodingOfTile;

        public void Grey(){ //Used at begining of turn to make all legal pieces grey
            for (Node c : this.getChildren().filtered(Cell.class::isInstance)) {
                Cell d = (Cell) c;
                d.setFill(setColor(owner).grayscale());
            }
        }

        public void Ungrey(){ //Used at begining of turn to make all legal pieces normal
            for (Node c : this.getChildren().filtered(Cell.class::isInstance)) {
                Cell d = (Cell) c;
                d.setFill(setColor(owner));
            }
        }

        public void Deactivate(){ //Called whenever it is no longer the owners turn.
            this.setDisable(true);
            if (!played)this.setVisible(false);
        }

        public void Activate(){ //Called at begining of owners turn
            this.setDisable(false);
            this.setVisible(true);
        }

        public Tile (int currentPlayer, double x, double y, ArrayList<Point> piece,PlayBoard board, int pieceNumber) {
            original_x = x;
            original_y = y;
            this.shape_encoding = pieceNumber;
            this.board = board;
            this.owner = currentPlayer;

            tile = new ArrayList<>();
            for (Point p : piece){
                Cell c = new Cell(p.x * 25 + x,p.y * 25 + y,CELL_LENGTH - 1);
                c.setFill(setColor(owner));
                tile.add(c);
                this.getChildren().add(c);
            }

            played = false;
            this.setOnMouseDragged(event -> { //Drag effect of piece
                if(!played) {
                    this.setLayoutX(event.getSceneX() - x - 12);
                    this.setLayoutY(event.getSceneY() - y - 12);
                    toFront();
                }
            });
            //No comment
            flipped = false;
            rotation_encoding = 0;
            this.setOnMouseReleased(event -> { //Handles encoding of piece and snapping to grid.
                if (event.getSceneX() < 500 && event.getSceneY() < 500 && event.getSceneX() > 0 && event.getSceneY() > 0) {
                    this.positionX_encoding = (int) Math.floor(event.getSceneX() / 25);
                    this.setLayoutX(this.positionX_encoding * 25 - x);
                    this.positionY_encoding = (int) Math.floor(event.getSceneY() / 25);
                    this.setLayoutY(this.positionY_encoding * 25 - y);
                    encodingOfTile = convertToCode(shape_encoding) + convertToCode(rotation_encoding + (this.flipped ? 4 : 0))
                            + convertToCode(positionX_encoding) + convertToCode(positionY_encoding);
                    if (BlokGame.legitimateGame(game + (game.equals("")? encodingOfTile:" "+ encodingOfTile))) {
                        if (game.equals("")) {
                            game += encodingOfTile;
                        } else {
                            game += " " + encodingOfTile;
                        }
                        System.out.println(game);
                        System.out.println(Arrays.toString(BlokGame.scoreGame(game)));
                        this.played = true;
                        //update the scores for each player
                        scores.update(BlokGame.scoreGame(game)[0], BlokGame.scoreGame(game)[1], BlokGame.scoreGame(game)[2], BlokGame.scoreGame(game)[3]);
                        board.nextTurn();
                    } else {
                        //if it is not a legitimate move then move the tile back to original coordinates
                        System.out.println("Bad move " + encodingOfTile);
                        this.setLayoutX(0);
                        this.setLayoutY(0);
                    }

                }
            });

            this.setOnMousePressed(event -> {
                if (event.getButton().equals(MouseButton.PRIMARY) && event.getClickCount() == 2) { //Rotate piece 90 deg Clockwise
                    for (Node c : this.getChildren().filtered(Cell.class::isInstance)) {
                        Cell d = (Cell) c;
                        double temp = d.getX() - original_x;
                        d.setX(original_x - (d.getY() - original_y));
                        d.setY(temp + original_y);
                    }

                    this.rotation_encoding = (this.rotation_encoding + 1) % 4;
                    System.out.println(this.rotation_encoding + " " + flipped);
                } else if (event.getButton().equals(MouseButton.SECONDARY)) { //Flip piece along Y axis
                    if (this.rotation_encoding % 2 == 1) {
                        this.rotation_encoding = 4 - this.rotation_encoding;
                    }
                    flipped = !flipped;
                    for (Node c : this.getChildren().filtered(Cell.class::isInstance)) {
                        Cell d = (Cell) c;
                        d.setX(original_x - (d.getX() - original_x));
                    }
                    System.out.println(this.rotation_encoding + " " + flipped);
                }
            });
        }

        Color setColor(int player) {
            Color color = null;
            switch(player) {
                case 0: color = Color.BLUE; break;
                case 1: color = Color.YELLOW; break;
                case 2: color = Color.RED; break;
                case 3:color = Color.GREEN; break;
            }
            return color;
        }
    }

    // Put all methods for the game here.
    /**
     * find the maximum score and the player for a game.
     * @return a pair of ints. The first one is the score, the second one is the player. .
     * (Written by Liyang(Leon)) */
    int[] max_score(ArrayList<Integer> scores) {
        int max = 0;
        int player = 0;
        int counter= 0;
        int[] rst = new int[2];
        for (int i :scores) {
            counter++;
            if (i >= max) {
                max = i;
                player = counter;
            }
        }
        rst[0] = max;
        rst[1] = player;

        return rst;
    }
    /**
     * Detect whether the game has consecutive 3 passes.
     * @return If there is 3 consecutive passes at the end of the game return true..
     * (Written by Liyang(Leon)) */
    boolean endgameDetection(String game) {
        int counter = 0;
        String[] tilesPLaced = game.split("\\s+");
        for (String s:tilesPLaced) {
            if (s.equals(".")) {
                counter++;
            } else {
                counter = 0;
            }
        }
        return (counter == 3);
    }
    /**
     * Activate all tiles for current turn and deactivate the others, present the player info and detect endgame. .
     * (Written by Jack and Liyang(Leon)) */
    void nextTurn() {
        Players.get(currentTurn).forEach(PlayBoard.Tile::Deactivate);
        currentTurn = (currentTurn + 1) % 4;
        ArrayList<Boolean> playable = legalPieces();
        Players.get(currentTurn).forEach(PlayBoard.Tile::Activate);
        for (int i = 0; i < Players.get(currentTurn).size(); i++) {
            if (!playable.get(i) && !Players.get(currentTurn).get(i).played) {
                Players.get(currentTurn).get(i).Grey();
            } else {
                Players.get(currentTurn).get(i).Ungrey();
            }
        }

        if (game_mode.length() == 1) {
            messageBox.renew("You are playing the \"eighty-four\" game. All moves are played by Player1.");
        } else if (game_mode.length() == 2) {
            if (currentTurn % 2 == 0)
                messageBox.renew("It's Player1's turn.");
            else
                messageBox.renew("It's Player2's turn.");
        } else if (game_mode.length() == 3) {
            if (currentTurn == 0) {
                messageBox.renew("It's Player1's turn.");
            } else if (currentTurn == 1) {
                messageBox.renew("It's Player2's turn.");
            } else if (currentTurn == 2) {
                messageBox.renew("It's Player3's turn.");
            } else {
                fourth_player = (fourth_player + 1) % 3;
                messageBox.renew("It's Player" + (fourth_player + 1) + "'s turn.");
                isHuman[3] = isHuman[fourth_player];
            }
        } else {
            messageBox.renew("It's Player" + (currentTurn + 1) + "'s turn.");
        }

        //if (!playable.contains(true) && endgameDetection(game)){
        if (!playable.contains(true)) {
            if(consecutivePass>=3) {
                game += " .";

                //End of the game message.
                if (game_mode.length() == 1) {
                    int overall = scores.blue_score + scores.red_score + scores.green_score + scores.yellow_score;
                    messageBox.renew("Game Over! \nYou achieve an overall score: " + overall + "\n Try to achieve a higher score next time.");
                } else if (game_mode.length() == 2) {
                    int max_score = Math.max(scores.blue_score + scores.red_score, scores.green_score + scores.yellow_score);
                    int player = (scores.blue_score + scores.red_score > scores.green_score + scores.yellow_score) ? 1 : 2;
                    messageBox.renew("Game Over! \nPlayer" + player + " win the game with a overall score:" + max_score + ".");
                } else if (game_mode.length() == 3) {
                    ArrayList<Integer> list = new ArrayList<>();
                    list.add(scores.blue_score);
                    list.add(scores.yellow_score);
                    list.add(scores.red_score);
                    int max_score = max_score(list)[0];
                    int max_score_player = max_score(list)[1];
                    messageBox.renew("Game Over! \nPlayer" + max_score_player + " win the game with a overall score:" + max_score + ".");
                } else {
                    ArrayList<Integer> list = new ArrayList<>();
                    list.add(scores.blue_score);
                    list.add(scores.yellow_score);
                    list.add(scores.red_score);
                    list.add(scores.green_score);
                    int max_score = max_score(list)[0];
                    int max_score_player = max_score(list)[1];
                    messageBox.renew("Game Over! \nPlayer" + max_score_player + " win the game with a overall score:" + max_score + ".");
                }
                return;
            } else {
                    game += " .";
                    consecutivePass++;
                    nextTurn();
            }
        } else consecutivePass = 0;

        if (!isHuman[currentTurn]) {
            String agentChoice = AIplayer.getMove(game);
            if (agentChoice.equals(" .")) {
                game += " " + agentChoice;
                nextTurn();
            } else {
                movePiece(agentChoice);
                if (game.length()>0){
                    game += " " + agentChoice;
                } else {
                    game += agentChoice;
                }
                scores.update(BlokGame.scoreGame(game)[0], BlokGame.scoreGame(game)[1], BlokGame.scoreGame(game)[2], BlokGame.scoreGame(game)[3]);
                nextTurn();
            }
        }
    }

    /**
     * Given a game string, determine it is what player's turn.
     * @param game A string representing the state of the game, as described in the assignment description.
     * @return Player Whose turn it is.
     *(Written by Liyang(Leon))*/
    Player getCurrentPlayer (String game) {
        String[] tilesPLaced = game.split("\\s+");
        return Player.getPlayer((tilesPLaced.length) % 4);
    }

    /**
     * @param i an integer representing the encoding character.
     * @return String a one-character string.
     *(Written by Liyang(Leon))*/
    String convertToCode(int i) {
        String rotation_code = "";
        switch (i) {
            case 0: rotation_code = "A"; break;
            case 1: rotation_code = "B"; break;
            case 2: rotation_code = "C"; break;
            case 3: rotation_code = "D"; break;
            case 4: rotation_code = "E"; break;
            case 5: rotation_code = "F"; break;
            case 6: rotation_code = "G"; break;
            case 7: rotation_code = "H"; break;
            case 8: rotation_code = "I"; break;
            case 9: rotation_code = "J"; break;
            case 10: rotation_code = "K"; break;
            case 11: rotation_code = "L"; break;
            case 12: rotation_code = "M"; break;
            case 13: rotation_code = "N"; break;
            case 14: rotation_code = "O"; break;
            case 15: rotation_code = "P"; break;
            case 16: rotation_code = "Q"; break;
            case 17: rotation_code = "R"; break;
            case 18: rotation_code = "S"; break;
            case 19: rotation_code = "T"; break;
            case 20: rotation_code = "U"; break;
        }
        return rotation_code;
    }

    /**
     * @return A boolean arraylist of which pieces have moves that can be legally made
     * Made by Jack Adamson
     */
    ArrayList<Boolean> legalPieces(){
        ArrayList<Boolean> legal = new ArrayList<>(21);
        for (int i = 0; i < 21; i++) {
            legal.add(false);
        }
        int turn = 1;
        String[] tilesPLaced = game.split("\\s+");
        int[] board0 = new int[400];
        if(game.length() <= 18){
            for (int i = 0; i < legal.size(); i++) {
                legal.set(i,true);
            }
            legal.set(20,false);
            return legal;
        }
        for (String s: tilesPLaced) {
            Legit.draw(s, board0,turn);
            turn = Legit.incrementTurn(turn);
        }
        for (char piece = 'A'; piece <= 'U'; piece++) {
            if(Players.get(currentTurn).get(piece - 'A').played){
                legal.set(piece-'A',false);
                continue;
            }
            for (char rotate = 'A'; rotate <= 'H' && ! legal.get(piece-'A'); rotate++){
                for (char x = 'A'; x < 'A'+20 && ! legal.get(piece-'A'); x++){
                    for (char y = 'A'; y < 'A' + 20 && ! legal.get(piece-'A'); y++){
                        if(Legit.checkLegitForTile (board0, "" + piece + rotate + x + y, false, currentTurn + 1) ){
                            legal.set(piece-'A',true);
                        }
                    }
                }
            }
        }

        return legal;
    }

    /**
     * @param move 4 character String indicating next move
     * Updates GUI with new move from text box.
     * Made by Jack Adamson
     */
    void movePiece(String move){
        //modify the case that generate by AIPlayer.
        if (move.length() == 5)
            move = move.substring(1,5);

        Tile current = Players.get(currentTurn).get(move.charAt(0) - 'A');
        int rotation = move.charAt(1)-'A';
        if(rotation >= 4) {
            rotation -= 4;
            for (Node c : current.getChildren().filtered(Cell.class::isInstance)) {
                Cell d = (Cell) c;
                d.setX(current.original_x - (d.getX() - current.original_x));
            }
        }
        for (int i = 0; i < rotation; i++) {
            for (Node c : current.getChildren().filtered(Cell.class::isInstance)) {
                Cell d = (Cell) c;
                double temp = d.getX() - current.original_x;
                d.setX(current.original_x - (d.getY() - current.original_y));
                d.setY(temp + current.original_y);
            }
        }
        current.setLayoutX(25 * (move.charAt(2)-'A') - current.original_x);
        current.setLayoutY(25 * (move.charAt(3)-'A') - current.original_y);
        current.played = true;
    }

    void initTiles() {
        for (int currentPlayer = 0;currentPlayer < 4; currentPlayer++){
            ArrayList<Tile> PlayerTiles = new ArrayList<>();
            Tile t1 = new Tile(currentPlayer, 500,0, gameTiles.Pieces.get(0),this,0);
            PlayerTiles.add(t1);
            Tile t2 = new Tile(currentPlayer, 550,0, gameTiles.Pieces.get(1),this,1);
            PlayerTiles.add(t2);
            Tile t3 = new Tile(currentPlayer, 600,0, gameTiles.Pieces.get(2),this,2);
            PlayerTiles.add(t3);
            Tile t4 = new Tile(currentPlayer, 650,0, gameTiles.Pieces.get(3),this,3);
            PlayerTiles.add(t4);
            Tile t5 = new Tile(currentPlayer, 500,50, gameTiles.Pieces.get(4),this,4);
            PlayerTiles.add(t5);
            Tile t6 = new Tile(currentPlayer, 575,75, gameTiles.Pieces.get(5),this,5);
            PlayerTiles.add(t6);
            Tile t7 = new Tile(currentPlayer, 650,325, gameTiles.Pieces.get(6),this,6);
            PlayerTiles.add(t7);
            Tile t8 = new Tile(currentPlayer, 500,175, gameTiles.Pieces.get(7),this,7);
            PlayerTiles.add(t8);
            Tile t9 = new Tile(currentPlayer, 575,175, gameTiles.Pieces.get(8),this,8);
            PlayerTiles.add(t9);
            Tile t10 = new Tile(currentPlayer, 500,250, gameTiles.Pieces.get(9),this,9);
            PlayerTiles.add(t10);
            Tile t11 = new Tile(currentPlayer, 575,250, gameTiles.Pieces.get(10),this,10);
            PlayerTiles.add(t11);
            Tile t12 = new Tile(currentPlayer, 675,200, gameTiles.Pieces.get(11),this,11);
            PlayerTiles.add(t12);
            Tile t13 = new Tile(currentPlayer, 525,400, gameTiles.Pieces.get(12),this,12);
            PlayerTiles.add(t13);
            Tile t14 = new Tile(currentPlayer, 575,375, gameTiles.Pieces.get(13),this,13);
            PlayerTiles.add(t14);
            Tile t15 = new Tile(currentPlayer, 650,75, gameTiles.Pieces.get(14),this,14);
            PlayerTiles.add(t15);
            Tile t16 = new Tile(currentPlayer, 650,425, gameTiles.Pieces.get(15),this,15);
            PlayerTiles.add(t16);
            Tile t17 = new Tile(currentPlayer, 500,500, gameTiles.Pieces.get(16),this,16);
            PlayerTiles.add(t17);
            Tile t18 = new Tile(currentPlayer, 500,600, gameTiles.Pieces.get(17),this,17);
            PlayerTiles.add(t18);
            Tile t19 = new Tile(currentPlayer, 550,475, gameTiles.Pieces.get(18),this,18);
            PlayerTiles.add(t19);
            Tile t20 = new Tile(currentPlayer, 625,550, gameTiles.Pieces.get(19),this,19);
            PlayerTiles.add(t20);
            Tile t21 = new Tile(currentPlayer, 625,625, gameTiles.Pieces.get(20),this,20);
            PlayerTiles.add(t21);
            Players.add(PlayerTiles);
        }
    }

    /** To be called by isValidEncoding. Given a user-inputted game string, check if it doesn't contain any illegal characters such as numbers,
     * expressions etc, and only contains letters.
     * Made by Faizan
     */
    public static boolean isOnlyAlpha(String gamepiece) {
        char[] chars = gamepiece.toCharArray();
        for (char c : chars) {
            if(!Character.isLetter(c)) {
                return false;
            }
        }
        return true;
    }

    /** Created by Faizan:
     *To be called by isValidEncoding. Given a user-inputted game string, check if the user-inputted game piece has only 4 characters.
     * Made by Faizan
     */
    public static boolean isLengthOnlyFour (String gamepiece) {
        return (gamepiece.length() == 4);
    }

    /** Created by Faizan:
     * Check if the user-inputted game piece has the valid encoding specifications. Even though this function should be able to do the job
     * of isLengthOnlyFour and isOnlyAlpha, the way I have written isValidEncoding is that it can only correctly check a string of exactly
     * four alphabets
     */
    public static boolean isValidEncoding (String gamepiece) {

        return  (
                isOnlyAlpha(gamepiece) && //checks if gamepiece contains only alphabetical values THEN
                        isLengthOnlyFour(gamepiece)) && //checks if gamepiece is of length 4 THEN
                ((gamepiece.substring(0, 1).matches("[A-Ua-u]")) &&  //checks if game piece corresponds to an actual tile THEN
                        (gamepiece.substring(1, 2).matches("[A-Ha-h]"))  &&  //checks if  game piece corresponds to a correct rotation THEN
                        (gamepiece.substring(2, 3).matches("[A-Ta-t]"))  &&  //checks if  game piece corresponds to an actual X coordinate THEN
                        (gamepiece.substring(3, 4).matches("[A-Ta-t]")));   //checks if  game piece corresponds to an actual  Y coordinate

    }

    @Override
    // The start method. (Written by the the whole group: Faizan, Jack, Liyang(Leon))
    public void start(Stage primaryStage) throws Exception {
        Group root = new Group();
        Scene main = new Scene(root, 700, 700);

        primaryStage.setTitle("Blokus!");
        Pane root_menu = new Pane();
        Menu menu = new Menu(root_menu, 700, 700);
        menu.selectionPane.setLayoutX(150);
        menu.selectionPane.setLayoutY(270);
        root_menu.getChildren().add(menu.selectionPane);
        menu.confirm.toFront();


        /*Background image for the menu made by Faizan, on Photoshop, by taking a screenshot of the game, rotating and pushing it back in the z-axis using
        3D transform tool, and then added some space starry background using noise and gaussian blur*/
        root_menu.setStyle(" -fx-background-image: url('file:assets/menu_background.png'); -fx-background-repeat: stretch;");

        Button launchGame = new Button("Launch Game!");
        launchGame.setLayoutX(250);
        launchGame.setLayoutY(520);
        // parse the game mode and go to the main scene.
        launchGame.setOnAction(event -> {
            game_mode = Menu.gameVariationState;
            if (game_mode.length() == 1) {
                isHuman[0] = true;
                isHuman[1] = true;
                isHuman[2] = true;
                isHuman[3] = true;
            } else if (game_mode.length() == 2) {
                if (game_mode.charAt(0) == 'H' && game_mode.charAt(1) == 'H') {
                    isHuman[0] = true;
                    isHuman[1] = true;
                    isHuman[2] = true;
                    isHuman[3] = true;
                } else if (game_mode.charAt(0) == 'H' && game_mode.charAt(1) == 'C') {
                    isHuman[0] = true;
                    isHuman[1] = false;
                    isHuman[2] = true;
                    isHuman[3] = false;
                } else {
                    isHuman[0] = false;
                    isHuman[1] = true;
                    isHuman[2] = false;
                    isHuman[3] = true;
                }
            } else if (game_mode.length() == 3) {
                for (int i = 0; i < 3; i++) {
                    isHuman[i] = game_mode.charAt(i) == 'H';
                }
                isHuman[3] = isHuman[0];
            } else if (game_mode.length() == 4) {
                for (int i = 0; i < 4; i++) {
                    isHuman[i] = game_mode.charAt(i) == 'H';
                }
            }
            primaryStage.setScene(main);
            nextTurn();
            messageBox.renew("Welcome to " + game_mode.length() + "-player game mode! Enjoy your time playing Blokus! \nIt's Player1's turn.");
        });

        Text blokusTitle = new Text("Blokus");
        blokusTitle.setFont(new Font(80));
        blokusTitle.setFill(Color.WHITE);
        blokusTitle.setLayoutX(220);
        blokusTitle.setLayoutY(100);

        Text madeBy = new Text("Made by Jack Adamson, Liyang Guan and Faizan Siddiqui");
        madeBy.setLayoutX(170);
        madeBy.setLayoutY(140);
        madeBy.setFill(Color.WHITE);
        primaryStage.setScene(menu);
        root_menu.getChildren().addAll(launchGame, blokusTitle, madeBy);
        primaryStage.show();


        //Draw the panel on the right-side
        Rectangle panel = new Rectangle(500, 0, 200, 700);
        panel.setFill(Color.GREY);
        root.getChildren().add(panel);

        //Draw the board for playing
        double x_c = 0;
        double y_c = 0;
        for (int i = 0; i < 20; i++) {
            for (int j = 0; j < 20; j++) {
                Cell c = new Cell(x_c, y_c, CELL_LENGTH - 1); //Leave 1 pixel for the margin.
                c.setFill(Color.LIGHTGREY);
                root.getChildren().add(c);
                board.add(c);

                x_c += CELL_LENGTH;
            }
            y_c += CELL_LENGTH;
            x_c = 0;
        }

        //Draw the Scoreboard
        scores.setLayoutX(50);
        scores.setLayoutY(530);
        root.getChildren().add(scores);

        //Add a message board
        root.getChildren().add(messageBox);

        //Add a textfield
        Text fieldpromt = new Text(325, 645, "Enter your game piece here");
        root.getChildren().add(fieldpromt);
        TextField field = new TextField();
        field.setPromptText("Enter your game piece...");
        field.setLayoutX(325);
        field.setLayoutY(650);
        field.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.ENTER) {
                if (isValidEncoding(field.getText())) {
                    if (BlokGame.legitimateGame(game + (game.equals("") ? "" : " ") + field.getText())) {
                        if (Objects.equals(game, "")) {
                            game += field.getText();
                        } else {
                            game += " " + field.getText(); //add piece to game
                        }
                        movePiece(field.getText());
                        nextTurn();
                    } else {
                        System.out.println(field.getText() + " is not a valid move!");
                    }
                } else {
                    System.out.println(field.getText() + " is an invalid encoding!");
                }
                System.out.println("So far the game is '" + game + "' and it is " + getCurrentPlayer(game) + "'s turn!");
                System.out.println("Suggested moves are:" + AIplayer.getMove(game));
                field.clear();
            }
        });
        root.getChildren().add(field);

        //Populate each players side panel with each piece
        initTiles();
        root.getChildren().addAll(Players.get(0));
        root.getChildren().addAll(Players.get(1));
        root.getChildren().addAll(Players.get(2));
        root.getChildren().addAll(Players.get(3));


        //Draw all the playable pieces
        for (ArrayList<Tile> playersTile : Players) {
            playersTile.forEach(PlayBoard.Tile::Deactivate);
        }
        currentTurn = 3;


    }

    public static void main(String[] args) {
        launch(args);
    }
}
