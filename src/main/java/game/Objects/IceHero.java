package game.Objects;

import game.GameObjects;
import game.Objects.Hero;

import java.util.ArrayList;

public class IceHero extends Hero {
    public IceHero(String pathToImage, double positionX, double positionY) {
        super(pathToImage, positionX, positionY, 250, 320, 120);
    }

    @Override
    protected int collideWithOtherObjects(ArrayList<GameObject> gameObjects) {
        System.out.println("IceHero");
        // getting/killing a star!
        return 0;
    }

}
