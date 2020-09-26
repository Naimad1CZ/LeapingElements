package damian.myjavagame.plugin.api.utils;

public class Enums {
    public enum HeroType {
        FIRE,
        ICE
    }

    public enum TurretAndProjectileType {
        FIRE,
        ICE,
        COMBINED
    }

    public enum TileType {
        OUT,
        AIR,
        SOLID,
        WATER
    }

    public enum Death {
        NONE,
        FALL_OUT,
        DROWN,
        MELT,
        BY_SIMPLE_ENEMY,
        BY_ENEMY,
        BY_FIRE_TURRET,
        BY_ICE_TURRET,
        BY_COMBINED_TURRET,
        BY_TURRET,
        BY_FIRE_PROJECTILE,
        BY_ICE_PROJECTILE,
        BY_COMBINED_PROJECTILE,
        BY_PROJECTILE,
        OTHER
    }
}
