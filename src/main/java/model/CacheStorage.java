package model;

public interface CacheStorage<K, V> {

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