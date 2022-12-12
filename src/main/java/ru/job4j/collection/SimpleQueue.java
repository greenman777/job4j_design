package ru.job4j.collection;

import java.util.NoSuchElementException;

public class SimpleQueue<T> {
    private final SimpleStack<T> in = new SimpleStack<>();
    private int inSize;
    private final SimpleStack<T> out = new SimpleStack<>();
    private int outSize;

    public T poll() {
        if (inSize <= 0 && outSize <= 0) {
            throw new NoSuchElementException();
        }
        if (outSize <= 0) {
            while (inSize > 0) {
                T elem = in.pop();
                inSize--;
                out.push(elem);
                outSize++;
            }
        }
        outSize--;
        return out.pop();
    }

    public void push(T value) {
        in.push(value);
        inSize++;
    }
}
