package game;

import game.objects.AbstractHero;
import game.objects.GameObject;
import javafx.scene.canvas.GraphicsContext;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.mapeditor.core.*;

import java.awt.geom.Point2D;
import java.util.List;

public class World {
    public static final double GRAVITY = 400;

    private Background background;
    private Terrain terrain;
    private GameObjects gameObjects;
    private final HUD hud;
    private final int maxScore;
    private int score = 0;
    private boolean completed = false;

    /**
     *
     * @param gc Graphics context
     * @param map Map from which all the data will be load
     */
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

    public AbstractHero getHero1() {
        return gameObjects.getHero1();
    }

    public AbstractHero getHero2() {
        return gameObjects.getHero2();
    }

    /**
     * Change camera mode.
     */
    public void changeCameraMode() {
        gameObjects.changeCameraMode();
    }

    /**
     * Updates and draws background, terrain, game objects and HUD. Also calculates coordinates of the part of the map
     * that will be drawn.
     * @param delta how long it's been since the last call of the method
     */
    public void updateAndDraw(double delta) {
        try {
            gameObjects.update(delta, this);

            Point2D.Double heroPosAvg = gameObjects.getHeroPositionsOptimalCenter();

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
            System.err.println("Error when updating and drawing:\n" + ExceptionUtils.getStackTrace(e));
        }
    }

    /**
     *
     * @return terrain
     */
    public Terrain getTerrain() {
        return terrain;
    }

    /**
     *
     * @return all game objects
     */
    public List<GameObject> getGameObjects() {
        return gameObjects.getGameObjects();
    }

    /**
     * Add a GameOBject to the game
     * @param go GameObject to be added
     */
    public void addGameObject(GameObject go) {
        gameObjects.addGameObject(go);
    }

    /**
     * Adds score and handle the situation when the score has reached the maximum score
     * @param value how much score to add
     */
    public void addScore(int value) {
        score += value;
        if (score >= maxScore) {
            setMessage("Level completed!", 4);
            completed = true;
        }
    }

    /**
     *
     * @return current score
     */
    public int getScore() {
        return score;
    }

    /**
     *
     * @return sum of the values of all stars in the game
     */
    public int getMaxScore() {
        return maxScore;
    }

    /**
     * Set the message to be shown to player in HUD for specified time
     * @param message message to be shown
     * @param length how long (in seconds) the message will be displayed
     */
    public void setMessage(String message, double length) {
        if (gameObjects.getTotalCurrentLives() == 0) {
            hud.setMessage("You lost. Press R to restart level.", 10);
        } else {
            hud.setMessage(message, length);
        }
    }

    /**
     *
     * @return currently displayed message
     */
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
