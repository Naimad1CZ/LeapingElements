package objectsAPI;

import javafx.scene.image.Image;
import utilsAPI.Enums;

public abstract class AbstractHero extends AbstractCreature {
    /**
     * @param image         skin of hero
     * @param positionX     default position X
     * @param positionY     default position Y
     * @param heroType      "FireHero", "IceHero" or other (custom) hero
     * @param movSpeed      put 0 to get default value (300)
     * @param jumpSpeed     put 0 to get default value (330)
     * @param swimmingSpeed put 0 to drown when in water, otherwise positive
     * @param lives         starting (and maximum) number of lives
     * @param HUDImgSource  source of image to be drawn on the top of the screen in HUD
     */
    public abstract void loadData(Image image, double positionX, double positionY, Enums.HeroType heroType,
                                  double movSpeed, double jumpSpeed, double swimmingSpeed, int lives,
                                  String HUDImgSource);

    public abstract int getMaxLives();

    public abstract int getCurrentLives();

    public abstract void looseLife();

    public abstract Image getImage();

    public abstract String getHUDImageSource();

    public abstract Enums.HeroType getType();
}
