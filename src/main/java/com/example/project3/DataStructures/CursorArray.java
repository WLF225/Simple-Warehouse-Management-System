package com.example.project3.DataStructures;

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

    public int insertFirst(T data, int L) {
        int i = mAlloc();
        if (i == 0) {
            cA[0].setNext(capacity);
            CursorArray<T> cursorArray = new CursorArray<>(capacity+5);
            for (int j = 0; j < capacity; j++) {
                cursorArray.cA[j] = cA[j];
            }
            capacity = capacity+5;
            cA = cursorArray.cA;
            System.out.println(1);
            return insertFirst(data, L);
        }
        cA[i] = new CNode<>(data, cA[L].getNext());
        cA[L].setNext(i);
        return 0;
    }


    public boolean isNull(int l) {
        return cA[l] == null;
    }

    public boolean isEmpty(int l) {
        return cA[l].getNext() == 0;
    }

    public String listToString(int l) {
        if (isNull(l) || isEmpty(l)) {
            return null;
        }
        String str = "list_" + l + "-->";
        return listToString(str, cA[cA[l].getNext()]);
    }

    public String listToString(String str, CNode<T> c) {
        str += c + "-->";

        if (c.getNext() == 0)
            return str + "null";

        return listToString(str, cA[c.getNext()]);
    }


    public int findPrevious(T data, int l) {
        while (!isNull(l) && !isEmpty(l)) {
            if (cA[cA[l].getNext()].getData().equals(data))
                return l;
            l = cA[l].getNext();
        }
        return -1; // not found
    }

    public CNode<T> delete(T data, int l) {
        int p = findPrevious(data, l);
        if (p != -1) {
            int c = cA[p].getNext();
            CNode<T> temp = cA[c];
            cA[p].setNext(temp.getNext());
            free(c);
        }
        return null;
    }

    public T deleteFirst(int l) {
        if (isEmpty(l)) {
            return null;
        }
        int deletedItem = cA[l].getNext();
        cA[l].setNext(cA[deletedItem].getNext());
        free(deletedItem);
        return cA[deletedItem].getData();
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

    public void clear(int l) {
        if (isEmpty(l))
            return;
        CNode<T> c = cA[l];

        if (deleteFirst(l) != null) {
            clear(l);
        }
    }

    public boolean find(T data, int l) {
        return find(data, l, cA[cA[l].getNext()]);
    }

    public boolean find(T data, int l, CNode<T> c) {
        if (isEmpty(l))
            return false;
        if (c == cA[0])
            return false;
        if (c.getData().compareTo(data) == 0)
            return true;

        return find(data, l, cA[c.getNext()]);
    }

    public boolean equals(int l1, int l2) {
        return equals(l1, l2, cA[l1], cA[l2]);
    }

    public boolean equals(int l1, int l2, CNode<T> c1, CNode<T> c2) {
        if (cA[l1].getNext() == 0 && cA[l2].getNext() == 0)
            return true;
        if (length(l1) != length(l2))
            return false;

        if (c1.getData() == c2.getData() && c1.getNext() == 0)
            return true;
        if (c1.getNext() == 0)
            return false;

        c1 = cA[c1.getNext()];
        c2 = cA[c2.getNext()];

        return equals(l1, l2, c1, c2);

//        CNode<T> c1 = cA[cA[l1].getNext()];
//        CNode<T> c2 = cA[cA[l2].getNext()];
//        boolean cond = true;
//        while (cA[l1].getNext() != 0){
//            if (c1.getData().compareTo(c2.getData())!= 0) {
//                cond = false;
//                break;
//            }
//        }
//        return cond;
    }

    public Iterator<T> iterator() {
        return new Iteratooor(1);
    }

    public class Iteratooor implements Iterator<T> {

        int l;

        public Iteratooor(int l) {
            this.l = l;
        }

        private CNode<T> c = cA[l];

        @Override
        public boolean hasNext() {
            return c.getNext() != 0;
        }

        @Override
        public T next() {
            return cA[c.getNext()].getData();
        }
    }

    public String print(){
        return print("Index\t+Data",0);
    }

    public String print(String str, int i){
//        System.out.println("Index\t+Data");
//        for(int i = 0; i < cA.length; i++){
//            System.out.println(i+"\t"+cA[i]);
//        }
        if (i == capacity)
            return str;
        str += "\n"+i+"\t"+cA[i];
        return print(str,i+1);
    }

}
