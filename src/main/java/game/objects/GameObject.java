package game.objects;

import game.Game;
import game.World;
import javafx.geometry.BoundingBox;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public abstract class GameObject {
    protected final double startPosX;
    protected final double startPosY;

    protected double posX;
    protected double posY;

    protected double speedX = 0;
    protected double speedY = 0;

    protected Image img;

    protected int width;
    protected int height;

    /**
     * @param image skin of the object
     * @param positionX default position X
     * @param positionY default position Y
     */
    public GameObject(Image image, double positionX, double positionY) {
        startPosX = positionX;
        startPosY = positionY;
        posX = positionX;
        posY = positionY;
        img = image;
        width = (int) image.getWidth();
        height = (int) image.getHeight();
    }

    /**
     * We get bounding boxes smaller than the actual object because objects usually doesn't have 100% of their size
     * filled with skin.
     *
     * @return hit box
     */
    public BoundingBox getBoundingBox() {
        return new BoundingBox(posX + 0.05 * width, posY + 0.05 * height, 0.9 * width, 0.9 * height);
    }

    /**
     *
     * @return bounding box of the size of object image
     */
    private BoundingBox getSkinBoundingBox() {
        return new BoundingBox(posX, posY, width, height);
    }

    public abstract String getName();

    /**
     * Updates position according to various forces.
     *
     * @param delta time in seconds since the last update
     * @param world world in which the object is
     * @return death code
     */
    public abstract int updatePosition(double delta, World world);

    /**
     * Interact with other objects.
     *
     * @param world world in which the creature is
     * @return death code
     */
    public abstract int updateWithOtherObjects(World world);

    /**
     * Draw the object.
     * @param gc graphics context
     * @param leftLabel X coordinate which is currently on the most left part of the screen
     * @param topLabel Y coordinate which is currently on the most top part of the screen
     */
    public void draw(GraphicsContext gc, int leftLabel, int topLabel) {
        if (getSkinBoundingBox().intersects(new BoundingBox(leftLabel, topLabel, Game.WIDTH, Game.HEIGHT))) {
            gc.drawImage(img, (int) (posX - leftLabel), (int) (posY - topLabel));
        }
    }
}
