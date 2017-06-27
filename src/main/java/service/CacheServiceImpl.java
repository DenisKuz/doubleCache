package service;

import model.FileSystemCacheStorage;
import model.MemoryCacheStorage;
import strategy.LRUStrategy;

public class CacheServiceImpl<K, V> implements CacheService<K, V> {

    private CacheStrategy<K, V> firstLevel;
    private CacheStrategy<K, V> secondLevel;

    public CacheServiceImpl(final CacheStrategy<K, V> firstLevel, final CacheStrategy<K, V> secondLevel) {
        this.firstLevel = firstLevel;
        this.secondLevel = secondLevel;
    }

    public CacheServiceImpl() {
        this.firstLevel = new CacheStrategy<>(new MemoryCacheStorage<>(), new LRUStrategy<>());
        this.secondLevel = new CacheStrategy<>(new FileSystemCacheStorage<>(), new LRUStrategy<>());
    }

    @Override
    public void put(K key, V value) {
        if (!this.firstLevel.getCacheStorage().hasFreeMemory()) {
            final V deletedValue = this.firstLevel.getCacheStorage().remove(this.firstLevel.getStrategy().kickExtraElement());
            this.secondLevel.getCacheStorage().save(key, value);
        }
        this.firstLevel.getCacheStorage().save(key, value);
    }

    @Override
    public V get(K key) {
        V value = this.firstLevel.getCacheStorage().retrieve(key);
        if (value == null) {
            value = this.secondLevel.getCacheStorage().retrieve(key);
        }
        return value;
    }
}