package game.Objects;

import game.GameObjects;

import java.util.ArrayList;

public class FireHero extends Hero {
    public FireHero(String pathToImage, double positionX, double positionY) {
        super(pathToImage, positionX, positionY, 300, 380, 0);
    }

    @Override
    protected int collideWithOtherObjects(ArrayList<GameObject> gameObjects) {
        int x = 77;
        x = super.collideWithOtherObjects(gameObjects);
        if (x != 77) {
            System.out.println("Yep, it's done");
        }

        // getting/killing a star!

        System.out.println("FireHero");
        return 0;
    }

}