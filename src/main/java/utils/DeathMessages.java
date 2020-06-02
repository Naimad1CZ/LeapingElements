package utils;

import game.Objects.GameObject;

public class DeathMessages {

    /**
     *
     * @param go GameObject that died
     * @param deathCode death code if the death
     * @return The death message of the game object that died with provided death code
     */
    public static String getDeathMessage(GameObject go, int deathCode) {
        String start = go.getName() + " ";
        String end;
        switch (deathCode) {
            case 1:
                end = "fell out of the world";
                break;
            case 2:
                end = "drowned";
                break;
            case 11:
                end = "was melted by Fire hero";
                break;
            case 21:
                end = "was killed by a simple enemy";
                break;
            case 22:
                end = "was killed by enemy";
                break;
            case 41:
                end = "was killed by a fire turret";
                break;
            case 42:
                end = "was killed by an ice turret";
                break;
            case 43:
                end = "was killed by a combined turret";
                break;
            case 44:
                end = "was killed by a turret";
                break;
            case 51:
                end = "was killed by a fire projectile";
                break;
            case 52:
                end = "was killed by an ice projectile";
                break;
            case 53:
                end = "was killed by a combined projectile";
                break;
            case 54:
                end = "was killed by a projectile";
                break;
            case 101:
                end = "smashed into a terrain";
                break;
            case 102:
                end = "smashed into a hero";
                break;
            case 103:
                end = "smashed into an enemy";
                break;
            case 104:
                end = "smashed into a turret";
                break;
            case 105:
                end = "collided with another projectile";
                break;
            case 106:
                end = "flied out of the world";
                break;
            case 201:
                end = "was claimed";
                break;
            default:
                end = "was killed by a and unidentified object";
                break;
        }
        return start + end;
    }
}
