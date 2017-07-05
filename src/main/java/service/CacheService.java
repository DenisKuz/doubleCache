package service;


import java.util.Map;

public interface CacheService<K, V> {

    // returns some element witch was supplanted from cache.
    Map<K, V> put(final K key, final V value);

    V get(final K key);
}
