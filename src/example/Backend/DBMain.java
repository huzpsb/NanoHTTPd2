package example.Backend;

import nano.http.d2.console.Console;
import nano.http.d2.console.Logger;
import nano.http.d2.core.NanoHTTPd;

public class DBMain {
    public static void main(String[] args) throws Exception {
        new NanoHTTPd(80, new DBServer());
        Logger.info("API Server started on port 80");
        Console.register("stop", () -> {
            DBServer.udb.save();
            // Do save the data before exit.
            // Or you will lose recent changes.

            Logger.info("Data saved ~ !");
            System.exit(0);
            // Compared against FileServer, we chose to exit the program directly here.
            // And it's strongly recommended to do alike this, as the program will exit immediately.
        });
    }
}
