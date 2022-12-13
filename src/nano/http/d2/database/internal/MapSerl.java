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
        Object[] KeyStorage = new Object[m.size()];
        Object[] ValueStorage = new Object[m.size()];
        int i = 0;
        for (K now : Entities) {
            KeyStorage[i] = now;
            ValueStorage[i++] = m.get(now);
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
        ois.close();
        return map;
    }
}
