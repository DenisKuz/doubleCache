package model;

import java.io.Serializable;

public class Element<K,V,R> implements Serializable {
    private K key;
    private V value;
    private R rating;

    public Element(K key, V value, R rating) {
        this.key = key;
        this.value = value;
        this.rating = rating;
    }

    public K getKey() {
        return key;
    }

    public V getValue() {
        return value;
    }

    public R getRating() {
        return rating;
    }

    public void setRating(R rating) {
        this.rating = rating;
    }
}
