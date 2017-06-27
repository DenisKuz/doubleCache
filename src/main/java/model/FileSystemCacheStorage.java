package model;

import java.io.*;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import static org.apache.commons.io.FileUtils.cleanDirectory;

public class FileSystemCacheStorage<K, V> extends AbstractCacheStorage<K, V> {

    private Logger log = Logger.getLogger(FileSystemCacheStorage.class.getName());

    private Map<K, String> fileNameMap;

    private final File directory;

    static final public int DEFAULT_fILE_STORAGE_MAX_SIZE = 6;

    public FileSystemCacheStorage() {
        this(DEFAULT_fILE_STORAGE_MAX_SIZE);
    }

    public FileSystemCacheStorage(final int maxSize) {
        super(maxSize);
        this.fileNameMap = new HashMap<>(maxSize);
        this.directory = new File("cache" + new Date().getTime());
        createDirectory();
    }

    @Override
    public void save(K key, V value) {
        if (hasFreeMemory()) {
            this.fileNameMap.put(key, key.toString() + new Date().getTime());
            writeToFile(value, createFile(key));
        }
    }

    @Override
    public V retrieve(final K key) {
        return readFromFile(fileNameMap.get(key));
    }

    @Override
    public void clear() {
        try {
            cleanDirectory(directory);
        } catch (IOException e) {
            log.log(Level.SEVERE, "Can not clear directory");
        }
    }

    @Override
    public int getCurrentSize() {
        return this.fileNameMap.size();
    }

    @Override
    public V remove(K key) {
        return null;
    }

    private void createDirectory() {
        if (!this.directory.exists()) {
            if (this.directory.mkdir()) {
                log.info("Cache directory was created");
            } else {
                log.log(Level.SEVERE, "Failed to create cache directory!");
            }
        }
    }

    private File createFile(final K key) {
        final String fileName = fileNameMap.get(key);
        final File file = new File(directory.getName().concat("/").concat(fileName));
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                log.log(Level.SEVERE, "Could not create file with name " + file.getName());
                return null;
            }
        }
        return file;
    }

    private void writeToFile(final V value, final File file) {
        if (file != null) {
            try (FileOutputStream fileInputStream = new FileOutputStream(file);
                 ObjectOutputStream objectInputStream = new ObjectOutputStream(fileInputStream)) {
                objectInputStream.writeObject(value);
            } catch (IOException ex) {
                log.log(Level.SEVERE, "Problem with writing object to file");
            }
        }
    }

    private V readFromFile(final String fileName) {
        File file = new File(directory.getName() + "/" + fileName);
        try (FileInputStream fileInputStream = new FileInputStream(file);
             ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream)) {
            return (V) objectInputStream.readObject();
        } catch (Exception ex) {
            log.log(Level.SEVERE, "Problem with reading from file");
        }
        return null;
    }
}