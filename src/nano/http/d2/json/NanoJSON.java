package nano.http.d2.json;

import java.lang.reflect.Array;
import java.lang.reflect.Field;

public class NanoJSON {
    public static String asJSON(Object o, Class<?> clazz) {
        try {
            Field[] allFields = clazz.getDeclaredFields();
            StringBuilder sb = new StringBuilder();
            sb.append("{");
            for (Field f : allFields) {
                if (f.getName().equals("serialVersionUID")) {
                    continue;
                }
                f.setAccessible(true);
                Object now = f.get(o);
                sb.append("\"").append(f.getName()).append("\":");
                if (now instanceof Array) {
                    sb.append("[");
                    int length = Array.getLength(o);
                    for (int i = 0; i < length; i++) {
                        sb.append(serl(Array.get(o, i)));
                        sb.append(",");
                    }
                    sb.append("]");
                } else {
                    sb.append(serl(now));
                }
                sb.append(",");
            }
            sb.append("}");
            return sb.toString();
        } catch (Exception e) {
            return "{}";
        }
    }

    private static String serl(Object o) {
        if (o instanceof String) {
            return "\"" + (String) o + "\"";
        } else {
            return o.toString();
        }
    }
}
