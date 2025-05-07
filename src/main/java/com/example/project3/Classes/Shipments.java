package com.example.project3.Classes;

import java.util.GregorianCalendar;

public class Shipments {

    private String shipmentID;
    private int quantity;
    private GregorianCalendar date;

    public Shipments(String shipmentID, int quantity, GregorianCalendar date) {
        setShipmentID(shipmentID);
        setQuantity(quantity);
        setDate(date);
    }

    public String getShipmentID() {
        return shipmentID;
    }

    public void setShipmentID(String shipmentID) {
        this.shipmentID = shipmentID;
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
}
