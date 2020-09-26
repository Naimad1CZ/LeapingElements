package damian.myjavagame.plugin.feafulenemyplugin.implementation;

import damian.myjavagame.plugin.api.game.AbstractWorld;
import damian.myjavagame.plugin.api.objects.AbstractEnemy;
import damian.myjavagame.plugin.api.objects.AbstractGameObject;
import damian.myjavagame.plugin.api.objects.AbstractHero;
import damian.myjavagame.plugin.api.utils.Enums;
import javafx.scene.image.Image;

import java.awt.geom.Point2D;
import java.util.List;


public class FeafulEnemy extends AbstractEnemy {
    protected int state = 0;
    private double routeLength;
    private double baseMovementSpeed;
    private double fleeingMovementSpeed;


    @Override
    public void loadData(Image image, double positionX, double positionY, double lengthOfRoute) {
        startPosX = positionX;
        startPosY = positionY;
        posX = positionX;
        posY = positionY;
        img = image;
        width = (int) image.getWidth();
        height = (int) image.getHeight();

        baseMovementSpeed = 120;
        fleeingMovementSpeed = 180;

        movementSpeed = baseMovementSpeed;
        jumpForce = 0;
        swimSpeed = 100;

        routeLength = lengthOfRoute;
    }

    @Override
    public String getName() {
        return "FeafulEnemy";
    }

    protected void checkForNearbyEnemy(double delta, AbstractWorld world) {
        AbstractHero nearbyHero = null;
        AbstractHero almostNearbyHero = null;

        AbstractHero h1 =  world.getHero1();
        AbstractHero h2 =  world.getHero2();

        if (h1 != null && h1.isAlive()) {
            Point2D.Double h1Pos = h1.getPosition();
            if (Math.abs(h1Pos.x - posX) < 200 && Math.abs(h1Pos.y - posY) < 200) {
                nearbyHero = h1;
            }
            if (Math.abs(h1Pos.x - posX) < 300 && Math.abs(h1Pos.y - posY) < 300) {
                almostNearbyHero = h1;
            }
        }

        if (h2 != null && h2.isAlive()) {
            Point2D.Double h2Pos = h2.getPosition();
            if (Math.abs(h2Pos.x - posX) < 200 && Math.abs(h2Pos.y - posY) < 200) {
                if (nearbyHero == null) {
                    nearbyHero = h2;
                } else {
                    Point2D.Double h1Pos = h1.getPosition();
                    if (Math.abs(h2Pos.x - posX) < Math.abs(h1Pos.x - posX)) {
                        nearbyHero = h2;
                    }
                }
            }
            if (Math.abs(h2Pos.x - posX) < 300 && Math.abs(h2Pos.y - posY) < 300) {
                if (almostNearbyHero == null) {
                    almostNearbyHero = h2;
                } else {
                    Point2D.Double h1Pos = h1.getPosition();
                    if (Math.abs(h2Pos.x - posX) < Math.abs(h1Pos.x - posX)) {
                        almostNearbyHero = h2;
                    }
                }
            }
        }

        if (nearbyHero != null) {
            Point2D.Double heroPos = nearbyHero.getPosition();
            if (heroPos.x < posX) {
                // flee to the right
                state = 2;
            } else {
                // flee to the left
                state = 3;
            }
        } else if (almostNearbyHero == null) {
            // stop fleeing
            if (state == 2) {
                state = 0;
            } else if (state == 3) {
                state = 1;
            }
        }
    }

    protected void behaveLikeAFailure(double delta) {
        if (state == 0) {
            movementSpeed = baseMovementSpeed;
            if (posX >= startPosX + routeLength) {
                stopMovingRight();
                state = 1;
                behaveLikeAFailure(delta);
            } else {
                startMovingRight();
            }
        } else if (state == 1) {
            movementSpeed = baseMovementSpeed;
            if (posX <= startPosX) {
                stopMovingLeft();
                state = 0;
                behaveLikeAFailure(delta);
            } else {
                startMovingLeft();
            }
        } else if (state == 2) {
            movementSpeed = fleeingMovementSpeed;
            stopMovingLeft();
            startMovingRight();
        } else if (state == 3) {
            movementSpeed = fleeingMovementSpeed;
            stopMovingRight();
            startMovingLeft();
        }
    }

    @Override
    public Enums.Death updateWithOtherObjects(AbstractWorld world) {
        List<AbstractGameObject> gameObjects = world.getGameObjects();

        for (AbstractGameObject gameObject : gameObjects) {
            if (gameObject != this) {
                if (getBoundingBox().intersects(gameObject.getBoundingBox())) {
                    if (gameObject instanceof AbstractHero && ((AbstractHero) gameObject).isAlive()) {
                        // die in the contact with Hero (but don't worry, Hero dies too)
                        return Enums.Death.OTHER;
                    }
                }
            }
        }

        return Enums.Death.NONE;
    }

    /**
     * Do logic and update the position.
     * @param delta time in seconds since the last update
     * @param world world in which the creature is
     * @return death code
     */
    @Override
    public Enums.Death updatePosition(double delta, AbstractWorld world) {
        checkForNearbyEnemy(delta, world);
        behaveLikeAFailure(delta);
        return super.updatePosition(delta, world);
    }
}
