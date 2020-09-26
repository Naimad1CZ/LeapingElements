package utils;

import objectsAPI.AbstractGameObject;
import utilsAPI.Enums.Death;

import static utils.ResourceUtils.getLocalisedText;

public class DeathMessages {
    /**
     *
     * @param go GameObject that died
     * @param deathCause death cause if the death
     * @return the death message of the game object that died with provided death code
     */
    public static String getDeathMessage(AbstractGameObject go, Death deathCause) {
        String start = getLocalisedText(go.getName().replaceAll("\\s","")) + " ";
        String end = getLocalisedText(deathCause.name());
        return start + end;
    }


}
