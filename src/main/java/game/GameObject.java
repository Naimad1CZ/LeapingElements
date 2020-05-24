package game;

import javafx.scene.image.Image;

public abstract class GameObject {
    protected final double MOVEMENT_SPEED;

    protected Image img;

    protected int width;
    protected int height;

    protected double posX;
    protected double posY;

    protected double speedX = 0;
    protected double speedY = 0;

    protected boolean alive = true;

    public GameObject(Image image, double positionX, double positionY, double movementSpeed) {
        img = image;
        width = (int)image.getWidth();
        height = (int)image.getHeight();
        posX = positionX;
        posY = positionY;
        MOVEMENT_SPEED = movementSpeed;
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
        int upYBlock = (int) (posY + height / 3) / terrain.TILE_HEIGHT;
        int lowYBlock = (int) (posY + 2 * height / 3) / terrain.TILE_HEIGHT;
        int lowerYBlock = (int)(posY + height - 1) / terrain.TILE_HEIGHT;

        int lefterXBlock = (int)posX / terrain.TILE_WIDTH;
        int leftXBlock = (int)(posX + width / 3) / terrain.TILE_WIDTH;
        int rightXBlock = (int)(posX + 2 * width / 3) / terrain.TILE_WIDTH;
        int righterXBlock = (int)(posX + width - 1) / terrain.TILE_WIDTH;

        String upLeftType = terrain.getTileType(leftXBlock, upperYBlock);
        String upRightType = terrain.getTileType(rightXBlock, upperYBlock);
        String downLeftType = terrain.getTileType(leftXBlock, lowerYBlock);
        String downRightType = terrain.getTileType(rightXBlock, lowerYBlock);

        String upperLeftType = terrain.getTileType(lefterXBlock, upperYBlock);
        String upperRightType = terrain.getTileType(righterXBlock, upperYBlock);
        String lowerLeftType = terrain.getTileType(lefterXBlock, lowerYBlock);
        String lowerRightType = terrain.getTileType(righterXBlock, lowerYBlock);

        //System.out.print("posY: " + posY);
        if (lowerLeftType.equals("solid") || lowerRightType.equals("solid")) {
            // if we fall on solid, get up so we exactly stand on solid

            posY = lowerYBlock * terrain.TILE_HEIGHT - height;
            //System.out.println(" posY2: " + posY);
            speedY = 0;
            // recalculating lower and higher Y block because of getting up
            lowerYBlock = (int)(posY + height - 1) / terrain.TILE_HEIGHT;
            upperYBlock = (int)posY / terrain.TILE_HEIGHT;

            upperLeftType = terrain.getTileType(lefterXBlock, upperYBlock);
            upperRightType = terrain.getTileType(righterXBlock, upperYBlock);
            lowerLeftType = terrain.getTileType(lefterXBlock, lowerYBlock);
            lowerRightType = terrain.getTileType(righterXBlock, lowerYBlock);
        } else if (lowerLeftType.equals("out") || lowerRightType.equals("out")) {
            // dieeeeeee (by falling out of the world)
            return 1;
        } else if (lowerLeftType == "water" || lowerRightType == "water") {
            // drown
            return 2;
        }

        if (upperLeftType.equals("solid") || upperRightType.equals("solid")) {
            // if we hit some solid with out head, get down so we are not stuck inside
            posY = (upperYBlock + 1) * terrain.TILE_HEIGHT;
            System.out.println("Hej!");
            speedY = 0;
            // recalculating lower and higher Y
            upperYBlock = (int)posY / terrain.TILE_HEIGHT;
            lowerYBlock = (int)(posY + height - 1) / terrain.TILE_HEIGHT;

            upperLeftType = terrain.getTileType(lefterXBlock, upperYBlock);
            upperRightType = terrain.getTileType(righterXBlock, upperYBlock);
            lowerLeftType = terrain.getTileType(lefterXBlock, lowerYBlock);
            lowerRightType = terrain.getTileType(righterXBlock, lowerYBlock);
        } else if (upperLeftType.equals("water") || lowerRightType.equals("water")) {
            // drown
            return 2;
        }

        if (upperRightType.equals("solid") || lowerRightType.equals("solid")) {
            posX = righterXBlock * terrain.TILE_WIDTH - width;
            speedX = 0;
        } else if (upperRightType.equals("water") || lowerRightType.equals("water")) {
            // drown
            return 2;
        }

        if (upperLeftType.equals("solid") || lowerLeftType.equals("solid")) {
            posX = (lefterXBlock + 1) * terrain.TILE_WIDTH;
            speedX = 0;
        } else if (upperLeftType.equals("water") || lowerLeftType.equals("water")) {
            // drown
            return 2;
        }

        return 0;
    }

    public abstract int update(double delta, World world);
}
