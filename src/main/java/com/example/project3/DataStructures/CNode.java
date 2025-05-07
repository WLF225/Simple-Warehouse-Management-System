package com.example.project3.DataStructures;


public class CNode<T> {

    private T data;
    private int next = 0;

    public CNode(T data, int next){
        setData(data);
        setNext(next);
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public int getNext() {
        return next;
    }

    public void setNext(int next) {
        this.next = next;
    }

    public String toString() {
        return "["+ data+ " , " + next + "]";
    }

}

