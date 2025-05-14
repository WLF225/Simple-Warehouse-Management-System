package com.example.project3.FX;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

public class MainMenu extends BorderPane {

    public MainMenu(Scene scene) {

        Main.stage.setTitle("Main Menu");

        Button[] buttons = {new Button("Category Management"),new Button("Show Shipments"),
                new Button("Statistical report"),new Button("Read from file"),
                new Button("Save to file"), new Button("Exit")};

        for (Button button : buttons) {
            button.setPrefSize(300,60);
        }

        VBox vBox = new VBox(60,buttons);
        vBox.setAlignment(Pos.CENTER);

        setCenter(vBox);

        scene.getStylesheets().add(getClass().getResource("/styles.css").toExternalForm());

        buttons[0].setOnAction(e -> scene.setRoot(new CategoryManagementMenu(scene)));

        buttons[1].setOnAction(e -> scene.setRoot(new ShowShipments(scene)));

        buttons[4].setOnAction(new SaveToFile());

        buttons[5].setOnAction(e -> System.exit(0));

    }

}