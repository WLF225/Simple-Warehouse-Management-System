package com.example.project3.Classes;

import com.example.project3.DataStructures.Queue;
import com.example.project3.DataStructures.Stack;

import java.util.GregorianCalendar;

public class ShipmentManagement {

    public static int shipmentID = 1;

    public static void addShipment(Shipment shipment, Product product, boolean fromRedo, GregorianCalendar calendar) {

        if (exists(shipment.getShipmentID()))
            throw new AlertException("The shipment id already exist");

        Action action = new Action(calendar,"Add Shipment",shipment,product.getProductID(),"+"+shipment.getQuantity());

        product.getUndoStack().push(action);

        product.getShipmentsQueue().enqueue(shipment);

        if(!fromRedo) {
            product.getRedoStack().clear();
            //To add it to history
            Log newLog = product.getLogList().getLast().clone();
            newLog.setRedoStack("");
            newLog.setAction("Add "+shipment.getShipmentID()+" (+"+shipment.getQuantity()+")");
            newLog.setShipmentQueue(newLog.getShipmentQueue()+shipment.getShipmentID()+"->");
            newLog.setUndoStack(newLog.getUndoStack()+"Add "+shipment.getShipmentID()+",");
            product.getLogList().add(newLog);
        }

        if (Integer.parseInt(shipment.getShipmentID().replace("SHP","")) >= shipmentID)
            shipmentID = Integer.parseInt(shipment.getShipmentID().replace("SHP","")) + 1;
    }

    public static void approveShipment(Product product,boolean fromRedo, GregorianCalendar calendar) {

        Shipment shipment = product.getShipmentsQueue().dequeue();

        if(shipment == null)
            throw new AlertException("The shipment queue is empty.");

        Action action = new Action(calendar,"Approve Shipment",shipment,product.getProductID(),"+"+shipment.getQuantity());

        product.getUndoStack().push(action);

        product.getInventoryStockList().insertFirst(1,shipment);

        if(!fromRedo) {
            product.getRedoStack().clear();
            //To add it to history
            Log newLog = product.getLogList().getLast().clone();
            newLog.setRedoStack("");
            newLog.setAction("Approve "+shipment.getShipmentID());
            newLog.setShipmentQueue(newLog.getShipmentQueue().replaceFirst(shipment.getShipmentID()+"->",""));
            newLog.setUndoStack(newLog.getUndoStack()+"Approve "+shipment.getShipmentID()+",");
            newLog.setInventory(newLog.getInventory()+shipment.getShipmentID()+",");
            product.getLogList().add(newLog);
        }

    }

    public static void cancelShipment(Product product,boolean fromRedo, GregorianCalendar calendar){

        Shipment shipment = product.getShipmentsQueue().dequeue();

        if(shipment == null)
            throw new AlertException("The shipment queue is empty.");

        Action action = new Action(calendar,"Cancel Shipment",shipment,product.getProductID(),"-"+shipment.getQuantity());

        product.getUndoStack().push(action);
        product.getCanceledShipments().insertFirst(1,shipment);

        if(!fromRedo) {
            product.getRedoStack().clear();
            //To add it to history
            Log newLog = product.getLogList().getLast().clone();
            newLog.setRedoStack("");
            newLog.setAction("Cancel "+shipment.getShipmentID());
            newLog.setShipmentQueue(newLog.getShipmentQueue().replaceFirst(shipment.getShipmentID()+"->",""));
            newLog.setUndoStack(newLog.getUndoStack()+"Cancel "+shipment.getShipmentID()+",");
            newLog.setCanceled(newLog.getCanceled()+shipment.getShipmentID()+",");
            product.getLogList().add(newLog);
        }

    }

    public static void undo(Product product){

        Action action = product.getUndoStack().pop();
        if(action == null)
            throw new AlertException("The undo stack is empty.");

        Shipment shipment = action.getShipment();
        Log newLog = product.getLogList().getLast().clone();

        if(action.getAction().equals("Approve Shipment")) {
            //To delete it from inventory and return it to queue
            product.getInventoryStockList().delete(1, shipment);
            addFirst(shipment,product.getShipmentsQueue());
            //To add it for history
            newLog.setAction("Undo Approve "+shipment.getShipmentID());
            newLog.setInventory(newLog.getInventory().replaceFirst(shipment.getShipmentID()+",",""));
            newLog.setShipmentQueue(shipment.getShipmentID()+"->"+newLog.getShipmentQueue());
            newLog.setRedoStack(newLog.getRedoStack()+"Approve "+shipment.getShipmentID()+",");
            newLog.setUndoStack(newLog.getUndoStack().replaceFirst("Approve "+shipment.getShipmentID()+",",""));
        } else if(action.getAction().equals("Cancel Shipment")) {
            //To delete it cancel list and return it to queue
            product.getCanceledShipments().delete(1, shipment);
            addFirst(shipment,product.getShipmentsQueue());
            //To add it for history
            newLog.setAction("Undo Cancel "+shipment.getShipmentID());
            newLog.setCanceled(newLog.getCanceled().replaceFirst(shipment.getShipmentID()+",",""));
            newLog.setShipmentQueue(shipment.getShipmentID()+"->"+newLog.getShipmentQueue());
            newLog.setRedoStack(newLog.getRedoStack()+"Cancel "+shipment.getShipmentID()+",");
            newLog.setUndoStack(newLog.getUndoStack().replaceFirst("Cancel "+shipment.getShipmentID()+",",""));
        }else if(action.getAction().equals("Add Shipment")) {
            //To delete it from queue
            deleteLast(product.getShipmentsQueue());
            //To add it for history
            newLog.setAction("Undo Add "+shipment.getShipmentID());
            newLog.setShipmentQueue(newLog.getShipmentQueue().replaceFirst(shipment.getShipmentID()+"->",""));
            newLog.setRedoStack(newLog.getRedoStack()+"Add "+shipment.getShipmentID()+",");
            newLog.setUndoStack(newLog.getUndoStack().replaceFirst("Add "+shipment.getShipmentID()+",",""));
        }else{
            Log.indexVar--;
            throw new AlertException("Wrong Modify code.");
        }

        product.getLogList().add(newLog);
        product.getRedoStack().push(action);

    }

