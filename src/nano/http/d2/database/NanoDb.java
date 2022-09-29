package nano.http.d2.database;

import nano.http.d2.database.internal.MapSerl;

import java.io.File;
import java.util.concurrent.ConcurrentHashMap;

public class NanoDb<K, V> {
    private final long save;
    private final int buffer;
    private final MapSerl<K, V> provider = new MapSerl<>();
    private final ConcurrentHashMap<K, V> data;
    private final String file;
    private long lastSave = System.currentTimeMillis();
    private int operations = 0;
    private boolean locked = false;

    public NanoDb(String datafile) throws Exception {
        this(datafile, 60000L, 10);
    }

    public NanoDb(String datafile, long saveInterval, int bufferSize) throws Exception {
        save = saveInterval;
        buffer = bufferSize;
        file = datafile;
        if (!new File(file).exists()) {
            data = new ConcurrentHashMap<>();
        } else {
            data = provider.fromFile(datafile);
        }
    }

    public boolean contains(K key) {
        return data.containsKey(key);
    }

    public V query(K key) {
        return data.get(key);
    }

    public void set(K key, V value) {
        operations++;
        if (operations >= buffer || System.currentTimeMillis() - lastSave >= save) {
            save();
        }
        data.put(key, value);
    }

    public void save() {
        if (!locked) {
            locked = true;
            try {
                provider.toFile(file, data);
                lastSave = System.currentTimeMillis();
                operations = 0;
            } catch (Exception e) {
                e.printStackTrace();
            }
            locked = false;
        }
    }
}
