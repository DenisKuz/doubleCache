package model;

import java.util.HashMap;
import java.util.Map;

public class MemoryCacheStorage<K, V> extends AbstractCacheStorage<K, V> {

    private Map<K, V> data;

    static public final int DEFAULT_MEMORY_MAX_SIZE = 3;

    public MemoryCacheStorage() {
        this(DEFAULT_MEMORY_MAX_SIZE);
    }

    public MemoryCacheStorage(int size) {
        super(size);
        data = new HashMap<>(size);
    }

    @Override
    public void save(K key, V value) {
        if ((key != null && value != null) && hasFreeMemory()) {
            this.data.put(key, value);
        }
    }

    @Override
    public V retrieve(K key) {
        return data.get(key);
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
    public V remove(final K key) {
        return this.data.remove(key);
    }
}