package model;


import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FileSystemCacheStorage<K, V> extends AbstractCacheStorage<K, V> {

    private Logger Log = Logger.getLogger(FileSystemCacheStorage.class.getName());

    private static final String CACHE_DIRECTORY_NAME = "cache";

    private Map<K, String> keyMap;

    public FileSystemCacheStorage(final int maxSize) {
        super(maxSize);
        keyMap = new HashMap<K, String>(maxSize);
        createDirectory();
    }

    private void createDirectory() {
        File directory = new File(CACHE_DIRECTORY_NAME);
        if (!directory.exists()) {
            if (directory.mkdir()) {
                Log.info("Cache directory was created");
            } else {
                Log.log(Level.SEVERE, "Failed to create cache directory!");
            }
        }
    }

    @Override
    public void save(Object key, Object value) throws Exception {

    }

    @Override
    public Object retrieve(Object key) throws Exception {
        return null;
    }

    @Override
    public boolean isFull() {
        return false;
    }

    @Override
    public void clear() {

    }

    @Override
    public int getCurrentSize() {
        return 0;
    }

    @Override
    public boolean hasFreeMemory() {
        return false;
    }
}
