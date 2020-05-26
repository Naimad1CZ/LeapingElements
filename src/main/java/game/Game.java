package game;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.stage.Stage;

public class Game extends Application {
    public static final int WIDTH = 1472;
    public static final int HEIGHT = 768;

    @Override
    public void start(Stage stage) {
        stage.setTitle("My Java Game");

        Group root = new Group();
        Scene scene = new Scene(root);
        stage.setScene(scene);


        Canvas canvas = new Canvas(WIDTH, HEIGHT);
        root.getChildren().add(canvas);

        GraphicsContext gc = canvas.getGraphicsContext2D();

        new GameLoop(gc, scene).start();

        stage.show();
    }
}
