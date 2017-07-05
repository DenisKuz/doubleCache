package strategy;

import java.util.*;
import java.util.Map.Entry;

public class LRUStrategy<K> implements Strategy<K> {

    private Map<K, Date> ratingMap;

    public LRUStrategy() {
        this.ratingMap = new HashMap<>();
    }

    @Override
    public K takeKeyOfSupplantedElement() {
        final K key = getEarliest();
        this.ratingMap.remove(key);
        return key;
    }

    @Override
    public void upDateRating(final K key) {
        this.ratingMap.put(key, new Date());
    }

    private K getEarliest() {
        //there is problem with date. Dates have been created at the same time!
        // as the result we have null key and value
        Date date = new Date();
        K key = null;
        for (final Entry<K, Date> entry : this.ratingMap.entrySet()) {
            if (date.compareTo(entry.getValue()) >= 0) {
                date = entry.getValue();
                key = entry.getKey();
            }
        }
        return key;
    }
}
