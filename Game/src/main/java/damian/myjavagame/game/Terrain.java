package damian.myjavagame.game;

import damian.myjavagame.plugin.api.game.AbstractTerrain;
import damian.myjavagame.plugin.api.utils.Constants;
import damian.myjavagame.plugin.api.utils.Enums.TileType;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import org.mapeditor.core.Map;
import org.mapeditor.core.Tile;
import org.mapeditor.core.TileLayer;

import java.util.HashMap;

public class Terrain extends AbstractTerrain {
    public final int TILE_HEIGHT;
    public final int TILE_WIDTH;
    public final int WIDTH;
    public final int HEIGHT;
    private final GraphicsContext gc;
    private final Tile[][] tiles;
    private final HashMap<Tile, Image> images = new HashMap<>();

    /**
     *
     * @param g Graphics context
     * @param l Tile layer with the tiles to be load
     * @param map Map from which we are loading
     */
    public Terrain(GraphicsContext g, TileLayer l, Map map) {
        gc = g;
        TILE_WIDTH = map.getTileWidth();
        TILE_HEIGHT = map.getTileHeight();
        WIDTH = TILE_WIDTH * map.getWidth();
        HEIGHT = TILE_HEIGHT * map.getHeight();

        tiles = new Tile[map.getWidth()][map.getHeight()];

        for (int i = 0; i < map.getWidth(); ++i) {
            for (int j = 0; j < map.getHeight(); ++j) {
                Tile t = l.getTileAt(i, j);
                tiles[i][j] = t;
                if ((t != null) && !images.containsKey(t)) {
                    javafx.scene.image.Image img = SwingFXUtils.toFXImage(t.getImage(), null);
                    images.put(t, img);
                }
            }
        }
    }

    /**
     * Draws a terrain in the speicfied coordinates.
     * @param leftLabel X coordinate which is currently on the most left part of the screen
     * @param topLabel Y coordinate which is currently on the most top part of the screen
     */
    public void draw(int leftLabel, int topLabel) {
        for (int i = leftLabel / TILE_WIDTH; i <= (leftLabel + Constants.WIDTH) / TILE_WIDTH; ++i) {
            for (int j = topLabel / TILE_HEIGHT; j <= (topLabel + Constants.HEIGHT) / TILE_HEIGHT; ++j) {
                if (tiles.length > i && tiles[0].length > j && tiles[i][j] != null) {
                    gc.drawImage(images.get(tiles[i][j]), i * TILE_WIDTH - leftLabel, j * TILE_HEIGHT - topLabel);
                }
            }
        }
    }

    /**
     *
     * @param x index X (first index) of the Tile array
     * @param y index Y (second index) of the Tile array
     * @return type of tile that lays in the specified position in Tile array
     */
    public TileType getTileType(int x, int y) {
        if (y >= tiles[0].length || y < -5 || x >= tiles.length + 5 || x < -5) {
            return TileType.OUT;
        } else if (y < 0 || x >= tiles.length || x < 0 || tiles[x][y] == null) {
            return TileType.AIR;
        } else {
            String kind = tiles[x][y].getProperties().getProperty("kind").toUpperCase();
            return TileType.valueOf(kind);
        }
    }

    public int getTileWidth() {
        return TILE_WIDTH;
    }

    public int getTileHeight() {
        return TILE_HEIGHT;
    }


}
