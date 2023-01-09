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
        int h = key == null ? 0 : key.hashCode();
        int hash = hash(h);
        if (count >= LOAD_FACTOR * capacity) {
            expand();
        }
        int i = indexFor(hash);
        if (table[i] == null) {
            table[i] = new MapEntry<>(key, value);
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

    private void expand() {
        capacity *= 2;
        MapEntry<K, V>[] tableNew = new MapEntry[capacity];
        for (MapEntry<K, V> node : table) {
            if (node == null) {
                continue;
            }
            int hashCodeKey = node.key == null ? 0 : node.key.hashCode();
            int hashKey = hash(hashCodeKey);
            int index = indexFor(hashKey);
            tableNew[index] = new MapEntry<>(node.key, node.value);
        }
        table = tableNew;
    }

    @Override
    public V get(K key) {
        V result = null;
        MapEntry<K, V> node;
        int hashCodeKey = key == null ? 0 : key.hashCode();
        int hashKey = hash(hashCodeKey);
        int index = indexFor(hashKey);
        node = table[index];
        if (node != null) {
            int hashCodeNode = node.key == null ? 0 : node.key.hashCode();
            int hashNode = hash(hashCodeNode);
            if (hashNode == hashKey && Objects.equals(node.key, key)) {
                result = node.value;
            }
        }
        return result;
    }

    @Override
    public boolean remove(K key) {
        boolean result = false;
        MapEntry<K, V> node;
        int hashCodeKey = key == null ? 0 : key.hashCode();
        int hashKey = hash(hashCodeKey);
        int index = indexFor(hashKey);
        node = table[index];
        if (node != null) {
            int hashCodeNode = node.key == null ? 0 : node.key.hashCode();
            int hashNode = hash(hashCodeNode);
            if (hashNode == hashKey && Objects.equals(node.key, key)) {
                table[index] = null;
                result = true;
                count--;
                modCount++;
            }
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
                while (index != capacity && table[index] == null) {
                    index++;
                }
                if (modCount != expectedModCount) {
                    throw new ConcurrentModificationException();
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