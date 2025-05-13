package com.example.project3.Classes;

public class ShipmentManagement {

    public static void addShipment(Shipment shipment, Product product,boolean fromRedo) {

        if (shipmentIDExist(shipment))
            throw new AlertException("The shipment id already exist");

        shipment.setModify('A');

        product.getUndoStack().push(shipment);

        product.getShipmentsQueue().enqueue(shipment);



        if(!fromRedo) {
            product.getRedoStack().clear();
            //To add it to history
            Log newLog = product.getLogList().getLast().clone();
            newLog.setRedoStack("");
            newLog.setAction("Add "+shipment.getProductID()+" (+"+shipment.getQuantity()+")");
            newLog.setShipmentQueue(newLog.getShipmentQueue()+shipment.getProductID()+"->");
            newLog.setUndoStack(newLog.getUndoStack()+"Add "+shipment.getProductID()+",");
            product.getLogList().add(newLog);
        }
    }

    public static void approveShipment(Product product,boolean fromRedo) {
        Shipment shipment = product.getShipmentsQueue().dequeue();
        if(shipment == null)
            throw new AlertException("The shipment queue is empty.");

        shipment.setModify('P');

        product.getUndoStack().push(shipment);
        product.getInventoryStockList().insertFirst(product.getApprovedList(),shipment);



        if(!fromRedo) {
            product.getRedoStack().clear();
            //To add it to history
            Log newLog = product.getLogList().getLast().clone();
            newLog.setRedoStack("");
            newLog.setAction("Approve "+shipment.getProductID());
            newLog.setShipmentQueue(newLog.getShipmentQueue().replaceFirst(shipment.getProductID()+"->",""));
            newLog.setUndoStack(newLog.getUndoStack()+"Approve "+shipment.getProductID()+",");
            newLog.setInventory(newLog.getInventory()+shipment.getProductID()+",");
            product.getLogList().add(newLog);
        }

    }

    public static void cancelShipment(Product product,boolean fromRedo){
        Shipment shipment = product.getShipmentsQueue().dequeue();
        if(shipment == null)
            throw new AlertException("The shipment queue is empty.");

        shipment.setModify('C');

        product.getUndoStack().push(shipment);
        product.getCanceledShipments().insertFirst(product.getCancelledList(),shipment);



        if(!fromRedo) {
            product.getRedoStack().clear();
            //To add it to history
            Log newLog = product.getLogList().getLast().clone();
            newLog.setRedoStack("");
            newLog.setAction("Cancel "+shipment.getProductID());
            newLog.setShipmentQueue(newLog.getShipmentQueue().replaceFirst(shipment.getProductID()+"->",""));
            newLog.setUndoStack(newLog.getUndoStack()+"Cancel "+shipment.getProductID()+",");
            newLog.setCanceled(newLog.getCanceled()+shipment.getProductID()+",");
            newLog.setCanceled(newLog.getCanceled()+shipment.getProductID()+",");
            product.getLogList().add(newLog);
        }

    }

    public static void undo(Product product){

        Shipment shipment = product.getUndoStack().pop();
        if(shipment == null)
            throw new AlertException("The undo stack is empty.");

        Log newLog = product.getLogList().getLast().clone();

        if(shipment.getModify() == 'P') {
            //To delete it from inventory and return it to queue
            product.getInventoryStockList().delete(product.getApprovedList(), shipment);
            product.getShipmentsQueue().addFirst(shipment);
            //To add it for history
            newLog.setAction("Undo Approve "+shipment.getProductID());
            newLog.setInventory(newLog.getInventory().replaceFirst(shipment.getShipmentID()+",",""));
            newLog.setShipmentQueue(newLog.getShipmentQueue()+shipment.getProductID()+"->");
            newLog.setRedoStack(newLog.getRedoStack()+"Approve "+shipment.getProductID()+",");
            newLog.setUndoStack(newLog.getUndoStack().replaceFirst("Approve "+shipment.getProductID()+",",""));
        } else if(shipment.getModify() == 'C') {
            //To delete it cancel list and return it to queue
            product.getCanceledShipments().delete(product.getCancelledList(), shipment);
            product.getShipmentsQueue().addFirst(shipment);
            //To add it for history
            newLog.setAction("Undo Cancel "+shipment.getProductID());
            newLog.setCanceled(newLog.getCanceled().replaceFirst(shipment.getShipmentID()+",",""));
            newLog.setShipmentQueue(newLog.getShipmentQueue()+shipment.getProductID()+"->");
            newLog.setRedoStack(newLog.getRedoStack()+"Cancel "+shipment.getProductID()+",");
            newLog.setUndoStack(newLog.getUndoStack().replaceFirst("Cancel "+shipment.getProductID()+",",""));
        }else if(shipment.getModify() == 'A') {
            //To delete it from queue
            product.getShipmentsQueue().deleteLast();
            //To add it for history
            newLog.setAction("Undo Add "+shipment.getProductID());
            newLog.setShipmentQueue(newLog.getShipmentQueue().replaceFirst(shipment.getProductID()+"->",""));
            newLog.setRedoStack(newLog.getRedoStack()+"Add "+shipment.getProductID()+",");
            newLog.setUndoStack(newLog.getUndoStack().replaceFirst("Add "+shipment.getProductID()+",",""));
        }else{
            Log.indexVar--;
            throw new AlertException("Wrong Modify code.");
        }

        product.getLogList().add(newLog);
        product.getRedoStack().push(shipment);

    }

    public static void redo(Product product){
        Shipment shipment = product.getRedoStack().pop();
        if(shipment == null)
            throw new AlertException("The redo stack is empty.");

        Log newLog = product.getLogList().getLast().clone();

        if(shipment.getModify() == 'P'){
            approveShipment(product,true);
            //To add it for history
            newLog.setAction("Redo Approve "+shipment.getProductID());
            newLog.setShipmentQueue(newLog.getShipmentQueue().replaceFirst(shipment.getProductID()+"->",""));
            newLog.setInventory(newLog.getInventory()+shipment.getProductID()+",");
            newLog.setUndoStack(newLog.getUndoStack()+"Approve "+shipment.getProductID()+",");
            newLog.setRedoStack(newLog.getRedoStack().replaceFirst("Approve "+shipment.getProductID()+",",""));

        } else if(shipment.getModify() == 'C'){
            cancelShipment(product,true);
            //To add it for history
            newLog.setAction("Redo Cancel "+shipment.getProductID());
            newLog.setShipmentQueue(newLog.getShipmentQueue().replaceFirst(shipment.getProductID()+"->",""));
            newLog.setCanceled(newLog.getCanceled()+shipment.getProductID()+",");
            newLog.setUndoStack(newLog.getUndoStack()+"Cancel "+shipment.getProductID()+",");
            newLog.setRedoStack(newLog.getRedoStack().replaceFirst("Cancel "+shipment.getProductID()+",",""));

        } else if(shipment.getModify() == 'A'){
            addShipment(shipment,product,true);
            //To add it for history
            newLog.setAction("Redo Add "+shipment.getProductID());
            newLog.setShipmentQueue(newLog.getShipmentQueue()+shipment.getProductID()+"->");
            newLog.setUndoStack(newLog.getUndoStack()+"Add "+shipment.getProductID()+",");
            newLog.setRedoStack(newLog.getRedoStack().replaceFirst("Add "+shipment.getProductID()+",",""));
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