    public static void redo(Product product){

        Action action = product.getRedoStack().pop();

        if(action == null)
            throw new AlertException("The redo stack is empty.");

        Shipment shipment = action.getShipment();

        Log newLog = product.getLogList().getLast().clone();

        if(action.getAction().equals("Approve Shipment")){
            approveShipment(product,true,action.getDate());
            //To add it for history
            newLog.setAction("Redo Approve "+shipment.getShipmentID());
            newLog.setShipmentQueue(newLog.getShipmentQueue().replaceFirst(shipment.getShipmentID()+"->",""));
            newLog.setInventory(newLog.getInventory()+shipment.getShipmentID()+",");
            newLog.setUndoStack(newLog.getUndoStack()+"Approve "+shipment.getShipmentID()+",");
            newLog.setRedoStack(newLog.getRedoStack().replaceFirst("Approve "+shipment.getShipmentID()+",",""));

        } else if(action.getAction().equals("Cancel Shipment")){
            cancelShipment(product,true,action.getDate());
            //To add it for history
            newLog.setAction("Redo Cancel "+shipment.getShipmentID());
            newLog.setShipmentQueue(newLog.getShipmentQueue().replaceFirst(shipment.getShipmentID()+"->",""));
            newLog.setCanceled(newLog.getCanceled()+shipment.getShipmentID()+",");
            newLog.setUndoStack(newLog.getUndoStack()+"Cancel "+shipment.getShipmentID()+",");
            newLog.setRedoStack(newLog.getRedoStack().replaceFirst("Cancel "+shipment.getShipmentID()+",",""));

        } else if(action.getAction().equals("Add Shipment")){
            addShipment(shipment,product,true,shipment.getDate());
            //To add it for history
            newLog.setAction("Redo Add "+shipment.getShipmentID());
            newLog.setShipmentQueue(newLog.getShipmentQueue()+shipment.getShipmentID()+"->");
            newLog.setUndoStack(newLog.getUndoStack()+"Add "+shipment.getShipmentID()+",");
            newLog.setRedoStack(newLog.getRedoStack().replaceFirst("Add "+shipment.getShipmentID()+",",""));
        } else{
            Log.indexVar--;
            throw new AlertException("Wrong Modify code.");
        }
        product.getLogList().add(newLog);
    }

    //view stock on the ShipmentManagementMenu class

    private static boolean exists(String shipmentID) {

        Shipment shipment = new Shipment(shipmentID,"P0",0,new GregorianCalendar());

        for (ProductCategory curr : CategoryManagement.categoriesList) {
            for (Product currProd : curr.getProductList()) {
                //to check the approved list
                Shipment approved = currProd.getInventoryStockList().find(1, shipment);
                Shipment canceled = currProd.getCanceledShipments().find(1, shipment);
                if (approved != null)
                    return true;

                //to check the cancel list
                if (canceled != null)
                    return true;

                if(exists(shipmentID,currProd.getShipmentsQueue()))
                    return true;
            }
        }
        return false;
    }

    public static void addFirst(Shipment data,Queue<Shipment> q1){
        Queue<Shipment> q2 = new Queue<>();

        while(!q1.isEmpty()){
            q2.enqueue(q1.dequeue());
        }

        q1.enqueue(data);
        while (!q2.isEmpty()) {
            q1.enqueue(q2.dequeue());
        }
    }

    public static Shipment deleteLast(Queue<Shipment> q1) {
        if (q1.isEmpty()) {
            return null;
        }

        Queue<Shipment> q2 = new Queue<>();
        Shipment lastElement = null;

        while (!q1.isEmpty()) {
            lastElement = q1.dequeue();
            if (!q1.isEmpty()) {
                q2.enqueue(lastElement);
            }
        }

        while (!q2.isEmpty()) {
            q1.enqueue(q2.dequeue());
        }

        return lastElement;
    }

    public static boolean exists(String shipmentID,Queue<Shipment> q1){

        Queue<Shipment> q2 = new Queue<>();
        boolean exists = false;

        while(!q1.isEmpty()){
            Shipment curr = q1.dequeue();
            q2.enqueue(curr);
            if(curr.getShipmentID().equals(shipmentID))
                exists = true;
        }

        while (!q2.isEmpty()) {
            q1.enqueue(q2.dequeue());
        }
        return exists;
    }
}
