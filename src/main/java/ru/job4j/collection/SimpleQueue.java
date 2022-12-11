package ru.job4j.collection;

import java.util.NoSuchElementException;

public class SimpleQueue<T> {
    private final SimpleStack<T> in = new SimpleStack<>();
    private final SimpleStack<T> out = new SimpleStack<>();

    public T poll() {
        T result;
        try {
            result = out.pop();
        } catch (NoSuchElementException e1) {
            T elem = in.pop();
            while (true) {
                out.push(elem);
                try {
                    elem = in.pop();
                } catch (NoSuchElementException e2) {
                    break;
                }
            }
            result = out.pop();
        }
        return result;
    }

    public void push(T value) {
        in.push(value);
    }
}
