package com.example.project3.Classes;

import java.util.GregorianCalendar;

public class Shipment implements Comparable<Shipment>{

    private String shipmentID;
    private int productID;
    private int quantity;
    private GregorianCalendar date;

    //i add this to know if it was canceled or approved or added
    private char modify;

    public Shipment(String shipmentID, int productID, int quantity, GregorianCalendar date) {
        setShipmentID(shipmentID);
        setProductID(productID);
        setQuantity(quantity);
        setDate(date);
    }

    public String getShipmentID() {
        return shipmentID;
    }

    public void setShipmentID(String shipmentID) {
        this.shipmentID = shipmentID;
    }

    public int getProductID() {
        return productID;
    }

    public void setProductID(int productID) {
        this.productID = productID;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public GregorianCalendar getDate() {
        return date;
    }

    public void setDate(GregorianCalendar date) {
        this.date = date;
    }


    public char getModify() {
        return modify;
    }

    public void setModify(char modify) {
        this.modify = modify;
    }


    @Override
    public int compareTo(Shipment o) {
        return shipmentID.compareTo(o.shipmentID);
    }

    @Override
    public boolean equals(Object o){
        if(o instanceof Shipment){
            return shipmentID.equals(((Shipment)o).getShipmentID());
        }
        return false;
    }
}
