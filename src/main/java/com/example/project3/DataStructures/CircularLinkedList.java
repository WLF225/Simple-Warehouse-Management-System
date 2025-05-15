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
        } else {
            Node<T> curr = head;
            while (curr.getNext() != head) {
                curr = curr.getNext();
            }
            newNode.setNext(head);
            head = newNode;
            curr.setNext(head);
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
}
