package nano.http.d2.hooks;

import nano.http.d2.core.Response;
import nano.http.d2.serve.ServeProvider;

import java.util.Properties;

public interface HookProvider {
    Response serve(String uri, String method, Properties header, Properties parms, Properties files, ServeProvider sp);
}
