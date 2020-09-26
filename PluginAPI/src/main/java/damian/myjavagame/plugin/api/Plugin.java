package damian.myjavagame.plugin.api;

import damian.myjavagame.plugin.api.objects.*;

public interface Plugin {
    public String getPluginName();
    public void loadPlugin();
    public AbstractEnemy getEnemyInstance();
    public AbstractHero getHeroInstance();
    public AbstractProjectile getProjectileInstance();
    public AbstractStar getStarInstance();
    public AbstractTurret getTurretInstance();
}
