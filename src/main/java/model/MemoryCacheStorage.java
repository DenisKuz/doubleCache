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
        //data = new LinkedHashMap<>(size);
        data = new HashMap<>(size);
    }

    @Override
    public void save(K key, V value) {
        if (!isFull()) {
            //throw new StorageOverFlowException("There is no place for new element!");
            this.data.put(key, value);
        }
    }

    @Override
    public V retrieve(K key) {
        /*if (element == null) {
            //throw new NotFoundElementException("there is no the element in the cache storage");
        }*/
        return data.get(key);
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

    @Override
    public V remove(final K key) {
       return this.data.remove(key);
    }

    public Map<K,V> getData(){
        return this.data;
    }
}