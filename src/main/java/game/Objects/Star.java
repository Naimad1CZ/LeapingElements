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

    public String getName() {
        return "A star";
    }

    public int updatePosition(double delta, World world) {
        return 0;
    }

    public int updateWithOtherObjects(World world) {
        if (lives == false) {
            // getting claimed
            return 201;
        } else {
            return 0;
        }
    }
}
