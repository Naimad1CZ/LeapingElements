package game;

import javafx.animation.AnimationTimer;
import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;

import static javafx.scene.input.KeyCode.RIGHT;

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

        Hero hero1 = myWorld.getHero1();
        Hero hero2 = myWorld.getHero2();

        scene = sc;
        scene.setOnKeyPressed(e -> {
            switch (e.getCode()) {
                case RIGHT:
                    hero1.startMovingRight();
                    break;
                case LEFT:
                    hero1.startMovingLeft();
                    break;
                case UP:
                    hero1.jump();
                    break;
                case D:
                    hero2.startMovingRight();
                    break;
                case A:
                    hero2.startMovingLeft();
                    break;
                case W:
                    hero2.jump();
                    break;
                case R:
                    hero1.respawn();
                    hero2.respawn();

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
                case D:
                    hero2.stopMovingRight();
                    break;
                case A:
                    hero2.stopMovingLeft();
                    break;
            }
        } );
    }

    @Override
    public void handle(long now) {
        double delta = ((double) (now - previousTime)) / NANOS_IN_SECOND;
        previousTime = now;

        if (delta > 0.1) {
            delta = 0.1;
        }

        gc.clearRect(0, 0, Game.WIDTH, Game.HEIGHT);
        if (startScreen) {
            // if some button then map -> load map etc.
        } else {
            myWorld.updateAndDraw(delta);
        }
    }
}
