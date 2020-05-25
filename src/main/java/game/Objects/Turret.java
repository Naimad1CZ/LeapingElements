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

    public Turret(Image image, double positionX, double positionY, double shootingAngle, double shootingInterval, double shootingSpeed, String turretType, Image bulletImage) {
        super(image, positionX, positionY);
        angle = shootingAngle;
        interval = shootingInterval;
        timeUntilShoot = interval;
        speed = shootingSpeed;
        type = turretType;
        projectileImage = bulletImage;
    }

    protected Projectile shoot() {
        return new Projectile(projectileImage, posX + width / 2, posY + height / 2, angle, speed, type, this);
    }

    @Override
    public int update(double delta, World world) {
        timeUntilShoot -= delta;
        if (timeUntilShoot <= 0) {
            Projectile p = shoot();
            world.addGameObject(p);
            timeUntilShoot += interval;
        }
        return 0;
    }
}
