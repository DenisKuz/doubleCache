package service;

import model.CacheStorage;
import model.Element;
import model.FileSystemCacheStorage;
import strategy.LRUStrategy;
import strategy.Strategy;

public class LRUSecondLevelCacheService<K, V> implements CacheService<K, V> {

    private CacheStorage<K, Element<K, V>> fileSystemStorage;
    private Strategy<K> strategy;

    public LRUSecondLevelCacheService(final int maxSize, final Strategy<K> strategy) {
        this.fileSystemStorage = new FileSystemCacheStorage<>(maxSize);
        this.strategy = strategy;
    }

    // by default we use least recently used
    public LRUSecondLevelCacheService() {
        this.fileSystemStorage = new FileSystemCacheStorage<>();
        this.strategy = new LRUStrategy<>();
    }

    @Override
    public void put(K key, V value) {
        if (!fileSystemStorage.hasFreeMemory()) {
            this.fileSystemStorage.remove(this.strategy.kickExtraElement());
        }
        fileSystemStorage.save(key, new Element<>(key, value));
    }

    @Override
    public V get(K key) {
        final Element<K, V> element = this.fileSystemStorage.retrieve(key);
        this.strategy.upDateRating(key);
        return element.getValue();
    }
}