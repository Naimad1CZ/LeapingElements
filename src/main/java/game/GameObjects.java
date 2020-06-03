package game;

import game.Objects.*;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.mapeditor.core.MapObject;
import org.mapeditor.core.ObjectGroup;
import org.mapeditor.core.Properties;
import utils.DeathMessages;
import utils.LoggingUtils;
import javafx.embed.swing.SwingFXUtils;

import java.awt.geom.Point2D;
import java.util.ArrayList;

import static java.lang.Double.parseDouble;
import static java.lang.Integer.parseInt;

public class GameObjects {
    private final GraphicsContext gc;

    private final ArrayList<GameObject> gameObjects = new ArrayList<>();
    private final ArrayList<GameObject> objectsToAdd = new ArrayList<>();
    private Hero hero1;
    private Hero hero2;

    /**
     * general properties of object tile
     */
    private Properties prop1;
    /**
     * specific properties of object
     */
    private Properties prop2;

    /**
     * cameraModes - works only when 2 players are alive.
     * mode 0 - auto - put camera between players, if too big difference in their position, put camera on the player behind
     * mode 1 - focus hero1
     * mode 2 - focus hero2
     */
    private int cameraMode = 0;

    /**
     *
     * @param g Graphics context
     * @param og Tiled's ObjectGroup with all objects that will be processed and converted to (programming) objects
     */
    public GameObjects(GraphicsContext g, ObjectGroup og) {
        gc = g;
        for (MapObject o : og) {
            try {
                prop1 = o.getTile().getProperties();
                prop2 = o.getProperties();

                Image img = SwingFXUtils.toFXImage(o.getTile().getImage(), null);
                double x = o.getX();
                // subtracting height because Tiled sets coordinates for the lower left point, not upper left
                double y = o.getY() - o.getHeight();

                String cls = getProperty("class");
                if (cls.equals("Hero")) {
                    String type = getProperty("type");
                    String movementSpeed = getProperty("movementSpeed");
                    String jumpSpeed = getProperty("jumpSpeed");
                    String swimmingSpeed = getProperty("swimmingSpeed");
                    String lives = getProperty("lives");
                    String HUDImageSource = getProperty("HUDImageSource");
                    double movSpeed;
                    double jmpSpeed;
                    double swimSpeed;
                    int liv;
                    if (type.equals("fire")) {
                        movSpeed = movementSpeed == null ? 280 : parseDouble(movementSpeed);
                        jmpSpeed = jumpSpeed == null ? 410 : parseDouble(jumpSpeed);
                        swimSpeed = swimmingSpeed == null ? 0 : parseDouble(swimmingSpeed);
                        liv = lives == null ? 3 : parseInt(lives);
                        HUDImageSource = HUDImageSource == null ? "Objects/HeroFireSmall.png" : HUDImageSource;
                    } else if (type.equals("ice")) {
                        movSpeed = movementSpeed == null ? 250 : parseDouble(movementSpeed);
                        jmpSpeed = jumpSpeed == null ? 350 : parseDouble(jumpSpeed);
                        swimSpeed = swimmingSpeed == null ? 120 : parseDouble(swimmingSpeed);
                        liv = lives == null ? 4 : parseInt(lives);
                        HUDImageSource = HUDImageSource == null ? "Objects/HeroIceSmall.png" : HUDImageSource;
                    } else {
                        movSpeed = movementSpeed == null ? 300 : parseDouble(movementSpeed);
                        jmpSpeed = jumpSpeed == null ? 350 : parseDouble(jumpSpeed);
                        swimSpeed = swimmingSpeed == null ? 0 : parseDouble(swimmingSpeed);
                        liv = lives == null ? 3 : parseInt(lives);
                    }

                    Hero h = new Hero(img, x, y, type, movSpeed, jmpSpeed, swimSpeed, liv, HUDImageSource);
                    gameObjects.add(h);
                    if (hero1 == null) {
                        hero1 = h;
                    } else if (hero2 == null) {
                        hero2 = h;
                        if (hero2.getType().equals("ice") && hero1.getType().equals("fire")) {
                            // ice is the first hero if we have both ice and fire heroes
                            Hero tmp = hero2;
                            hero2 = hero1;
                            hero1 = tmp;
                        }
                    }
                } else if (cls.equals("SimpleEnemy")) {
                    String routeLength = getProperty("routeLength");
                    double rtLength = parseDouble(routeLength);

                    SimpleEnemy se = new SimpleEnemy(img, x, y, rtLength);
                    gameObjects.add(se);
                } else if (cls.equals("Turret")) {
                    String shootingAngle = getProperty("shootingAngle");
                    String type = getProperty("type");
                    String shootingInterval = getProperty("shootingInterval");
                    String shootingSpeed = getProperty("shootingSpeed");
                    String pathToBulletImage = getProperty("pathToBulletImage");

                    // shootingAngle is essential as well as type
                    double shAngle = parseDouble(shootingAngle);
                    double shInterval = shootingInterval == null ? 1.5d : parseDouble(shootingInterval);
                    double shSpeed = shootingSpeed == null ? 400 : parseDouble(shootingSpeed);

                    if (type.equals("fire")) {
                        pathToBulletImage = pathToBulletImage == null ? "Objects/BulletFire.png" : pathToBulletImage;
                    } else if (type.equals("ice")) {
                        pathToBulletImage = pathToBulletImage == null ? "Objects/BulletIce.png" : pathToBulletImage;
                    } else if (type.equals("combined")) {
                        pathToBulletImage = pathToBulletImage == null ? "Objects/BulletCombined.png" : pathToBulletImage;
                    }

                    Image bulletImage = new Image(GameObjects.class.getClassLoader().getResourceAsStream(pathToBulletImage));
                    Turret t = new Turret(img, x, y, shAngle, type, shInterval, shSpeed, bulletImage);
                    gameObjects.add(t);
                } else if (cls.equals("Star")) {
                    String value = getProperty("value");
                    int val = value == null ? 1 : parseInt(value);

                    Star s = new Star(img, x, y, val);
                    gameObjects.add(s);
                } else {
                    LoggingUtils.logInfo("Cannot recognize object with class " + cls);
                }
            } catch (Exception e) {
                LoggingUtils.logError("Error while getting object:\n" + ExceptionUtils.getStackTrace(e));
            }
        }
    }

