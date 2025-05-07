package com.example.project3.DataStructures;

public class CircularLinkedList<T> {

    private Node<T> head;

    public CircularLinkedList() {

    }

    public void insertFirst(T data) {
        Node<T> newNode = new Node<>(data);
        if (head == null) {
            head = newNode;
            head.setNext(head);
        }else {
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

}
