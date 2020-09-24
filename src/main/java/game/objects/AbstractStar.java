package game.objects;

import javafx.scene.image.Image;

public abstract class AbstractStar extends GameObject {
    public abstract void loadData(Image image, double positionX, double positionY, int score);

    public abstract int claim();

    public abstract int getValue();
}
