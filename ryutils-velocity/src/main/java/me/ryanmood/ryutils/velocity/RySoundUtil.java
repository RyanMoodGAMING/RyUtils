package me.ryanmood.ryutils.velocity;

import com.velocitypowered.api.proxy.Player;
import me.ryanmood.ryutils.base.RySoundBase;
import me.ryanmood.ryutils.base.exceptions.UninitializedUtilException;
import me.ryanmood.ryutils.base.exceptions.UnsupportedPlatformException;

public class RySoundUtil implements RySoundBase<Player> {

    private static RySoundUtil instance;

    public RySoundUtil() {
        instance = this;
    }

    /**
     * @param player The player who the sound should be played too.
     * @param sound  The sound been played.
     */
    @Override
    public void playSound(String player, String sound) {
        throw new UnsupportedPlatformException("#playSound()", "Velocity");
    }

    /**
     * @param player The player who the sound should be played too.
     * @param sound  The sound been played.
     */
    public void playSound(Player player, String sound) {
        throw new UnsupportedPlatformException("#playSound()", "Velocity");
    }

    /**
     * Get the instance of the util.
     *
     * @return {@link RySoundUtil}
     */
    public static RySoundUtil getUtil() {
        if (instance == null) throw new UninitializedUtilException("RySoundUtil#getUtil", "#getUtil", "Velocity");
        else return instance;
    }
}
