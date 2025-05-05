package me.ryanmood.ryutils.spigot;

import com.cryptomorin.xseries.XSound;
import me.ryanmood.ryutils.base.RySoundBase;
import me.ryanmood.ryutils.base.exceptions.UninitializedUtilException;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.Objects;

public class RySoundUtil implements RySoundBase<Player> {

    public static RySoundUtil instance;

    public RySoundUtil() {
        instance = this;
    }

    /**
     * Play a sound to a player.
     *
     * @param player The player who the sound should be played too.
     * @param sound  The sound been played.
     */
    @Override
    public void playSound(String player, String sound) {
        this.playSound(Objects.requireNonNull(Bukkit.getPlayer(player)), sound);
    }

    /**
     * Play a sound to a player.
     *
     * @param player The player who the sound should be played too.
     * @param sound  The sound been played.
     */
    public void playSound(Player player, String sound) {
        player.playSound(player.getLocation(), Objects.requireNonNull(XSound.of(sound).orElse(XSound.BLOCK_NOTE_BLOCK_CHIME).get()), 1F, 1F);
    }

    /**
     * Get the instance of the util.
     *
     * @return {@link RySoundUtil}
     */
    public static RySoundUtil getUtil() {
        if (instance == null) throw new UninitializedUtilException("RySoundUtil#getUtil", "#getUtil", "Spigot");
        else return instance;
    }

}
