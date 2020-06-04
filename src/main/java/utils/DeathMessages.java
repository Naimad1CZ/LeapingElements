package utils;

import game.objects.GameObject;
import utils.Enums.Death;

public class DeathMessages {

    /**
     *
     * @param go GameObject that died
     * @param deathCause death cause if the death
     * @return the death message of the game object that died with provided death code
     */
    public static String getDeathMessage(GameObject go, Death deathCause) {
        String start = go.getName() + " ";
        String end;
        switch (deathCause) {
            case fallOut:
                end = "fell out of the world";
                break;
            case drown:
                end = "drowned";
                break;
            case melt:
                end = "was melted by Fire hero";
                break;
            case bySimpleEnemy:
                end = "was killed by a simple enemy";
                break;
            case byEnemy:
                end = "was killed by enemy";
                break;
            case byFireTurret:
                end = "was killed by a fire turret";
                break;
            case byIceTurret:
                end = "was killed by an ice turret";
                break;
            case byCombinedTurret:
                end = "was killed by a combined turret";
                break;
            case byTurret:
                end = "was killed by a turret";
                break;
            case byFireProjectile:
                end = "was killed by a fire projectile";
                break;
            case byIceProjectile:
                end = "was killed by an ice projectile";
                break;
            case byCombinedProjectile:
                end = "was killed by a combined projectile";
                break;
            case byProjectile:
                end = "was killed by a projectile";
                break;
            default:
                end = "was killed by a and unidentified object";
                break;
        }
        return start + end;
    }
}
