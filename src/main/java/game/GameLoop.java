package game;

import game.Objects.Creature;
import javafx.animation.AnimationTimer;
import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;

public class GameLoop extends AnimationTimer {

    private static final long NANOS_IN_SECOND = 1_000_000_000L;
    private long previousTime = System.nanoTime();
    private GraphicsContext gc;

    private boolean startScreen = false;

    private Scene scene;

    MyMap myMap;
    World myWorld;

    public GameLoop(GraphicsContext gc, Scene sc) {
        this.gc = gc;

        myMap = new MyMap(null);
        myWorld = myMap.loadWorld(gc);

        Creature hero1 = myWorld.getHero1();
        Creature hero2 = myWorld.getHero2();

        scene = sc;

        if (hero2 == null) {
            scene.setOnKeyPressed(e -> {
                switch (e.getCode()) {
                    case RIGHT:
                        hero1.startMovingRight();
                        break;
                    case LEFT:
                        hero1.startMovingLeft();
                        break;
                    case UP:
                        hero1.startMovingUp();
                        break;
                    case R:
                        hero1.respawn();
                }
            } );

            scene.setOnKeyReleased(e -> {
                switch (e.getCode()) {
                    case RIGHT:
                        hero1.stopMovingRight();
                        break;
                    case LEFT:
                        hero1.stopMovingLeft();
                        break;
                    case UP:
                        hero1.stopMovingUp();
                        break;
                }
            } );
        } else {
            scene.setOnKeyPressed(e -> {
                switch (e.getCode()) {
                    case RIGHT:
                        hero1.startMovingRight();
                        break;
                    case LEFT:
                        hero1.startMovingLeft();
                        break;
                    case UP:
                        hero1.startMovingUp();
                        break;
                    case D:
                        hero2.startMovingRight();
                        break;
                    case A:
                        hero2.startMovingLeft();
                        break;
                    case W:
                        hero2.startMovingUp();
                        break;
                    case R:
                        hero1.respawn();
                        hero2.respawn();
                    case C:
                        myWorld.changeCameraMode();

                }
            } );

            scene.setOnKeyReleased(e -> {
                switch (e.getCode()) {
                    case RIGHT:
                        hero1.stopMovingRight();
                        break;
                    case LEFT:
                        hero1.stopMovingLeft();
                        break;
                    case UP:
                        hero1.stopMovingUp();
                        break;
                    case D:
                        hero2.stopMovingRight();
                        break;
                    case A:
                        hero2.stopMovingLeft();
                        break;
                    case W:
                        hero2.stopMovingUp();
                        break;
                }
            } );
        }
    }

    @Override
    public void handle(long now) {
        double delta = ((double) (now - previousTime)) / NANOS_IN_SECOND;
        previousTime = now;

        if (delta > 0.05) {
            delta = 0.05;
        }

        gc.clearRect(0, 0, Game.WIDTH, Game.HEIGHT);
        if (startScreen) {
            // if some button then map -> load map etc.
        } else {
            myWorld.updateAndDraw(delta);
        }
    }
}
