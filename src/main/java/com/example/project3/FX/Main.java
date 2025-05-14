package com.example.project3.FX;

import com.example.project3.Classes.*;
import com.example.project3.DataStructures.Stack;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class Main extends Application {

    public static Stage stage;

    @Override
    public void start(Stage stage) {
        this.stage = stage;

        Product prod = new Product("P1","test","Category 1",'I');
        ProductManagement.addProduct(prod);
        ProductManagement.addProduct(new Product("P2","test2","Category 1",'A'));

        CategoryManagement.addCategory(new ProductCategory(3,"Category 2","Description 2"));
        CategoryManagement.addCategory(new ProductCategory(4,"Category 3","Description 3"));
        CategoryManagement.addCategory(new ProductCategory(5,"Category 4","Description 4"));

        ShipmentManagement.addShipment(new Shipment("SHP1","P1",15, new GregorianCalendar()),prod,false, new GregorianCalendar());
        ShipmentManagement.addShipment(new Shipment("SHP2","P1",10, new GregorianCalendar()),prod,false, new GregorianCalendar());
        ShipmentManagement.addShipment(new Shipment("SHP3","P1",12, new GregorianCalendar()),prod,false, new GregorianCalendar());

        ShipmentManagement.approveShipment(prod,false, new GregorianCalendar());

        ShipmentManagement.cancelShipment(prod,false, new GregorianCalendar());


        Scene scene = new Scene(new Pane());
        MainMenu mainMenu = new MainMenu(scene);
        scene.setRoot(mainMenu);
        scene.getStylesheets().add(getClass().getResource("/styles.css").toExternalForm());
        stage.setScene(scene);
        stage.setMaximized(true);
        stage.show();
    }

    public static void main(String[] args) {

        launch();
    }


}