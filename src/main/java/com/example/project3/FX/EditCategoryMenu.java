package com.example.project3.FX;

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

public class EditCategoryMenu extends BorderPane {

    public EditCategoryMenu(Scene scene, ProductCategory productCategory) {

        Main.stage.setTitle("Edit Category");

        setPadding(new javafx.geometry.Insets(100,300,100,300));

        TextField[] textFields = {new TextField(),new TextField()};
        TextArea textArea = new TextArea();
        Button[] buttons = {new Button("Edit Category"), new Button("Refill"),new Button("Back")};

        for (TextField textField : textFields) {
            textField.setPrefSize(300,60);
            textField.setDisable(true);
        }

        textFields[0].setPromptText("Category ID");
        textFields[1].setPromptText("Category Name");
        textArea.setPromptText("Description");




        VBox tFVB = new VBox(60,textFields[0],textFields[1],textArea);
        tFVB.setAlignment(Pos.CENTER);

        setCenter(tFVB);

        buttons[0].setOnAction(e -> {
            CategoryManagement.updateCategory(productCategory, textArea.getText());
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Category edit");
            alert.setHeaderText(null);
            alert.setContentText("Category edited successfully");
            alert.showAndWait();
        });

        buttons[1].setOnAction(e -> {
            textFields[0].setText(productCategory.getCategoryID()+"");
            textFields[1].setText(productCategory.getCategoryName());
            textArea.setText(productCategory.getDescription());
        });

        buttons[1].fire();

        buttons[2].setOnAction(e -> scene.setRoot(new CategoryManagementMenu(scene)));

        HBox buttonsHB = new HBox(60,buttons);
        buttonsHB.setAlignment(Pos.CENTER);

        setBottom(buttonsHB);

    }
}
