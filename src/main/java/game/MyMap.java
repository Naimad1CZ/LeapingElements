package game;

import java.util.logging.Logger;
import game.LoggingUtils;

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

import org.mapeditor.core.Map;
import org.mapeditor.core.ObjectGroup;
import org.mapeditor.core.MapLayer;
import org.mapeditor.core.TileLayer;
import org.mapeditor.io.TMXMapReader;
import org.mapeditor.view.HexagonalRenderer;
import org.mapeditor.view.MapRenderer;
import org.mapeditor.view.OrthogonalRenderer;
import org.mapeditor.view.IsometricRenderer;

import java.awt.*;
import java.io.InputStream;

public class MyMap {
    private Map map;
    public MyMap(String location) {
        if (location == null) {
            location = "src/main/resources/bezjmena3.tmx";
        }
        try {
            TMXMapReader mapReader = new TMXMapReader();
            map = mapReader.readMap(location);

        } catch (Exception e) {
            LoggingUtils.logError("Error while reading the map:\n" + e.getMessage());
            return;
        }
    }

    public World loadWorld(GraphicsContext gc) {
        return new World(gc, map);
    }
}
