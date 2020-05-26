package utils;

import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public final class LoggingUtils {
    private static final Logger errorLogger = Logger.getLogger("errorLogger");
    private static final Logger infoLogger = Logger.getLogger("infoLogger");
    private static FileHandler errorHandler;
    private static FileHandler infoHandler;

    static {
        try {
            errorHandler = new FileHandler("logs/Error.txt", true);
            infoHandler = new FileHandler("logs/Info.txt", true);
            errorLogger.addHandler(errorHandler);
            infoLogger.addHandler(infoHandler);
            SimpleFormatter formatter = new SimpleFormatter();
            errorHandler.setFormatter(formatter);
            infoHandler.setFormatter(formatter);
            errorLogger.setUseParentHandlers(false);
            infoLogger.setUseParentHandlers(false);

        } catch (Exception e) {
            System.out.println("No log file found.");
        }
    }

    private LoggingUtils() {/* do nothing */}

    public static void logError(String msg) {
        errorLogger.severe(msg);
    }

    public static void logInfo(String msg) {
        infoLogger.info(msg);
    }
}
