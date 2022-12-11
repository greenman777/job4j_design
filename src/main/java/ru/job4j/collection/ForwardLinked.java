package ru.job4j.collection;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;

public class ForwardLinked<T> implements Iterable<T> {
    private int size = 0;
    private int modCount = 0;
    private Node<T> head;

    public void add(T value) {
        ForwardLinked.Node<T> nodeNew = new ForwardLinked.Node<>(value, null);
        if (head == null) {
            head = nodeNew;
        } else {
            ForwardLinked.Node<T> nodeNext = head;
            while (nodeNext.next != null) {
                nodeNext = nodeNext.next;
            }
            nodeNext.next = nodeNew;
        }
        size++;
        modCount++;
    }

    public T get(int index) {
        Objects.checkIndex(index, size);
        ForwardLinked.Node<T> node = head;
        for (int i = 0; i < index; i++) {
            node = node.next;
        }
        return node.item;
    }

    public T deleteFirst() {
        if (head != null) {
            T item = head.item;
            Node<T> headNew = head.next;
            head.next = null;
            head.item = null;
            head = headNew;
            size--;
            modCount++;
            return item;
        } else {
            throw new NoSuchElementException();
        }
    }

    @Override
    public Iterator<T> iterator() {
        return new Iterator<>() {
            private ForwardLinked.Node<T> next = head;
            private final int expectedModCount = modCount;

            @Override
            public boolean hasNext() {
                if (modCount != expectedModCount) {
                    throw new ConcurrentModificationException();
                }
                return next != null;
            }

            @Override
            public T next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                ForwardLinked.Node<T> lastReturned = next;
                next = next.next;
                return lastReturned.item;
            }
        };
    }

    private static class Node<T> {
        private T item;
        private ForwardLinked.Node<T> next;

        Node(T element, ForwardLinked.Node<T> next) {
            this.item = element;
            this.next = next;
        }
    }
}