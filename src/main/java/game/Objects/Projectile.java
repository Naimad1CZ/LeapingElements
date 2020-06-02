package game.Objects;

import game.Terrain;
import game.World;
import javafx.scene.image.Image;

import java.util.ArrayList;

public class Projectile extends GameObject {
    protected double speedX;
    protected double speedY;

    protected String type;
    protected GameObject creator;

    /**
     *
     * @param image skin of the object
     * @param positionX default position X
     * @param positionY default position Y
     * @param angle angle at which the Projectile is shot
     * @param speed speed
     * @param projectileType type of projectile
     * @param source turret that shot this projectile
     */
    public Projectile(Image image, double positionX, double positionY, double angle, double speed, String projectileType, GameObject source) {
        super(image, positionX, positionY);
        speedX = Math.sin(Math.toRadians(angle)) * speed;
        speedY = Math.cos(Math.toRadians(angle)) * speed;
        type = projectileType;
        creator = source;
    }

    /**
     *
     * @return type of turret
     */
    public String getType() {
        return type;
    }

    /**
     *
     * @return name of projectile
     */
    public String getName() {
        if (type.equals("fire")) {
            return "A fire projectile";
        } else if (type.equals("ice")) {
            return "An ice projectile";
        } else if (type.equals("combined")) {
            return "A combined projectile";
        } else {
            return "A projectile";
        }
    }

    /**
     * Check if not colliding with terrain, die if colliding
     * @param terrain world's terrain
     * @return death code
     */
    protected int collideWithTerrain(Terrain terrain) {
        int upperYBlock = (int) posY / terrain.TILE_HEIGHT;
        int middleYBlock = (int) (posY + height / 2) / terrain.TILE_HEIGHT;
        int lowerYBlock = (int) (posY + height - 1) / terrain.TILE_HEIGHT;

        int lefterXBlock = (int) posX / terrain.TILE_WIDTH;
        int middleXBlock = (int) (posX + width / 2) / terrain.TILE_WIDTH;
        int righterXBlock = (int) (posX + width - 1) / terrain.TILE_WIDTH;

        String upType = terrain.getTileType(middleXBlock, upperYBlock);
        String lowType = terrain.getTileType(middleXBlock, lowerYBlock);
        String leftType = terrain.getTileType(lefterXBlock, middleYBlock);
        String rightType = terrain.getTileType(righterXBlock, middleYBlock);

        if (upType.equals("out") || lowType.equals("out") || leftType.equals("out") || rightType.equals("out")) {
            // fly out of the world
            return 106;
        } else if (!upType.equals("air") || !lowType.equals("air") || !leftType.equals("air") || !rightType.equals("air")) {
            // hit something and get destroyed by terrain
            return 101;
        }

        return 0;
    }

    /**
     *
     * @param delta time in seconds since the last update
     * @param world world in which the creature is
     * @return death code
     */
    public int updatePosition(double delta, World world) {
        posX += speedX * delta;
        posY += speedY * delta;

        return collideWithTerrain(world.getTerrain());
    }

    /**
     *
     * @param world world in which the creature is
     * @return death code
     */
    public int updateWithOtherObjects(World world) {
        ArrayList<GameObject> gameObjects = world.getGameObjects();
        for (GameObject gameObject : gameObjects) {
            if (gameObject != this && gameObject != creator) {
                if (getBoundingBox().intersects(gameObject.getBoundingBox())) {
                    if (gameObject instanceof Hero && ((Hero) gameObject).isAlive()) {
                        return 102;
                    } else if (gameObject instanceof Enemy && ((Enemy) gameObject).isAlive()) {
                        return 103;
                    } else if (gameObject instanceof Turret) {
                        return 104;
                    } else if (gameObject instanceof Projectile) {
                        return 105;
                    }
                }
            }
        }

        return 0;
    }
}
