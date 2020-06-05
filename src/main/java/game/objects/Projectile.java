package game.objects;

import game.Terrain;
import game.World;
import javafx.scene.image.Image;
import utils.Enums.Death;
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

    @Override
    public String getName() {
        if (type == TurretAndProjectileType.FIRE) {
            return "A fire projectile";
        } else if (type == TurretAndProjectileType.ICE) {
            return "An ice projectile";
        } else if (type == TurretAndProjectileType.COMBINED) {
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
    protected Death collideWithTerrain(Terrain terrain) {
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

        if (upType == TileType.OUT || lowType == TileType.OUT || leftType == TileType.OUT || rightType == TileType.OUT) {
            // fly out of the world
            return Death.OTHER;
        } else if (upType != TileType.AIR || lowType != TileType.AIR || leftType != TileType.AIR || rightType != TileType.AIR) {
            // hit something and get destroyed by terrain
            return Death.OTHER;
        }

        return Death.NONE;
    }

    /**
     *
     * @param delta time in seconds since the last update
     * @param world world in which the creature is
     * @return death code
     */
    @Override
    public Death updatePosition(double delta, World world) {
        posX += speedX * delta;
        posY += speedY * delta;

        return collideWithTerrain(world.getTerrain());
    }

    /**
     *
     * @param world world in which the creature is
     * @return death code
     */
    @Override
    public Death updateWithOtherObjects(World world) {
        List<GameObject> gameObjects = world.getGameObjects();
        for (GameObject gameObject : gameObjects) {
            if (gameObject != this && gameObject != creator) {
                if (getBoundingBox().intersects(gameObject.getBoundingBox())) {
                    if (gameObject instanceof Creature) {
                        if (((Creature) gameObject).isAlive()) {
                            return Death.OTHER;
                        }
                    } else if (!(gameObject instanceof Star)) {
                        return Death.OTHER;
                    }
                }
            }
        }

        return Death.NONE;
    }
}
