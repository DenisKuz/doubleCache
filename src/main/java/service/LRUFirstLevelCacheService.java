package service;

import model.CacheStorage;
import model.Element;
import model.MemoryCacheStorage;
import strategy.LRUStrategy;
import strategy.Strategy;


public class LRUFirstLevelCacheService<K, V> implements CacheService<K, V> {

    private CacheStorage<K, Element<K, V>> memoryStorage;
    private Strategy<K> strategy;

    public LRUFirstLevelCacheService(final int maxSize, final Strategy<K> strategy) {
        this.memoryStorage = new MemoryCacheStorage<>(maxSize);
        this.strategy = strategy;
    }

    // by default we use least recently used
    public LRUFirstLevelCacheService() {
        this.memoryStorage = new MemoryCacheStorage<>();
        this.strategy = new LRUStrategy<>();
    }

    @Override
    public void put(K key, V value) {
        if (!memoryStorage.hasFreeMemory()) {
            this.memoryStorage.remove(this.strategy.kickExtraElement());
        }
        memoryStorage.save(key, new Element<>(key, value));
    }

    @Override
    public V get(final K key) {
        final Element<K, V> element = this.memoryStorage.retrieve(key);
        this.strategy.upDateRating(key);
        return element.getValue();
    }
}
