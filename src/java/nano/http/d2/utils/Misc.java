package nano.http.d2.utils;

import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.StringTokenizer;
import java.util.TimeZone;
import java.util.concurrent.ConcurrentHashMap;

public class Misc {
    public static final ConcurrentHashMap<String, String> theMimeTypes = new ConcurrentHashMap<>();
    public static final SimpleDateFormat gmtFrmt;

    static {
        theMimeTypes.put("css", "text/css");
        theMimeTypes.put("js", "text/javascript");
        theMimeTypes.put("htm", "text/html");
        theMimeTypes.put("txt", "text/plain");
        theMimeTypes.put("asc", "text/plain");
        theMimeTypes.put("gif", "image/gif");
        theMimeTypes.put("jpg", "image/jpeg");
        theMimeTypes.put("jpeg", "image/jpeg");
        theMimeTypes.put("png", "image/png");
        theMimeTypes.put("mp3", "audio/mpeg");
        theMimeTypes.put("m3u", "audio/mpeg-url");
        theMimeTypes.put("pdf", "application/pdf");
        theMimeTypes.put("doc", "application/msword");
        theMimeTypes.put("ogg", "application/x-ogg");
        theMimeTypes.put("zip", "application/octet-stream");
        theMimeTypes.put("exe", "application/octet-stream");
        theMimeTypes.put("class", "application/octet-stream");
    }

    static {
        gmtFrmt = new SimpleDateFormat("E, d MMM yyyy HH:mm:ss 'GMT'", Locale.US);
        gmtFrmt.setTimeZone(TimeZone.getTimeZone("GMT"));
    }

    public static String encodeUri(String uri) {
        StringBuilder newUri = new StringBuilder();
        StringTokenizer st = new StringTokenizer(uri, "/ ", true);
        while (st.hasMoreTokens()) {
            String tok = st.nextToken();
            if (tok.equals("/"))
                newUri.append("/");
            else if (tok.equals(" "))
                newUri.append("%20");
            else {
                try {
                    newUri.append(URLEncoder.encode(tok, "UTF-8"));
                } catch (Exception ignored) {
                }
            }
        }
        return newUri.toString();
    }
}
