package gameAPI;

import objectsAPI.AbstractGameObject;
import objectsAPI.AbstractHero;

import java.util.List;

public abstract class AbstractWorld {
    public abstract AbstractHero getHero1();

    public abstract AbstractHero getHero2();

    public abstract List<AbstractGameObject> getGameObjects();

    public abstract void addGameObject(AbstractGameObject go);

    public abstract void addScore(int value);

    public abstract AbstractTerrain getTerrain();
}
