package example.FileServer;

import nano.http.d2.console.Console;
import nano.http.d2.console.Logger;
import nano.http.d2.core.NanoHTTPd;
import nano.http.d2.serve.ExampleServer;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        NanoHTTPd server = new NanoHTTPd(80, new ExampleServer());
        Logger.info("File server started on port 80");
        Console.register("stop", () -> {
            server.stop();
            // In fact, System.exit(0) works exactly the same, or even better.
            // This method is designed for you need multiple servers running on the same JVM.
            // It should also be noted that even if you call this method, the JVM will not exit immediately.
            Logger.info("Server stopped");
        });
        Logger.info("Type 'stop' to stop the server");
    }
}
