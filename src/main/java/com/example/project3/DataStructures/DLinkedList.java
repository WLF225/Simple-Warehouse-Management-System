package com.example.project3.DataStructures;


import javafx.collections.ObservableList;

import java.util.ListIterator;

public class DLinkedList<T extends Comparable<T>> implements Iterable<T> {

    private DNode<T> head;

    public DLinkedList() {
        head = new DNode<>(null);
        head.setNext(head);
        head.setPrev(head);
    }

    public void insetSorted(T data) {
        DNode<T> newNode = new DNode<>(data);
        if (head.getNext() == head) {
            newNode.setNext(head);
            newNode.setPrev(head);
            head.setNext(newNode);
            head.setPrev(newNode);
            return;
        }
        DNode<T> curr = head.getNext();
        while (curr != head) {
            if (curr.compareTo(newNode) > 0)
                break;
            curr = curr.getNext();
        }
        if (curr == head) {
            newNode.setNext(head);
            newNode.setPrev(head.getPrev());
            head.getPrev().setNext(newNode);
            head.setPrev(newNode);
            return;
        }
        newNode.setPrev(curr.getPrev());
        newNode.setNext(curr);
        curr.getPrev().setNext(newNode);
        curr.setPrev(newNode);

    }

    public int size() {
        int size = 0;
        for (T t:this)
            size++;
        return size;
    }

    //This implementation is only for sorted list
    public void removeDuplicates() {
        DNode<T> curr = head.getNext();
        while (curr.getNext() != head) {
            if (curr.getNext().getData().compareTo(curr.getData()) == 0) {
                curr.getNext().getNext().setPrev(curr);
                curr.setNext(curr.getNext().getNext());
                continue;
            }
            curr = curr.getNext();
        }
    }

    public DNode<T> delete(T data) {
        DNode<T> curr = head.getNext();
        while (curr != head && data.compareTo(curr.getData()) >= 0) {
            if (curr.getData().equals(data)) {
                break;
            }
            curr = curr.getNext();
        }
        if (curr == head) {
            return null;
        }
        curr.getNext().setPrev(curr.getPrev());
        curr.getPrev().setNext(curr.getNext());
        return curr;
    }

    public void clear() {
        head.setNext(head);
        head.setPrev(head);
    }

    @Override
    public String toString() {

        String s = "";
        DNode<T> node = head.getNext();
        while (true) {
            if (node == head)
                break;
            s += node.toString();
            node = node.getNext();

        }
        return "Head->" + s + "Head";
    }

    public boolean isEmpty() {
        return head.getNext() == head;
    }


    public ListIterator<T> iterator() {
        return new Iterator();
    }

    public void addToObservableList(ObservableList<T> list) {
        for (T item : this) {
            list.add(item);
        }
    }

    //I used this instead of the normal iterator because it have prev not only next
    public class Iterator implements ListIterator<T> {
        private DNode<T> curr = head;

        @Override
        public boolean hasNext() {
            return curr.getNext() != head;
        }

        @Override
        public T next() {
            T t = curr.getNext().getData();
            curr = curr.getNext();
            return t;
        }

        @Override
        public boolean hasPrevious() {
            return curr.getPrev() != head;
        }

        @Override
        public T previous() {
            T t = curr.getPrev().getData();
            curr = curr.getPrev();
            return t;
        }

        @Override
        public int nextIndex() {
            return 0;
        }

        @Override
        public int previousIndex() {
            return 0;
        }

        @Override
        public void remove() {

        }

        @Override
        public void set(T t) {

        }

        @Override
        public void add(T t) {

        }
    }
}
