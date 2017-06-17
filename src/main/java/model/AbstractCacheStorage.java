package model;

abstract class AbstractCacheStorage<K, V> implements CacheStorage<K, V> {
    private int maxSize;
    static final int DEFAULT_MAX_SIZE = 5;

    AbstractCacheStorage() {
        maxSize = DEFAULT_MAX_SIZE;
    }

    AbstractCacheStorage(int maxSize) {
        this.maxSize = maxSize;
    }

    public int getMaxSize() {
        return maxSize;
    }
}
