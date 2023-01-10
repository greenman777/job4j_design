package ru.job4j.map;

import java.util.*;

public class SimpleMap<K, V> implements Map<K, V> {

    private static final float LOAD_FACTOR = 0.75f;
    private int capacity = 8;
    private int count = 0;
    private int modCount = 0;
    private MapEntry<K, V>[] table = new MapEntry[capacity];

    @Override
    public boolean put(K key, V value) {
        boolean result = false;
        if (count >= LOAD_FACTOR * capacity) {
            expand();
        }
        int index = indexForKey(key);
        if (Objects.isNull(table[index])) {
            table[index] = new MapEntry<>(key, value);
            result = true;
            count++;
            modCount++;
        }
        return result;
    }

    private int hash(int hashCode) {
        return hashCode ^ (hashCode >>> 16);
    }

    private int indexFor(int hash) {
        return (capacity - 1) & hash;
    }

    private int indexForKey(K key) {
        int hashCodeKey = Objects.hashCode(key);
        int hashKey = hash(hashCodeKey);
        return indexFor(hashKey);
    }

    private void expand() {
        capacity *= 2;
        MapEntry<K, V>[] tableNew = new MapEntry[capacity];
        for (MapEntry<K, V> node : table) {
            if (Objects.nonNull(node)) {
                tableNew[indexForKey(node.key)] = node;
            }
        }
        table = tableNew;
    }

    private MapEntry<K, V> getNode(K key) {
        MapEntry<K, V> node = null;
        int hashCodeKey = Objects.hashCode(key);
        int hashKey = hash(hashCodeKey);
        int index = indexFor(hashKey);
        node = table[index];
        if (Objects.nonNull(node)) {
            int hashCodeNode = Objects.hashCode(node.key);
            int hashNode = hash(hashCodeNode);
            if (hashNode != hashKey || !Objects.equals(node.key, key)) {
                node = null;
            }
        }
        return node;
    }

    @Override
    public V get(K key) {
        V result = null;
        MapEntry<K, V> node;
        node = getNode(key);
        if (Objects.nonNull(node)) {
            result = node.value;
        }
        return result;
    }

    @Override
    public boolean remove(K key) {
        boolean result = false;
        MapEntry<K, V> node;
        node = getNode(key);
        if (Objects.nonNull(node)) {
            table[indexForKey(key)] = null;
            result = true;
            count--;
            modCount++;
        }
        return result;
    }

    @Override
    public Iterator<K> iterator() {
        return new Iterator<>() {
            int index;
            final int expectedModCount = modCount;

            @Override
            public boolean hasNext() {
                if (modCount != expectedModCount) {
                    throw new ConcurrentModificationException();
                }
                while (index != capacity && table[index] == null) {
                    index++;
                }
                return index != capacity;
            }

            @Override
            public K next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                return table[index++].key;
            }
        };
    }

    private static class MapEntry<K, V> {
        K key;
        V value;

        public MapEntry(K key, V value) {
            this.key = key;
            this.value = value;
        }
    }
}