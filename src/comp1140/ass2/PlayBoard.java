package comp1140.ass2;

import com.sun.rowset.internal.Row;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.scene.input.MouseButton;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.shape.Rectangle;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;


import javafx.stage.Stage;

import javax.swing.text.TableView;
import java.awt.*;
import java.util.ArrayList;


/**
 * Created by Administrator on 2015/9/20/0020.
 */
public class PlayBoard extends Application {
    //Put only global variables for the GAME here
    final static double CELL_LENGTH = 25;

    ArrayList<Cell> board = new ArrayList<>();
    String game = "";
    Tiles gameTiles = new Tiles();

    //Put all inner classes here, and those events inside the object.

    class Scoreboard {

        int blue_score;
        int yellow_score;
        int red_score;
        int green_score;

        public Scoreboard (double x, double y) {

        }



        //store 4 values for each player
        //give coords, 4 ints
    }

    class Cell extends Rectangle {
        public Cell (double x, double y, double length) {
            this.setX(x);
            this.setY(y);
            this.setWidth(length);
            this.setHeight(length);
        }
    }

    class Tile extends Group {
        private PlayBoard board = null;
        ArrayList<Cell> tile = new ArrayList<>();
        double original_x;
        double original_y;
        int shape_encoding;
        int rotation_encoding = 0;
        int positionX_encoding;
        int positionY_encoding;
        String encodingOfTile;
        String encodingTile = "";

