package damian.myjavagame.plugin.api.objects;

import javafx.scene.image.Image;

public abstract class AbstractEnemy extends AbstractCreature {
    /**
     * @param image skin of SimpleEnemys
     * @param positionX default position X
     * @param positionY default position Y
     * @param lengthOfRoute tells mow much should the enemy go to the right before going back
     */
    public abstract void loadData(Image image, double positionX, double positionY, double lengthOfRoute);
}
