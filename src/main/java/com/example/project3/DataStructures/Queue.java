package com.example.project3.DataStructures;

public class Queue<T> implements QueueInterface<T> {

    private Stack<T> stack1 = new Stack<>();
    private Stack<T> stack2 = new Stack<>();

    @Override
    public void enqueue(T data) {
        stack1.push(data);
    }

    @Override
    public T dequeue() {

        if (isEmpty()) {
            return null;
        }

        if (stack2.isEmpty()) {
            while (!stack1.isEmpty()) {
                stack2.push(stack1.pop());
            }
        }
        return stack2.pop();
    }

    @Override
    public T getFront() {
        if (isEmpty()) {
            return null;
        }

        if (stack2.isEmpty()) {
            while (!stack1.isEmpty()) {
                stack2.push(stack1.pop());
            }
        }
        return stack2.peek();
    }

    @Override
    public boolean isEmpty() {
        return stack1.isEmpty() && stack2.isEmpty();
    }

    public boolean exist(T data){
        return stack1.exist(data) || stack2.exist(data);
    }

    @Override
    public void clear() {
        stack1.clear();
        stack2.clear();
    }

    public void addFirst(T data){
        Queue<T> q2 = new Queue<>();

        while(!isEmpty()){
            q2.enqueue(dequeue());
        }

        enqueue(data);
        while (!q2.isEmpty()) {
            enqueue(q2.dequeue());
        }
    }

    public T deleteLast(){
        if(isEmpty())
            return null;

        if(!stack1.isEmpty())
            return stack1.pop();

        //to delete the bottom of stack2
        Stack<T> temp = new Stack<>();
        while(!stack2.isEmpty())
            temp.push(stack2.pop());

        T data = temp.pop();

        while(!temp.isEmpty())
            stack2.push(temp.pop());

        return data;
    }
}
