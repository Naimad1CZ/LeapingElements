package game;
import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Logger;

public final class LoggingUtils {
    private static final Logger errorLogger = Logger.getLogger("errorLogger");
    private static final Logger infoLogger = Logger.getLogger("infoLogger");
    private FileHandler errorHandler;
    private FileHandler infoHandler;

    {
        try {
            errorHandler = new FileHandler("logs/Error.txt");
            infoHandler = new FileHandler("logs/Info.txt");
            errorLogger.addHandler(errorHandler);
            infoLogger.addHandler(infoHandler);
        } catch (Exception e) {
            System.out.println("No log file found.");
        }
    }

    private LoggingUtils() {/* do nothing */};

    public static void logError(String msg) {
        errorLogger.severe(msg);
    }

    public static void logInfo(String msg) {
        infoLogger.info(msg);
    }
}
