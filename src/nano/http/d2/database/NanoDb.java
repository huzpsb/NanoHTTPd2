package nano.http.d2.database;

import nano.http.d2.database.csv.Localizer;
import nano.http.d2.database.csv.Reflectior;
import nano.http.d2.database.internal.MapSerl;
import nano.http.d2.utils.Misc;

import java.io.File;
import java.io.StreamCorruptedException;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicBoolean;

@SuppressWarnings("unused")
public class NanoDb<K, V> {
    private final long save;
    private final int buffer;
    private final MapSerl<K, V> provider = new MapSerl<>();
    private final ConcurrentHashMap<K, V> data;
    private final String file;
    private final AtomicBoolean locked = new AtomicBoolean(false);
    private long lastSave = System.currentTimeMillis();
    private int operations = 0;

    public NanoDb(String datafile) throws Exception {
        this(datafile, 60000L, 10);
    }

    public NanoDb(String datafile, long saveInterval, int bufferSize) throws Exception {
        save = saveInterval;
        buffer = bufferSize;
        file = datafile;
        if (new File(file + "_").exists()) {
            throw new StreamCorruptedException("A unsaved backup is found!");
        }
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

    public void remove(K key) {
        scheduleSave();
        data.remove(key);
    }

    public void set(K key, V value) {
        scheduleSave();
        data.put(key, value);
    }

    public int size() {
        return this.data.size();
    }

    public Set<K> list() {
        return this.data.keySet();
    }

    public String toCSV(Class<?> CBase, String indexName, Localizer l) {
        StringBuilder sb = new StringBuilder();
        Reflectior<V> ref = new Reflectior<>(CBase);
        Set<K> index = this.list();
        sb.append(Misc.BOM);
        sb.append("\"").append(indexName).append("\",");
        sb.append(ref.title(l));
        for (K now : index) {
            sb.append("\"").append(now.toString()).append("\",");
            sb.append(ref.serl(this.query(now)));
        }
        return sb.toString();
    }

    private void scheduleSave() {
        while (locked.get()) {
            try {
                //noinspection BusyWait
                Thread.sleep(1L);
            } catch (Exception ignored) {

            }
        }
        operations++;
        if (operations >= buffer && System.currentTimeMillis() - lastSave >= save) {
            save();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    public void save() {
        if (!locked.get()) {
            locked.set(true);
            try {
                lastSave = System.currentTimeMillis();
                operations = 0;
                provider.toFile(file + "_", data);
                File original = new File(file);
                File temp = new File(file + "_");
                while (!original.delete()) ;
                while (!temp.renameTo(original)) ;
            } catch (Exception e) {
                e.printStackTrace();
            }
            locked.set(false);
        }
    }
}
