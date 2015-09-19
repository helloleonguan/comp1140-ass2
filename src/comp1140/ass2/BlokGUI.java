package comp1140.ass2;/**
 * Created by Jack on 8/31/2015.
 */

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.awt.*;
import java.util.ArrayList;

public class BlokGUI extends Application {
    public int turn;
    Tiles players[];
    boolean dragging;
    public static void main(String[] args) {
        launch(args);
    }
    String game = "";
    Tiles tiles = new Tiles();
    @Override
    public void start(Stage primaryStage) {
        turn = 0;
        int page = 0;
        players = new Tiles[4];
        primaryStage.setTitle("Blokus!");
        Group root = new Group();
        Canvas canvas = new Canvas(700,700);
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gridGame(gc);
        drawShapes(gc);
        gameToBoard(gc, 0, 0,"GCEE"); //testing sample game-piece
        root.getChildren().add(canvas);
        primaryStage.setScene(new Scene(root, 700, 700));
        primaryStage.show();
    }
    private void drawShapes(GraphicsContext gc) {
        gc.setFill(Color.GRAY);
        gc.fillRect(500, 0, 200, 700);
        gc.setStroke(Color.BLACK);
        gc.setLineWidth(3);
        gc.strokeLine(500, 0, 500, 700);
        for (int i = 0; i < tiles.Pieces.size(); i++) {
            drawPiece(gc,66 + 150 * (i%4) ,10 + 127*((i/4)),tiles.Pieces.get(i),0);
        }
    }


    /** (created by Faizan) Produces a 20x20 grid to represent the game board */
    private void gridGame (GraphicsContext gc) {
        for (int k = 0; k < 20; k++) {    //for the black squares
            for (int i = 0; i < 20; i++) {
                gc.fillRect(i * 25, k * 25, 25, 25);
                gc.setFill(Color.BLACK);
            }
        }

        for (int l = 0; l < 20; l++) {   //for the white squares
            for (int m = 0; m < 20; m++) {
                gc.fillRect(m * 25 + 1, l * 25 + 1, 23, 23);
                gc.setFill(Color.WHITE);
            }

        }
    }

    /** Created by Faizan: It is a function that will take a game-piece string and draw it on the board. ATM it is only putting it at the 0,0 coord*/
    private void gameToBoard(GraphicsContext gc, int x, int y, String game) {
        int index = 0;
        int encodingpart = 0;
        Tiles tileSet = new Tiles();
        ArrayList<Point> pieces = null;
        switch (encodingpart) {
            case 0: //Check if pass otherwise encode piece as ArrayList of points
                if (game.charAt(index) == '.') {
                    encodingpart = 5;
                    break;
                }
                pieces = new ArrayList<Point>();
                for (Point p : tileSet.Pieces.get(game.charAt(index) - 'A')) {
                    pieces.add(new Point(p.x, p.y));
                }
                //piece = (ArrayList<Point>) tileSet.Pieces.get(game.charAt(index)-'A').clone();
                break;
            case 1: //Rotate each square in ArrayList as required
                if ((game.charAt(index) - 'A') > 3) {
                    for (Point p : pieces) {
                        p.x = -(p.x);
                    }
                }
                switch ((game.charAt(index) - 'A') % 4) {
                    case 0:
                        break;
                    case 1:
                        for (Point p : pieces) {
                            int temp = p.x;
                            p.x = -p.y;
                            p.y = temp;
                        }
                        break;
                    case 2:
                        for (Point p : pieces) {
                            p.x = -(p.x);
                            p.y = -(p.y);
                        }
                        break;
                    case 3:
                        for (Point p : pieces) {
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

        }
        encodingpart++;
        index++;
        drawPiece(gc,x,y,pieces,2);
    }

    private void drawAvailablePieces(GraphicsContext gc, int player, int page){
        int actualpage = players[player].Pieces.size() <= 10 ? 0 : page;
        for (int i = 0; i < 10; i++) {
            if(i==players[player].Pieces.size())break;
            drawPiece(gc,i%2 * 100 + 500, 10 + 125 * (i/2),players[player].Pieces.get(10* actualpage + i),player);

        }
    }

    private void drawPiece(GraphicsContext gc, int x, int y, ArrayList<Point> Piece, int Player){
        for (Point p : Piece){
            gc.setFill(Color.BLACK);
            gc.fillRect(p.x * 25 + x, p.y * 25 + y, 25, 25);
            switch (Player) {
                case 0:
                    gc.setFill(Color.BLUE);
                    break;
                case 1:
                    gc.setFill(Color.YELLOW);
                    break;
                case 2:
                    gc.setFill(Color.RED);
                    break;
                case 3:
                    gc.setFill(Color.GREEN);
                    break;
            }
            gc.fillRect(p.x * 25 + x + 1, p.y * 25 + y + 1, 23, 23);
        }
    }
}
