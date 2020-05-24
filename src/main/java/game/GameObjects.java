package game;

import javafx.scene.canvas.GraphicsContext;
import org.mapeditor.core.Map;

import java.awt.geom.Point2D;

public class GameObjects {
    private final GraphicsContext gc;

    private Hero hero1 = new Hero();
    private Hero hero2 = null;
    private Terrain terrain;

    public GameObjects(GraphicsContext g, Map m) {
        gc = g;
    }

    public Hero getHero1() {
        return hero1;
    }

    public Point2D.Double getHeroPositionsAverage() {
        if (hero2 != null) {
            var pos1 = hero1.getPosition();
            var pos2 = hero2.getPosition();
            return new Point2D.Double((pos1.x + pos2.x) / 2, (pos1.y + pos2.y) / 2);
        } else if (hero1.isAlive()){
            return hero1.getPosition();
        } else {
            return new Point2D.Double(0, 0);
        }
    }

    public void update(double delta, World world) {
        if (hero1.isAlive()) {
            hero1.update(delta, world);
        }
    }

    public void draw(int leftLabel, int topLabel) {
        if (hero1.isAlive()) {
            hero1.draw(gc, leftLabel, topLabel);
        }
    }
}
