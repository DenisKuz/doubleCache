package model;

interface CacheStorage<K,V> {

    void save(final K key, final V value) throws StorageOverFlowException;

    V retrieve(final K key) throws NotFoundElementException;

    boolean isFull();

    void clear();

    int getCurrentSize();

    boolean hasFreeMemory();
}