package example.advancedDb;

import nano.http.d2.core.NanoHTTPd;

import java.util.Scanner;

public class DBMain {
    public static void main(String[] args) throws Exception {
        NanoHTTPd server = new NanoHTTPd(80, new DBServer());
        System.out.println("Hello world!");
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            DBServer.udb.save();
            System.out.println("Data saved~!");
        }));
        new Scanner(System.in).nextLine();
        server.stop();
        System.out.println("Bye world!");
        System.exit(0);
    }
}
