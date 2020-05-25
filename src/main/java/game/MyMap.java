package game;

import utils.LoggingUtils;

import javafx.scene.canvas.GraphicsContext;

import org.mapeditor.core.Map;
import org.mapeditor.io.TMXMapReader;

public class MyMap {
    private Map map;
    public MyMap(String location) {
        if (location == null) {
            location = "src/main/resources/bezjmena3.tmx";
        }
        try {
            TMXMapReader mapReader = new TMXMapReader();
            map = mapReader.readMap(location);

        } catch (Exception e) {
            LoggingUtils.logError("Error while reading the map:\n" + e.getMessage());
            return;
        }
    }

    public World loadWorld(GraphicsContext gc) {
        return new World(gc, map);
    }
}
