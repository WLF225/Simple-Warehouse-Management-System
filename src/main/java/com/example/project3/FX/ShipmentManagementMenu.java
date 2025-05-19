package com.example.project3.FX;

import com.example.project3.Classes.*;
import javafx.beans.property.SimpleStringProperty;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;

import java.io.File;
import java.util.GregorianCalendar;
import java.util.Scanner;

public class ShipmentManagementMenu extends BorderPane {

    public ShipmentManagementMenu(Scene scene, Product product, ProductCategory productCategory) {

        Main.stage.setTitle("Shipment Management for Product " + product.getProductName());

        setPadding(new javafx.geometry.Insets(100));

        Button[] buttons = {new Button("Undo"), new Button("Redo"), new Button("Approve Shipment"),
                new Button("Cancel Shipment"), new Button("Add Shipment"), new Button("Back"),
                new Button("Show file")};

        TableView<Log> tableView = new TableView<>(product.getLogList());

        TableColumn<Log, Integer> indexColumn = new TableColumn<>("Step");
        indexColumn.setCellValueFactory(new PropertyValueFactory<>("index"));
        indexColumn.setMinWidth(120);

        TableColumn<Log, String> actionColumn = new TableColumn<>("Action Taken");
        actionColumn.setCellValueFactory(new PropertyValueFactory<>("action"));
        actionColumn.setMinWidth(500);


        TableColumn<Log, String> shipmentQueueColumn = new TableColumn<>("Shipment Queue\n(Front->Rear)");
        shipmentQueueColumn.setCellValueFactory(new PropertyValueFactory<>("shipmentQueue"));
        shipmentQueueColumn.setMinWidth(500);

        TableColumn<Log, String> inventoryColumn = new TableColumn<>("Inventory List\n(add Last)");
        inventoryColumn.setCellValueFactory(new PropertyValueFactory<>("inventory"));
        inventoryColumn.setMinWidth(500);

        TableColumn<Log, String> canceledColumn = new TableColumn<>("CanceledShipments\n(Add Last)");
        canceledColumn.setCellValueFactory(new PropertyValueFactory<>("canceled"));
        canceledColumn.setMinWidth(500);

        TableColumn<Log, String> undoStackColumn = new TableColumn<>("Undo Stack (→ Top)");
        undoStackColumn.setCellValueFactory(new PropertyValueFactory<>("undoStack"));
        undoStackColumn.setMinWidth(800);

        TableColumn<Log, String> redoStackColumn = new TableColumn<>("Redo Stack");
        redoStackColumn.setCellValueFactory(new PropertyValueFactory<>("redoStack"));
        redoStackColumn.setMinWidth(500);

        TableColumn<Log, String> dateCol = new TableColumn<>("Date");
        dateCol.setCellValueFactory(e -> new SimpleStringProperty(e.getValue().dateToString()));
        dateCol.setMinWidth(600);

        tableView.getColumns().addAll(indexColumn, actionColumn, shipmentQueueColumn,
                inventoryColumn, canceledColumn, undoStackColumn, redoStackColumn, dateCol);

        setCenter(tableView);

        if (product.getUndoStack().isEmpty())
            buttons[0].setDisable(true);

        if (product.getRedoStack().isEmpty())
            buttons[1].setDisable(true);

        buttons[0].setOnAction(e -> {
            ShipmentManagement.undo(product);
            if (product.getUndoStack().isEmpty())
                buttons[0].setDisable(true);
            buttons[1].setDisable(false);
        });

        buttons[1].setOnAction(e -> {
            ShipmentManagement.redo(product);
            if (product.getRedoStack().isEmpty())
                buttons[1].setDisable(true);
            buttons[0].setDisable(false);
        });

        buttons[2].setOnAction(e -> {
            try {
                Shipment ship = product.getShipmentsQueue().getFront();
                if (ship == null)
                    throw new AlertException("Shipment queue is empty.");
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Confirmation");
                alert.setHeaderText(null);
                alert.setContentText("Are you sure you want to approve shipment " + ship.getShipmentID() + "?");
                if (alert.showAndWait().get() == javafx.scene.control.ButtonType.OK) {
                    ShipmentManagement.approveShipment(product, false, new GregorianCalendar());
                    Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
                    alert1.setTitle("Shipment Approved");
                    alert1.setHeaderText(null);
                    alert1.setContentText("Shipment " + ship.getShipmentID() + " has been approved.");
                    alert1.showAndWait();
                }
            } catch (AlertException e1) {
                Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                errorAlert.setTitle("Error");
                errorAlert.setHeaderText(null);
                errorAlert.setContentText(e1.getMessage());
                errorAlert.showAndWait();
            }
        });


        buttons[3].setOnAction(e -> {
            try {
                Shipment ship = product.getShipmentsQueue().getFront();
                if (ship == null)
                    throw new AlertException("Shipment queue is empty.");
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Confirmation");
                alert.setHeaderText(null);
                alert.setContentText("Are you sure you want to cancel shipment " + ship.getShipmentID() + "?");
                if (alert.showAndWait().get() == javafx.scene.control.ButtonType.OK) {
                    ShipmentManagement.cancelShipment(product, false, new GregorianCalendar());
                    Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
                    alert1.setTitle("Shipment Cancelled");
                    alert1.setHeaderText(null);
                    alert1.setContentText("Shipment " + ship.getShipmentID() + " has been cancelled.");
                    alert1.showAndWait();
                }
            } catch (AlertException e1) {
                Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                errorAlert.setTitle("Error");
                errorAlert.setHeaderText(null);
                errorAlert.setContentText(e1.getMessage());
                errorAlert.showAndWait();
            }
        });

        buttons[4].setOnAction(e -> scene.setRoot(new AddShipmentMenu(scene, product, productCategory)));

        buttons[5].setOnAction(e -> scene.setRoot(new ProductManagementMenu(scene, productCategory)));

        buttons[6].setOnAction(e -> {
            try{
                FileChooser fileChooser = new FileChooser();
                fileChooser.setTitle("Open Resource File");

                File file = fileChooser.showOpenDialog(Main.stage);
                if(file == null)
                    throw new AlertException("File not found.");

                Scanner scanner = new Scanner(file);

                TextArea textArea = new TextArea();
                textArea.setEditable(false);

                while(scanner.hasNextLine()){
                    textArea.appendText(scanner.nextLine()+"\n");
                }

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("File Content");
                alert.setHeaderText(null);
                alert.getDialogPane().setContent(textArea);
                alert.showAndWait();

                scanner.close();

            }catch(Exception ex){
                Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                errorAlert.setTitle("Error");
                errorAlert.setHeaderText(null);
                errorAlert.setContentText("File not found.");
                errorAlert.showAndWait();
            }

        });

        HBox hB1 = new HBox(60, buttons[0], buttons[1]);
        hB1.setAlignment(Pos.CENTER);
        HBox hB2 = new HBox(60, buttons[6], buttons[2], buttons[3], buttons[4], buttons[5]);
        hB2.setAlignment(Pos.CENTER);

        VBox vbox = new VBox(60, hB1, hB2);
        vbox.setAlignment(Pos.CENTER);

        vbox.setPadding(new javafx.geometry.Insets(100, 0, 0, 0));

        setBottom(vbox);


    }


}
