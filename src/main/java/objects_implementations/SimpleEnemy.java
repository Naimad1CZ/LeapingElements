package objects_implementations;

import game.World;
import game.objects.AbstractEnemy;
import javafx.scene.image.Image;
import utils.Enums.Death;

public class SimpleEnemy extends AbstractEnemy {
    protected int state = 0;
    private double routeLength;

    /**
     * @param image skin of SimpleEnemys
     * @param positionX default position X
     * @param positionY default position Y
     * @param lengthOfRoute tells mow much should the enemy go to the right before going back
     */
    @Override
    public void loadData(Image image, double positionX, double positionY, double lengthOfRoute) {
        startPosX = positionX;
        startPosY = positionY;
        posX = positionX;
        posY = positionY;
        img = image;
        width = (int) image.getWidth();
        height = (int) image.getHeight();

        movementSpeed = 150;
        jumpForce = 0;
        swimSpeed = 100;

        routeLength = lengthOfRoute;
    }

    /**
     * Move right if not lengthOfRoute distance from original position Y.
     * @param delta time in seconds since the last update
     */
    protected void doLogic(double delta) {
        if (state == 0) {
            if (posX >= startPosX + routeLength) {
                stopMovingRight();
                state = 1;
                doLogic(delta);
            } else {
                startMovingRight();
            }
        } else if (state == 1) {
            if (posX <= startPosX) {
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
     * Simple enemy doesn't die or anything if it encounters anything.
     *
     * @param world world in which the creature is
     * @return Death.none
     */
    @Override
    public Death updateWithOtherObjects(World world) {
        return Death.NONE;
    }

    /**
     * Do logic and update the position.
     * @param delta time in seconds since the last update
     * @param world world in which the creature is
     * @return death code
     */
    @Override
    public Death updatePosition(double delta, World world) {
        doLogic(delta);
        return super.updatePosition(delta, world);
    }
}
