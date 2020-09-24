package game.objects;

import game.Terrain;
import game.World;
import utils.Enums.Death;
import utils.Enums.TileType;

import java.awt.geom.Point2D;

public abstract class Creature extends GameObject {
    protected double movementSpeed;
    protected double jumpForce;

    protected double swimSpeed;
    protected boolean isSwimming = false;

    protected boolean alive = true;
    protected boolean onGround = false;

    private boolean moveRight = false;
    private boolean moveLeft = false;
    private boolean moveUp = false;

    private boolean waitingForJump = false;

    /**
     * Get creature position.
     * @return creature position
     */
    public Point2D.Double getPosition() {
        return new Point2D.Double(posX, posY);
    }

    /**
     * Start intentionally moving left.
     */
    public void startMovingLeft() {
        moveLeft = true;
    }

    /**
     * Stop intentionally moving left.
     */
    public void stopMovingLeft() {
        moveLeft = false;
    }

    /**
     * Start intentionally moving right.
     */
    public void startMovingRight() {
        moveRight = true;
    }

    /**
     * Stop intentionally moving right.
     */
    public void stopMovingRight() {
        moveRight = false;
    }

    /**
     * Start moving intentionally up, jump in on ground.
     */
    public void startMovingUp() {
        moveUp = true;
        // if on ground, jump
        if (onGround) {
            waitingForJump = true;
        }
    }

    /**
     * Stop intentionally moving up.
     */
    public void stopMovingUp() {
        moveUp = false;
        waitingForJump = false;
    }

    /**
     *
     * @return if the creature is alive.
     */
    public boolean isAlive() {
        return alive;
    }

    /**
     * Kills the creature.
     */
    public void kill() {
        alive = false;
    }

    /**
     * Respawns the player in the place of default position.
     */
    public void respawn() {
        alive = true;
        speedX = 0;
        speedY = 0;
        posX = startPosX;
        posY = startPosY;
    }

