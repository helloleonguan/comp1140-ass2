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

    public static void main(String[] args) {
        launch(args);
    }
    String game = "";
    Tiles tiles = new Tiles();
    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Blokus!");
        Group root = new Group();
        Canvas canvas = new Canvas(700,700);
        GraphicsContext gc = canvas.getGraphicsContext2D();
        drawShapes(gc);
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
