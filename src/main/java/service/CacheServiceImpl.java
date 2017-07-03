package service;

import model.FileSystemCacheStorage;
import model.MemoryCacheStorage;
import strategy.LRUStrategy;
import strategy.Strategy;

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

    public CacheServiceImpl(final Strategy<K> firstLevelStrategy, final Strategy<K> secondLevelStrategy) {
        this.firstLevel = new CacheStrategy<>(new MemoryCacheStorage<>(), firstLevelStrategy);
        this.secondLevel = new CacheStrategy<>(new FileSystemCacheStorage<>(), secondLevelStrategy);
    }

    @Override
    public void put(K key, V value) {

        if (this.firstLevel.getCacheStorage().hasElement(key)) {
            this.firstLevel.getCacheStorage().save(key, value);
        }
        else if (this.secondLevel.getCacheStorage().hasElement(key)) {
            this.secondLevel.getCacheStorage().save(key, value);
        }
        else {
            if (!this.firstLevel.getCacheStorage().hasFreeMemory()) {

                final K keyOfDeletedElement = this.firstLevel.getStrategy().kickExtraElement();
                final V valueOfDeletedValue = this.firstLevel.getCacheStorage().remove(keyOfDeletedElement);

                this.secondLevel.getCacheStorage().save(keyOfDeletedElement, valueOfDeletedValue);
                this.secondLevel.getStrategy().upDateRating(key);
            }

            this.firstLevel.getCacheStorage().save(key, value);
            this.firstLevel.getStrategy().upDateRating(key);
        }

        /**/
    }

    @Override
    public V get(K key) {
        V value = this.firstLevel.getCacheStorage().retrieve(key);
        this.firstLevel.getStrategy().upDateRating(key);
        if (value == null) {
            value = this.secondLevel.getCacheStorage().retrieve(key);
            this.secondLevel.getStrategy().upDateRating(key);
        }
        return value;
    }
}