    /**
     * Gets property of the object from the Tiled's ObjectGroup that we currently work with
     * @param name name of the property
     * @return value of the property
     */
    private String getProperty(String name) {
        String s1 = prop1.getProperty(name);
        String s2 = prop2.getProperty(name);
        if (s2 != null) {
            return s2;
        } else {
            return s1;
        }
    }

    /**
     *
     * @return hero1
     */
    public Hero getHero1() {
        return hero1;
    }

    /**
     *
     * @return hero2
     */
    public Hero getHero2() {
        return hero2;
    }

    /**
     * Change camera mode
     */
    public void changeCameraMode() {
        cameraMode = (cameraMode + 1) % 3;
    }

    /**
     * Gets all game objects
     * @return all game objects
     */
    public ArrayList<GameObject> getGameObjects() {
        return gameObjects;
    }

    /**
     * Adds a game object
     * @param go a game object to be added
     */
    public void addGameObject(GameObject go) {
        objectsToAdd.add(go);
    }

    /**
     *
     * @return sum of score from all the stars in game
     */
    public int getCurrentObtainableScore() {
        int res = 0;
        for (GameObject go : gameObjects) {
            if (go instanceof Star) {
                res += ((Star) go).getValue();
            }
        }
        return res;
    }

    /**
     *
     * @return sum of current lives of both heroes
     */
    public int getTotalCurrentLives() {
        int lifeCount = 0;
        if (getHero1() != null) {
            lifeCount += getHero1().getCurrentLives();
        }
        if (getHero2() != null) {
            lifeCount += getHero2().getCurrentLives();
        }
        return lifeCount;
    }

