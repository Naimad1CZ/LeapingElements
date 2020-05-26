package game;

import game.Objects.GameObject;
import game.Objects.Hero;
import javafx.scene.canvas.GraphicsContext;
import org.mapeditor.core.ImageLayer;
import org.mapeditor.core.Map;
import org.mapeditor.core.ObjectGroup;
import org.mapeditor.core.TileLayer;
import utils.LoggingUtils;

import java.util.ArrayList;

public class World {
    public static final double GRAVITY = 400;

    private final Background background;
    private final Terrain terrain;
    private final GameObjects gameObjects;

    private int score = 0;

    public World(GraphicsContext gc, Map map) {
        background = new Background(gc, (ImageLayer) map.getLayer(0));
        terrain = new Terrain(gc, (TileLayer) map.getLayer(1), map);
        gameObjects = new GameObjects(gc, (ObjectGroup) map.getLayer(2));
    }

    public Hero getHero1() {
        return gameObjects.getHero1();
    }

    public Hero getHero2() {
        return gameObjects.getHero2();
    }

    public void changeCameraMode() {
        gameObjects.changeCameraMode();
    }

    public void updateAndDraw(double delta) {
        try {
            gameObjects.update(delta, this);

            var heroPosAvg = gameObjects.getHeroPositionsOptimalCenter();

            int leftLabel = (int) (heroPosAvg.x - Game.WIDTH / 2);
            int topLabel = (int) (heroPosAvg.y - Game.HEIGHT / 2);

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
        } catch (Exception e) {
            LoggingUtils.logError("Error when updating and drawing: " + e.getMessage() + ", " + e.toString());
        }
    }

    public Terrain getTerrain() {
        return terrain;
    }

    public ArrayList<GameObject> getGameObjects() {
        return gameObjects.getGameObjects();
    }

    public void addGameObject(GameObject go) {
        gameObjects.addGameObject(go);
    }

    public void addScore(int value) {
        score += value;
    }

    public int getScore() {
        return score;
    }
}
