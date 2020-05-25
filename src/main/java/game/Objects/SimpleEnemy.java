package game.Objects;

import javafx.scene.image.Image;

import java.util.ArrayList;

public class SimpleEnemy extends Enemy {
    private final double routeLength;

    /**
     * @param image
     * @param positionX
     * @param positionY
     * @param lengthOfRoute tells mow much should the enemy go to the right before going back
     */
    public SimpleEnemy(Image image, double positionX, double positionY, double lengthOfRoute) {
        super(image, positionX, positionY, 350, 0, 150);
        routeLength = lengthOfRoute;
    }

    @Override
    protected void doLogic(double delta) {
        if (state == 0) {
            if (posY >= START_POS_Y + routeLength) {
                stopMovingRight();
                state = 1;
                doLogic(delta);
            } else {
                startMovingRight();
            }
        } else if (state == 1) {
            if (posY <= START_POS_Y) {
                stopMovingLeft();
                state = 0;
                doLogic(delta);
            } else {
                startMovingLeft();
            }
        }
    }

    /**
     * Simple enemy doesn't die or anything if it encounters anything
     * @param gameObjects
     * @return 0
     */
    @Override
    protected int collideWithOtherObjects(ArrayList<GameObject> gameObjects) {
        return 0;
    }
}