    /**
     * Calculates optimal coordinates of the center of the screen based on heroes positions
     * @return coordinates of optimal center of the screen
     */
    public Point2D.Double getHeroPositionsOptimalCenter() {
        if (hero2 != null) {
            if (hero1.isAlive() && hero2.isAlive()) {
                Point2D.Double pos1 = hero1.getPosition();
                Point2D.Double pos2 = hero2.getPosition();

                if (cameraMode == 0) {
                    // if they are too far away, show the one more behind
                    if (pos2.x - pos1.x > 8.5 * Game.WIDTH / 10) {
                        return new Point2D.Double(pos1.x + (Game.WIDTH / 10), pos1.y);
                    } else if (pos1.x - pos2.x > 8.5 * Game.WIDTH / 10) {
                        return new Point2D.Double(pos2.x + (Game.WIDTH / 10), pos2.y);
                    } else if (pos2.y - pos1.y > 8.5 * Game.HEIGHT / 10) {
                        return new Point2D.Double(pos2.x + (Game.WIDTH / 10), pos2.y);
                    } else if (pos1.y - pos2.y > 8.5 * Game.HEIGHT / 10) {
                        return new Point2D.Double(pos1.x + (Game.WIDTH / 10), pos1.y);
                    }
                    // else return cca. their average
                    return new Point2D.Double((pos1.x + pos2.x) / 2 + (Game.WIDTH / 10), (pos1.y + pos2.y) / 2);
                } else if (cameraMode == 1) {
                    return new Point2D.Double(pos1.x + (Game.WIDTH / 10), pos1.y);
                } else {
                    // cameraMode == 2
                    return new Point2D.Double(pos2.x + (Game.WIDTH / 10), pos2.y);
                }
            } else if (hero1.isAlive()) {
                Point2D.Double pos1 = hero1.getPosition();
                return new Point2D.Double(pos1.x + (Game.WIDTH / 10), pos1.y);
            } else if (hero2.isAlive()) {
                Point2D.Double pos2 = hero2.getPosition();
                return new Point2D.Double(pos2.x + (Game.WIDTH / 10), pos2.y);
            } else {
                return new Point2D.Double(0, 0);
            }
        } else {
            if (hero1 != null && hero1.isAlive()) {
                Point2D.Double pos1 = hero1.getPosition();
                return new Point2D.Double(pos1.x + (Game.WIDTH / 10), pos1.y);
            } else {
                return new Point2D.Double(0, 0);
            }
        }
    }

    /**
     * Updates all game objects.
     * For non-creatures deletes them if they died.
     * For heroes respawns them if they have any lives left.
     *
     * @param delta time in seconds since the last update
     * @param world world in which the objects are
     */
    public void update(double delta, World world) {
        gameObjects.addAll(objectsToAdd);
        objectsToAdd.clear();

        ArrayList<GameObject> toDelete = new ArrayList<>();

        String deathMessage = null;

        for (GameObject go : gameObjects) {
            if (!(go instanceof Creature) || ((Creature) go).isAlive()) {
                int deathCode = go.updatePosition(delta, world);
                if (deathCode > 0) {
                    toDelete.add(go);
                    if (go instanceof Hero) {
                        deathMessage = DeathMessages.getDeathMessage(go, deathCode);
                    }
                }
            }
        }

        for (GameObject go : gameObjects) {
            if (!(go instanceof Creature) || ((Creature) go).isAlive()) {
                int deathCode = go.updateWithOtherObjects(world);
                if (deathCode > 0) {
                    toDelete.add(go);
                    if (go instanceof Hero) {
                        deathMessage = DeathMessages.getDeathMessage(go, deathCode);
                    }
                }
            }
        }

        for (GameObject item : toDelete) {
            if (item instanceof Creature) {
                ((Creature) item).kill();
                if (item instanceof Hero) {
                    ((Hero) item).looseLife();
                    if (((Hero) item).getCurrentLives() > 0) {
                        ((Hero) item).respawn();
                        continue;
                    }
                }
            }
            gameObjects.remove(item);
        }

        if (deathMessage != null) {
            world.setMessage(deathMessage, 3);
        }
    }

    /**
     * Draw all alive game objects
     * @param leftLabel X coordinate which is currently on the most left part of the screen
     * @param topLabel Y coordinate which is currently on the most top part of the screen
     */
    public void draw(int leftLabel, int topLabel) {
        for (GameObject go : gameObjects) {
            if (go instanceof Creature) {
                if (((Creature) go).isAlive()) {
                    go.draw(gc, leftLabel, topLabel);
                }
            } else {
                go.draw(gc, leftLabel, topLabel);
            }
        }
    }
}