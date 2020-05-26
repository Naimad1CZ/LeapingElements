package game.Objects;

import game.World;
import javafx.scene.image.Image;

public abstract class Enemy extends Creature {
    protected int state = 0;

    public Enemy(Image image, double positionX, double positionY, double movSpeed, double jumpSpeed, double swimmingSpeed) {
        super(image, positionX, positionY, movSpeed, jumpSpeed, swimmingSpeed);
    }

    /**
     * Do some moves according to the implemented logic. Mostly call start/stop moving right/left/up based on current position and state
     *
     * @param delta
     */
    protected abstract void doLogic(double delta);

    public String getName() {
        return "An enemy";
    }

    public int updatePosition(double delta, World world) {
        doLogic(delta);
        return super.updatePosition(delta, world);
    }
}
