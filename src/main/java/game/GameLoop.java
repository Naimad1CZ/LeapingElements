package game;

import game.objects.Hero;
import javafx.animation.AnimationTimer;
import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseButton;
import javafx.stage.Stage;
import org.apache.commons.lang3.exception.ExceptionUtils;

import java.awt.*;
import java.util.List;

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

    /**
     * Initialize stuff.
     *
     * @param gc                  Graphics Context of the canvas, on which the levels will be painted on
     * @param stg                 game Stage
     * @param main                main scene
     * @param game                game scene
     * @param buttonsToInitialize level buttons + load level button that needs to be initialized here
     */
    public GameLoop(GraphicsContext gc, Stage stg, Scene main, Scene game, ArrayList<MenuButton> buttonsToInitialize) {
        gameGC = gc;
        stage = stg;
        mainScene = main;
        gameScene = game;

        initializeMainSceneButtonListeners(buttonsToInitialize);
        initializeGameSceneKeyboardListeners();
    }

    /**
     * Loads a level.
     * @param path path to the level (might be relative to the resources dir or absolute)
     * @param absolutePath if the path is absolute
     */
    private void loadLevel(String path, boolean absolutePath) {
        myMap = new MyMap(path, absolutePath);
        reloadLevel();
    }

    /**
     * Reloads (resets) a level.
     */
    private void reloadLevel() {
        if (myMap != null) {
            myWorld = myMap.loadWorld(gameGC);

            hero1 = myWorld.getHero1();
            hero2 = myWorld.getHero2();
        }
    }

    /**
     * Disposes a level.
     */
    private void disposeLevel() {
        myMap = null;
        myWorld = null;
        hero1 = null;
        hero2 = null;
    }

    /**
     * Calculate delta (time between current time and previous time when this method was called), sets the scene
     * if neccessary, handle the level.
     * @param now current time in nanoseconds
     */
    @Override
    public void handle(long now) {
        double delta = ((double) (now - previousTime)) / NANOS_IN_SECOND;
        previousTime = now;

        if (delta > 0.05) {
            delta = 0.05;
        }

        if (startScreen) {
            if (stage.getScene() != mainScene) {
                stage.setScene(mainScene);
            }
        } else {
            if (stage.getScene() != gameScene) {
                stage.setScene(gameScene);
            }

            myWorld.updateAndDraw(delta);
            if (myWorld.isCompleted()) {
                startScreen = true;
                disposeLevel();
            }
        }
    }

    /**
     * Initialize mouse listeners on level buttons and load button to load level on click.
     *
     * @param buttons level buttons + load level button that needs to be initialized here
     */
    private void initializeMainSceneButtonListeners(List<MenuButton> buttons) {
        for (int i = 0; i < buttons.size() - 1; ++i) {
            MenuButton b = buttons.get(i);
            b.setOnMouseReleased(event -> {
                if (event.getButton().equals(MouseButton.PRIMARY)) {
                    b.setButtonReleasedStyle();
                    try {
                        loadLevel("Levels/Level" + b.getText() + ".tmx", false);
                        startScreen = false;
                    } catch (Exception e) {
                        System.err.println("Error when loading level: " + "Level" + b.getText() + "\n" + ExceptionUtils.getStackTrace(e));
                    }

                }
            });
        }

        MenuButton loadLevelButton = buttons.get(buttons.size() - 1);
        loadLevelButton.setOnMouseReleased(event -> {
            if (event.getButton().equals(MouseButton.PRIMARY)) {
                loadLevelButton.setButtonReleasedStyle();

                FileDialog dialog = new FileDialog((Frame)null, "Select level file to Open");
                dialog.setMode(FileDialog.LOAD);
                dialog.setVisible(true);
                String file = dialog.getDirectory().replace("\\", "/") + dialog.getFile();

                try {
                    loadLevel(file, true);
                    startScreen = false;
                } catch (Exception e) {
                    System.err.println("Error when loading custom level: " + file + "\n" + ExceptionUtils.getStackTrace(e));
                }

            }
        });
    }

    /**
     * Initialize keyboard hooks for controlling heroes and other controls.
     */
    private void initializeGameSceneKeyboardListeners() {
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
                    disposeLevel();
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

}
