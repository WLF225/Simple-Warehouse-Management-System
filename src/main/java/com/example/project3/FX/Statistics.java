package com.example.project3.FX;

import com.example.project3.Classes.*;
import com.example.project3.DataStructures.DLinkedList;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;

public class Statistics extends BorderPane {

    public Statistics(Scene scene) {

        Main.stage.setTitle("Statistical Report");
        setPadding(new javafx.geometry.Insets(100));

        Button backB = new Button("Back");

        TextArea tA = new TextArea();
        tA.setEditable(false);
        tA.setWrapText(true);

        setCenter(tA);

        backB.setOnAction(e -> scene.setRoot(new MainMenu(scene)));
        //i added this HBoxes so the padding work
        HBox backHB = new HBox(backB);

        backHB.setPadding(new javafx.geometry.Insets(80,0,0,0));
        backHB.setAlignment(Pos.CENTER);

        setBottom(backHB);

        //for the products and Shipments number
        int size = 0;
        int cancelSize = 0;
        int approvedSize = 0;

        //For the max quantity shipment/s Note: i only counted the approved shipments
        int maxQuantity = 0;
        DLinkedList<Shipment> linkList = new DLinkedList<>();

        int numOfActiveProd = 0;

        Shipment mostRecShipment = null;

        String cancelRate = "";

        int productQuantity;
        DLinkedList<ProductQuantity> productQuantityList = new DLinkedList<>();

        for(ProductCategory category: CategoryManagement.categoriesList){
            int shipNum = 0;
            int shipCanceled = 0;
            size += category.getProductList().size();
            for(Product prod: category.getProductList()){
                productQuantity = 0;

                cancelSize += prod.getCanceledShipments().length(1);
                approvedSize += prod.getInventoryStockList().length(1);

                if(prod.getStatus() == 'A')
                    numOfActiveProd++;

                shipCanceled += prod.getCanceledShipments().length(1);
                shipNum += prod.getCanceledShipments().length(1) + prod.getInventoryStockList().length(1);

                //for the max quantity and most recent shipments
                for (Shipment ship:prod.getInventoryStockList()){
                    if(ship.getQuantity() > maxQuantity){
                        linkList.clear();
                        linkList.insetSorted(ship);
                        maxQuantity = ship.getQuantity();
                    } else if(ship.getQuantity() == maxQuantity){
                        linkList.insetSorted(ship);
                    }

                    if(mostRecShipment == null)
                        mostRecShipment = ship;

                    if(ship.getDate().after(mostRecShipment.getDate()) || ship.getDate().equals(mostRecShipment.getDate()))
                        mostRecShipment = ship;

                    productQuantity += ship.getQuantity();
                }
                productQuantityList.insetSorted(new ProductQuantity(prod.getProductName(),productQuantity));
            }
            cancelRate = cancelRate + "-"+category.getCategoryName()+": "+shipCanceled+"/"+shipNum+"\n";
        }

        tA.appendText("Total Products: " + size + "\n");
        tA.appendText("Approved Shipments: " + approvedSize + "\n");
        tA.appendText("Canceled Shipments: " + cancelSize + "\n");
        tA.appendText("Total Incoming Shipments: " + (approvedSize + cancelSize) + "\n\n");

        tA.appendText("Max Quantity Shipment/s:\n");
        for(Shipment ship: linkList){
            Product prod = ProductManagement.findProductById(ship.getProductID());
            tA.appendText("-"+ship.getShipmentID()+"("+prod.getProductName()+") -> "+ship.getQuantity()+"\n");
        }

        tA.appendText("\nMost Recent Shipment:\n");
        if(mostRecShipment != null) {
            Product prod = ProductManagement.findProductById(mostRecShipment.getProductID());
            tA.appendText("-" + mostRecShipment.getShipmentID() + "(" + prod.getProductName() + ") -> " + mostRecShipment.dateToString() + "\n\n");
        }
        tA.appendText("Cancel Rate Per Category:\n"+cancelRate);

        tA.appendText("\nStatus Summary: \n");
        tA.appendText("-Active Products: " + numOfActiveProd + "\n");
        tA.appendText("-Inactive Products: " + (size-numOfActiveProd) + "\n\n");

        tA.appendText("Inventory Summary:\n");
        for(ProductQuantity curr:productQuantityList)
            tA.appendText("-"+curr.getProdName()+": "+curr.getQuantity()+" units\n");

    }
}
