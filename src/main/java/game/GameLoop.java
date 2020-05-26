package game;

import game.Objects.Hero;
import javafx.animation.AnimationTimer;
import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;

public class GameLoop extends AnimationTimer {

    private static final long NANOS_IN_SECOND = 1_000_000_000L;
    private long previousTime = System.nanoTime();
    private final GraphicsContext gc;

    private final boolean startScreen = false;

    private final Scene scene;

    private MyMap myMap;
    private World myWorld;
    private Hero hero1;
    private Hero hero2;

    public GameLoop(GraphicsContext gc, Scene sc) {
        this.gc = gc;

        loadLevel(null);

        scene = sc;

        scene.setOnKeyPressed(e -> {
            switch (e.getCode()) {
                case RIGHT:
                    if (hero1 != null) {
                        hero1.startMovingRight();
                    }
                    break;
                case LEFT:
                    if (hero1 != null) {
                        hero1.startMovingLeft();
                    }
                    break;
                case UP:
                    if (hero1 != null) {
                        hero1.startMovingUp();
                    }
                    break;
                case D:
                    if (hero2 != null) {
                        hero2.startMovingRight();
                    }
                    break;
                case A:
                    if (hero2 != null) {
                        hero2.startMovingLeft();
                    }
                    break;
                case W:
                    if (hero2 != null) {
                        hero2.startMovingUp();
                    }
                    break;
                case R:
                    reloadLevel();
                    break;
                case C:
                    if (myWorld != null) {
                        myWorld.changeCameraMode();
                    }
                    break;
            }
        });

        scene.setOnKeyReleased(e -> {
            switch (e.getCode()) {
                case RIGHT:
                    if (hero1 != null) {
                        hero1.stopMovingRight();
                    }
                    break;
                case LEFT:
                    if (hero1 != null) {
                        hero1.stopMovingLeft();
                    }
                    break;
                case UP:
                    if (hero1 != null) {
                        hero1.stopMovingUp();
                    }
                    break;
                case D:
                    if (hero2 != null) {
                        hero2.stopMovingRight();
                    }
                    break;
                case A:
                    if (hero2 != null) {
                        hero2.stopMovingLeft();
                    }
                    break;
                case W:
                    if (hero2 != null) {
                        hero2.stopMovingUp();
                    }
                    break;
            }
        });
    }

    private void loadLevel(String path) {
        myMap = new MyMap(path);
        reloadLevel();
    }

    private void reloadLevel() {
        if (myMap != null) {
            myWorld = myMap.loadWorld(gc);

            hero1 = myWorld.getHero1();
            hero2 = myWorld.getHero2();
        }
    }

    private void disposeLevel() {
        myMap = null;
        myWorld = null;
        hero1 = null;
        hero2 = null;
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
