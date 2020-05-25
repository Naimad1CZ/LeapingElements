package game.Objects;

import game.GameObjects;
import game.Terrain;
import game.World;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import java.awt.geom.Point2D;
import java.util.ArrayList;

public abstract class Creature extends GameObject {
    protected double movementSpeed = 300;
    protected double jumpForce = 330;

    protected double swimSpeed = 0;
    protected boolean isSwimming = false;

    protected boolean alive = true;
    protected boolean onGround = false;

    private boolean moveRight = false;
    private boolean moveLeft = false;
    private boolean moveUp = false;

    public Creature(Image image, double positionX, double positionY, double movSpeed, double jumpSpeed, double swimmingSpeed) {
        super(image, positionX, positionY);

        if (movSpeed > 0) {
            movementSpeed = movSpeed;
        }
        if (jumpSpeed > 0) {
            jumpForce = jumpSpeed;
        }
        swimSpeed = swimmingSpeed;
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

    public void startMovingUp() {
        moveUp = true;
        // if on ground, jump
        if (onGround) {
            speedY -= jumpForce;
            onGround = false;
        }
    }

    public void stopMovingUp() {
        moveUp = false;
    }

    public boolean isAlive() {
        return alive;
    }

    public void respawn() {
        alive = true;
        posX = START_POS_X;
        posY = START_POS_Y;
    }

    /**
     * Check collisions with terrain and corrects position of GameObject to not be inside solid terrain and to be integers.
     * @param terrain
     * @return cause of death, 0 if object didn't die
     */
    protected int collideWithTerrain(Terrain terrain) {
        boolean inWater = false;

        posX = (int)posX;
        posY = (int)posY;

        int upperYBlock = (int)posY / terrain.TILE_HEIGHT;
        int upYBlock = (int) (posY + height / 6) / terrain.TILE_HEIGHT;
        int lowYBlock = (int) (posY + 5 * height / 6) / terrain.TILE_HEIGHT;
        int lowerYBlock = (int)(posY + height - 1) / terrain.TILE_HEIGHT;

        int lefterXBlock = (int)posX / terrain.TILE_WIDTH;
        int leftXBlock = (int)(posX + width / 6) / terrain.TILE_WIDTH;
        int rightXBlock = (int)(posX + 5 * width / 6) / terrain.TILE_WIDTH;
        int righterXBlock = (int)(posX + width - 1) / terrain.TILE_WIDTH;

        String upperLeftType = terrain.getTileType(leftXBlock, upperYBlock);
        String upperRightType = terrain.getTileType(rightXBlock, upperYBlock);
        String lowerLeftType = terrain.getTileType(leftXBlock, lowerYBlock);
        String lowerRightType = terrain.getTileType(rightXBlock, lowerYBlock);

        String righterUpType = terrain.getTileType(righterXBlock, upYBlock);
        String righterLowType = terrain.getTileType(righterXBlock, lowYBlock);
        String lefterUpType = terrain.getTileType(lefterXBlock, upYBlock);
        String lefterLowType = terrain.getTileType(lefterXBlock, lowYBlock);

        if (speedX >= 0 && (righterUpType.equals("solid") || righterLowType.equals("solid"))) {
            posX = righterXBlock * terrain.TILE_WIDTH - width;
            speedX = 0;

            // recalculate
            rightXBlock = (int)(posX + 5 * width / 6) / terrain.TILE_WIDTH;
            upperRightType = terrain.getTileType(rightXBlock, upperYBlock);
            lowerRightType = terrain.getTileType(rightXBlock, lowerYBlock);
        } else if (righterUpType.equals("water") || righterLowType.equals("water")) {
            inWater = true;
        }

        if (speedX <= 0 && (lefterUpType.equals("solid") || lefterLowType.equals("solid"))) {
            posX = (lefterXBlock + 1) * terrain.TILE_WIDTH;
            speedX = 0;

            // recalculate
            leftXBlock = (int)(posX + width / 6) / terrain.TILE_WIDTH;
            upperLeftType = terrain.getTileType(leftXBlock, upperYBlock);
            lowerLeftType = terrain.getTileType(leftXBlock, lowerYBlock);
        } else if (lefterUpType.equals("water") || lefterLowType.equals("water")) {
            inWater = true;
        }

        if (speedY >= 0 && (lowerLeftType.equals("solid") || lowerRightType.equals("solid"))) {
            // if we fall on solid, get up so we exactly stand on solid
            posY = lowerYBlock * terrain.TILE_HEIGHT - height;
            speedY = 0;
            onGround = true;
        } else if (lowerLeftType.equals("water") || lowerRightType.equals("water")) {
            inWater = true;
        }
        else if (lowerLeftType.equals("out") || lowerRightType.equals("out")) {
            // dieeeeeee (by falling out of the world)
            return 1;
        } else if (speedY < 0) {
            onGround = false;
        }

        if (speedY <= 0 && (upperLeftType.equals("solid") || upperRightType.equals("solid"))) {
            // if we hit some solid with out head, get down so we are not stuck inside
            posY = (upperYBlock + 1) * terrain.TILE_HEIGHT;
            speedY = 0;
        } else if (upperLeftType.equals("water") || lowerRightType.equals("water")) {
            inWater = true;
        }

        if (inWater) {
            if (swimSpeed > 0) {
                isSwimming = true;
                if (upperLeftType.equals("water") || upperRightType.equals("water")) {
                    // go up;
                    speedY = -swimSpeed * 3 / 4;//-swimSpeed;
                } else if (lefterUpType.equals("water") || righterUpType.equals("water")) {
                    // stay floating
                    speedY = 0;
                } else if (lefterLowType.equals("water") || righterLowType.equals("water")) {
                    // go sligthly down
                    speedY = swimSpeed * 3 / 4;
                } else {
                    // go down a lot
                    speedY = swimSpeed * 1.5;
                }
            } else {
                if (!(lowerLeftType.equals("water") || lowerRightType.equals("water"))) {
                    // if not just touching the water slightly
                    // drooown
                    return 2;
                }
            }
        } else {
            isSwimming = false;
        }

        return 0;
    }

    protected abstract int collideWithOtherObjects(ArrayList<GameObject> gameObjects);

    public int update(double delta, World world) {
        // change of position because of movement triggered by keyboard
        if (isSwimming) {
            if (moveUp) {
                posY -= swimSpeed * delta;
            }
            if (moveRight) {
                posX += swimSpeed * delta;
            }
            if (moveLeft) {
                posX -= swimSpeed * delta;
            }
        } else {
            // change speed by gravity
            speedY += World.GRAVITY * delta;
            if (speedY > World.GRAVITY * 1.5) {
                // max falling speed (caused by air resistance or something)
                speedY = World.GRAVITY * 1.5;
            }

            if (moveRight) {
                posX += movementSpeed * delta;
            }
            if (moveLeft) {
                posX -= movementSpeed * delta;
            }
        }

        // change the position based on speed
        posY += speedY * delta;

        int died = collideWithTerrain(world.getTerrain());
        if (died > 0) {
            System.out.println("You died");
            alive = false;
            return died;
        }

        died = collideWithOtherObjects(world.getGameObjects());

        if (died > 0) {
            System.out.println("You died");
            alive = false;
        }
        return died;
    }

}
