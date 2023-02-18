package example.Backend;

import nano.http.d2.console.Logger;
import nano.http.d2.consts.Mime;
import nano.http.d2.consts.Status;
import nano.http.d2.core.Response;
import nano.http.d2.database.NanoDb;
import nano.http.d2.json.NanoJSON;
import nano.http.d2.serve.ServeProvider;

import java.util.Properties;

public class DBServer implements ServeProvider {
    public static final NanoDb<String, User> udb;

    static {
        try {
            udb = new NanoDb<>("students.udb");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Response serve(String uri, String method, Properties header, Properties parms, Properties files) {
        try {
            switch (uri) {
                case "/create":
                    User u = new User();
                    u.age = Integer.parseInt(parms.getProperty("age"));
                    u.home = parms.getProperty("home");
                    udb.set(parms.getProperty("name"), u);
                    return new Response(Status.HTTP_OK, Mime.MIME_PLAINTEXT, "Success");
                case "/delete":
                    udb.remove(parms.getProperty("name"));
                    return new Response(Status.HTTP_OK, Mime.MIME_PLAINTEXT, "Success");
                case "/download":
                    Response r = new Response(Status.HTTP_OK, Mime.MIME_DEFAULT_BINARY, udb.toCSV(User.class, "姓名", Locates.getLocalizer()));
                    r.addHeader("Content-Disposition", "attachment; filename=\"download.csv\"");
                    return r;
                case "/query":
                    return new Response(Status.HTTP_OK, Mime.MIME_JSON, NanoJSON.asJSON(udb.query(parms.getProperty("name")), User.class));
                default:
                    Logger.warning(uri);
                    // Please read the notes in ExampleServer.java (package: nano.http.d2.serve)
                    // You may want to use FileServer.serveFile() here!
                    return new Response(Status.HTTP_NOTFOUND, Mime.MIME_PLAINTEXT, "Not found");
            }
        } catch (Exception e) {
            return new Response(Status.HTTP_INTERNALERROR, Mime.MIME_PLAINTEXT, "Mal-formatted request!");
        }
    }
}
