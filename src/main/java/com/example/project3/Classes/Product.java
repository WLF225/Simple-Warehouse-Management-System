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
    private Stack<Action> undoStack = new Stack<>();
    private Stack<Action> redoStack = new Stack<>();
    private CursorArray<Shipment> canceledShipments = new CursorArray<>(10);
    private int approvedList = inventoryStockList.createList();
    private int cancelledList = canceledShipments.createList();
    //To make the date of operations
    private ObservableList<Log> logList = FXCollections.observableArrayList();

    public Product(String productID, String productName, String categoryName, char status) {
        logList.add(new Log("Initial State", "", "", "", "", ""));
        setProductID(productID);
        setProductName(productName);
        setCategoryName(categoryName);
        setStatus(status);
    }

    public String getProductID() {
        return productID;
    }

    public void setProductID(String productID) {
        if(!productID.matches("^P\\d+$"))
            throw new AlertException("Product ID must start with SHP followed by a number.");
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

    public Stack<Action> getUndoStack() {
        return undoStack;
    }

    public void setUndoStack(Stack<Action> undoStack) {
        this.undoStack = undoStack;
    }

    public Stack<Action> getRedoStack() {
        return redoStack;
    }

    public void setRedoStack(Stack<Action> redoStack) {
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
        return productID.compareTo(o.getProductID());
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

    @Override
    public String toString(){
        return productID+","+productName+","+categoryName+","+((status=='A')?"Active":"Inactive");
    }
}
