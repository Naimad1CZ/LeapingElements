package game;

import javafx.scene.canvas.GraphicsContext;
import org.mapeditor.core.ImageLayer;
import org.mapeditor.core.Map;
import org.mapeditor.core.TileLayer;

public class World {
    public static final double GRAVITY = 400;

    private Background background;
    private Terrain terrain;
    private GameObjects gameObjects;

    public World(GraphicsContext gc, Map map) {
        background = new Background(gc, (ImageLayer)map.getLayer(0));
        terrain = new Terrain(gc, (TileLayer)map.getLayer(1), map);
        gameObjects = new GameObjects(gc, map);
    }

    public Hero getHero1() {
        return gameObjects.getHero1();
    }

    public Hero getHero2() {
        return gameObjects.getHero2();
    }

    public void updateAndDraw(double delta) {
        gameObjects.update(delta, this);

        var heroPosAvg = gameObjects.getHeroPositionsOptimalCenter();

        int leftLabel = (int)(heroPosAvg.x - Game.WIDTH / 2);
        int topLabel = (int)(heroPosAvg.y - Game.HEIGHT / 2);

        if (leftLabel < 0) {
            leftLabel = 0;
        } else if (leftLabel + Game.WIDTH > terrain.WIDTH) {
            leftLabel = terrain.WIDTH - Game.WIDTH;
        }

        if (topLabel < 0) {
            topLabel = 0;
        } else if (topLabel + Game.HEIGHT > terrain.HEIGHT) {
            topLabel = terrain.HEIGHT - Game.HEIGHT;
        }

        background.draw(leftLabel, topLabel, terrain.HEIGHT);
        terrain.draw(leftLabel, topLabel);
        gameObjects.draw(leftLabel, topLabel);
    }

    public Terrain getTerrain() {
        return terrain;
    }

    public GameObjects getGameObjects() {
        return gameObjects;
    }
}
