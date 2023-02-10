package example.DbTransfer;

import example.Backend.User;
import nano.http.d2.console.Logger;
import nano.http.d2.database.NanoDb;
import nano.http.d2.database.Transfer;
import nano.http.d2.json.NanoJSON;

@Deprecated
public class TransferMain {
    public static void main(String[] args) throws Exception {
        NanoDb<String, User> udb = new NanoDb<>("students.udb");
        NanoDb<String, NewUser> udb2 = new NanoDb<>("students2.udb");
        Logger.info("size " + udb.size());
        for (String now : udb.list()) {
            udb2.set(now, (NewUser) Transfer.copy(udb.query(now), new NewUser()));
        }
        Logger.info(NanoJSON.asJSON(udb2.query("John"), NewUser.class));
        udb2.save();
    }
}
