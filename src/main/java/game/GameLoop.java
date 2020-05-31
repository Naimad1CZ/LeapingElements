package game;

import game.Objects.Hero;
import javafx.animation.AnimationTimer;
import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import javafx.stage.Stage;

public class GameLoop extends AnimationTimer {
    private static final long NANOS_IN_SECOND = 1_000_000_000L;
    private long previousTime = System.nanoTime();

    private final Stage stage;

    private final Scene mainScene;

    private final Scene gameScene;
    private final GraphicsContext gameGC;

    private boolean startScreen = true;

    private MyMap myMap;
    private World myWorld;
    private Hero hero1;
    private Hero hero2;

    public GameLoop(GraphicsContext gc, Stage stg, Scene main, Scene game) {
        gameGC = gc;
        stage = stg;
        mainScene = main;
        gameScene = game;

        loadLevel(null);

        mainScene.setOnKeyPressed(e -> {
            switch (e.getCode()) {
                case T:
                    startScreen = false;
                    break;
            }
        });

        gameScene.setOnKeyPressed(e -> {
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
                case ESCAPE:
                    startScreen = true;
                    break;
            }
        });

        gameScene.setOnKeyReleased(e -> {
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
            myWorld = myMap.loadWorld(gameGC);

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

        gameGC.clearRect(0, 0, Game.WIDTH, Game.HEIGHT);
        if (startScreen) {
            if (stage.getScene() != mainScene) {
                //stage.hide();
                stage.setScene(mainScene);
                //stage.show();
            }
            // if some button then map -> load map etc.
        } else {
            if (stage.getScene() != gameScene) {
                //stage.hide();
                stage.setScene(gameScene);
                //stage.show();
            }

            myWorld.updateAndDraw(delta);
        }
    }


}
