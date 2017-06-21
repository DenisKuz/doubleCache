package service;

import model.Element;
import model.MemoryCacheStorage;

import java.util.Date;
import java.util.Map;

import static model.MemoryCacheStorage.DEFAULT_MEMORY_MAX_SIZE;


public class LRUFirstLevelCacheService<K, V> implements CacheService<K, V> {

    private MemoryCacheStorage<K, Element<K, V, Date>> memory;

    public LRUFirstLevelCacheService(final int maxSize) {
        this.memory = new MemoryCacheStorage<>(maxSize);
    }


    public LRUFirstLevelCacheService() {
        this(DEFAULT_MEMORY_MAX_SIZE);
    }

    @Override
    public void put(K key, V value) {

        if(!memory.hasFreeMemory()){
            deleteElement();
        }
        memory.save(key, new Element<>(key, value, new Date()));

        /*final Element<K, V, Date> element = new Element<>(key, value, new Date());
        try {
            memory.save(key, element);
        } catch (StorageOverFlowException e) {

            deleteLRUElement();
            memory.save(key, element);
            e.printStackTrace();
        }*/
    }

    private Element<K, V, Date> deleteElement() {
        Element<K, V, Date> element = new Element<>(null, null, new Date());

        for (Map.Entry<K, Element<K, V, Date>> entry : memory.getData().entrySet()) {
            if (element.getRating().compareTo(entry.getValue().getRating()) > 0) {
                element = entry.getValue();
            }
        }

        return memory.remove(element.getKey());
    }

    @Override
    public V get(final K key) {
        final Element<K,V,Date> element = this.memory.retrieve(key);
        element.setRating(new Date());
        return element.getValue();
    }

    private Map<K,Element<K,V,Date>> getMap(){
        return this.memory.getData();
    }

    public static void main(String...str){
        final LRUFirstLevelCacheService<Integer, String> cache = new LRUFirstLevelCacheService<>();
        cache.put(1,"fuck");
        cache.put(2,"fuck2");
        cache.put(3,"fuck3");
      //  cache.get(1);
        cache.put(4, "fuck4");
        cache.getMap();
    }
}
