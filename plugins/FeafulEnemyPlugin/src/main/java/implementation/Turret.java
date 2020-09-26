package implementation;

import gameAPI.AbstractWorld;
import javafx.scene.image.Image;
import objectsAPI.AbstractProjectile;
import objectsAPI.AbstractTurret;
import org.apache.commons.lang3.exception.ExceptionUtils;
import utilsAPI.Enums.Death;
import utilsAPI.Enums.TurretAndProjectileType;

public class Turret extends AbstractTurret {
    /**
     * angle 0 - 360 at which the tower shoots
     */
    protected double angle;
    protected double interval;
    protected double speed;
    protected double timeUntilShoot;

    protected TurretAndProjectileType type;
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
                         TurretAndProjectileType turretType, double shootingInterval, double shootingSpeed,
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
        if (type == TurretAndProjectileType.FIRE) {
            return "A fire turret";
        } else if (type == TurretAndProjectileType.ICE) {
            return "An ice turret";
        } else if (type == TurretAndProjectileType.COMBINED) {
            return "A combined turret";
        } else {
            return "A turret";
        }
    }

    /**
     *
     * @return type of the turret
     */
    public TurretAndProjectileType getType() {
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
    public Death updatePosition(double delta, AbstractWorld world) {
        timeUntilShoot -= delta;
        if (timeUntilShoot <= 0) {
            AbstractProjectile p = shoot();
            world.addGameObject(p);
            timeUntilShoot += interval;
        }
        return Death.NONE;
    }

    /**
     * Turret doesn't interact with other objects, therefore don't do anything.
     * @param world world in which the creature is
     * @return Death.none
     */
    @Override
    public Death updateWithOtherObjects(AbstractWorld world) {
        return Death.NONE;
    }

}
