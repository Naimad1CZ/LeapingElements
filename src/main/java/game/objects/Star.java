package game.objects;

import game.World;
import javafx.scene.image.Image;
import utils.Enums.Death;

public class Star extends GameObject {
    protected boolean lives = true;
    protected int value;

    /**
     *
     * @param image skin of star
     * @param positionX default position X
     * @param positionY default position Y
     * @param score how much score points is got when claiming the star
     */
    public Star(Image image, double positionX, double positionY, int score) {
        super(image, positionX, positionY);
        value = score;
    }

    /**
     * Claim the star.
     * @return amount of score points to be added
     */
    public int claim() {
        if (!lives) {
            return 0;
        }
        lives = false;
        return value;
    }

    @Override
    public String getName() {
        return "A star";
    }

    /**
     *
     * @return how much score points is got when claiming the star
     */
    public int getValue() {
        return value;
    }

    /**
     * Don't do anything, but neede to be implemented.
     * @param delta time in seconds since the last update
     * @param world world in which the creature is
     * @return Death.none
     */
    @Override
    public Death updatePosition(double delta, World world) {
        return Death.NONE;
    }

    /**
     *
     * @param world world in which the creature is
     * @return Death.other if got claimed, else Death.none
     */
    @Override
    public Death updateWithOtherObjects(World world) {
        if (!lives) {
            // getting claimed
            return Death.OTHER;
        } else {
            return Death.NONE;
        }
    }
}
