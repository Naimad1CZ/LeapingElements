package damian.myjavagame.plugin.basicplugin.objectsimplementations;

import damian.myjavagame.plugin.api.game.AbstractWorld;
import damian.myjavagame.plugin.api.objects.AbstractStar;
import damian.myjavagame.plugin.api.utils.Enums;
import javafx.scene.image.Image;

public class Star extends AbstractStar {
    protected boolean lives = true;
    protected int value;

    /**
     *
     * @param image skin of star
     * @param positionX default position X
     * @param positionY default position Y
     * @param score how much score points is got when claiming the star
     */
    @Override
    public void loadData(Image image, double positionX, double positionY, int score) {
        startPosX = positionX;
        startPosY = positionY;
        posX = positionX;
        posY = positionY;
        img = image;
        width = (int) image.getWidth();
        height = (int) image.getHeight();

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
    public Enums.Death updatePosition(double delta, AbstractWorld world) {
        return Enums.Death.NONE;
    }

    /**
     *
     * @param world world in which the creature is
     * @return Death.other if got claimed, else Death.none
     */
    @Override
    public Enums.Death updateWithOtherObjects(AbstractWorld world) {
        if (!lives) {
            // getting claimed
            return Enums.Death.OTHER;
        } else {
            return Enums.Death.NONE;
        }
    }
}
