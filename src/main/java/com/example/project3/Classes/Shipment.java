package com.example.project3.Classes;

import com.example.project3.DataStructures.Stack;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class Shipment implements Comparable<Shipment>{

    private String shipmentID;
    private String productID;
    private int quantity;
    private GregorianCalendar date;


    public Shipment(String shipmentID, String productID, int quantity, GregorianCalendar date) {
        setShipmentID(shipmentID);
        setProductID(productID);
        setQuantity(quantity);
        setDate(date);
    }

    public String getShipmentID() {
        return shipmentID;
    }

    public void setShipmentID(String shipmentID) {
        if(!shipmentID.matches("^SHP\\d+$"))
            throw new AlertException("Shipment ID must start with SHP followed by a number.");
        this.shipmentID = shipmentID;
    }

    public String getProductID() {
        return productID;
    }

    public void setProductID(String productID) {
        if(!productID.matches("^P\\d+$"))
            throw new AlertException("Product ID must start with P followed by a number.");
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

    public String dateToString(){
        return date.get(Calendar.YEAR)+"-"+(date.get(Calendar.MONTH)+1)+"-"+date.get(Calendar.DAY_OF_MONTH);
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

    @Override
    public String toString() {
        return shipmentID+","+productID+","+quantity+","+dateToString();
    }
}
