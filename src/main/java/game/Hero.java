package game;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import java.awt.geom.Point2D;

public class Hero extends GameObject{
    private boolean moveRight = false;
    private boolean moveLeft = false;

    public Hero() {
        super(new Image(Hero.class.getClassLoader().getResourceAsStream("other/Ice_hero.png")),
                100,0, 300);
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

    private int updateWithOtherObjects(GameObjects gameObjects) {
        return 0;
    }

    public int update(double delta, World world) {
        // y movement
        speedY += World.GRAVITY * delta;
        posY += speedY * delta;

        // x movement
        if (moveRight == true) {
            posX += MOVEMENT_SPEED * delta;
        }
        if (moveLeft == true) {
            posX -= MOVEMENT_SPEED * delta;
        }

        //System.out.println(posX + " " + posY);

        int died = collideWithTerrain(world.getTerrain());
        if (died > 0) {
            System.out.println("You died");
            alive = false;
            return died;
        }

        //System.out.println(posX + " " + posY);

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
