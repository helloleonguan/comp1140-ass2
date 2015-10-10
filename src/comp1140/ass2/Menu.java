package comp1140.ass2;

import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.geometry.HPos;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * Created by Faizan on 5/10/2015.
 */

public class Menu extends Scene {
    Button confirm;
    public PlayerSelectionPane selectionPane = new PlayerSelectionPane();
    public static String gameVariationState;
    Stage menuStage;
    Group root = new Group();
    public Menu(Parent root, double x, double y) {
        super(root,x,y);
    }

    class PlayerSelectionPane extends GridPane {
        public PlayerSelectionPane() {
            this.setHgap(5);
            this.setVgap(5);
            this.setMinWidth(290);

            ColumnConstraints column1 = new ColumnConstraints(100);
            ColumnConstraints column2 = new ColumnConstraints(100);


            this.getColumnConstraints().addAll(column1, column2);

            Text selectNumberLabel = new Text("Select the number of players for this game (both humans and computers):");
            selectNumberLabel.setFont(new Font(18));
            selectNumberLabel.setWrappingWidth(350);

            GridPane NumberSelectionPane = new GridPane();
            ChoiceBox selectNumber = new ChoiceBox(FXCollections.observableArrayList(1,2,3,4) );
            NumberSelectionPane.add(selectNumberLabel, 0, 0);
            NumberSelectionPane.add(selectNumber, 0, 1);

            Text Player1Label = new Text("Player 1");
            Player1Label.setFont(new Font(18));

            ChoiceBox Player1 = new ChoiceBox();
            Player1.setMinWidth(150);
            Player1.setItems(FXCollections.observableArrayList("Human Player", "Computer Player"));


            Text Player2Label = new Text("Player 2");
            Player2Label.setFont(new Font(18));

            ChoiceBox Player2 = new ChoiceBox();
            Player2.setMinWidth(150);
            Player2.setItems(FXCollections.observableArrayList("Human Player", "Computer Player"));


            Text Player3Label = new Text("Player 3");
            Player3Label.setFont(new Font(18));

            ChoiceBox Player3 = new ChoiceBox();
            Player3.setMinWidth(150);
            Player3.setItems(FXCollections.observableArrayList("Human Player", "Computer Player"));


            Text Player4Label = new Text("Player 4");
            Player4Label.setFont(new Font(18));

            ChoiceBox Player4 = new ChoiceBox();
            Player4.setMinWidth(150);
            Player4.setItems(FXCollections.observableArrayList("Human Player", "Computer Player"));

            Player1Label.setVisible(false);
            Player1.setVisible(false);
            Player2Label.setVisible(false);
            Player2.setVisible(false);
            Player3Label.setVisible(false);
            Player3.setVisible(false);
            Player4Label.setVisible(false);
            Player4.setVisible(false);

            Player1.getSelectionModel().select(0);
            Player2.getSelectionModel().select(0);
            Player3.getSelectionModel().select(0);
            Player4.getSelectionModel().select(0);

            confirm = new Button("Confirm your selection");


            selectNumber.getSelectionModel().selectedItemProperty()
                    .addListener((ObservableValue observable ,
                                  Object oldValue, Object newValue) -> {

                        if (selectNumber.getValue().equals(1)) {
                            //Start game, go to 84game mode
                            Player1Label.setVisible(false);
                            Player1.setVisible(false);
                            Player2Label.setVisible(false);
                            Player2.setVisible(false);
                            Player3Label.setVisible(false);
                            Player3.setVisible(false);
                            Player4Label.setVisible(false);
                            Player4.setVisible(false);
                        }

                        else if (selectNumber.getValue().equals(2)) {
                            Player1Label.setVisible(true);
                            Player1.setVisible(true);
                            Player2Label.setVisible(true);
                            Player2.setVisible(true);
                            Player3Label.setVisible(false);
                            Player3.setVisible(false);
                            Player4Label.setVisible(false);
                            Player4.setVisible(false);
                        }

                        else if (selectNumber.getValue().equals(3)) {
                            Player1Label.setVisible(true);
                            Player1.setVisible(true);
                            Player2Label.setVisible(true);
                            Player2.setVisible(true);
                            Player3Label.setVisible(true);
                            Player3.setVisible(true);
                            Player4Label.setVisible(false);
                            Player4.setVisible(false);
                        }

                        else if (selectNumber.getValue().equals(4)) {
                            Player1Label.setVisible(true);
                            Player1.setVisible(true);
                            Player2Label.setVisible(true);
                            Player2.setVisible(true);
                            Player3Label.setVisible(true);
                            Player3.setVisible(true);
                            Player4Label.setVisible(true);
                            Player4.setVisible(true);
                        }});

            StringBuilder gameVariation = new StringBuilder("    ");

            confirm.setOnAction(event -> {
                if (Player1.getValue().equals("Human Player")) {
                    gameVariation.setCharAt(0, 'H');
                }

                if (Player1.getValue().equals("Computer Player")) {
                    gameVariation.setCharAt(0, 'C');
                }

                if (Player2.getValue().equals("Human Player")) {
                    gameVariation.setCharAt(1, 'H');
                }

                if (Player2.getValue().equals("Computer Player")) {
                    gameVariation.setCharAt(1, 'C');
                }

                if (Player3.getValue().equals("Human Player")) {
                    gameVariation.setCharAt(2, 'H');
                }

                if (Player3.getValue().equals("Computer Player")) {
                    gameVariation.setCharAt(2, 'C');
                }

                if (Player4.getValue().equals("Human Player")) {
                    gameVariation.setCharAt(3, 'H');
                }

                if (Player4.getValue().equals("Computer Player")) {
                    gameVariation.setCharAt(3, 'C');
                }

                gameVariationState = gameVariation.substring(0, Integer.parseInt(selectNumber.getValue().toString()));

                System.out.println(gameVariationState);


            });



           GridPane.setHalignment(Player1Label, HPos.LEFT);
            this.add(Player1Label,0,1);
            this.add(Player1,1,1);

            this.add(NumberSelectionPane,0,0,2,1);

            GridPane.setHalignment(Player2Label, HPos.LEFT);
            this.add(Player2Label, 0, 2);
            this.add(Player2,1,2);

            GridPane.setHalignment(Player3Label, HPos.LEFT);
            this.add(Player3Label, 0, 3);
            this.add(Player3,1,3);

            GridPane.setHalignment(Player4Label, HPos.LEFT);
            this.add(Player4Label, 0, 4);
            this.add(Player4,1,4);

            this.add(confirm,0,6,2,1);

        }



    }

    public static String getGame() {
        return gameVariationState;
    }

//    @Override
//    public void start(Stage primaryStage) throws Exception {
//        primaryStage.setTitle("Blokus!");
//        Group root = new Group();
//        Scene main = new Scene(root,700,700);
//        primaryStage.setScene(main);
//        primaryStage.show();
//        menuStage = primaryStage;
//
//        root.getChildren().add(selectionPane);
//        selectionPane.setLayoutX(150);
//        selectionPane.setLayoutY(270);
//
//
//        Text blokus = new Text("Blokus!");
//        root.getChildren().add(blokus);
//
//
//
//    }


}
