package game.Objects;

import game.World;
import javafx.scene.image.Image;
import utils.Enums.HeroType;
import utils.Enums.TurretAndProjectileType;

import java.util.List;

public class Hero extends Creature {
    private final HeroType type;
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
    public Hero(Image image, double positionX, double positionY, HeroType heroType, double movSpeed, double jumpSpeed, double swimmingSpeed, int lives, String HUDImgSource) {
        super(image, positionX, positionY, movSpeed, jumpSpeed, swimmingSpeed);
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
        if (type == HeroType.fire) {
            return "Fire hero";
        } else if (type == HeroType.ice) {
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
    public int updateWithOtherObjects(World world) {
        List<GameObject> gameObjects = world.getGameObjects();

        for (GameObject gameObject : gameObjects) {
            if (gameObject != this) {
                if (getBoundingBox().intersects(gameObject.getBoundingBox())) {
                    if (gameObject instanceof Hero && ((Hero) gameObject).isAlive()) {
                        if (type == HeroType.ice && ((Hero) gameObject).getType() == HeroType.fire) {
                            // die by melting
                            return 11;
                        }
                    } else if (gameObject instanceof Enemy && ((Enemy) gameObject).isAlive()) {
                        if (gameObject instanceof SimpleEnemy) {
                            // get killed by simple enemy
                            return 21;
                        } else {
                            // get killed by some other enemy
                            return 22;
                        }
                    } else if (gameObject instanceof Turret) {
                        if (((Turret) gameObject).getType() == TurretAndProjectileType.fire) {
                            // get killed by fire turret
                            return 41;
                        } else if (((Turret) gameObject).getType() == TurretAndProjectileType.ice) {
                            // get killed by ice turret
                            return 42;
                        } else if (((Turret) gameObject).getType() == TurretAndProjectileType.combined) {
                            // get killed by combined turret
                            return 43;
                        } else {
                            // get killed by turret
                            return 44;
                        }
                    } else if (gameObject instanceof Projectile) {
                        if (((Projectile) gameObject).getType() == TurretAndProjectileType.fire) {
                            // fire heroes are resistant to fire bullets
                            if (type == HeroType.fire) {
                                continue;
                            }
                            // killed by fire bullet
                            return 51;
                        } else if (((Projectile) gameObject).getType() == TurretAndProjectileType.ice) {
                            // ice heroes are resistant ti ice bullets
                            if (type == HeroType.ice) {
                                continue;
                            }
                            // killed by ice bullet
                            return 52;
                        } else if (((Projectile) gameObject).getType() == TurretAndProjectileType.combined) {
                            // nobody is resistant to combined bullets :(
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
