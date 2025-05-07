package com.example.project3.FX;

import com.example.project3.Classes.AlertException;
import com.example.project3.Classes.CategoryManagement;
import com.example.project3.Classes.ProductCategory;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class AddCategoryMenu extends BorderPane {

    public AddCategoryMenu(Scene scene) {

        Main.stage.setTitle("Add Category");

        setPadding(new javafx.geometry.Insets(100,300,100,300));

        TextField[] textFields = {new TextField(),new TextField()};
        TextArea textArea = new TextArea();
        Button[] buttons = {new Button("Add Category"), new Button("Clear"),new Button("Back")};



        for (TextField textField : textFields) {
            textField.setPrefSize(300,60);
        }

        textFields[0].setPromptText("Category ID");
        textFields[1].setPromptText("Category Name");
        textArea.setPromptText("Description");

        textFields[0].setDisable(true);
        textFields[0].setText(CategoryManagement.categoryID+"");

        VBox tFVB = new VBox(60,textFields[0],textFields[1],textArea);
        tFVB.setAlignment(Pos.CENTER);

        setCenter(tFVB);

        buttons[0].setOnAction(e -> {
            try {
                if (textFields[1].getText().isEmpty()) {
                    throw new AlertException("Category Name can't be empty");
                }
                CategoryManagement.addCategory(new ProductCategory(Integer.parseInt(textFields[0].getText()),
                                                                  textFields[1].getText(),textArea.getText()));
                buttons[1].fire();
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Category add");
                alert.setHeaderText(null);
                alert.setContentText("Category added successfully");
                alert.showAndWait();
            }catch (Exception ex){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Add Category Failed");
                alert.setHeaderText(null);
                alert.setContentText(ex.getMessage());
                alert.showAndWait();
            }
        });

        buttons[1].setOnAction(e -> {textFields[0].setText(CategoryManagement.categoryID+"");textFields[1].setText("");textArea.setText("");});

        buttons[2].setOnAction(e -> scene.setRoot(new CategoryManagementMenu(scene)));

        HBox buttonsHB = new HBox(60,buttons);
        buttonsHB.setAlignment(Pos.CENTER);

        setBottom(buttonsHB);

    }

}
