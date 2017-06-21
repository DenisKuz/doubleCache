package model;

public interface CacheStorage<K,V> {

    void save(final K key, final V value);

    V retrieve(final K key) throws NotFoundElementException;

    boolean isFull();

    void clear();

    int getCurrentSize();

    boolean hasFreeMemory();

    V remove(final K key);

}