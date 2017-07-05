package service;

import model.FileSystemCacheStorage;
import model.MemoryCacheStorage;
import strategy.LRUStrategy;
import strategy.Strategy;

import java.util.Map;

public class CacheServiceImpl<K, V> implements CacheService<K, V> {

    private CacheLevel<K, V> firstLevel;
    private CacheLevel<K, V> secondLevel;

    public CacheServiceImpl(final CacheLevel<K, V> firstLevel, final CacheLevel<K, V> secondLevel) {
        this.firstLevel = firstLevel;
        this.secondLevel = secondLevel;
    }

    public CacheServiceImpl() {
        this.firstLevel = new CacheLevel<>(new MemoryCacheStorage<>(), new LRUStrategy<>());
        this.secondLevel = new CacheLevel<>(new FileSystemCacheStorage<>(), new LRUStrategy<>());
    }

    public CacheServiceImpl(final Strategy<K> firstLevelStrategy, final Strategy<K> secondLevelStrategy) {
        this.firstLevel = new CacheLevel<>(new MemoryCacheStorage<>(), firstLevelStrategy);
        this.secondLevel = new CacheLevel<>(new FileSystemCacheStorage<>(), secondLevelStrategy);
    }

    @Override
    public Map<K, V> put(K key, V value) {
        final Map<K, V> returnedMap = this.firstLevel.put(key, value);
        if (returnedMap != null) {
            final K returnedKey = (K) returnedMap.keySet().toArray()[0];
            final V returnedValue = returnedMap.get(returnedKey);
            return this.secondLevel.put(returnedKey, returnedValue);
        }
        return null;
    }

    @Override
    public V get(K key) {
        V value = this.firstLevel.get(key);
        if (value == null) {
            value = secondLevel.get(key);
        }
        return value;
    }
}