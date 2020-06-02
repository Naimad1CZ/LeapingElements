package game.Objects;

import game.World;
import javafx.scene.image.Image;

public class Turret extends GameObject {
    /**
     * angle 0 - 360 at which the tower shoots
     */
    protected double angle;
    protected double interval;
    protected double speed;
    protected double timeUntilShoot;

    protected String type;
    protected Image projectileImage;

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
    public Turret(Image image, double positionX, double positionY, double shootingAngle, String turretType, double shootingInterval, double shootingSpeed, Image bulletImage) {
        super(image, positionX, positionY);
        angle = shootingAngle;
        type = turretType;
        interval = shootingInterval;
        timeUntilShoot = interval;
        speed = shootingSpeed;

        projectileImage = bulletImage;
    }

    /**
     *
     * @return name
     */
    public String getName() {
        if (type.equals("fire")) {
            return "A fire turret";
        } else if (type.equals("ice")) {
            return "An ice turret";
        } else if (type.equals("combined")) {
            return "A combined turret";
        } else {
            return "A turret";
        }
    }

    /**
     *
     * @return type of the turret
     */
    public String getType() {
        return type;
    }

    /**
     * Shoot a projectile
     * @return a Projectile object that the turret just shot away
     */
    protected Projectile shoot() {
        return new Projectile(projectileImage, posX + width / 2 - projectileImage.getWidth() / 2, posY + height / 2 - projectileImage.getHeight() / 2, angle, speed, type, this);
    }

    /**
     * Shoots if the time had came
     * @param delta time in seconds since the last update
     * @param world world in which the creature is
     * @return 0
     */
    public int updatePosition(double delta, World world) {
        timeUntilShoot -= delta;
        if (timeUntilShoot <= 0) {
            Projectile p = shoot();
            world.addGameObject(p);
            timeUntilShoot += interval;
        }
        return 0;
    }

    /**
     * Turret doesn't interact with other objects, therefore don't do anything
     * @param world world in which the creature is
     * @return 0
     */
    public int updateWithOtherObjects(World world) {
        return 0;
    }

}
