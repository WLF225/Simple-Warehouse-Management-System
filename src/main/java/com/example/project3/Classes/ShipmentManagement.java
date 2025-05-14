package com.example.project3.Classes;

public class ShipmentManagement {

    public static int shipmentID = 1;

    public static void addShipment(Shipment shipment, Product product,boolean fromRedo) {

        if (shipmentIDExist(shipment))
            throw new AlertException("The shipment id already exist");

        shipment.pushModify('A');

        product.getUndoStack().push(shipment);

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

    public static void approveShipment(Product product,boolean fromRedo) {

        Shipment shipment = product.getShipmentsQueue().dequeue();

        if(shipment == null)
            throw new AlertException("The shipment queue is empty.");

        shipment.pushModify('P');

        product.getUndoStack().push(shipment);

        product.getInventoryStockList().insertFirst(product.getApprovedList(),shipment);

        if(!fromRedo) {
            product.getRedoStack().clear();
            shipment.clearUndoHistory();
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

    public static void cancelShipment(Product product,boolean fromRedo){
        Shipment shipment = product.getShipmentsQueue().dequeue();
        if(shipment == null)
            throw new AlertException("The shipment queue is empty.");

        shipment.pushModify('C');

        product.getUndoStack().push(shipment);
        product.getCanceledShipments().insertFirst(product.getCancelledList(),shipment);

        if(!fromRedo) {
            product.getRedoStack().clear();
            shipment.clearUndoHistory();
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

        Shipment shipment = product.getUndoStack().pop();
        if(shipment == null)
            throw new AlertException("The undo stack is empty.");

        Log newLog = product.getLogList().getLast().clone();

        char modify = shipment.popModify();

        if(modify == 'P') {
            //To delete it from inventory and return it to queue
            product.getInventoryStockList().delete(product.getApprovedList(), shipment);
            product.getShipmentsQueue().addFirst(shipment);
            //To add it for history
            newLog.setAction("Undo Approve "+shipment.getShipmentID());
            newLog.setInventory(newLog.getInventory().replaceFirst(shipment.getShipmentID()+",",""));
            newLog.setShipmentQueue(shipment.getShipmentID()+"->"+newLog.getShipmentQueue());
            newLog.setRedoStack(newLog.getRedoStack()+"Approve "+shipment.getShipmentID()+",");
            newLog.setUndoStack(newLog.getUndoStack().replaceFirst("Approve "+shipment.getShipmentID()+",",""));
        } else if(modify == 'C') {
            //To delete it cancel list and return it to queue
            product.getCanceledShipments().delete(product.getCancelledList(), shipment);
            product.getShipmentsQueue().addFirst(shipment);
            //To add it for history
            newLog.setAction("Undo Cancel "+shipment.getShipmentID());
            newLog.setCanceled(newLog.getCanceled().replaceFirst(shipment.getShipmentID()+",",""));
            newLog.setShipmentQueue(shipment.getShipmentID()+"->"+newLog.getShipmentQueue());
            newLog.setRedoStack(newLog.getRedoStack()+"Cancel "+shipment.getShipmentID()+",");
            newLog.setUndoStack(newLog.getUndoStack().replaceFirst("Cancel "+shipment.getShipmentID()+",",""));
        }else if(modify == 'A') {
            //To delete it from queue
            product.getShipmentsQueue().deleteLast();
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
        shipment.pushUndoHistory(modify);
        product.getRedoStack().push(shipment);

    }

    public static void redo(Product product){
        Shipment shipment = product.getRedoStack().pop();
        if(shipment == null)
            throw new AlertException("The redo stack is empty.");

        Log newLog = product.getLogList().getLast().clone();

        char modify = shipment.popUndoHistory();

        if(modify == 'P'){
            approveShipment(product,true);
            //To add it for history
            newLog.setAction("Redo Approve "+shipment.getShipmentID());
            newLog.setShipmentQueue(newLog.getShipmentQueue().replaceFirst(shipment.getShipmentID()+"->",""));
            newLog.setInventory(newLog.getInventory()+shipment.getShipmentID()+",");
            newLog.setUndoStack(newLog.getUndoStack()+"Approve "+shipment.getShipmentID()+",");
            newLog.setRedoStack(newLog.getRedoStack().replaceFirst("Approve "+shipment.getShipmentID()+",",""));

        } else if(modify == 'C'){
            cancelShipment(product,true);
            //To add it for history
            newLog.setAction("Redo Cancel "+shipment.getShipmentID());
            newLog.setShipmentQueue(newLog.getShipmentQueue().replaceFirst(shipment.getShipmentID()+"->",""));
            newLog.setCanceled(newLog.getCanceled()+shipment.getShipmentID()+",");
            newLog.setUndoStack(newLog.getUndoStack()+"Cancel "+shipment.getShipmentID()+",");
            newLog.setRedoStack(newLog.getRedoStack().replaceFirst("Cancel "+shipment.getShipmentID()+",",""));

        } else if(modify == 'A'){
            addShipment(shipment,product,true);
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

    private static boolean shipmentIDExist(Shipment shipment) {

        for (ProductCategory curr : CategoryManagement.categoriesList) {
            for (Product currProd : curr.getProductList()) {
                //to check the approved list
                if (currProd.getInventoryStockList().find(currProd.getApprovedList(), shipment))
                    return true;

                //to check the cancel list
                if (currProd.getCanceledShipments().find(currProd.getCancelledList(), shipment))
                    return true;

                //to check the queue
                if (currProd.getShipmentsQueue().exist(shipment))
                    return true;
            }
        }
        return false;
    }
}
