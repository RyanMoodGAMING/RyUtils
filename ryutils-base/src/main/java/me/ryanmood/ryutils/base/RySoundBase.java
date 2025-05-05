package me.ryanmood.ryutils.base;

public interface RySoundBase<Player> {

    void playSound(String player, String sound);

    /**
     * Play a sound to a player.
     *
     * @param player The player who the sound should be played too.
     * @param sound  The sound been played.
     */
    void playSound(Player player, String sound);

}
