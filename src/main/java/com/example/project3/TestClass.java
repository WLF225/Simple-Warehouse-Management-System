package com.example.project3;

import com.example.project3.Classes.*;
import com.example.project3.DataStructures.CircularLinkedList;
import com.example.project3.DataStructures.CursorArray;
import com.example.project3.DataStructures.Queue;
import javafx.collections.ObservableList;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.ListIterator;

public class TestClass {

    public static void main(String[] args) {


        CircularLinkedList<Integer> list = new CircularLinkedList<>();

        for(int i = 0; i < 20;i++)
            list.insertFirst(i);

        for(int num:list)
            System.out.println(num);


//        Product prod = new Product("P1","test","cat",'A');
//        ProductManagement.addProduct(prod);
//
//
//        ObservableList<Log> list = prod.getLogList();
//
//        ShipmentManagement.addShipment(new Shipment("1","P1",15, new GregorianCalendar()),prod,false);
//
//        for (Log log : list){
//            System.out.println(log);
//        }
//
//        ShipmentManagement.addShipment(new Shipment("2","P1",10, new GregorianCalendar()),prod,false);
//        ShipmentManagement.addShipment(new Shipment("3","P1",12, new GregorianCalendar()),prod,false);
//
//        for (Log log : list){
//            System.out.println(log);
//        }
//
//        Calendar cal = new GregorianCalendar();
//
//        cal.set(Calendar.HOUR,8);
//        cal.set(Calendar.MINUTE,0);
//        System.out.println(cal.get(Calendar.HOUR)+":"+cal.get(Calendar.MINUTE));
//
//
//        ShipmentManagement.approveShipment(prod,false);
//
//        ShipmentManagement.cancelShipment(prod,false);
//        ShipmentManagement.undo(prod);
//        ShipmentManagement.redo(prod);
//
//        for (Log log : list){
//            System.out.println(log);
//        }


    }
}
