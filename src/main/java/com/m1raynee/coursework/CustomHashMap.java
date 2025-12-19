package com.m1raynee.coursework;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class CustomHashMap<V> implements Iterable<MapEntry<V>> {

    @SuppressWarnings({ "rawtypes", "unchecked" })
    private static final MapEntry DELETED_MARKER = new MapEntry(null, null);

    private MapEntry<V>[] table;
    private int size;
    private int capacity;
    private double loadFactorThreshold;
    private final ProbeStrategy probeStrategy;

    private long totalProbes;
    private boolean usePrimes;

    @SuppressWarnings("unchecked")
    public CustomHashMap(ProbeStrategy strategy, int initialCapacity, Boolean usePrimes) {
        if (initialCapacity <= 0) {
            initialCapacity = 16;
        }

        this.probeStrategy = strategy;
        this.usePrimes = (usePrimes != null) ? usePrimes : false;

        this.capacity = CapacityUtils.getOptimalCapacity(initialCapacity, this.usePrimes);

        this.table = (MapEntry<V>[]) new MapEntry[capacity];
        this.size = 0;
        this.totalProbes = 0;

        this.loadFactorThreshold = 0.75;
    }

    private int hash(Integer key) {
        return Math.abs(key) % capacity;
    }

    private int getIndex(Integer key, int i) {
        int initialHash = hash(key);
        int probeOffset = probeStrategy.probe(i);

        if (usePrimes) {
            return (initialHash + probeOffset) % capacity;
        } else {
            return (initialHash + probeOffset) & (capacity - 1);
        }
    }

    public void put(Integer key, V value) {
        if (key == null)
            return;

        if ((double) size / capacity >= loadFactorThreshold) {
            resize();
        }

        int i = 0;
        int firstDeletedIndex = -1;
        int index;

        while (true) {
            index = getIndex(key, i);
            MapEntry<V> entry = table[index];

            if (entry != null && entry != DELETED_MARKER) { // ACTIVE
                if (entry.key.equals(key)) {
                    entry.value = value;
                    return;
                }
            } else if (entry == DELETED_MARKER) { // DELETED
                if (firstDeletedIndex == -1) {
                    firstDeletedIndex = index;
                }
            } else { // entry == null (EMPTY)
                break;
            }

            i++;
            totalProbes++;
            if (i >= capacity) {
                System.err.println("Ошибка: Хэш-таблица переполнена/зациклилась при put.");
                return;
            }
        }

        if (firstDeletedIndex != -1)
            index = firstDeletedIndex;

        table[index] = new MapEntry<>(key, value);
        size++;
    }

    public V get(Integer key) {
        if (key == null)
            return null;

        int i = 0;
        int index;

        while (true) {
            index = getIndex(key, i);
            MapEntry<V> entry = table[index];

            if (entry != null && entry != DELETED_MARKER) { // ACTIVE
                if (entry.key.equals(key)) {
                    return entry.value;
                }
            } else if (entry == null) { // EMPTY
                return null;
            }

            i++;
            totalProbes++;
            if (i >= capacity) {
                return null;
            }
        }
    }

    @SuppressWarnings("unchecked")
    public boolean remove(Integer key) {
        if (key == null)
            return false;

        int i = 0;
        int index;

        while (true) {
            index = getIndex(key, i);
            MapEntry<V> entry = table[index];

            if (entry != null && entry != DELETED_MARKER) { // ACTIVE
                if (entry.key.equals(key)) {
                    table[index] = DELETED_MARKER;
                    size--;
                    return true;
                }
            } else if (entry == null) // EMPTY
                return false;

            i++;
            totalProbes++;
            if (i >= capacity) {
                return false;
            }
        }
    }

    public boolean containsKey(Integer key) {
        return get(key) != null;
    }

    public int getSize() {
        return size;
    }

    public int getCapacity() {
        return capacity;
    }

    @SuppressWarnings("unchecked")
    private void resize() {
        int oldCapacity = capacity;
        MapEntry<V>[] oldTable = table;

        if (usePrimes) {
            capacity = CapacityUtils.getNextPrime(capacity * 2);
        } else {
            capacity *= 2;
        }

        table = (MapEntry<V>[]) new MapEntry[capacity];
        size = 0;

        for (int j = 0; j < oldCapacity; j++) {
            MapEntry<V> entry = oldTable[j];
            if (entry != null && entry != DELETED_MARKER) {
                putInternal(entry.key, entry.value);
            }
        }

        // System.out.printf("Таблица изменена: %d -> %d. Новый size: %d\n",
        // oldCapacity, capacity, size);
    }

    private void putInternal(Integer key, V value) {
        int i = 0;
        int index;

        while (true) {
            index = getIndex(key, i);
            MapEntry<V> entry = table[index];

            if (entry != null && entry != DELETED_MARKER) { // ACTIVE
                if (entry.key.equals(key)) {
                    entry.value = value;
                    return;
                }
            } else if (entry == DELETED_MARKER) { // DELETED
            } else { // entry == null (EMPTY)
                table[index] = new MapEntry<>(key, value);
                size++;
                return;
            }

            i++;
            if (i >= capacity) {
                System.err.println("Ошибка: Хэш-таблица переполнена/зациклилась при putInternal.");
                return;
            }
        }
    }

    @Override
    public Iterator<MapEntry<V>> iterator() {
        return new CustomHashMapIterator();
    }

    /**
     * Внутренний класс для поддержки итерации.
     */
    private class CustomHashMapIterator implements Iterator<MapEntry<V>> {
        private int currentIndex = 0;
        private int elementsReturned = 0;

        @Override
        public boolean hasNext() {
            return elementsReturned < size;
        }

        @Override
        public MapEntry<V> next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }

            while (currentIndex < capacity) {
                MapEntry<V> entry = table[currentIndex];
                if (entry != null && entry != DELETED_MARKER) { // ACTIVE
                    currentIndex++;
                    elementsReturned++;
                    return entry;
                }
                currentIndex++;
            }

            throw new NoSuchElementException();
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException(
                    "Удаление через итератор не поддерживается. Используйте remove(key).");
        }
    }

    public long getTotalProbes() {
        return totalProbes;
    }

    public void resetProbes() {
        this.totalProbes = 0;
    }
}