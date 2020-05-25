package game.Objects;

import game.World;
import javafx.scene.image.Image;

public class Star extends GameObject {
    protected boolean lives = true;
    protected int value;

    public Star(Image image, double positionX, double positionY, int score) {
        super(image, positionX, positionY);
        value = score;
    }

    public int claim() {
        if (lives == false) {
            return 0;
        }
        lives = false;
        return value;
    }

    @Override
    public int update(double delta, World world) {
        if (lives == false) {
            // getting claimed
            return 201;
        } else {
            return 0;
        }
    }
}
