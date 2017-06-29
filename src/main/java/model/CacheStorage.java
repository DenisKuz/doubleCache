package model;

public interface CacheStorage<K, V> {

    // we do not need null key or null value in cache
    void save(final K key, final V value);

    V retrieve(final K key);

    void clear();

    int getCurrentSize();

    int getMaxSize();

    V remove(final K key);

    default boolean hasFreeMemory() {
        return getMaxSize() > getCurrentSize();
    }
}