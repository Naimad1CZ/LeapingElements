package damian.myjavagame.game;

import javafx.scene.canvas.GraphicsContext;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.mapeditor.core.Map;
import org.mapeditor.io.TMXMapReader;

public class MyMap {
    private Map map;

    /**
     *
     * @param location path to the level (might be relative to the resources dir or absolute)
     * @param absolute if the path is absolute
     */
    public MyMap(String location, boolean absolute) {
        try {
            if (!absolute) {
                location = System.getProperty("user.dir").replace("\\", "/") + "/Game/src/main/resources/" + location;
            }

            TMXMapReader mapReader = new TMXMapReader();
            map = mapReader.readMap(location);

        } catch (Exception e) {
            System.err.println("Error while reading the map:\n" + ExceptionUtils.getStackTrace(e));
        }
    }

    /**
     * Loads the world.
     * @param gc Graphics context
     * @return loaded World
     */
    public World loadWorld(GraphicsContext gc) {
        return new World(gc, map);
    }
}
