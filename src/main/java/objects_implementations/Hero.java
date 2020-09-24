package objects_implementations;

import game.World;
import game.objects.*;
import javafx.scene.image.Image;
import utils.Enums.Death;
import utils.Enums.HeroType;
import utils.Enums.TurretAndProjectileType;

import java.util.List;

public class Hero extends AbstractHero {
    private HeroType type;
    protected int maxLives;
    protected int currentLives;
    protected String HUDImageSource;

    /**
     * @param image         skin of hero
     * @param positionX     default position X
     * @param positionY     default position Y
     * @param heroType      "FireHero", "IceHero" or other (custom) hero
     * @param movSpeed      put 0 to get default value (300)
     * @param jumpSpeed     put 0 to get default value (330)
     * @param swimmingSpeed put 0 to drown when in water, otherwise positive
     * @param lives         starting (and maximum) number of lives
     * @param HUDImgSource  source of image to be drawn on the top of the screen in HUD
     */
    @Override
    public void loadData(Image image, double positionX, double positionY, HeroType heroType, double movSpeed, double jumpSpeed, double swimmingSpeed, int lives, String HUDImgSource) {
        startPosX = positionX;
        startPosY = positionY;
        posX = positionX;
        posY = positionY;
        img = image;
        width = (int) image.getWidth();
        height = (int) image.getHeight();

        movementSpeed = movSpeed;
        jumpForce = jumpSpeed;
        swimSpeed = swimmingSpeed;

        type = heroType;
        maxLives = lives;
        currentLives = maxLives;
        HUDImageSource = HUDImgSource;
    }

    /**
     *
     * @return maximum possible lives
     */
    public int getMaxLives() {
        return maxLives;
    }

    /**
     *
     * @return current amount of lives
     */
    public int getCurrentLives() {
        return currentLives;
    }

    /**
     * Loses life
     */
    public void looseLife() {
        currentLives--;
    }

    /**
     *
     * @return skin of the Hero
     */
    public Image getImage() {
        return img;
    }

    /**
     *
     * @return source of the (smaller) skin for HUD
     */
    public String getHUDImageSource() {
        return HUDImageSource;
    }

    /**
     *
     * @return Hero type
     */
    public HeroType getType() {
        return type;
    }

    @Override
    public String getName() {
        if (type == HeroType.FIRE) {
            return "Fire hero";
        } else if (type == HeroType.ICE) {
            return "Ice hero";
        } else {
            return "Hero";
        }
    }

    /**
     * Interacts with other objects, mostly die or claim a star.
     * @param world world in which the creature is
     * @return death code
     */
    @Override
    public Death updateWithOtherObjects(World world) {
        List<GameObject> gameObjects = world.getGameObjects();

        for (GameObject gameObject : gameObjects) {
            if (gameObject != this) {
                if (getBoundingBox().intersects(gameObject.getBoundingBox())) {
                    if (gameObject instanceof AbstractHero && ((AbstractHero) gameObject).isAlive()) {
                        if (type == HeroType.ICE && ((AbstractHero) gameObject).getType() == HeroType.FIRE) {
                            // die by melting
                            return Death.MELT;
                        }
                    } else if (gameObject instanceof AbstractEnemy && ((AbstractEnemy) gameObject).isAlive()) {
                        // get killed by some enemy
                        return Death.BY_ENEMY;
                    } else if (gameObject instanceof AbstractTurret) {
                        if (((AbstractTurret) gameObject).getType() == TurretAndProjectileType.FIRE) {
                            // get killed by fire turret
                            return Death.BY_FIRE_TURRET;
                        } else if (((AbstractTurret) gameObject).getType() == TurretAndProjectileType.ICE) {
                            // get killed by ice turret
                            return Death.BY_ICE_TURRET;
                        } else if (((AbstractTurret) gameObject).getType() == TurretAndProjectileType.COMBINED) {
                            // get killed by combined turret
                            return Death.BY_COMBINED_TURRET;
                        } else {
                            // get killed by turret
                            return Death.BY_TURRET;
                        }
                    } else if (gameObject instanceof AbstractProjectile) {
                        if (((AbstractProjectile) gameObject).getType() == TurretAndProjectileType.FIRE) {
                            // fire heroes are resistant to fire bullets
                            if (type == HeroType.FIRE) {
                                continue;
                            }
                            // killed by fire bullet
                            return Death.BY_FIRE_PROJECTILE;
                        } else if (((AbstractProjectile) gameObject).getType() == TurretAndProjectileType.ICE) {
                            // ice heroes are resistant ti ice bullets
                            if (type == HeroType.ICE) {
                                continue;
                            }
                            // killed by ice bullet
                            return Death.BY_ICE_PROJECTILE;
                        } else if (((AbstractProjectile) gameObject).getType() == TurretAndProjectileType.COMBINED) {
                            // nobody is resistant to combined bullets :(
                            // killed by combined bullet
                            return Death.BY_COMBINED_PROJECTILE;
                        } else {
                            // killed by some other bullet
                            return Death.BY_PROJECTILE;
                        }
                    } else if (gameObject instanceof AbstractStar) {
                        int plusScore = ((AbstractStar) gameObject).claim();
                        if (plusScore > 0) {
                            world.addScore(plusScore);
                        }
                    }
                }
            }
        }

        return Death.NONE;
    }

}
