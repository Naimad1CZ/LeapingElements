package game;

import javafx.scene.canvas.GraphicsContext;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.mapeditor.core.Map;
import org.mapeditor.io.TMXMapReader;
import utils.LoggingUtils;

public class MyMap {
    private Map map;

    public MyMap(String location) {
        if (location == null) {
            location = System.getProperty("user.dir").replace("\\", "/") + "/src/main/resources/Levels/Level1.tmx";
        }
        try {
            TMXMapReader mapReader = new TMXMapReader();
            map = mapReader.readMap(location);

        } catch (Exception e) {
            LoggingUtils.logError("Error while reading the map:\n" + ExceptionUtils.getStackTrace(e));
            return;
        }
    }

    public World loadWorld(GraphicsContext gc) {
        return new World(gc, map);
    }
}
