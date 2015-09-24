package comp1140.ass2;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import javafx.stage.Stage;

import java.awt.*;
import java.awt.image.PixelInterleavedSampleModel;
import java.util.ArrayList;


/**
 * Created by Administrator on 2015/9/20/0020.
 */
public class PlayBoard extends Application {
    //Put only global variables for the GAME here
    final static double CELL_LENGTH = 25;

    ArrayList<Cell> board = new ArrayList<>();
    String game = "";
    Tiles mytiles = new Tiles();
    //Put all inner classes here, and those events inside the object.

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
        double rotation;
        String encodingTile = "";

        public Tile (double x, double y, ArrayList<Point> piece,PlayBoard board) {
            this.board = board;
            this.rotation = 0.0;

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

            this.setOnMouseReleased(event -> {
                //if it is not a legitimate move then move the tile back to original coordinates
                //if it move outside the board then move the tile back to originak coordinates
                // else place on board and change the encoding game string.
            });

            this.setOnMouseClicked(event -> {
                if (event.isDragDetect()) {
                    this.setRotate(rotation + 90);
                    rotation = this.getRotate();
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

        //Draw all the playable pieces
        Tile t1 = new Tile(500,0,mytiles.Pieces.get(0),this);
        root.getChildren().add(t1);
        Tile t2 = new Tile(550,0,mytiles.Pieces.get(1),this);
        root.getChildren().add(t2);
        Tile t3 = new Tile(600,0,mytiles.Pieces.get(2),this);
        root.getChildren().add(t3);
        Tile t4 = new Tile(650,0,mytiles.Pieces.get(3),this);
        root.getChildren().add(t4);
        Tile t5 = new Tile(500,50,mytiles.Pieces.get(4),this);
        root.getChildren().add(t5);
        Tile t6 = new Tile(575,75,mytiles.Pieces.get(5),this);
        root.getChildren().add(t6);
        Tile t7 = new Tile(650,325,mytiles.Pieces.get(6),this);
        root.getChildren().add(t7);
        Tile t8 = new Tile(500,175,mytiles.Pieces.get(7),this);
        root.getChildren().add(t8);
        Tile t9 = new Tile(575,175,mytiles.Pieces.get(8),this);
        root.getChildren().add(t9);
        Tile t10 = new Tile(500,250,mytiles.Pieces.get(9),this);
        root.getChildren().add(t10);
        Tile t11 = new Tile(575,250,mytiles.Pieces.get(10),this);
        root.getChildren().add(t11);
        Tile t12 = new Tile(675,200,mytiles.Pieces.get(11),this);
        root.getChildren().add(t12);
        Tile t13 = new Tile(525,400,mytiles.Pieces.get(12),this);
        root.getChildren().add(t13);
        Tile t14 = new Tile(575,375,mytiles.Pieces.get(13),this);
        root.getChildren().add(t14);
        Tile t15 = new Tile(650,75,mytiles.Pieces.get(14),this);
        root.getChildren().add(t15);
        Tile t16 = new Tile(650,425,mytiles.Pieces.get(15),this);
        root.getChildren().add(t16);
        Tile t17 = new Tile(500,500,mytiles.Pieces.get(16),this);
        root.getChildren().add(t17);
        Tile t18 = new Tile(500,600,mytiles.Pieces.get(17),this);
        root.getChildren().add(t18);
        Tile t19 = new Tile(550,475,mytiles.Pieces.get(18),this);
        root.getChildren().add(t19);
        Tile t20 = new Tile(625,550,mytiles.Pieces.get(19),this);
        root.getChildren().add(t20);
        Tile t21 = new Tile(625,625,mytiles.Pieces.get(20),this);
        root.getChildren().add(t21);


        primaryStage.setScene(main);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
