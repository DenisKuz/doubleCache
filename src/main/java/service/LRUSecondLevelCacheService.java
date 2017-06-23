package service;

import model.CacheStorage;
import model.Element;
import model.FileSystemCacheStorage;
import model.FileSystemCacheStorage.Node;

import java.util.Date;
import java.util.Map;

import static model.FileSystemCacheStorage.DEFAULT_fILE_STORAGE_MAX_SIZE;

public class LRUSecondLevelCacheService<K, V> implements CacheService<K, V> {

    private CacheStorage<K, Element<K, V, Date>> fileSystemStorage;

    public LRUSecondLevelCacheService(final int maxSize) {
        this.fileSystemStorage = new FileSystemCacheStorage<>(maxSize);
    }

    public LRUSecondLevelCacheService() {
        this.fileSystemStorage = new FileSystemCacheStorage<>();
    }

    @Override
    public void put(K key, V value) {
        if (!fileSystemStorage.hasFreeMemory()) {
            deleteUnwantedElement();
        }
        fileSystemStorage.save(key, new Element<>(key, value, new Date()));
    }


    private Element<K, V, Date> deleteUnwantedElement() {
        Element<K, V, Date> element = new Element<>(null, null, new Date());

        for (Map.Entry<K, ?> entry : fileSystemStorage.getDataSet().entrySet()) {
            if (element.getRating().compareTo(((Node)entry.getValue()).getRating()) > 0) {
                element = entry.getValue();
            }
        }
        return fileSystemStorage.remove(element.getKey());
    }

    @Override
    public V get(K key) {
        return null;
    }
}