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

    public Turret(Image image, double positionX, double positionY, double shootingAngle, String turretType, double shootingInterval, double shootingSpeed, Image bulletImage) {
        super(image, positionX, positionY);
        angle = shootingAngle;
        type = turretType;
        interval = shootingInterval;
        timeUntilShoot = interval;
        speed = shootingSpeed;

        projectileImage = bulletImage;
    }

    @Override
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

    public String getType() {
        return type;
    }

    protected Projectile shoot() {
        return new Projectile(projectileImage, posX + width / 2 - projectileImage.getWidth() / 2, posY + height / 2 - projectileImage.getHeight() / 2, angle, speed, type, this);
    }

    public int updatePosition(double delta, World world) {
        timeUntilShoot -= delta;
        if (timeUntilShoot <= 0) {
            Projectile p = shoot();
            world.addGameObject(p);
            timeUntilShoot += interval;
        }
        return 0;
    }

    public int updateWithOtherObjects(World world) {
        return 0;
    }

}