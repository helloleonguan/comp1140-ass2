package comp1140.ass2;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.*;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.*;
import javafx.scene.shape.Rectangle;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import java.awt.*;
import java.awt.Image;
import java.awt.Label;
import java.util.ArrayList;
import java.util.Arrays;


/**
 * Created by Liyang(Leon) Guan on 2015/9/20.
 */
public class PlayBoard extends Application {
    //Put only global variables for the GAME here
    final static double CELL_LENGTH = 25;

    ArrayList<Cell> board = new ArrayList<>();
    ArrayList<ArrayList<Tile>> Players = new ArrayList<>();
    String game = "";
    Tiles gameTiles = new Tiles();
    int currentTurn = 0;
    int passes = 0;
    Scoreboard scores = new Scoreboard();

    //Put all inner classes here, and those events inside the object.
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
        private PlayBoard board = null;
        int owner; //Index of enum of players
        public boolean played = false;
        public boolean flipped = false; //Whether flipped along Y axis
        ArrayList<Cell> tile = new ArrayList<>();
        double original_x;
        double original_y;
        int shape_encoding;
        int rotation_encoding = 0;
        int positionX_encoding;
        int positionY_encoding;
        String encodingOfTile;
        String encodingTile = "";

        public void Grey(){
            for (Node c : this.getChildren().filtered(p -> Cell.class.isInstance(p))) {
                Cell d = (Cell) c;
                d.setFill(setColor(owner).darker());
            }
        }

