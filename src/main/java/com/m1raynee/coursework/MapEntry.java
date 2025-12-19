package com.m1raynee.coursework;

/**
 * Класс для хранения пары ключ-значение.
 */
public class MapEntry<V> {
    public final Integer key;
    public V value;

    public MapEntry(Integer key, V value) {
        this.key = key;
        this.value = value;
    }

    @Override
    public String toString() {
        return String.format("(%d, %s)", key, value);
    }
}