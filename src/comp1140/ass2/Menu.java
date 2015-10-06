package comp1140.ass2;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableArray;
import javafx.geometry.HPos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Separator;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.awt.*;
import java.util.List;
import java.util.Observable;

/**
 * Created by Faizan on 5/10/2015.
 */

public class Menu extends Application {

    PlayerSelectionPane selectionPane = new PlayerSelectionPane();

    class PlayerSelectionPane extends GridPane {

        public PlayerSelectionPane() {
            this.setHgap(5);
            this.setVgap(5);
            this.setMinWidth(290);
            this.setStyle("-fx-background-color: #C0C0C0;");

            ColumnConstraints column1 = new ColumnConstraints(100);
            ColumnConstraints column2 = new ColumnConstraints(100);


            this.getColumnConstraints().addAll(column1, column2);

            Text selectNumberLabel = new Text("Select the number of players for this game (both humans and computers):");
            selectNumberLabel.setFont(new Font(18));
            selectNumberLabel.setWrappingWidth(290);

            GridPane NumberSelectionPane = new GridPane();
            ChoiceBox selectNumber = new ChoiceBox(FXCollections.observableArrayList(2,3,4) );
            NumberSelectionPane.add(selectNumberLabel, 0, 0);
            NumberSelectionPane.add(selectNumber, 0, 1);

            selectNumber.setOnAction(event -> {
                if (selectNumber.getValue().equals(2)) {
                    //Show Blue and Yellow together, and green and red
                }

            });

            Text blueLabel = new Text("BLUE");
            blueLabel.setFill(Color.BLUE);
            blueLabel.setFont(new Font(18));

            ChoiceBox bluePlayer = new ChoiceBox();
            bluePlayer.setMinWidth(150);
            bluePlayer.setItems(FXCollections.observableArrayList( "Human Player", "Computer Player", new Separator(), "Not Playing") );


            Text yellowLabel = new Text("YELLOW");
            yellowLabel.setFill(Color.YELLOW);
            yellowLabel.setFont(new Font(18));

            ChoiceBox yellowPlayer = new ChoiceBox();
            yellowPlayer.setMinWidth(150);
            yellowPlayer.setItems(FXCollections.observableArrayList( "Human Player", "Computer Player", new Separator(), "Not Playing") );



            Text redLabel = new Text("RED");
            redLabel.setFill(Color.RED);
            redLabel.setFont(new Font(18));

            ChoiceBox redPlayer = new ChoiceBox();
            redPlayer.setMinWidth(150);
            redPlayer.setItems(FXCollections.observableArrayList( "Human Player", "Computer Player", new Separator(), "Not Playing") );


            Text greenLabel = new Text("GREEN");
            greenLabel.setFill(Color.GREEN);
            greenLabel.setFont(new Font(18));

            ChoiceBox greenPlayer = new ChoiceBox();
            greenPlayer.setMinWidth(150);
            greenPlayer.setItems(FXCollections.observableArrayList( "Human Player", "Computer Player", new Separator(), "Not Playing") );




           GridPane.setHalignment(blueLabel, HPos.LEFT);
            this.add(blueLabel,0,1);
            this.add(bluePlayer,1,1);

            this.add(NumberSelectionPane,0,0,2,1);

            GridPane.setHalignment(yellowLabel, HPos.LEFT);
            this.add(yellowLabel, 0, 2);
            this.add(yellowPlayer,1,2);

            GridPane.setHalignment(redLabel, HPos.LEFT);
            this.add(redLabel, 0, 3);
            this.add(redPlayer,1,3);

            GridPane.setHalignment(greenLabel, HPos.LEFT);
            this.add(greenLabel, 0, 4);
            this.add(greenPlayer,1,4);

        }
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Blokus!");
        Group root = new Group();
        Scene main = new Scene(root,700,700);
        primaryStage.setScene(main);
        primaryStage.show();

        root.getChildren().add(selectionPane);
        selectionPane.setLayoutX(150);
        selectionPane.setLayoutY(270);


        Text blokus = new Text("Blokus!");
        root.getChildren().add(blokus);




    }

    public static void main(String[] args) {
        launch(args);
    }
}
