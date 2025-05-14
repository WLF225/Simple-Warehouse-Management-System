package com.example.project3.FX;

import com.example.project3.Classes.*;
import com.example.project3.DataStructures.Queue;
import com.example.project3.DataStructures.Stack;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.PrintWriter;

public class SaveToFile implements EventHandler<ActionEvent> {

    @Override
    public void handle(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("INFORMATION");
        alert.setHeaderText(null);
        alert.setContentText("You need to chose products.txt then shipments.txt then log_export.txt");
        alert.showAndWait();

        try {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Chose products file");

            File productsFile = fileChooser.showSaveDialog(null);
            if (productsFile == null) {
                throw new AlertException("Couldn't find any file.");
            }

            fileChooser.setTitle("Chose shipments file");
            File shipmentsFile = fileChooser.showSaveDialog(null);
            if (shipmentsFile == null) {
                throw new AlertException("shipments file and log_export file not found.");
            }

            fileChooser.setTitle("Chose log_export file");
            File log_exportFile = fileChooser.showSaveDialog(null);
            if (log_exportFile == null) {
                throw new AlertException("log_export file not found.");
            }

            PrintWriter productsPR = new PrintWriter(productsFile);
            PrintWriter shipmentsPR = new PrintWriter(shipmentsFile);
            PrintWriter log_exportFR = new PrintWriter(log_exportFile);

            productsPR.println("ProductID,Name,Category,Status");
            shipmentsPR.println("ShipmentID,ProductID,Quantity,Date");

            for (ProductCategory cat : CategoryManagement.categoriesList) {
                for (Product prod : cat.getProductList()) {
                    productsPR.println(prod.toString());

                    //To print queue shipments
                    Queue<Shipment> queue = prod.getShipmentsQueue();
                    Queue<Shipment> tempQueue = new Queue<>();

                    while (!queue.isEmpty()) {
                        Shipment temp = queue.dequeue();
                        shipmentsPR.println(temp.toString());
                        tempQueue.enqueue(temp);
                    }
                    while (!tempQueue.isEmpty())
                        queue.enqueue(tempQueue.dequeue());

                    //To print approved items
                    for (Shipment ship : prod.getInventoryStockList())
                        shipmentsPR.println(ship.toString());

                    //To print canceled items
                    for (Shipment ship : prod.getCanceledShipments())
                        shipmentsPR.println(ship.toString());

                    //To print history changes
                    Stack<Action> stack = prod.getUndoStack();
                    Stack<Action> tempStack = new Stack<>();

                    while (!stack.isEmpty())
                        tempStack.push(stack.pop());

                    while (!tempStack.isEmpty()) {
                        Action temp = tempStack.pop();
                        log_exportFR.println(temp.toString());
                        stack.push(temp);
                    }
                }

            }

            productsPR.close();
            shipmentsPR.close();
            log_exportFR.close();

        } catch (Exception e) {
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setTitle("Error");
            errorAlert.setHeaderText(null);
            errorAlert.setContentText(e.getMessage());
            errorAlert.showAndWait();
        }


    }
}
