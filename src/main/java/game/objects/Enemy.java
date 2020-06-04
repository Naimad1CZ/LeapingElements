package game.objects;

import game.World;
import javafx.scene.image.Image;

public abstract class Enemy extends Creature {
    protected int state = 0;

    /**
     *
     * @param image skin of Enemy
     * @param positionX default position X
     * @param positionY default position Y
     * @param movSpeed movement speed
     * @param jumpSpeed jumping force
     * @param swimmingSpeed swimming speed
     */
    public Enemy(Image image, double positionX, double positionY, double movSpeed, double jumpSpeed, double swimmingSpeed) {
        super(image, positionX, positionY, movSpeed, jumpSpeed, swimmingSpeed);
    }

    /**
     * Do some moves according to the implemented logic. Mostly call start/stop moving right/left/up based on current position and state
     *
     * @param delta time in seconds since the last update
     */
    protected abstract void doLogic(double delta);

    @Override
    public String getName() {
        return "An enemy";
    }

    /**
     * Do logic and update the position.
     * @param delta time in seconds since the last update
     * @param world world in which the creature is
     * @return death code
     */
    @Override
    public int updatePosition(double delta, World world) {
        doLogic(delta);
        return super.updatePosition(delta, world);
    }
}
