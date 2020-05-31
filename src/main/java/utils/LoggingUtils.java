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
        try (FileWriter fileWriter = new FileWriter(errorFile, true)) {
            LocalDateTime dateTime = LocalDateTime.now();
            String s = dateTime.format(formatter);
            fileWriter.append("[" + s + "] " + msg + "\n");
        } catch (Exception e) { }
    }

    public static void logInfo(String msg) {
        try (FileWriter fileWriter = new FileWriter(infoFile, true)) {
            LocalDateTime dateTime = LocalDateTime.now();
            String s = dateTime.format(formatter);
            fileWriter.append("[" + s + "] " + msg + "\n");
        } catch (Exception e) { }
    }
}
