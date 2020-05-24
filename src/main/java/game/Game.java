package game;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.Scrollable;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;

import org.mapeditor.core.Map;
import org.mapeditor.core.ObjectGroup;
import org.mapeditor.core.MapLayer;
import org.mapeditor.core.TileLayer;
import org.mapeditor.io.TMXMapReader;
import org.mapeditor.view.HexagonalRenderer;
import org.mapeditor.view.MapRenderer;
import org.mapeditor.view.OrthogonalRenderer;
import org.mapeditor.view.IsometricRenderer;

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
