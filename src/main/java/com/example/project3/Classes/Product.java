package com.example.project3.Classes;

import com.example.project3.DataStructures.CursorArray;
import com.example.project3.DataStructures.Queue;
import com.example.project3.DataStructures.Stack;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Product implements Comparable<Product>{

    private String productID;
    private String productName;
    private String categoryName;
    private char status;
    private Queue<Shipment> shipmentsQueue = new Queue<>();
    private CursorArray<Shipment> inventoryStockList = new CursorArray<>(10);
    private Stack<Shipment> undoStack = new Stack<>();
    private Stack<Shipment> redoStack = new Stack<>();
    private CursorArray<Shipment> canceledShipments = new CursorArray<>(10);
    private int approvedList;
    private int cancelledList;
    //To make the date of operations
    private ObservableList<Log> logList = FXCollections.observableArrayList();

    public Product(String productID, String productName, String categoryName, char status) {
        logList.add(new Log("Initial State", "", "", "", "", ""));
        setProductID(productID);
        setProductName(productName);
        setCategoryName(categoryName);
        setStatus(status);
        approvedList = inventoryStockList.createList();
        cancelledList = canceledShipments.createList();
    }

    public String getProductID() {
        return productID;
    }

    public void setProductID(String productID) {
        this.productID = productID;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String CategoryName) {
        this.categoryName = CategoryName;
    }

    public char getStatus() {
        return status;
    }


    public Queue<Shipment> getShipmentsQueue() {
        return shipmentsQueue;
    }

    public void setShipmentsQueue(Queue<Shipment> shipmentsQueue) {
        this.shipmentsQueue = shipmentsQueue;
    }

    public CursorArray<Shipment> getInventoryStockList() {
        return inventoryStockList;
    }

    public void setInventoryStockList(CursorArray<Shipment> inventoryStockList) {
        this.inventoryStockList = inventoryStockList;
    }

    public Stack<Shipment> getUndoStack() {
        return undoStack;
    }

    public void setUndoStack(Stack<Shipment> undoStack) {
        this.undoStack = undoStack;
    }

    public Stack<Shipment> getRedoStack() {
        return redoStack;
    }

    public void setRedoStack(Stack<Shipment> redoStack) {
        this.redoStack = redoStack;
    }

    public CursorArray<Shipment> getCanceledShipments() {
        return canceledShipments;
    }

    public void setCanceledShipments(CursorArray<Shipment> canceledShipments) {
        this.canceledShipments = canceledShipments;
    }

    public void setStatus(char status) {
        if (status == 'A' || status == 'I')
            this.status = status;
        else
            throw new AlertException("Status can only be Active or Inactive.");
    }

    public int getApprovedList() {
        return approvedList;
    }

    public int getCancelledList() {
        return cancelledList;
    }

    public ObservableList<Log> getLogList() {
        return logList;
    }

    @Override
    public int compareTo(Product o) {
        return productName.compareTo(o.getProductName());
    }

    @Override
    public boolean equals(Object o){
        if (o instanceof Product){
            Product prod = (Product) o;
            if (prod.getProductID() == productID)
                return true;
            return false;
        }
        return false;
    }
}
