package com.example.project3.Classes;

import com.example.project3.DataStructures.CursorArray;
import com.example.project3.DataStructures.Queue;
import com.example.project3.DataStructures.Stack;

public class Product implements Comparable<Product>{

    private String productID;
    private String productName;
    private String categoryName;
    private char status;
    private Queue<Shipments> shipmentsQueue;
    private CursorArray<Product> inventoryStockList;
    private Stack<Product> undoStack;
    private Stack<Product> redoStack;
    private CursorArray<Product> canceledShipments;

    public Product(String productID, String productName, String categoryName, char status) {
        setProductID(productID);
        setProductName(productName);
        setCategoryName(categoryName);
        setStatus(status);
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


    public Queue<Shipments> getShipmentsQueue() {
        return shipmentsQueue;
    }

    public void setShipmentsQueue(Queue<Shipments> shipmentsQueue) {
        this.shipmentsQueue = shipmentsQueue;
    }

    public CursorArray<Product> getInventoryStockList() {
        return inventoryStockList;
    }

    public void setInventoryStockList(CursorArray<Product> inventoryStockList) {
        this.inventoryStockList = inventoryStockList;
    }

    public Stack<Product> getUndoStack() {
        return undoStack;
    }

    public void setUndoStack(Stack<Product> undoStack) {
        this.undoStack = undoStack;
    }

    public Stack<Product> getRedoStack() {
        return redoStack;
    }

    public void setRedoStack(Stack<Product> redoStack) {
        this.redoStack = redoStack;
    }

    public CursorArray<Product> getCanceledShipments() {
        return canceledShipments;
    }

    public void setCanceledShipments(CursorArray<Product> canceledShipments) {
        this.canceledShipments = canceledShipments;
    }

    public void setStatus(char status) {
        if (status == 'A' || status == 'I')
            this.status = status;
        else
            throw new AlertException("Status can only be Active or Inactive.");
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
