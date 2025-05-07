package com.example.project3.FX;

import com.example.project3.Classes.AlertException;
import com.example.project3.Classes.Product;
import com.example.project3.Classes.ProductCategory;
import com.example.project3.Classes.ProductManagement;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class EditProductMenu extends BorderPane {

    public EditProductMenu(Scene scene, Product product, ProductCategory productCategory){

        Main.stage.setTitle("Edit Product");

        setPadding(new javafx.geometry.Insets(100,300,100,300));

        TextField[] textFields = {new TextField(),new TextField(),new TextField()};

        Button[] buttons = {new Button("Edit Product"), new Button("Refill"),new Button("Back")};

        RadioButton[] radioButtons = {new RadioButton("Inactive"),new RadioButton("Active")};

        ToggleGroup toggleGroup = new ToggleGroup();

        radioButtons[0].setToggleGroup(toggleGroup);
        radioButtons[1].setToggleGroup(toggleGroup);
        radioButtons[0].setSelected(true);

        HBox radioButtonsHB = new HBox(60,radioButtons);
        radioButtonsHB.setAlignment(Pos.CENTER);


        for (TextField textField : textFields) {
            textField.setPrefSize(300,60);
        }

        textFields[0].setPromptText("Product ID");
        textFields[1].setPromptText("Product Name");
        textFields[2].setPromptText("Category name");


        textFields[0].setDisable(true);
        textFields[2].setDisable(true);

        VBox tFVB = new VBox(60,textFields);
        tFVB.getChildren().add(radioButtonsHB);
        tFVB.setAlignment(Pos.CENTER);

        setCenter(tFVB);

        HBox buttonsHB = new HBox(60,buttons);
        buttonsHB.setAlignment(Pos.CENTER);

        setBottom(buttonsHB);

        buttons[0].setOnAction(e -> {
            try {
                if (textFields[1].getText().isEmpty()) {
                    throw new AlertException("Product Name can't be empty");
                }
                char status = (radioButtons[0].isSelected() ? 'I' : 'A');
                ProductManagement.updateProduct(product, textFields[1].getText(), status);
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Product edit");
                alert.setHeaderText(null);
                alert.setContentText("Product edited successfully");
                alert.showAndWait();
            }catch (AlertException e1){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Add Product Failed");
                alert.setHeaderText(null);
                alert.setContentText(e1.getMessage());
                alert.showAndWait();
            }

        });

        buttons[1].setOnAction(e -> {
            textFields[0].setText(product.getProductID());
            textFields[1].setText(product.getProductName());
            textFields[2].setText(product.getCategoryName());
            if (product.getStatus() == 'I') {
                radioButtons[0].fire();
            }else {
                radioButtons[1].fire();
            }
        });

        buttons[1].fire();

        buttons[2].setOnAction(e -> scene.setRoot(new ProductManagementMenu(scene,productCategory)));
    }
}
