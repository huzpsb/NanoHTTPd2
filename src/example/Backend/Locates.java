package example.Backend;

import nano.http.d2.database.csv.Localizer;

public class Locates {
    private static Localizer l = null;

    public static Localizer getLocalizer() {
        if (l == null) {
            l = new Localizer();
            l.add("age", "年龄");
            l.add("home", "家庭住址");
        }
        return l;
    }
}
