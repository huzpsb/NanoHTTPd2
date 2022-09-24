package example;

import nano.http.d2.core.NanoHTTPd;
import nano.http.d2.serve.ExampleServer;

import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException {
        NanoHTTPd server = new NanoHTTPd(80, new ExampleServer());
        System.out.println("Hello world!");
        new Scanner(System.in).nextLine();
        server.stop();
        System.out.println("Bye world!");
        new Scanner(System.in).nextLine();
    }
}
