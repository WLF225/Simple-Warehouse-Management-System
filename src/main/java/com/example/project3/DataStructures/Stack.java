package com.example.project3.DataStructures;

public class Stack<T> implements StackInterface<T> {

    private CircularLinkedList<T> list = new CircularLinkedList<>();

    @Override
    public void push(T data) {
        list.insertFirst(data);
    }

    @Override
    public T pop() {
        return list.deleteFirst();
    }

    @Override
    public T peek() {
        return list.getFirst();
    }

    @Override
    public boolean isEmpty() {
        return list.isEmpty();
    }

    @Override
    public void clear() {
        list.clear();
    }
}
