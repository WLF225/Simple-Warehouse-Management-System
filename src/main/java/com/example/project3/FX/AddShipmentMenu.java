package com.example.project3.FX;

import com.example.project3.Classes.*;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import java.util.GregorianCalendar;

public class AddShipmentMenu extends BorderPane {

    public AddShipmentMenu(Scene scene, Product product, ProductCategory productCategory) {

        Main.stage.setTitle("Add Shipment for Product " + product.getProductName());

        setPadding(new javafx.geometry.Insets(100, 300, 100, 300));

        TextField[] textFields = {new TextField(), new TextField(), new TextField()};
        DatePicker datePicker = new DatePicker();

        Button[] buttons = {new Button("Add Shipment"), new Button("Clear"), new Button("Back")};

        textFields[0].setPromptText("Shipment ID");
        textFields[0].setDisable(true);
        textFields[1].setPromptText("Product ID");
        textFields[2].setPromptText("Quantity");

        datePicker.setEditable(false);
        datePicker.setPromptText("Shipment Date");


        VBox vBox = new VBox(60, textFields[0], textFields[1], textFields[2], datePicker);

        vBox.setAlignment(Pos.CENTER);

        vBox.setPadding(new Insets(0, 0, 100, 0));

        setCenter(vBox);

        HBox buttonsHB = new HBox(60, buttons);
        buttonsHB.setAlignment(Pos.CENTER);

        setBottom(buttonsHB);

        buttons[0].setOnAction(e -> {

            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setTitle("Error");
            errorAlert.setHeaderText(null);

            try {

                if (datePicker.getValue() == null) {
                    errorAlert.setContentText("Please select a date.");
                    errorAlert.showAndWait();
                    return;
                }

                GregorianCalendar calendar = new GregorianCalendar(datePicker.getValue().getYear(),
                        datePicker.getValue().getMonthValue() - 1, datePicker.getValue().getDayOfMonth());

                Shipment ship = new Shipment(textFields[0].getText(),textFields[1].getText(),
                        Integer.parseInt(textFields[2].getText()),calendar);

                Product prod = ProductManagement.findProductById(ship.getProductID());

                if(prod == null)
                    throw new AlertException("Product with this ID not found.");

                ShipmentManagement.addShipment(ship,prod,false,calendar);

                buttons[1].fire();

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Shipment add");
                alert.setHeaderText(null);
                alert.setContentText("Shipment added successfully.");
                alert.showAndWait();

            } catch (AlertException e1) {
                errorAlert.setContentText(e1.getMessage());
                errorAlert.showAndWait();
            } catch (NumberFormatException e2) {
                errorAlert.setContentText("Quantity must be a number.");
                errorAlert.showAndWait();
            }
        });

        buttons[1].setOnAction(e -> {
            textFields[0].setText("SHP" + ShipmentManagement.shipmentID);
            textFields[1].setText(product.getProductID());
            textFields[2].setText("");
            datePicker.setValue(null);
        });

        buttons[2].setOnAction(e -> scene.setRoot(new ShipmentManagementMenu(scene, product, productCategory)));

        buttons[1].fire();

    }

}
