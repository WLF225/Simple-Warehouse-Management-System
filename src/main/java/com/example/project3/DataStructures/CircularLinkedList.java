package com.example.project3.DataStructures;

import java.util.Iterator;

public class CircularLinkedList<T> implements Iterable<T> {

    private Node<T> head;

    public CircularLinkedList() {

    }

    public void insertFirst(T data) {
        Node<T> newNode = new Node<>(data);
        if (head == null) {
            head = newNode;
            head.setNext(head);
        } else {
            newNode.setNext(head);
            head = newNode;
        }
    }

    public T deleteFirst() {
        if (head == null)
            return null;
        T data = head.getData();
        if (head.getNext() == head) {
            head = null;
            return data;
        }

        Node<T> curr = head;
        while(curr.getNext() != head)
            curr = curr.getNext();

        //To fix the circular
        curr.setNext(head.getNext());
        head = head.getNext();
        return data;
    }

    public T getFirst() {
        if (head == null)
            return null;
        return head.getData();
    }

    public boolean isEmpty() {
        return head == null;
    }

    public void clear() {
        head = null;
    }

    @Override
    public Iterator<T> iterator() {
        return new Iteratorr();
    }

    public class Iteratorr implements Iterator<T> {

        Node<T> curr = head;

        @Override
        public boolean hasNext() {
            return curr.getNext() != head;
        }

        @Override
        public T next() {
            T data = curr.getData();
            curr = curr.getNext();
            return data;
        }
    }
}
