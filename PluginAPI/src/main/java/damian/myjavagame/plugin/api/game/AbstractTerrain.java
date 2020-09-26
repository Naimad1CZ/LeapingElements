package damian.myjavagame.plugin.api.game;

import damian.myjavagame.plugin.api.utils.Enums;

public abstract class AbstractTerrain {
    public abstract int getTileWidth();

    public abstract int getTileHeight();

    public abstract Enums.TileType getTileType(int x, int y);


}
