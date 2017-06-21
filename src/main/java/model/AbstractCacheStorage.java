package model;

abstract class AbstractCacheStorage<K, V> implements CacheStorage<K, V> {

    private int maxSize;

    AbstractCacheStorage(int maxSize) {
        this.maxSize = maxSize;
    }

    public int getMaxSize() {
        return maxSize;
    }
}