        public void Ungrey(){
            for (Node c : this.getChildren().filtered(p -> Cell.class.isInstance(p))) {
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

            for (Point p : piece){
                Cell c = new Cell(p.x * 25 + x,p.y * 25 + y,CELL_LENGTH - 1);
                c.setFill(setColor(owner));
                tile.add(c);
                this.getChildren().add(c);
            }

            this.setOnMouseDragged(event -> { //Drag effect of piece
                if(!played) {
                    this.setLayoutX(event.getSceneX() - x - 12);
                    this.setLayoutY(event.getSceneY() - y - 12);
                    toFront();
                }
            });

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
                        /*TODO
                        Need to handle players that need to pass or reaching an end of game state.

                         */
                    } else {
                        System.out.println("Bad move " + encodingOfTile);
                        this.setLayoutX(0);
                        this.setLayoutY(0);
                    }

                    //if it is not a legitimate move then move the tile back to original coordinates
                    //if it move outside the board then move the tile back to original coordinates
                    // else place on board and change the encoding game string.
                }
            });

            this.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2) { //Rotate piece 90 deg Clockwise
                    for (Node c : this.getChildren().filtered(p -> Cell.class.isInstance(p))) {
                        Cell d = (Cell) c;
                        double temp = d.getX() - original_x;
                        d.setX(original_x - (d.getY() - original_y));
                        d.setY(temp + original_y);
                    }
                    this.rotation_encoding = (this.rotation_encoding + 1) % 4;
                } else if (event.getButton().equals(MouseButton.SECONDARY)) { //Flip piece along Y axis
                    flipped = !flipped;
                    for (Node c : this.getChildren().filtered(p -> Cell.class.isInstance(p))) {
                        Cell d = (Cell) c;
                        d.setX(original_x - (d.getX() - original_x));
                    }
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
     * Activate all tiles for current turn and deactivate the others.
     * (Written by Jack) */
    void nextTurn(){
        for(Tile t : Players.get(currentTurn)){
            t.Deactivate();
        }
        currentTurn = (currentTurn + 1) % 4;
        ArrayList<Boolean> playable = legalPieces();
        for(Tile t : Players.get(currentTurn)){
            t.Activate();
        }
        for (int i = 0; i < Players.get(currentTurn).size(); i++) {
            if(!playable.get(i)){
                Players.get(currentTurn).get(i).Grey();
            }else{
                Players.get(currentTurn).get(i).Ungrey();
            }
        }
        if (!playable.contains(true)){
            if(passes >= 3){
                //TODO END OF GAME
            } else {
                passes++;
                game += " .";
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
        return Player.getPlayer((tilesPLaced.length - 1) % 4);
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

    ArrayList<Boolean> legalPieces(){
        ArrayList<Boolean> legal = new ArrayList<>(21);
        for (int i = 0; i < 21; i++) {
            legal.add(false);
        }
        int turn = 1;
        String[] tilesPLaced = game.split("\\s+");
        int[] board0 = new int[400];
        boolean initial = game.length() <= 18;
        for (String s: tilesPLaced) {
            Legit.draw(s, board0,turn);
            turn = Legit.incrementTurn(turn);
        }
        for (char piece = 'A'; piece <= 'U'; piece++) {
            if(Players.get(currentTurn).get(piece - 'A').played){
                legal.set(piece-'A',true);
                continue;
            }
            for (char rotate = 'A'; rotate <= 'H' && ! legal.get(piece-'A'); rotate++){
                for (char x = 'A'; x < 'A'+20 && ! legal.get(piece-'A'); x++){
                    for (char y = 'A'; y < 'A' + 20 && ! legal.get(piece-'A'); y++){
                        if(Legit.checkLegitForTile (board0, "" + piece + rotate + x + y, initial, currentTurn + 1) ){
                            legal.set(piece-'A',true);
                        }
                    }
                }
            }
        }

        return legal;
    }

    void movePiece(String move){
        Tile current = Players.get(currentTurn).get(move.charAt(0)-'A');
        int rotation = move.charAt(1)-'A';
        if(rotation >= 4) {
            rotation -= 4;
            for (Node c : current.getChildren().filtered(p -> Cell.class.isInstance(p))) {
                Cell d = (Cell) c;
                d.setX(current.original_x - (d.getX() - current.original_x));
            }
        }
        for (int i = 0; i < rotation; i++) {
            for (Node c : current.getChildren().filtered(p -> Cell.class.isInstance(p))) {
                Cell d = (Cell) c;
                double temp = d.getX() - current.original_x;
                d.setX(current.original_x - (d.getY() - current.original_y));
                d.setY(temp + current.original_y);
            }
        }
        current.setLayoutX(25 * (move.charAt(2)-'A') - current.original_x);
        System.out.println(25*(move.charAt(2)-'A'));
        System.out.println("Moved to X:" + (25 * (move.charAt(2)-'A') - current.original_x) + " y: "+ (25 * (move.charAt(3)-'A') - current.original_y));
        current.setLayoutY(25 * (move.charAt(3)-'A') - current.original_y);
        current.played = true;
    }

    @Override
    // The start method. (Written by the the whole group: Faizan, Jack, Liyang(Leon))
    public void start(Stage primaryStage) throws Exception {
        Group root = new Group();
        Scene main = new Scene(root,700,700);

        primaryStage.setTitle("Blokus!");
        Pane root_menu = new Pane();
        Menu menu = new Menu(root_menu,700,700);
        menu.selectionPane.setLayoutX(150);
        menu.selectionPane.setLayoutY(270);
        root_menu.getChildren().add(menu.selectionPane);
        menu.confirm.toFront();
        root_menu.setStyle(" -fx-background-image: url('comp1140/ass2/menu_background.png'); -fx-background-repeat: stretch;");

        Button launchGame = new Button("Launch Game!");
        launchGame.setLayoutX(200);
        launchGame.setLayoutY(520);
        Text blokusTitle = new Text("Blokus");
        blokusTitle.setFont(new Font(80));
        blokusTitle.setFill(Color.WHITE);
        blokusTitle.setLayoutX(220);
        blokusTitle.setLayoutY(100);
        Text madeBy = new Text("Made by Jack Adamson, Liyang Guan and Faizan Siddiqui");
        madeBy.setLayoutX(170);
        madeBy.setLayoutY(140);
        madeBy.setFill(Color.WHITE);
        root_menu.getChildren().addAll(launchGame, blokusTitle, madeBy);
        launchGame.setOnAction(event -> {
            primaryStage.setScene(main);
        });
        primaryStage.setScene(menu);
        primaryStage.show();


        //Draw the panel on the right-side
        Rectangle panel = new Rectangle(500,0,200,700);
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


        //Add a textfield
        Text fieldpromt = new Text(300,645,"Enter your game piece here");
        root.getChildren().add(fieldpromt);
        TextField field = new TextField();
        field.setPromptText("Enter your game piece...");
        field.setLayoutX(300);
        field.setLayoutY(650);
        field.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent e) {
                if (e.getCode() == KeyCode.ENTER) {
                    if (BlokGUI.isValidEncoding(field.getText())) {
                        if (BlokGame.legitimateGame(game + (game.equals("")? "":" ") +field.getText())) {
                            if (game == "") {
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
            }
        });
        root.getChildren().add(field);

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
            root.getChildren().addAll(PlayerTiles);
        }

        //Draw all the playable pieces
        for (ArrayList<Tile> playersTile : Players){
            for (Tile t : playersTile){
                t.Deactivate();
            }
        }

        for (Tile t : Players.get(currentTurn)){
            t.Activate();
        }

    }

    public static void main(String[] args) {
        launch(args);
    }
}
