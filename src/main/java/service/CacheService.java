package service;


public interface CacheService<K, V> {

    void put(final K key, final V value);

    V get(final K key);
}
