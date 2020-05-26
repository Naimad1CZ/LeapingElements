package game.Objects;

import game.World;
import javafx.scene.image.Image;

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

    @Override
    public String getName() {
        return "A simple enemy";
    }

    /**
     * Simple enemy doesn't die or anything if it encounters anything
     *
     * @param world
     * @return 0
     */
    @Override
    protected int collideWithOtherObjects(World world) {
        return 0;
    }
}
