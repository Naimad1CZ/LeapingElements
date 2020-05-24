package game;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import java.awt.geom.Point2D;

public class Hero extends GameObject{
    private boolean moveRight = false;
    private boolean moveLeft = false;

    public Hero(String pathToImage) {
        super(new Image(Hero.class.getClassLoader().getResourceAsStream(pathToImage)),
                100, 0, 300, 330);
    }

    public Point2D.Double getPosition() {
        return new Point2D.Double(posX, posY);
    }

    public void startMovingLeft() {
        moveLeft = true;
    }

    public void stopMovingLeft() {
        moveLeft = false;
    }

    public void startMovingRight() {
        moveRight = true;
    }

    public void stopMovingRight() {
        moveRight = false;
    }

    public void jump() {
        if (onGround) {
            speedY -= jumpForce;
            onGround = false;
        }
    }

    public void respawn() {
        alive = true;
        posX = START_POS_X;
        posY = START_POS_Y;
    }

    private int updateWithOtherObjects(GameObjects gameObjects) {
        return 0;
    }

    public int update(double delta, World world) {
        // y movement
        speedY += World.GRAVITY * delta;
        if (speedY > World.GRAVITY * 1.5) {
            // max falling speed (caused by air resistance or something)
            speedY = World.GRAVITY * 1.5;
        }
        posY += speedY * delta;

        // x movement
        if (moveRight) {
            posX += movementSpeed * delta;
        }
        if (moveLeft) {
            posX -= movementSpeed * delta;
        }

        int died = collideWithTerrain(world.getTerrain());
        if (died > 0) {
            System.out.println("You died");
            alive = false;
            return died;
        }

        died = updateWithOtherObjects(world.getGameObjects());

        if (died > 0) {
            System.out.println("You died");
            alive = false;
        }
        return died;
    }

    public void draw(GraphicsContext gc, int leftLabel, int topLabel) {
        gc.drawImage(img, posX - leftLabel, posY - topLabel);
    }


}
