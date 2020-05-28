package game.Objects;

import game.Game;
import game.World;
import javafx.geometry.BoundingBox;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public abstract class GameObject {
    protected final double START_POS_X;
    protected final double START_POS_Y;

    protected double posX;
    protected double posY;

    protected double speedX = 0;
    protected double speedY = 0;

    protected Image img;

    protected int width;
    protected int height;

    /**
     * @param image
     * @param positionX
     * @param positionY
     */
    public GameObject(Image image, double positionX, double positionY) {
        START_POS_X = positionX;
        START_POS_Y = positionY;
        posX = positionX;
        posY = positionY;
        img = image;
        width = (int) image.getWidth();
        height = (int) image.getHeight();
    }

    /**
     * we get bounding boxes smaller than the actual object because objects usually doesn't have 100% of their size
     * filled with skin
     *
     * @return
     */
    public BoundingBox getBoundingBox() {
        return new BoundingBox(posX + 0.05 * width, posY + 0.05 * height, 0.9 * width, 0.9 * height);
    }

    private BoundingBox getSkinBoundingBox() {
        return new BoundingBox(posX, posY, width, height);
    }

    public abstract String getName();

    public abstract int updatePosition(double delta, World world);

    public abstract int updateWithOtherObjects(World world);

    public void draw(GraphicsContext gc, int leftLabel, int topLabel) {
        if (getSkinBoundingBox().intersects(new BoundingBox(leftLabel, topLabel, Game.WIDTH, Game.HEIGHT))) {
            gc.drawImage(img, (int) (posX - leftLabel), (int) (posY - topLabel));
        }
    }
}
