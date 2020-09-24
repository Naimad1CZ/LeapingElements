package game.objects;

import javafx.scene.image.Image;
import utils.Enums;

public abstract class AbstractTurret extends GameObject {
    public abstract void loadData(Image image, double positionX, double positionY, double shootingAngle,
                                  Enums.TurretAndProjectileType turretType, double shootingInterval,
                                  double shootingSpeed, Image bulletImage, AbstractProjectile defaultProjectile);

    public abstract Enums.TurretAndProjectileType getType();

    protected abstract AbstractProjectile shoot();
}