    /**
     * Check collisions with terrain and corrects position of GameObject to not be inside solid terrain and to be integers.
     *
     * @param terrain terrain of the world
     * @return cause of death, Death.none if object didn't die
     */
    protected Death collideWithTerrain(Terrain terrain) {
        boolean inWater = false;

        int intPosX = (int) posX;
        if (intPosX < 0) {
            // for calculating block types
            intPosX -= terrain.TILE_WIDTH;
        }
        int intPosY = (int) posY;
        if (intPosY < 0) {
            intPosY -= terrain.TILE_WIDTH;
        }

        int upperYBlock = intPosY / terrain.TILE_HEIGHT;
        int upYBlock = (intPosY + height / 6) / terrain.TILE_HEIGHT;
        int lowYBlock = (intPosY + 5 * height / 6) / terrain.TILE_HEIGHT;
        int lowerYBlock = (intPosY + height - 1) / terrain.TILE_HEIGHT;

        int lefterXBlock = intPosX / terrain.TILE_WIDTH;
        int leftXBlock = (intPosX + width / 6) / terrain.TILE_WIDTH;
        int rightXBlock = (intPosX + 5 * width / 6) / terrain.TILE_WIDTH;
        int righterXBlock = (intPosX + width - 1) / terrain.TILE_WIDTH;

        TileType upperLeftType = terrain.getTileType(leftXBlock, upperYBlock);
        TileType upperRightType = terrain.getTileType(rightXBlock, upperYBlock);
        TileType lowerLeftType = terrain.getTileType(leftXBlock, lowerYBlock);
        TileType lowerRightType = terrain.getTileType(rightXBlock, lowerYBlock);

        TileType righterUpType = terrain.getTileType(righterXBlock, upYBlock);
        TileType righterLowType = terrain.getTileType(righterXBlock, lowYBlock);
        TileType lefterUpType = terrain.getTileType(lefterXBlock, upYBlock);
        TileType lefterLowType = terrain.getTileType(lefterXBlock, lowYBlock);

        // if I'm in solid from both side, don't do right and left correction
        if (!(righterLowType == TileType.SOLID && lefterLowType == TileType.SOLID)) {
            if (speedX >= 0 && (righterUpType == TileType.SOLID || righterLowType == TileType.SOLID)) {
                posX = righterXBlock * terrain.TILE_WIDTH - width;
                speedX = 0;

                // recalculate
                rightXBlock = (int) (posX + 5 * width / 6) / terrain.TILE_WIDTH;
                upperRightType = terrain.getTileType(rightXBlock, upperYBlock);
                lowerRightType = terrain.getTileType(rightXBlock, lowerYBlock);
            } else if (righterUpType == TileType.WATER || righterLowType == TileType.WATER) {
                inWater = true;
            }

            if (speedX <= 0 && (lefterUpType == TileType.SOLID || lefterLowType == TileType.SOLID)) {
                posX = (lefterXBlock + 1) * terrain.TILE_WIDTH;
                speedX = 0;

                // recalculate
                leftXBlock = (int) (posX + width / 6) / terrain.TILE_WIDTH;
                upperLeftType = terrain.getTileType(leftXBlock, upperYBlock);
                lowerLeftType = terrain.getTileType(leftXBlock, lowerYBlock);
            } else if (lefterUpType == TileType.WATER || lefterLowType == TileType.WATER) {
                inWater = true;
            }
        }

        if (speedY >= 0 && (lowerLeftType == TileType.SOLID || lowerRightType == TileType.SOLID)) {
            // if we fall on solid, get up so we exactly stand on solid
            posY = lowerYBlock * terrain.TILE_HEIGHT - height;
            speedY = 0;
            onGround = true;
        } else if (lowerLeftType == TileType.WATER || lowerRightType == TileType.WATER) {
            inWater = true;
        } else if (lowerLeftType == TileType.OUT || lowerRightType == TileType.OUT) {
            // dieeeeeee (by falling out of the world)
            return Death.FALL_OUT;
        } else if (speedY > World.GRAVITY / 20 || speedY < 0) {
            onGround = false;
        }

        if (speedY < 0 && (upperLeftType == TileType.SOLID || upperRightType == TileType.SOLID)) {
            // if we hit some solid with out head, get down so we are not stuck inside
            posY = (upperYBlock + 1) * terrain.TILE_HEIGHT;
            speedY = 0;
        } else if (upperLeftType == TileType.WATER || lowerRightType == TileType.WATER) {
            inWater = true;
        }

        if (inWater) {
            if (swimSpeed > 0) {
                isSwimming = true;
                if (upperLeftType == TileType.WATER || upperRightType == TileType.WATER) {
                    // go up;
                    speedY = -swimSpeed * 3 / 4;
                } else if (lefterUpType == TileType.WATER || righterUpType == TileType.WATER) {
                    // stay floating
                    speedY = 0;
                } else if (lefterLowType == TileType.WATER || righterLowType == TileType.WATER) {
                    // go sligthly down
                    speedY = swimSpeed * 3 / 4;
                } else {
                    // go down a lot
                    speedY = swimSpeed * 1;
                }
            } else {
                if (lefterLowType == TileType.WATER || righterLowType == TileType.WATER) {
                    // if not just touching the water slightly
                    // drooown
                    return Death.DROWN;
                }
            }
        } else {
            isSwimming = false;
        }

        return Death.NONE;
    }

    /**
     * Updates coordinates depending on gravitation, presence in water, intentional moves, and corrections
     * to not be stuck inside terrain.
     * @param delta time in seconds since the last update
     * @param world world in which the creature is
     * @return death code
     */
    @Override
    public Death updatePosition(double delta, World world) {
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
            // if is supposed to jump, then jump
            if (waitingForJump) {
                speedY -= jumpForce;
                onGround = false;
                waitingForJump = false;
            }

            // change speed by gravity
            speedY += World.GRAVITY * delta;
            if (speedY > World.GRAVITY * 1.5) {
                // max falling speed (caused by air resistance or something)
                speedY = World.GRAVITY * 1.5;
            }

            if (moveRight) {
                if (onGround) {
                    posX += movementSpeed * delta;
                } else {
                    // move slower when in air
                    posX += movementSpeed * delta * 2 / 3;
                }

            }
            if (moveLeft) {
                if (onGround) {
                    posX -= movementSpeed * delta;
                } else {
                    // move slower when in air
                    posX -= movementSpeed * delta * 2 / 3;
                }

            }
        }

        // change the position based on speed
        posY += speedY * delta;

        return collideWithTerrain(world.getTerrain());
    }

}
