package service;

import model.CacheStorage;
import strategy.Strategy;

import java.util.HashMap;
import java.util.Map;

public class CacheLevel<K, V> implements CacheService<K, V> {
    private CacheStorage<K, V> cacheStorage;
    private Strategy<K> strategy;

    public CacheLevel(final CacheStorage<K, V> cacheStorage, final Strategy<K> strategy) {
        this.cacheStorage = cacheStorage;
        this.strategy = strategy;
    }

    public CacheStorage<K, V> getCacheStorage() {
        return cacheStorage;
    }

    public Strategy<K> getStrategy() {
        return strategy;
    }

    @Override
    public Map<K, V> put(K key, V value) {
        if (!this.cacheStorage.hasElement(key) && !this.cacheStorage.hasFreeMemory()) {
            final K supplantedKey = this.strategy.takeKeyOfSupplantedElement();
            final V supplantedValue = this.cacheStorage.remove(supplantedKey);
            return new HashMap<K, V>() {{
                put(supplantedKey, supplantedValue);
            }};
        }
        this.cacheStorage.save(key, value);
        this.strategy.upDateRating(key);
        return null;
    }

    @Override
    public V get(K key) {
        final V value = this.cacheStorage.retrieve(key);
        if (value != null) {
            this.strategy.upDateRating(key);
        }
        return value;
    }
}