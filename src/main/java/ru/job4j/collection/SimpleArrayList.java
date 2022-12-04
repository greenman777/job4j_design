package ru.job4j.collection;

import java.util.*;

public class SimpleArrayList<T> implements SimpleList<T> {

    private T[] container;

    private int size;

    private int modCount;

    private static final int DEFAULT_CAPACITY = 10;

    public SimpleArrayList(int capacity) {
        this.container = (T[]) new Object[capacity];
    }

    private Object[] grow() {
        int minCapacity = size + 1;
        int oldCapacity = container.length;
        if (oldCapacity > 0) {
            return Arrays.copyOf(container, container.length * 2);
        } else {
            return new Object[Math.max(DEFAULT_CAPACITY, minCapacity)];
        }
    }

    @Override
    public void add(T value) {
        if (size == container.length) {
            container = (T[]) grow();
        }
        container[size++] = value;
        modCount++;
    }

    @Override
    public T set(int index, T newValue) {
        Objects.checkIndex(index, container.length);
        T oldValue = container[index];
        container[index] = newValue;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        Objects.checkIndex(index, container.length);
        T oldValue = container[index];
        if ((size - 1) > index) {
            System.arraycopy(container, index + 1, container, index, size - 1 - index);
        }
        container[size - 1] = null;
        size--;
        modCount++;
        return oldValue;
    }

    @Override
    public T get(int index) {
        Objects.checkIndex(index, size);
        return container[index];
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public Iterator<T> iterator() {
        return new Iterator<T>() {
            int index;
            final int expectedModCount = modCount;
            @Override
            public boolean hasNext() {
                if (modCount != expectedModCount) {
                    throw new ConcurrentModificationException();
                }
                return index != size;
            }

            @Override
            public T next() {
                if (modCount != expectedModCount) {
                    throw new ConcurrentModificationException();
                }
                if (index >= size) {
                    throw new NoSuchElementException();
                }
                return (T) container[index++];
            }

        };
    }
}