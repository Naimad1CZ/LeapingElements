package game;

import javafx.scene.canvas.GraphicsContext;
import org.mapeditor.core.Map;

import java.awt.geom.Point2D;

public class GameObjects {
    private final GraphicsContext gc;

    private Hero hero1 = new Hero("other/Ice_hero.png");
    private Hero hero2 = new Hero("other/Fire_enemy.png");
    private Terrain terrain;

    public GameObjects(GraphicsContext g, Map m) {
        gc = g;
    }

    public Hero getHero1() {
        return hero1;
    }

    public Hero getHero2() {
        return hero2;
    }

    public Point2D.Double getHeroPositionsOptimalCenter() {
        if (hero2 != null) {
            if (hero1.isAlive() && hero2.isAlive()) {
                var pos1 = hero1.getPosition();
                var pos2 = hero2.getPosition();

                // if they are too far away, show the one more behind
                if (pos2.x - pos1.x > 8.5 * Game.WIDTH / 10) {
                    return new Point2D.Double(pos1.x + (Game.WIDTH / 10), pos1.y);
                } else if (pos1.x - pos2.x > 8.5 * Game.WIDTH / 10) {
                    return new Point2D.Double(pos2.x + (Game.WIDTH / 10), pos2.y);
                }
                // else return cca. their average
                return new Point2D.Double((pos1.x + pos2.x) / 2 + (Game.WIDTH / 10), (pos1.y + pos2.y) / 2);
            } else if (hero1.isAlive()) {
                var pos1 = hero1.getPosition();
                return new Point2D.Double(pos1.x + (Game.WIDTH / 10), pos1.y);
            } else if (hero2.isAlive()){
                var pos2 = hero2.getPosition();
                return new Point2D.Double(pos2.x + (Game.WIDTH / 10), pos2.y);
            } else {
                return new Point2D.Double(0, 0);
            }
        } else {
            if (hero1.isAlive()){
                var pos1 = hero1.getPosition();
                return new Point2D.Double(pos1.x + (Game.WIDTH / 10), pos1.y);
            } else {
                return new Point2D.Double(0, 0);
            }
        }
    }

    public void update(double delta, World world) {
        if (!hero1.isAlive()) {
            hero1.respawn();
        }
        hero1.update(delta, world);

        if (hero2 != null) {
            if (!hero2.isAlive()) {
                hero2.respawn();
            }
            hero2.update(delta, world);
        }
    }

    public void draw(int leftLabel, int topLabel) {
        if (hero1.isAlive()) {
            hero1.draw(gc, leftLabel, topLabel);
        }
        if (hero2 != null && hero2.isAlive()) {
            hero2.draw(gc, leftLabel, topLabel);
        }
    }
}
