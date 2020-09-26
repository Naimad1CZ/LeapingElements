package damian.myjavagame.plugin.basicplugin;

import damian.myjavagame.plugin.api.Plugin;
import damian.myjavagame.plugin.api.objects.*;
import damian.myjavagame.plugin.basicplugin.objectsimplementations.*;

public class BasicPlugin implements Plugin {
    SimpleEnemy e;
    Hero h;
    Projectile p;
    Star s;
    Turret t;

    @Override
    public String getPluginName() {
        return "Default Plugin";
    }

    @Override
    public void loadPlugin() {
        e = new SimpleEnemy();
        h = new Hero();
        p = new Projectile();
        s = new Star();
        t = new Turret();
    }

    @Override
    public AbstractEnemy getEnemyInstance() {
        return e;
    }

    @Override
    public AbstractHero getHeroInstance() {
        return h;
    }

    @Override
    public AbstractProjectile getProjectileInstance() {
        return p;
    }

    @Override
    public AbstractStar getStarInstance() {
        return s;
    }

    @Override
    public AbstractTurret getTurretInstance() {
        return t;
    }
}
