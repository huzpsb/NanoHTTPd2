package nano.http.d2.console;

import java.util.Scanner;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicBoolean;

public class Console {
    private static final AtomicBoolean awaiting = new AtomicBoolean(false);
    private static final ConcurrentHashMap<String, Runnable> commands = new ConcurrentHashMap<>();
    private static String result;

    static {
        Thread t = new Thread(() -> {
            Scanner s = new Scanner(System.in);
            while (true) {
                handle(s.nextLine());
            }
        });
        t.setDaemon(true);
        t.start();

    }

    private static void handle(String s) {
        if (awaiting.get()) {
            result = s;
            synchronized (awaiting) {
                awaiting.notify();
            }
            return;
        }
        if (commands.containsKey(s)) {
            new Thread(commands.get(s)).start();
        } else {
            Logger.warning("Unknown command: " + s);
        }
    }

    public static String await() {
        if (awaiting.get()) {
            throw new RuntimeException("Already awaiting!");
        }
        awaiting.set(true);
        try {
            synchronized (awaiting) {
                awaiting.wait();
            }
        } catch (InterruptedException e) {
            return null;
        }
        awaiting.set(false);
        return result;
    }

    public static void register(String command, Runnable r) {
        commands.put(command, r);
    }
}
