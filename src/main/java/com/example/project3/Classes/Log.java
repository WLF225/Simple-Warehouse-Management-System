package com.example.project3.Classes;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class Log implements Cloneable{

    public static int indexVar = 1;
    private int index;
    private String action;
    private String shipmentQueue;
    private String inventory;
    private String canceled;
    private String undoStack;
    private String redoStack;
    private GregorianCalendar date;

    public Log(String action, String shipmentQueue, String inventory, String canceled, String undoStack, String redoStack) {
        this.index = indexVar++;
        this.action = action;
        this.shipmentQueue = shipmentQueue;
        this.inventory = inventory;
        this.canceled = canceled;
        this.undoStack = undoStack;
        this.redoStack = redoStack;
        date = new GregorianCalendar();
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getShipmentQueue() {
        return shipmentQueue;
    }

    public void setShipmentQueue(String shipmentQueue) {
        this.shipmentQueue = shipmentQueue;
    }

    public String getInventory() {
        return inventory;
    }

    public void setInventory(String inventory) {
        this.inventory = inventory;
    }

    public String getCanceled() {
        return canceled;
    }

    public void setCanceled(String canceled) {
        this.canceled = canceled;
    }

    public String getUndoStack() {
        return undoStack;
    }

    public void setUndoStack(String undoStack) {
        this.undoStack = undoStack;
    }

    public String getRedoStack() {
        return redoStack;
    }

    public void setRedoStack(String redoStack) {
        this.redoStack = redoStack;
    }

    public GregorianCalendar getDate() {
        return date;
    }

    public void setDate(GregorianCalendar date) {
        this.date = date;
    }

    public String dateToString(){
        return date.get(Calendar.YEAR)+"-"+(date.get(Calendar.MONTH)+1)+"-"
                +date.get(Calendar.DAY_OF_MONTH)+" "
                +((date.get(Calendar.HOUR)<10)?"0"+date.get(Calendar.HOUR):date.get(Calendar.HOUR))+":"
                +((date.get(Calendar.MINUTE)<10)?"0"+date.get(Calendar.MINUTE):date.get(Calendar.MINUTE))+":"
                +((date.get(Calendar.SECOND)<10)?"0"+date.get(Calendar.SECOND):date.get(Calendar.SECOND));
    }

    @Override
    public Log clone(){
        //To make a copy with id + 1
        Log log = new Log(action, shipmentQueue, inventory, canceled, undoStack, redoStack);
        return log;
    }

    @Override
    public String toString() {
        String[] string = action.split(" ");

        return dateToString()+"|";
    }
}
