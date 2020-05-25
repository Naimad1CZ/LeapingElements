package game;

import game.Objects.*;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import org.mapeditor.core.Map;
import org.mapeditor.core.ObjectGroup;
import org.mapeditor.core.Properties;
import utils.LoggingUtils;

import java.awt.geom.Point2D;
import java.util.ArrayList;

import static java.lang.Double.parseDouble;

public class GameObjects {
    private final GraphicsContext gc;

    private Hero hero1;
    private Hero hero2;

    private ArrayList<GameObject> gameObjects = new ArrayList<>();

    /**
     * camereModes - works only when 2 players are alive.
     * mode 0 - auto - put camera between players, if too big difference in their position, put camera on the player behind
     * mode 1 - focus hero1
     * mode 2 - focus hero2
     */
    private int cameraMode = 0;

    public GameObjects(GraphicsContext g, ObjectGroup og) {
        gc = g;
        for (var o : og) {
            try {
                Image img = new Image(GameObjects.class.getClassLoader().getResourceAsStream(o.getImageSource()));
                double x = o.getX();
                double y = o.getY();

                Properties p = o.getProperties();
                String cls = p.getProperty("class");
                if (cls.equals("Hero")) {
                    String type = p.getProperty("type");
                    String movementSpeed = p.getProperty("movementSpeed");
                    String jumpSpeed = p.getProperty("jumpSpeed");
                    String swimmingSpeed = p.getProperty("swimmingSpeed");
                    double movSpeed;
                    double jmpSpeed;
                    double swimSpeed;
                    if (type.equals("FireHero")) {
                        movSpeed = movementSpeed == null ? 300 : parseDouble(movementSpeed);
                        jmpSpeed = jumpSpeed == null ? 380 : parseDouble(jumpSpeed);
                        swimSpeed = swimmingSpeed == null ? 0 : parseDouble(swimmingSpeed);
                    } else if (type.equals("IceHero")) {
                        movSpeed = movementSpeed == null ? 250 : parseDouble(movementSpeed);
                        jmpSpeed = jumpSpeed == null ? 320 : parseDouble(jumpSpeed);
                        swimSpeed = swimmingSpeed == null ? 120 : parseDouble(swimmingSpeed);
                    } else {
                        movSpeed = movementSpeed == null ? 0 : parseDouble(movementSpeed);
                        jmpSpeed = jumpSpeed == null ? 0 : parseDouble(jumpSpeed);
                        swimSpeed = swimmingSpeed == null ? 0 : parseDouble(swimmingSpeed);
                    }

                    Hero h = new Hero(img, x, y, type, movSpeed, jmpSpeed, swimSpeed);
                    gameObjects.add(h);
                    if (hero1 == null) {
                        hero1 = h;
                    } else if (hero2 == null) {
                        hero2 = h;
                    }
                } else if (cls.equals("SimpleEnemy")) {
                    String routeLength = p.getProperty("routeLength");
                    double rtLength = parseDouble(routeLength);

                    SimpleEnemy se = new SimpleEnemy(img, x, y, rtLength);
                    gameObjects.add(se);
                } else if (cls.equals("Turret")) {

                } else if (cls.equals("Star")) {

                } else {
                    LoggingUtils.logInfo("Cannot recognize object with class " + cls);
                }



                double x = o.getX();
                double tt = 1;

            /*Image i = o getImage(1);
            o.getImageSource()*/
            } catch (Exception e) {
                LoggingUtils.logError("Error while getting object: " + e.getMessage() + ", " + e.toString());
            }
        }

    }

    public Hero getHero1() {
        return hero1;
    }

    public Hero getHero2() {
        return hero2;
    }

    public void changeCameraMode() {
        cameraMode = (cameraMode + 1) % 3;
    }

    public ArrayList<GameObject> getGameObjects() {
        return gameObjects;
    }

    public void addGameObject(GameObject go) {
        gameObjects.add(go);
    }

    public Point2D.Double getHeroPositionsOptimalCenter() {
        if (hero2 != null) {
            if (hero1.isAlive() && hero2.isAlive()) {
                var pos1 = hero1.getPosition();
                var pos2 = hero2.getPosition();

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
                var pos1 = hero1.getPosition();
                return new Point2D.Double(pos1.x + (Game.WIDTH / 10), pos1.y);
            } else if (hero2.isAlive()){
                var pos2 = hero2.getPosition();
                return new Point2D.Double(pos2.x + (Game.WIDTH / 10), pos2.y);
            } else {
                return new Point2D.Double(0, 0);
            }
        } else {
            if (hero1.isAlive()){
                var pos1 = hero1.getPosition();
                return new Point2D.Double(pos1.x + (Game.WIDTH / 10), pos1.y);
            } else {
                return new Point2D.Double(0, 0);
            }
        }
    }

    // tadyyyyyyyyyk
    public void update(double delta, World world) {
        if (!hero1.isAlive()) {
            hero1.respawn();
        }
        hero1.update(delta, world);

        if (hero2 != null) {
            if (!hero2.isAlive()) {
                hero2.respawn();
            }
            hero2.update(delta, world);
        }
    }

    // tadyyyyyyk
    public void draw(int leftLabel, int topLabel) {
        if (hero1.isAlive()) {
            hero1.draw(gc, leftLabel, topLabel);
        }
        if (hero2 != null && hero2.isAlive()) {
            hero2.draw(gc, leftLabel, topLabel);
        }
    }
}
