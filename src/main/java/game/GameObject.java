package game;

import javafx.scene.image.Image;

public abstract class GameObject {
    protected double movementSpeed;
    protected double jumpForce;

    protected Image img;

    protected int width;
    protected int height;

    protected final double START_POS_X;
    protected final double START_POS_Y;

    protected double posX;
    protected double posY;

    protected double speedX = 0;
    protected double speedY = 0;

    protected boolean alive = true;
    protected boolean onGround = false;

    public GameObject(Image image, double positionX, double positionY, double movSpeed, double jumpSpeed) {
        img = image;
        width = (int)image.getWidth();
        height = (int)image.getHeight();
        posX = positionX;
        posY = positionY;
        START_POS_X = positionX;
        START_POS_Y = positionY;
        movementSpeed = movSpeed;
        jumpForce = jumpSpeed;
    }

    public boolean isAlive() {
        return alive;
    }

    /**
     * Check collisions with terrain and corrects position of GameObject to not be inside solid terrain and to be integers.
     * @param terrain
     * @return cause of death, 0 if object didn't die
     */
    public int collideWithTerrain(Terrain terrain) {
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
            // drown
            return 2;
        }

        if (speedX <= 0 && (lefterUpType.equals("solid") || lefterLowType.equals("solid"))) {
            posX = (lefterXBlock + 1) * terrain.TILE_WIDTH;
            speedX = 0;

            // recalculate
            leftXBlock = (int)(posX + width / 6) / terrain.TILE_WIDTH;
            upperLeftType = terrain.getTileType(leftXBlock, upperYBlock);
            lowerLeftType = terrain.getTileType(leftXBlock, lowerYBlock);
        } else if (lefterUpType.equals("water") || lefterLowType.equals("water")) {
            // drown
            return 2;
        }


        if (speedY >= 0 && (lowerLeftType.equals("solid") || lowerRightType.equals("solid"))) {
            // if we fall on solid, get up so we exactly stand on solid
            posY = lowerYBlock * terrain.TILE_HEIGHT - height;
            speedY = 0;
            onGround = true;
        } else if (lowerLeftType.equals("out") || lowerRightType.equals("out")) {
            // dieeeeeee (by falling out of the world)
            return 1;
        } else if (lowerLeftType.equals("water") || lowerRightType.equals("water")) {
            // drown
            return 2;
        } else if (speedY < 0) {
            onGround = false;
        }

        if (speedY <= 0 && (upperLeftType.equals("solid") || upperRightType.equals("solid"))) {
            // if we hit some solid with out head, get down so we are not stuck inside
            posY = (upperYBlock + 1) * terrain.TILE_HEIGHT;
            speedY = 0;
        } else if (upperLeftType.equals("water") || lowerRightType.equals("water")) {
            // drown
            return 2;
        }

        return 0;
    }

    public abstract int update(double delta, World world);
}
