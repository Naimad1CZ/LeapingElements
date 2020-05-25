package game.Objects;

import game.Objects.Creature;
import javafx.scene.image.Image;

import java.util.ArrayList;

public class Hero extends Creature {
    protected String type;

    /**
     *
     * @param image
     * @param positionX
     * @param positionY
     * @param heroType "FireHero", "IceHero" or other (custom) hero
     * @param movSpeed put 0 to get default value (300)
     * @param jumpSpeed put 0 to get default value (330)
     * @param swimmingSpeed put 0 to drown when in water, otherwise positive
     */
    public Hero(Image image, double positionX, double positionY, String heroType, double movSpeed, double jumpSpeed, double swimmingSpeed) {
        super(image, positionX, positionY, movSpeed, jumpSpeed, swimmingSpeed);
        type = heroType;
    }

    @Override
    protected int collideWithOtherObjects(ArrayList<GameObject> gameObjects) {


        // getting/killing a star!
        // if type == ice then get klled by fire hero

        System.out.println("FireHero");
        return 0;
    }

}
