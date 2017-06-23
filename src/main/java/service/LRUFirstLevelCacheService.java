package service;

import model.CacheStorage;
import model.Element;
import model.MemoryCacheStorage;

import java.util.Date;
import java.util.Map;

import static model.MemoryCacheStorage.DEFAULT_MEMORY_MAX_SIZE;


public class LRUFirstLevelCacheService<K, V> implements CacheService<K, V> {

    private CacheStorage<K, Element<K, V, Date>> memoryStorage;

    public LRUFirstLevelCacheService(final int maxSize) {
        this.memoryStorage = new MemoryCacheStorage<>(maxSize);
    }

    public LRUFirstLevelCacheService() {
        this.memoryStorage = new MemoryCacheStorage<>();
    }

    @Override
    public void put(K key, V value) {
        if (!memoryStorage.hasFreeMemory()) {
            deleteUnwantedElement();
        }
        memoryStorage.save(key, new Element<>(key, value, new Date()));
    }

    @Override
    public V get(final K key) {
        final Element<K, V, Date> element = this.memoryStorage.retrieve(key);
        element.setRating(new Date());
        return element.getValue();
    }

    private Element<K, V, Date> deleteUnwantedElement() {
        Element<K, V, Date> element = new Element<>(null, null, new Date());


        for (Map.Entry<K, ?> entry : memoryStorage.getDataSet().entrySet()) {
            if (element.getRating().compareTo(((Element<K, V, Date>)entry.getValue()).getRating()) > 0) {
                element = (Element<K, V, Date>)entry.getValue();
            }
        }
        return memoryStorage.remove(element.getKey());
    }
}
