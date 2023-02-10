package example.DbTransfer;

import example.Backend.User;
import nano.http.d2.console.Logger;
import nano.http.d2.database.NanoDb;
import nano.http.d2.json.NanoJSON;

public class DownCompatibleMain {
    public static void main(String[] args) throws Exception {
        NanoDb<String, User> udb = new NanoDb<>("students.udb");
        udb.lock();
        for (String now : udb.list()) {
            User user = udb.query(now);
            Logger.info(now + ": " + NanoJSON.asJSON(user));
        }
        NanoDb<String, NewUser> udb2 = new NanoDb<>("students.udb", NewUser.class);
        udb2.lock();
        for (String now : udb2.list()) {
            NewUser user = udb2.query(now);
            Logger.info(now + ": " + NanoJSON.asJSON(user));
        }
        // udb.unlock(); udb2.unlock();
        // We keep them locked, so they won't be saved to disk.
        // This is because the conversion is not revertible.
        // If you use udb2.save() to save the converted data, the age is lost permanently.
    }
}
