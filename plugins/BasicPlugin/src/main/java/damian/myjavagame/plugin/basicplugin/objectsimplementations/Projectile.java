package damian.myjavagame.plugin.basicplugin.objectsimplementations;

import damian.myjavagame.plugin.api.game.AbstractTerrain;
import damian.myjavagame.plugin.api.game.AbstractWorld;
import damian.myjavagame.plugin.api.objects.AbstractCreature;
import damian.myjavagame.plugin.api.objects.AbstractGameObject;
import damian.myjavagame.plugin.api.objects.AbstractProjectile;
import damian.myjavagame.plugin.api.objects.AbstractStar;
import damian.myjavagame.plugin.api.utils.Enums.Death;
import damian.myjavagame.plugin.api.utils.Enums.TileType;
import damian.myjavagame.plugin.api.utils.Enums.TurretAndProjectileType;
import javafx.scene.image.Image;

import java.util.List;

public class Projectile extends AbstractProjectile {
    protected double speedX;
    protected double speedY;

    protected TurretAndProjectileType type;
    protected AbstractGameObject creator;

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
    @Override
    public void loadData(Image image, double positionX, double positionY, double angle, double speed, TurretAndProjectileType projectileType, AbstractGameObject source) {
        startPosX = positionX;
        startPosY = positionY;
        posX = positionX;
        posY = positionY;
        img = image;
        width = (int) image.getWidth();
        height = (int) image.getHeight();

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
    protected Death collideWithTerrain(AbstractTerrain terrain) {
        int upperYBlock = (int) posY / terrain.getTileHeight();
        int middleYBlock = (int) (posY + height / 2) / terrain.getTileHeight();
        int lowerYBlock = (int) (posY + height - 1) / terrain.getTileHeight();

        int lefterXBlock = (int) posX / terrain.getTileWidth();
        int middleXBlock = (int) (posX + width / 2) / terrain.getTileWidth();
        int righterXBlock = (int) (posX + width - 1) / terrain.getTileWidth();

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
    public Death updatePosition(double delta, AbstractWorld world) {
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
    public Death updateWithOtherObjects(AbstractWorld world) {
        List<AbstractGameObject> gameObjects = world.getGameObjects();
        for (AbstractGameObject gameObject : gameObjects) {
            if (gameObject != this && gameObject != creator) {
                if (getBoundingBox().intersects(gameObject.getBoundingBox())) {
                    if (gameObject instanceof AbstractCreature) {
                        if (((AbstractCreature) gameObject).isAlive()) {
                            return Death.OTHER;
                        }
                    } else if (!(gameObject instanceof AbstractStar)) {
                        return Death.OTHER;
                    }
                }
            }
        }

        return Death.NONE;
    }
}