        public Tile (double x, double y, ArrayList<Point> piece,PlayBoard board) {
            this.shape_encoding = piece.indexOf(gameTiles);
            this.board = board;

            for (Point p : piece){
                Cell c = new Cell(p.x * 25 + x,p.y * 25 + y,CELL_LENGTH - 1);
                c.setFill(setColor(getCurrentPlayer(game)));
                tile.add(c);
                this.getChildren().add(c);
            }

            this.setOnMouseDragEntered(event -> {
                this.original_x = event.getSceneX();
                this.original_y = event.getSceneY();
                toFront();
            });

            this.setOnMouseDragged(event -> {
                this.setLayoutX(event.getSceneX() - x);
                this.setLayoutY(event.getSceneY() - y);
                toFront();
                this.original_x = event.getSceneX();
                this.original_y = event.getSceneY();
            });

            this.setOnMouseDragExited(event -> {
                encodingOfTile = convertToCode(shape_encoding) + convertToCode(rotation_encoding + 4 * (this.getScaleX() == -1 ? 1 : 0))
                        + convertToCode(positionX_encoding) + convertToCode(positionY_encoding);

                if (BlokGame.legitimateGame(game + encodingOfTile)) {
                    if (game.equals("")) {
                        game += encodingOfTile;
                    } else {
                        game += " " + encodingOfTile;
                    }
                } else {
                    // Move the tile back to the panel.
                }
                //if it is not a legitimate move then move the tile back to original coordinates
                //if it move outside the board then move the tile back to originak coordinates
                // else place on board and change the encoding game string.

            });

            this.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2) {
                    this.setRotate(this.getRotate() + 90);
                    this.rotation_encoding = (this.rotation_encoding + 1) % 4;
                } else if (event.getButton().equals(MouseButton.SECONDARY)) {
                    if (this.getScaleX() == -1) {
                        this.setScaleX(1);
                    } else {
                        this.setScaleX(-1);
                    }
                }
            });

        }

        Color setColor(Player player) {
            Color color = null;
            switch(player) {
                case BLUE: color = Color.BLUE; break;
                case YELLOW: color = Color.YELLOW; break;
                case RED: color = Color.RED; break;
                case GREEN:color = Color.GREEN; break;
            }

            return color;
        }
    }


    //Put all methods here
    Player getCurrentPlayer (String game) {
        String[] tilesPLaced = game.split("\\s+");
        return Player.getPlayer((tilesPLaced.length - 1) % 4);
    }

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

    @Override
    public void start(Stage primaryStage) throws Exception {

        primaryStage.setTitle("Blokus!");
        Group root = new Group();
        Scene main = new Scene(root,700,700);

        //Draw and add shapes to the 'main' scene here as well as local variables.

        //Draw the panel on the right-side
        Rectangle panel = new Rectangle(500,0,200,700);
        panel.setFill(Color.GREY);
        root.getChildren().add(panel);

        //Draw the board for playing
        double x_c = 0;
        double y_c = 0;
        for (int i = 0; i < 20; i++) {
            for (int j = 0; j < 20; j++) {
                Cell c = new Cell(x_c,y_c,CELL_LENGTH - 1); //Leave 1 pixel for the margin.
                c.setFill(Color.LIGHTGREY);
                root.getChildren().add(c);
                board.add(c);

                x_c += CELL_LENGTH;
            }
            y_c += CELL_LENGTH;
            x_c = 0;
        }

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
                        if (BlokGame.legitimateGame(game + field.getText())) {
                            if (game == "") {
                                game += field.getText();
                            } else {
                                game += " " + field.getText(); //add piece to game
                            }
                        } else {
                            System.out.println(field.getText() + " is not a valid move!");
                        }
                    } else {
                        System.out.println(field.getText() + " is an invalid encoding!");
                    }
                    System.out.println("So far the game is '" + game + "' and it is " + getCurrentPlayer(game) + "'s turn!");
                    System.out.println("Suggested moves are:"+AIplayer.getMove(game));
                    field.clear();
                }
            }
        });
        root.getChildren().add(field);


        //Draw all the playable pieces
        Tile t1 = new Tile(500,0, gameTiles.Pieces.get(0),this);
        root.getChildren().add(t1);
        Tile t2 = new Tile(550,0, gameTiles.Pieces.get(1),this);
        root.getChildren().add(t2);
        Tile t3 = new Tile(600,0, gameTiles.Pieces.get(2),this);
        root.getChildren().add(t3);
        Tile t4 = new Tile(650,0, gameTiles.Pieces.get(3),this);
        root.getChildren().add(t4);
        Tile t5 = new Tile(500,50, gameTiles.Pieces.get(4),this);
        root.getChildren().add(t5);
        Tile t6 = new Tile(575,75, gameTiles.Pieces.get(5),this);
        root.getChildren().add(t6);
        Tile t7 = new Tile(650,325, gameTiles.Pieces.get(6),this);
        root.getChildren().add(t7);
        Tile t8 = new Tile(500,175, gameTiles.Pieces.get(7),this);
        root.getChildren().add(t8);
        Tile t9 = new Tile(575,175, gameTiles.Pieces.get(8),this);
        root.getChildren().add(t9);
        Tile t10 = new Tile(500,250, gameTiles.Pieces.get(9),this);
        root.getChildren().add(t10);
        Tile t11 = new Tile(575,250, gameTiles.Pieces.get(10),this);
        root.getChildren().add(t11);
        Tile t12 = new Tile(675,200, gameTiles.Pieces.get(11),this);
        root.getChildren().add(t12);
        Tile t13 = new Tile(525,400, gameTiles.Pieces.get(12),this);
        root.getChildren().add(t13);
        Tile t14 = new Tile(575,375, gameTiles.Pieces.get(13),this);
        root.getChildren().add(t14);
        Tile t15 = new Tile(650,75, gameTiles.Pieces.get(14),this);
        root.getChildren().add(t15);
        Tile t16 = new Tile(650,425, gameTiles.Pieces.get(15),this);
        root.getChildren().add(t16);
        Tile t17 = new Tile(500,500, gameTiles.Pieces.get(16),this);
        root.getChildren().add(t17);
        Tile t18 = new Tile(500,600, gameTiles.Pieces.get(17),this);
        root.getChildren().add(t18);
        Tile t19 = new Tile(550,475, gameTiles.Pieces.get(18),this);
        root.getChildren().add(t19);
        Tile t20 = new Tile(625,550, gameTiles.Pieces.get(19),this);
        root.getChildren().add(t20);
        Tile t21 = new Tile(625,625, gameTiles.Pieces.get(20),this);
        root.getChildren().add(t21);


        primaryStage.setScene(main);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
