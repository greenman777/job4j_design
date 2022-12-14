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
        Node<T> nodeNew = new Node<>(value, null);
        if (head == null) {
            head = nodeNew;
        } else {
            Node<T> nodeNext = head;
            while (nodeNext.next != null) {
                nodeNext = nodeNext.next;
            }
            nodeNext.next = nodeNew;
        }
        size++;
        modCount++;
    }

    public boolean revert() {
        boolean result = true;
        if (size <= 1) {
            result = false;
        } else {
            Node<T> nodePrev = null;
            Node<T> nodeCurrent = head;
            while (nodeCurrent != null) {
                Node<T> nodeNext = nodeCurrent.next;
                nodeCurrent.next = nodePrev;
                nodePrev = nodeCurrent;
                nodeCurrent = nodeNext;
            }
            head = nodePrev;
        }
        return result;
    }

    public void addFirst(T value) {
        head = new Node<>(value, head);
        size++;
        modCount++;
    }

    public T get(int index) {
        Objects.checkIndex(index, size);
        Node<T> node = head;
        for (int i = 0; i < index; i++) {
            node = node.next;
        }
        return node.item;
    }

    public T deleteFirst() {
        if (head == null) {
            throw new NoSuchElementException();
        }
        T item = head.item;
        Node<T> headNew = head.next;
        head.next = null;
        head.item = null;
        head = headNew;
        size--;
        modCount++;
        return item;
    }

    @Override
    public Iterator<T> iterator() {
        return new Iterator<>() {
            private Node<T> next = head;
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
                Node<T> lastReturned = next;
                next = next.next;
                return lastReturned.item;
            }
        };
    }

    private static class Node<T> {
        private T item;
        private Node<T> next;

        Node(T element, Node<T> next) {
            this.item = element;
            this.next = next;
        }
    }
}