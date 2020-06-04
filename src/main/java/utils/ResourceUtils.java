package utils;

import org.apache.commons.lang3.exception.ExceptionUtils;

import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

public class ResourceUtils {
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
}
