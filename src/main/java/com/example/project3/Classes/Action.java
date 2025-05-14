package com.example.project3.Classes;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class Action {

    private GregorianCalendar date;
    private String action;
    private Shipment shipment;
    private String productID;
    private String quantity;

    public Action(GregorianCalendar date, String action, Shipment shipment, String productID, String quantity) {
        setDate(date);
        setAction(action);
        setShipment(shipment);
        setProductID(productID);
        setQuantity(quantity);
    }

    public GregorianCalendar getDate() {
        return date;
    }

    public void setDate(GregorianCalendar date) {
        this.date = date;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public Shipment getShipment() {
        return shipment;
    }

    public void setShipment(Shipment shipment) {
        this.shipment = shipment;
    }

    public String getProductID() {
        return productID;
    }

    public void setProductID(String productID) {
        this.productID = productID;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String dateToString(){
        return date.get(Calendar.YEAR)+"-"+(date.get(Calendar.MONTH)+1)+"-"
                +date.get(Calendar.DAY_OF_MONTH)+" "
                +((date.get(Calendar.HOUR)<10)?"0"+date.get(Calendar.HOUR):date.get(Calendar.HOUR))+":"
                +((date.get(Calendar.MINUTE)<10)?"0"+date.get(Calendar.MINUTE):date.get(Calendar.MINUTE))+":"
                +((date.get(Calendar.SECOND)<10)?"0"+date.get(Calendar.SECOND):date.get(Calendar.SECOND));
    }

    @Override
    public String toString() {
        return dateToString()+"|"+action+"|"+shipment.getShipmentID()+"|"+productID+"|"+quantity;
    }
}
