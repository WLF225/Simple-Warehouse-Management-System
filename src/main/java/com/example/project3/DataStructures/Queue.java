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

    @Override
    public void clear() {
        stack1.clear();
        stack2.clear();
    }
}
