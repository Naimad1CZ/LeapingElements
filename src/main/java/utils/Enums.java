package utils;

public class Enums {
    public enum HeroType {
        fire,
        ice
    }

    public enum TurretAndProjectileType {
        fire,
        ice,
        combined
    }

    public enum TileType {
        out,
        air,
        solid,
        water
    }

    public enum Death {
        none,
        fallOut,
        drown,
        melt,
        bySimpleEnemy,
        byEnemy,
        byFireTurret,
        byIceTurret,
        byCombinedTurret,
        byTurret,
        byFireProjectile,
        byIceProjectile,
        byCombinedProjectile,
        byProjectile,
        other
    }
}
