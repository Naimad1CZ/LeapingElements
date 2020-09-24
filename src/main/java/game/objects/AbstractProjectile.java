package game.objects;

import javafx.scene.image.Image;
import utils.Enums;

public abstract class AbstractProjectile extends GameObject {
    public abstract void loadData(Image image, double positionX, double positionY, double angle, double speed, Enums.TurretAndProjectileType projectileType, GameObject source);

    public abstract Enums.TurretAndProjectileType getType();
}
