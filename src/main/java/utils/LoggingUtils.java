package utils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public final class LoggingUtils {
    private static File errorFile;
    private static File infoFile;
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd hh:mm:ss");

    static {
        try {
            File dir = new File(System.getProperty("user.dir"), "logs");
            dir.mkdirs();
            errorFile = new File(dir, "Error.txt");
            errorFile.createNewFile();
            infoFile = new File(dir, "Info.txt");
            infoFile.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private LoggingUtils() {/* do nothing */}

    public static void logError(String msg) {
        System.err.println(msg);
    }

    public static void logInfo(String msg) {
        System.out.println(msg);
    }
}
