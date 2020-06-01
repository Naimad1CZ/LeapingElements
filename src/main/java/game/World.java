package game;

import game.Objects.GameObject;
import game.Objects.Hero;
import javafx.scene.canvas.GraphicsContext;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.mapeditor.core.*;
import utils.LoggingUtils;

import java.util.ArrayList;

public class World {
    public static final double GRAVITY = 400;

    private Background background;
    private Terrain terrain;
    private GameObjects gameObjects;
    private final HUD hud;
    private final int maxScore;
    private int score = 0;
    private boolean completed = false;

    public World(GraphicsContext gc, Map map) {
        for (int i = 0; i <= map.getLayerCount(); ++i) {
            MapLayer tmp = map.getLayer(i);
            if (tmp != null) {
                if (tmp instanceof ImageLayer) {
                    background = new Background(gc, (ImageLayer) tmp);
                } else if (tmp instanceof TileLayer) {
                    terrain = new Terrain(gc, (TileLayer) tmp, map);
                } else if (tmp instanceof ObjectGroup) {
                    gameObjects = new GameObjects(gc, (ObjectGroup) tmp);
                }

            }
        }


        maxScore = gameObjects.getCurrentObtainableScore();
        hud = new HUD(gc, this);
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
            hud.draw(delta);
        } catch (Exception e) {
            LoggingUtils.logError("Error when updating and drawing:\n" + ExceptionUtils.getStackTrace(e));
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
        if (score == maxScore) {
            setMessage("Level completed!", 4);
            completed = true;
        }
    }

    public int getScore() {
        return score;
    }

    public int getMaxScore() {
        return maxScore;
    }

    public void setMessage(String message, double length) {
        if (gameObjects.getTotalCurrentLives() == 0) {
            hud.setMessage("You lost. Press R to restart level.", 10);
        } else {
            hud.setMessage(message, length);
        }
    }

    public String getMessage() {
        return hud.getMessage();
    }

    /**
     * If the level is completed
     *
     * @return true if level is completed and the "Level completed!" message already disappeared.
     */
    public boolean isCompleted() {
        return completed && !getMessage().equals("Level completed!") && hud.getMessageTimeLeft() <= 0;
    }
}
