package damian.myjavagame.plugin.basicplugin.objectsimplementations;

import damian.myjavagame.plugin.api.game.AbstractWorld;
import damian.myjavagame.plugin.api.objects.AbstractProjectile;
import damian.myjavagame.plugin.api.objects.AbstractTurret;
import damian.myjavagame.plugin.api.utils.Enums;
import javafx.scene.image.Image;
import org.apache.commons.lang3.exception.ExceptionUtils;

public class Turret extends AbstractTurret {
    /**
     * angle 0 - 360 at which the tower shoots
     */
    protected double angle;
    protected double interval;
    protected double speed;
    protected double timeUntilShoot;

    protected Enums.TurretAndProjectileType type;
    protected Image projectileImage;
    protected AbstractProjectile defaultProjectileInstance;

    /**
     *
     * @param image skin of turret
     * @param positionX default position X
     * @param positionY default position Y
     * @param shootingAngle angle at which the turret shoots
     * @param turretType type of turret
     * @param shootingInterval interval (in seconds) at which the turret shoots
     * @param shootingSpeed speed of projectiles that the turret shoots
     * @param bulletImage skin of projectile
     */
    @Override
    public void loadData(Image image, double positionX, double positionY, double shootingAngle,
                         Enums.TurretAndProjectileType turretType, double shootingInterval, double shootingSpeed,
                         Image bulletImage, AbstractProjectile defaultProjectile) {
        startPosX = positionX;
        startPosY = positionY;
        posX = positionX;
        posY = positionY;
        img = image;
        width = (int) image.getWidth();
        height = (int) image.getHeight();

        angle = shootingAngle;
        type = turretType;
        interval = shootingInterval;
        timeUntilShoot = interval;
        speed = shootingSpeed;

        projectileImage = bulletImage;
        defaultProjectileInstance = defaultProjectile;
    }

    @Override
    public String getName() {
        if (type == Enums.TurretAndProjectileType.FIRE) {
            return "A fire turret";
        } else if (type == Enums.TurretAndProjectileType.ICE) {
            return "An ice turret";
        } else if (type == Enums.TurretAndProjectileType.COMBINED) {
            return "A combined turret";
        } else {
            return "A turret";
        }
    }

    /**
     *
     * @return type of the turret
     */
    public Enums.TurretAndProjectileType getType() {
        return type;
    }

    /**
     * Shoot a projectile.
     * @return a Projectile object that the turret just shot away
     */
    protected AbstractProjectile shoot() {
        try {
            AbstractProjectile p = defaultProjectileInstance.getClass().newInstance();
            p.loadData(projectileImage, posX + width / 2 - projectileImage.getWidth() / 2,
                    posY + height / 2 - projectileImage.getHeight() / 2, angle, speed, type, this);
            return p;
        } catch (Exception e) {
            System.err.println("Error while creating new projectile instance:\n" + ExceptionUtils.getStackTrace(e));
            return null;
        }
    }

    /**
     * Shoots if the time had came.
     * @param delta time in seconds since the last update
     * @param world world in which the creature is
     * @return Death.none
     */
    @Override
    public Enums.Death updatePosition(double delta, AbstractWorld world) {
        timeUntilShoot -= delta;
        if (timeUntilShoot <= 0) {
            AbstractProjectile p = shoot();
            world.addGameObject(p);
            timeUntilShoot += interval;
        }
        return Enums.Death.NONE;
    }

    /**
     * Turret doesn't interact with other objects, therefore don't do anything.
     * @param world world in which the creature is
     * @return Death.none
     */
    @Override
    public Enums.Death updateWithOtherObjects(AbstractWorld world) {
        return Enums.Death.NONE;
    }

}
