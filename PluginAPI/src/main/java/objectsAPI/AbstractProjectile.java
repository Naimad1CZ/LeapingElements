package objectsAPI;

import javafx.scene.image.Image;
import utilsAPI.Enums;

public abstract class AbstractProjectile extends AbstractGameObject {
    public abstract void loadData(Image image, double positionX, double positionY, double angle, double speed, Enums.TurretAndProjectileType projectileType, AbstractGameObject source);

    public abstract Enums.TurretAndProjectileType getType();
}
