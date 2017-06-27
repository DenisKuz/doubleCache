package strategy;

import java.util.*;
import java.util.Map.Entry;

public class LRUStrategy<K> implements Strategy<K> {

    private Map<K, Date> ratingMap;

    public LRUStrategy() {
        this.ratingMap = new HashMap<>();
    }

    @Override
    public K kickExtraElement() {
        final K key = getEarliest();
        this.ratingMap.remove(key);
        return key;
    }

    public void upDateRating(final K key) {
        this.ratingMap.put(key, new Date());
    }

    private K getEarliest() {
        Date date = new Date();
        K key = null;
        for (final Entry<K, Date> entry : this.ratingMap.entrySet()) {
            if (date.compareTo(entry.getValue()) > 0) {
                date = entry.getValue();
                key = entry.getKey();
            }
        }
        return key;
    }
}
