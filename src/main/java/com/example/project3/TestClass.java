package com.example.project3;

import com.example.project3.Classes.CategoryManagement;
import com.example.project3.Classes.Product;
import com.example.project3.Classes.ProductCategory;
import com.example.project3.DataStructures.CursorArray;
import com.example.project3.DataStructures.Queue;

import java.util.ListIterator;

public class TestClass {

    public static void main(String[] args) {

        CursorArray<Integer> cursorArray = new CursorArray<>(4);

        int l = cursorArray.createList();

        for (int i = 0; i < 7; i++){
            cursorArray.insertFirst(i,l);
        }


        System.out.println(cursorArray.print());

    }
}
