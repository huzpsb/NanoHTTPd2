package nano.http.d2.core;

import nano.http.d2.serve.ServeProvider;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class NanoHTTPd {
    private final Thread myThread;
    private final ServerSocket myServerSocket;

    public NanoHTTPd(int port, ServeProvider server) throws IOException {
        myServerSocket = new ServerSocket(port);
        myThread = new Thread(() -> {
            try {
                while (true) {
                    Socket s = myServerSocket.accept();
                    new HTTPSession(s, server);
                }
            } catch (IOException ignored) {
            }
        });
        myThread.start();
    }

    public void stop() {
        try {
            myServerSocket.close();
            myThread.join();
        } catch (Exception ignored) {
        }
    }
}
