package com.example.project3.DataStructures;

import javafx.collections.ObservableList;
import java.util.Iterator;

public class CursorArray<T extends Comparable<T>> implements Iterable<T>{

    CNode<T>[] cA;
    int capacity;

    public CursorArray(int capacity) {
        this.capacity = capacity;
        cA = new CNode[capacity];
        for (int i = 0; i < cA.length - 1; i++) {
            cA[i] = new CNode<>(null, i + 1);
        }
        cA[cA.length - 1] = new CNode<>(null, 0);
    }

    public int mAlloc() {
        if (cA[0].getNext() != 0) {
            int i = cA[0].getNext();
            cA[0].setNext(cA[i].getNext());
            return i;
        }
        return 0;
    }

    private void free(int i) {
        cA[i].setNext(cA[0].getNext());
        cA[0].setNext(i);
    }


    public int createList() {
        int i = mAlloc();
        if (i == 0) {
            System.out.println("The list is full");
            return 0;
        }
        cA[i] = new CNode<>(null, 0);
        return i;
    }

    public int insertFirst(int l,T data) {
        int i = mAlloc();
        if (i == 0) {
            cA[0].setNext(capacity);
            CursorArray<T> cursorArray = new CursorArray<>(capacity+5);
            for (int j = 0; j < capacity; j++) {
                cursorArray.cA[j] = cA[j];
            }
            capacity = capacity+5;
            cA = cursorArray.cA;
            return insertFirst(l,data);
        }
        cA[i] = new CNode<>(data, cA[l].getNext());
        cA[l].setNext(i);
        return 0;
    }


    public boolean isNull(int l) {
        return cA[l] == null;
    }

    public boolean isEmpty(int l) {
        return cA[l].getNext() == 0;
    }

    public T find(int l,T data){

        while(l !=0){
            if(isEmpty(l) || isNull(l))
                return null;

            l = cA[l].getNext();
            if(cA[l].getData().equals(data))
                return cA[l].getData();
        }
        return null;
    }

    public int findPrevious(int l,T data) {
        while (!isNull(l) && !isEmpty(l)) {
            if (cA[cA[l].getNext()].getData().equals(data))
                return l;
            l = cA[l].getNext();
        }
        return -1; // not found
    }

    public CNode<T> delete(int l,T data) {
        int p = findPrevious(l,data);
        if (p != -1) {
            int c = cA[p].getNext();
            CNode<T> temp = cA[c];
            cA[p].setNext(temp.getNext());
            free(c);
        }
        return null;
    }

    public int length(int l) {
        return length(l, 0);
    }

    public int length(int l, int length) {
        if (isEmpty(l) && length == 0)
            return 0;

        if (cA[l].getNext() == 0)
            return length;

        return length(cA[l].getNext(), length + 1);
    }


    public Iterator<T> iterator() {
        return new Iteratooor(1);
    }

    public class Iteratooor implements Iterator<T> {
        private int currentIndex;

        public Iteratooor(int l) {
            this.currentIndex = cA[l].getNext();
        }

        @Override
        public boolean hasNext() {
            return currentIndex != 0;
        }

        @Override
        public T next() {
            T data = cA[currentIndex].getData();
            currentIndex = cA[currentIndex].getNext();
            return data;
        }
    }

    public void toObservable(int l,ObservableList<T> list){
        for(T data:this){
            list.add(data);
        }
    }

}
