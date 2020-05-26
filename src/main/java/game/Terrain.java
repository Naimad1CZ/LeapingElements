package game;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import org.mapeditor.core.Map;
import org.mapeditor.core.Tile;
import org.mapeditor.core.TileLayer;
import utils.SwingFXUtils;

import java.util.HashMap;

public class Terrain {
    public final int TILE_HEIGHT;
    public final int TILE_WIDTH;
    public final int WIDTH;
    public final int HEIGHT;
    private final GraphicsContext gc;
    private final Tile[][] tiles;
    private final HashMap<Tile, Image> images = new HashMap<>();

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
                if (!(t == null) && !images.containsKey(t)) {
                    javafx.scene.image.Image img = SwingFXUtils.toFXImage(t.getImage(), null);
                    images.put(t, img);
                }
            }
        }
    }

    public void draw(int leftLabel, int topLabel) {
        for (int i = leftLabel / TILE_WIDTH; i <= (leftLabel + Game.WIDTH) / TILE_WIDTH; ++i) {
            for (int j = topLabel / TILE_HEIGHT; j <= (topLabel + Game.HEIGHT) / TILE_HEIGHT; ++j) {
                if (tiles.length > i && tiles[0].length > j && tiles[i][j] != null) {
                    var x = images.get(tiles[i][j]);
                    gc.drawImage(images.get(tiles[i][j]), i * TILE_WIDTH - leftLabel, j * TILE_HEIGHT - topLabel);
                }
            }
        }
    }

    public String getTileType(int x, int y) {
        if (y >= tiles[0].length || y < -5 || x > tiles.length + 5 || x < -5) {
            return "out";
        } else if (x < 0 || x >= tiles.length || y < 0 || tiles[x][y] == null) {
            return "air";
        } else {
            return tiles[x][y].getProperties().getProperty("kind");
        }
    }
}
