package com.kosa.libaraySystem.util;

public class TupleKNY<K, V> {
    private K key;
    private V value;

    public TupleKNY(K key, V value) {
        this.key = key;
        this.value = value;
    }

    public K getKey() {
        return key;
    }

    public V getValue() {
        return value;
    }
}