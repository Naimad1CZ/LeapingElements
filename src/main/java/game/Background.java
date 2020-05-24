package game;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

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

import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import org.mapeditor.core.*;
import org.mapeditor.io.TMXMapReader;
import org.mapeditor.view.HexagonalRenderer;
import org.mapeditor.view.MapRenderer;
import org.mapeditor.view.OrthogonalRenderer;
import org.mapeditor.view.IsometricRenderer;

import java.awt.*;
import java.io.InputStream;

public class Background {
    private final GraphicsContext gc;

    private final Image backgroundImg;
    private final int backgroundWidth;
    private final int backgroundHeight;

    public Background(GraphicsContext g, ImageLayer l) {
        gc = g;
        backgroundImg = new Image(Background.class.getClassLoader().getResourceAsStream(l.getImage().getSource()));
        backgroundWidth = (int)backgroundImg.getWidth();
        backgroundHeight = (int)backgroundImg.getHeight();
    }

    /**
     * Draws a background image.
     * @param leftLabel position of most left x coordinate on the screen
     * @param topLabel  position of most top y coordinate on the screen
     */
    public void draw(int leftLabel, int topLabel, int terrainHeight) {
        leftLabel /= 5;
        leftLabel = leftLabel % Game.WIDTH;

        var backgroundHeightToTerrainHeightRatio = backgroundHeight / (double)terrainHeight;
        var topLabelInBackgroundCoordinates = topLabel * backgroundHeightToTerrainHeightRatio;
        if (topLabelInBackgroundCoordinates < 0) {
            topLabelInBackgroundCoordinates = 0;
        } else if (topLabelInBackgroundCoordinates + Game.HEIGHT > backgroundHeight) {
            topLabelInBackgroundCoordinates = backgroundHeight - Game.HEIGHT;
        }

        gc.drawImage(backgroundImg, backgroundWidth - leftLabel, (int)-topLabelInBackgroundCoordinates);
        gc.drawImage(backgroundImg, -leftLabel, (int)-topLabelInBackgroundCoordinates);
    }
}
