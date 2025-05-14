package com.example.project3.FX;

import com.example.project3.Classes.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.FileChooser;

import java.io.File;
import java.util.GregorianCalendar;
import java.util.Scanner;

public class ReadFiles implements EventHandler<ActionEvent> {

    @Override
    public void handle(ActionEvent event) {
        boolean cond = true;
        int lineNum = 1;
        String file = "products";
        try {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("INFORMATION");
            alert.setHeaderText(null);
            alert.setContentText("You need to chose products.txt then shipments.txt");
            alert.showAndWait();

            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Chose products file");

            File productsFile = fileChooser.showOpenDialog(null);
            if (productsFile == null) {
                throw new AlertException("Couldn't find any file.");
            }

            fileChooser.setTitle("Chose shipments file");
            File shipmentsFile = fileChooser.showOpenDialog(null);
            if (shipmentsFile == null) {
                throw new AlertException("shipments file not found.");
            }

            Scanner productsScanner = new Scanner(productsFile);
            Scanner shipmentsScanner = new Scanner(shipmentsFile);

            productsScanner.nextLine();
            shipmentsScanner.nextLine();

            while (productsScanner.hasNextLine()) {
                lineNum++;
                String[] temp = productsScanner.nextLine().split(",");
                char status = temp[3].charAt(0);
                ProductManagement.addProduct(new Product(temp[0],temp[1],temp[2],status));
            }

            lineNum = 1;
            file = "shipments";

            while (shipmentsScanner.hasNextLine()) {
                lineNum++;
                String[] temp = shipmentsScanner.nextLine().split(",");

                String[] parts = temp[3].split("-");

                int year = Integer.parseInt(parts[0]);
                int month = Integer.parseInt(parts[1]) - 1;  // Month is zero-based
                int day = Integer.parseInt(parts[2]);

                GregorianCalendar calendar = new GregorianCalendar(year, month, day);
                Product prod = ProductManagement.findProductById(temp[1]);
                if(prod == null)
                    throw new AlertException("Product with this ID not found.");

                ShipmentManagement.addShipment(new Shipment(temp[0],temp[1],Integer.parseInt(temp[2]),calendar),prod,false,calendar);
            }


        } catch (AlertException e) {
            if (cond) {
                Alert alertE = new Alert(Alert.AlertType.ERROR);
                //To close all the alerts at once
                ButtonType closeAllBT = new ButtonType("Close all");
                alertE.getButtonTypes().add(closeAllBT);
                alertE.setTitle("Error");
                alertE.setHeaderText(null);
                alertE.setContentText(e.getMessage() + "This error is in line " + lineNum + " in "+file+" file.");
                if (alertE.showAndWait().get() == closeAllBT) {
                    cond = false;
                }
            }
        }catch (Exception e1){
            if (cond) {
                Alert alertE = new Alert(Alert.AlertType.ERROR);
                //To close all the alerts at once
                ButtonType closeAllBT = new ButtonType("Close all");
                alertE.getButtonTypes().add(closeAllBT);
                alertE.setTitle("Error");
                alertE.setHeaderText(null);
                alertE.setContentText("Rong format in line " + lineNum + " in "+file+" file.");
                if (alertE.showAndWait().get() == closeAllBT) {
                    cond = false;
                }
            }
        }
    }
}
