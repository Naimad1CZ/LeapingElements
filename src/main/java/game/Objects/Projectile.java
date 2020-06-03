package game.Objects;

import game.Terrain;
import game.World;
import javafx.scene.image.Image;
import utils.Enums.TileType;
import utils.Enums.TurretAndProjectileType;

import java.util.List;

public class Projectile extends GameObject {
    protected double speedX;
    protected double speedY;

    protected TurretAndProjectileType type;
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
    public Projectile(Image image, double positionX, double positionY, double angle, double speed, TurretAndProjectileType projectileType, GameObject source) {
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
    public TurretAndProjectileType getType() {
        return type;
    }

    /**
     *
     * @return name of projectile
     */
    public String getName() {
        if (type.equals("fire")) {
            return "A fire projectile";
        } else if (type == TurretAndProjectileType.ice) {
            return "An ice projectile";
        } else if (type == TurretAndProjectileType.combined) {
            return "A combined projectile";
        } else {
            return "A projectile";
        }
    }

    /**
     * Check if not colliding with terrain, die if colliding.
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

        TileType upType = terrain.getTileType(middleXBlock, upperYBlock);
        TileType lowType = terrain.getTileType(middleXBlock, lowerYBlock);
        TileType leftType = terrain.getTileType(lefterXBlock, middleYBlock);
        TileType rightType = terrain.getTileType(righterXBlock, middleYBlock);

        if (upType == TileType.out || lowType == TileType.out || leftType == TileType.out || rightType == TileType.out) {
            // fly out of the world
            return 106;
        } else if (upType != TileType.air || lowType != TileType.air || leftType != TileType.air || rightType != TileType.air) {
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
        List<GameObject> gameObjects = world.getGameObjects();
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
