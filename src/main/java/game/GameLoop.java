package game;

import javafx.animation.AnimationTimer;
import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;

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

        Hero hero1 = myWorld.getHero();

        scene = sc;
        scene.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.RIGHT) {
                hero1.startMovingRight();
            } else if (e.getCode() == KeyCode.LEFT) {
                hero1.startMovingLeft();
            }
        } );
        scene.setOnKeyReleased(e -> {
            if (e.getCode() == KeyCode.RIGHT) {
                hero1.stopMovingRight();
            } else if (e.getCode() == KeyCode.LEFT) {
                hero1.stopMovingLeft();
            }
        } );

    }

    @Override
    public void handle(long now) {
        double delta = ((double) (now - previousTime)) / NANOS_IN_SECOND;
        previousTime = now;

        if (delta > 0.5) {
            delta = 0.5;
        }

        gc.clearRect(0, 0, Game.WIDTH, Game.HEIGHT);
        if (startScreen) {
            // if some button then map -> load map etc.
        } else {
            myWorld.updateAndDraw(delta);
        }
    }
}
