package service;

import model.CacheStorage;
import strategy.Strategy;

public class CacheStrategy<K,V> {
    private CacheStorage<K,V> cacheStorage;
    private Strategy<K> strategy;

    public CacheStrategy(final CacheStorage<K,V> cacheStorage, final Strategy<K> strategy){
        this.cacheStorage = cacheStorage;
        this.strategy = strategy;
    }

    public CacheStorage<K, V> getCacheStorage() {
        return cacheStorage;
    }

    public Strategy<K> getStrategy() {
        return strategy;
    }
}
