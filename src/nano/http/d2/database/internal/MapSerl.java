package nano.http.d2.database.internal;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@SuppressWarnings("IOStreamConstructor")
public class MapSerl<K, V> {
    public void toFile(String f, Map<K, V> m) throws Exception {
        Set<K> Entities = m.keySet();
        int s = Entities.size();
        Object[] KeyStorage = new Object[s];
        Object[] ValueStorage = new Object[s];
        int i = 0;
        for (K now : Entities) {
            if (i >= s)
                continue;
            KeyStorage[i] = now;
            ValueStorage[i++] = m.get(now);
        }
        SerlItem item = new SerlItem(KeyStorage, ValueStorage);
        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(f));
        oos.writeObject(item);
        oos.close();
    }

    public void toFile(String f, Set<Map.Entry<K, V>> entries) throws Exception {
        int s = entries.size();
        Object[] KeyStorage = new Object[s];
        Object[] ValueStorage = new Object[s];
        int i = 0;
        for (Map.Entry<K, V> now : entries) {
            if (i >= s)
                continue;
            KeyStorage[i] = now.getKey();
            ValueStorage[i++] = now.getValue();
        }
        SerlItem item = new SerlItem(KeyStorage, ValueStorage);
        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(f));
        oos.writeObject(item);
        oos.close();
    }

    @SuppressWarnings("unchecked")
    public ConcurrentHashMap<K, V> fromFile(String f) throws Exception {
        ConcurrentHashMap<K, V> map = new ConcurrentHashMap<>();
        ObjectInputStream ois = new ObjectInputStream(new FileInputStream(f));
        SerlItem item = (SerlItem) ois.readObject();
        for (int i = 0; i < item.key.length; i++) {
            map.put((K) item.key[i], (V) item.value[i]);
        }
        return map;
    }
}
