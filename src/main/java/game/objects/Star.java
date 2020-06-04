package game.objects;

import game.World;
import javafx.scene.image.Image;

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
     * @return 0
     */
    @Override
    public int updatePosition(double delta, World world) {
        return 0;
    }

    /**
     *
     * @param world world in which the creature is
     * @return 201 if got claimed
     */
    @Override
    public int updateWithOtherObjects(World world) {
        if (!lives) {
            // getting claimed
            return 201;
        } else {
            return 0;
        }
    }
}
