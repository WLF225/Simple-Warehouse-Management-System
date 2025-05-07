package com.example.project3.DataStructures;


public class DNode<T extends Comparable<T>> implements Comparable<DNode<T>> {

    private T data;
    private DNode<T> next;
    private DNode<T> prev;

    DNode(T data) {
        setData(data);
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public DNode<T> getNext() {
        return next;
    }

    public void setNext(DNode<T> next) {
        this.next = next;
    }

    public DNode<T> getPrev() {
        return prev;
    }

    public void setPrev(DNode<T> prev) {
        this.prev = prev;
    }
    public String toString() {
        return "["+data+"]->";
    }

    @Override
    public int compareTo(DNode<T> node) {
        return data.compareTo(node.getData());
    }
}
