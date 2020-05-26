package game.Objects;

import game.World;
import javafx.scene.image.Image;

import java.util.ArrayList;

public class Hero extends Creature {
    private final String type;
    protected int maxLives;
    protected int currentLives;

    /**
     * @param image
     * @param positionX
     * @param positionY
     * @param heroType      "FireHero", "IceHero" or other (custom) hero
     * @param movSpeed      put 0 to get default value (300)
     * @param jumpSpeed     put 0 to get default value (330)
     * @param swimmingSpeed put 0 to drown when in water, otherwise positive
     */
    public Hero(Image image, double positionX, double positionY, String heroType, double movSpeed, double jumpSpeed, double swimmingSpeed, int lives) {
        super(image, positionX, positionY, movSpeed, jumpSpeed, swimmingSpeed);
        type = heroType;
        maxLives = lives;
        currentLives = maxLives;
    }

    public int getMaxLives() {
        return maxLives;
    }

    public int getCurrentLives() {
        return currentLives;
    }

    public void looseLife() {
        currentLives--;
    }

    public Image getImage() {
        return img;
    }

    public String getType() {
        return type;
    }

    @Override
    public String getName() {
        if (type.equals("fire")) {
            return "Fire hero";
        } else if (type.equals("ice")) {
            return "Ice hero";
        } else {
            return "Hero";
        }
    }

    @Override
    protected int collideWithOtherObjects(World world) {
        ArrayList<GameObject> gameObjects = world.getGameObjects();

        for (var gameObject : gameObjects) {
            if (gameObject != this) {
                if (getBoundingBox().intersects(gameObject.getBoundingBox())) {
                    if (gameObject instanceof Hero) {
                        if (type.equals("ice") && ((Hero) gameObject).getType().equals("fire")) {
                            // die by melting
                            return 11;
                        }
                    } else if (gameObject instanceof Enemy) {
                        if (gameObject instanceof SimpleEnemy) {
                            // get killed by simple enemy
                            return 21;
                        } else {
                            // get killed by some other enemy
                            return 22;
                        }
                    } else if (gameObject instanceof Turret) {
                        if (((Turret) gameObject).type.equals("fire")) {
                            // get killed by fire turret
                            return 41;
                        } else if (((Turret) gameObject).type.equals("ice")) {
                            // get killed by fire turret
                            return 42;
                        } else if (((Turret) gameObject).type.equals("combined")) {
                            // get killed by fire turret
                            return 43;
                        } else {
                            // get killed by turret
                            return 44;
                        }
                    } else if (gameObject instanceof Projectile) {
                        if (((Projectile) gameObject).getType().equals("fire") && !type.equals("fire")) {
                            // killed by fire bullet
                            return 51;
                        } else if (((Projectile) gameObject).getType().equals("ice") && !type.equals("ice")) {
                            // killed by ice bullet
                            return 52;
                        } else if (((Projectile) gameObject).getType().equals("combined") && !type.equals("combined")) {
                            // killed by combined bullet
                            return 53;
                        } else {
                            // killed by some other bullet
                            return 54;
                        }
                    } else if (gameObject instanceof Star) {
                        int plusScore = ((Star) gameObject).claim();
                        if (plusScore > 0) {
                            world.addScore(plusScore);
                        }
                    }
                }
            }
        }

        return 0;
    }

}
