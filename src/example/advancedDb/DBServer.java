package example.advancedDb;

import nano.http.d2.core.Response;
import nano.http.d2.database.NanoDb;
import nano.http.d2.json.NanoJSON;
import nano.http.d2.serve.ServeProvider;
import nano.http.d2.utils.Mime;
import nano.http.d2.utils.Status;

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
                    System.out.println(uri);
                    return new Response(Status.HTTP_NOTFOUND, Mime.MIME_PLAINTEXT, "Not found");
            }
        } catch (Exception e) {
            return new Response(Status.HTTP_INTERNALERROR, Mime.MIME_PLAINTEXT, e.getMessage());
        }
    }
}
