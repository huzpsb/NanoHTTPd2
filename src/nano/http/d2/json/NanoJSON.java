package nano.http.d2.json;

import java.lang.reflect.Field;

public class NanoJSON {
    // Warning! NanoJSON can only serialize objects with basic(int,bool,etc.) or basic[] members!
    // Or, if necessary, implement toString method as return NanoJSON.asJSON(this,this.class);
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
                if (now == null) {
                    sb.append("null");
                } else if (now.getClass().isArray()) {
                    sb.append("[");
                    Object[] oa = (Object[]) now;
                    for (Object value : oa) {
                        sb.append(serl(value));
                        sb.append(",");
                    }
                    if (sb.charAt(sb.length() - 1) == ',') {
                        sb.setCharAt(sb.length() - 1, ']');
                    } else {
                        sb.append("]");
                    }
                } else {
                    sb.append(serl(now));
                }
                sb.append(",");
            }
            if (sb.charAt(sb.length() - 1) == ',') {
                sb.setCharAt(sb.length() - 1, '}');
            } else {
                sb.append("}");
            }
            return sb.toString();
        } catch (Exception e) {
            return "{}";
        }
    }

    private static String serl(Object o) {
        if (o == null) {
            return "null";
        } else if (o instanceof String) {
            return "\"" + o + "\"";
        } else {
            return o.toString();
        }
    }
}
