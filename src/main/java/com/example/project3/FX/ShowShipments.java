package com.example.project3.FX;

import com.example.project3.Classes.*;
import com.example.project3.DataStructures.Queue;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;

public class ShowShipments extends BorderPane {

    public ShowShipments(Scene scene) {

        setPadding(new javafx.geometry.Insets(100));

        ComboBox<String> filterCB = new ComboBox<>();
        filterCB.getItems().addAll("Show approved Shipments","Show canceled Shipments","Show shipment queue");
        filterCB.setValue("Show approved Shipments");

        Button backB = new Button("Back");

        ObservableList<Shipment> approveList = FXCollections.observableArrayList();
        ObservableList<Shipment> canceledList = FXCollections.observableArrayList();
        ObservableList<Shipment> queueList = FXCollections.observableArrayList();

        for(ProductCategory category:CategoryManagement.categoriesList){
            for(Product prod: category.getProductList()){
                prod.getInventoryStockList().toObservable(1, approveList);
                prod.getCanceledShipments().toObservable(1, canceledList);
                Queue<Shipment> queue = prod.getShipmentsQueue();
                Queue<Shipment> tempQueue = new Queue<>();

                while(!queue.isEmpty()){
                    Shipment temp = queue.dequeue();
                    queueList.add(temp);
                    tempQueue.enqueue(temp);
                }
                while(!tempQueue.isEmpty())
                    queue.enqueue(tempQueue.dequeue());
            }
        }

        TableView<Shipment> tableView = new TableView<>(approveList);

        TableColumn<Shipment, String> shipmentIDCol = new TableColumn<>("Shipment ID");
        shipmentIDCol.setCellValueFactory(new PropertyValueFactory<>("shipmentID"));
        shipmentIDCol.setMinWidth(240);

        TableColumn<Shipment, String> productIDCol = new TableColumn<>("Product ID");
        productIDCol.setCellValueFactory(new PropertyValueFactory<>("productID"));
        productIDCol.setMinWidth(240);

        TableColumn<Shipment, Integer> quantityCol = new TableColumn<>("Quantity");
        quantityCol.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        quantityCol.setMinWidth(240);

        TableColumn<Shipment, String> dateCol = new TableColumn<>("Date");
        dateCol.setCellValueFactory(e -> new SimpleStringProperty(e.getValue().dateToString()));
        dateCol.setMinWidth(400);

        tableView.getColumns().addAll(shipmentIDCol, productIDCol, quantityCol, dateCol);

        filterCB.setOnAction(e -> {
            if(filterCB.getValue().equals("Show approved Shipments"))
                tableView.setItems(approveList);
            else if(filterCB.getValue().equals("Show canceled Shipments"))
                tableView.setItems(canceledList);
            else
                tableView.setItems(queueList);
        });

        backB.setOnAction(e -> scene.setRoot(new MainMenu(scene)));
        //i added these 2 HBoxes so the padding work
        HBox backHB = new HBox(backB);
        HBox filterCBHB = new HBox(filterCB);

        backHB.setPadding(new javafx.geometry.Insets(80,0,0,0));
        backHB.setAlignment(Pos.CENTER);

        filterCBHB.setPadding(new javafx.geometry.Insets(0,0,80,0));
        filterCBHB.setAlignment(Pos.CENTER);

        setTop(filterCBHB);
        setCenter(tableView);
        setBottom(backHB);


    }
}
