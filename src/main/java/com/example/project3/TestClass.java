package com.example.project3;

import com.example.project3.Classes.CategoryManagement;
import com.example.project3.Classes.Product;
import com.example.project3.Classes.ProductCategory;
import com.example.project3.Classes.ShipmentManagement;
import com.example.project3.DataStructures.CursorArray;
import com.example.project3.DataStructures.Queue;

import java.util.ListIterator;

public class TestClass {

    public static void main(String[] args) {


        Product prod = new Product("P1","test","cat",'A');

        ShipmentManagement.approveShipment(prod);

        Queue<Integer> queue = new Queue<>();

        for (int i = 0; i < 10; i++) {
            queue.enqueue(i);
        }


        queue.addFirst(11);

        for (int i = 0; i < 11; i++) {
            System.out.println(queue.dequeue());
        }

    }
}
