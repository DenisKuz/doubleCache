package model;

interface CacheStorage<K,V> {

    void save(final K key, final V value) throws Exception;

    V retrieve(final K key) throws Exception;

    boolean isFull();

    void clear();

    int getCurrentSize();

    boolean hasFreeMemory();
}
