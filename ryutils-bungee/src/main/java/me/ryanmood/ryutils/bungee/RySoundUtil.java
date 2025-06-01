package me.ryanmood.ryutils.bungee;

import me.ryanmood.ryutils.base.RySoundBase;
import me.ryanmood.ryutils.base.exceptions.UninitializedUtilException;
import me.ryanmood.ryutils.base.exceptions.UnsupportedPlatformException;
import net.md_5.bungee.api.connection.ProxiedPlayer;

public class RySoundUtil implements RySoundBase<ProxiedPlayer> {

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
        throw new UnsupportedPlatformException("#playSound()", "bungeecord");
    }

    /**
     * @param player The player who the sound should be played too.
     * @param sound  The sound been played.
     */
    @Override
    public void playSound(ProxiedPlayer player, String sound) {
        throw new UnsupportedPlatformException("#playSound()", "bungeecord");
    }

    /**
     * Get the instance of the util.
     *
     * @return {@link RySoundUtil}
     */
    public static RySoundUtil getUtil() {
        if (instance == null) throw new UninitializedUtilException("RySoundUtil#getUtil", "#getUtil", "Bungeecord");
        else return instance;
    }
}
