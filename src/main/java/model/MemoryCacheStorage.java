package model;

import java.util.LinkedHashMap;
import java.util.Map;

public class MemoryCacheStorage<K, V> extends AbstractCacheStorage<K, V> {

    private Map<K, V> data;

    public MemoryCacheStorage() {
        this(DEFAULT_MAX_SIZE);
    }

    public MemoryCacheStorage(int size) {
        super(size);
        data = new LinkedHashMap<K, V>(size);
    }

    @Override
    public void save(K key, V value) throws StorageOverFlowException {
        if (isFull()) {
            throw new StorageOverFlowException("There is no place for new element!");
        }
        data.put(key, value);
    }

    @Override
    public V retrieve(K key) throws NotFoundElementException {
        V element = data.get(key);
        if (element == null) {
            throw new NotFoundElementException("there is no the element in the cache storage");
        }
        return element;
    }

    @Override
    public boolean isFull() {
        return data.size() == getMaxSize();
    }

    @Override
    public void clear() {
        data.clear();
    }

    @Override
    public int getCurrentSize() {
        return data.size();
    }

    @Override
    public boolean hasFreeMemory() {
        return getMaxSize() > getCurrentSize();
    }
}