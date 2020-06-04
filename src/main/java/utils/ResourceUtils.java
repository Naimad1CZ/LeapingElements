package utils;

import org.apache.commons.lang3.exception.ExceptionUtils;

import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.Locale;
import java.util.ResourceBundle;

public class ResourceUtils {
    private static Locale locale;
    private static ResourceBundle labels;

    static {
        locale = Locale.getDefault();
        labels = ResourceBundle.getBundle("text", locale);
    }

    public static InputStream getResource(String path) {
        URL url = ResourceUtils.class.getClassLoader().getResource(path);
        try {
            URLConnection c = url.openConnection();
            return c.getInputStream();
        } catch (Exception e) {
            System.err.println("Error when loading font in: " + path + "\n" + ExceptionUtils.getStackTrace(e));
            return null;
        }
    }

    public static String getLocalisedText(String text) {
        return labels.getString(text);
    }
}
