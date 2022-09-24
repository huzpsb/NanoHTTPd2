package nano.http.d2.serve;

import nano.http.d2.core.Response;

import java.io.File;
import java.util.Enumeration;
import java.util.Properties;

public class ExampleServer implements ServeProvider {
    public Response serve(String uri, String method, Properties header, Properties parms, Properties files) {
        System.out.println(method + " '" + uri + "' ");

        Enumeration<?> e = header.propertyNames();
        while (e.hasMoreElements()) {
            String value = (String) e.nextElement();
            System.out.println("  HDR: '" + value + "' = '" +
                    header.getProperty(value) + "'");
        }
        e = parms.propertyNames();
        while (e.hasMoreElements()) {
            String value = (String) e.nextElement();
            System.out.println("  PRM: '" + value + "' = '" +
                    parms.getProperty(value) + "'");
        }
        e = files.propertyNames();
        while (e.hasMoreElements()) {
            String value = (String) e.nextElement();
            System.out.println("  UPLOADED: '" + value + "' = '" +
                    files.getProperty(value) + "'");
        }

        return FileServer.serveFile(uri, header, new File("."), true);
    }
}
