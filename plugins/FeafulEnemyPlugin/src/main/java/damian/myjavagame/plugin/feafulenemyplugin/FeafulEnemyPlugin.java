package damian.myjavagame.plugin.feafulenemyplugin;

import damian.myjavagame.plugin.api.Plugin;
import damian.myjavagame.plugin.api.objects.*;
import damian.myjavagame.plugin.feafulenemyplugin.implementation.*;

public class FeafulEnemyPlugin implements Plugin {
    FeafulEnemy e;
    Hero h;
    Projectile p;
    Star s;
    Turret t;

    @Override
    public String getPluginName() {
        return "Feaful Enemy Plugin";
    }

    @Override
    public void loadPlugin() {
        e = new FeafulEnemy();
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
