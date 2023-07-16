package me.ryanmood.ryutils.spigot;

import com.cryptomorin.xseries.XSound;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

/*
 * This software and its content is copyright of RyanMoodGAMING - © RyanMoodGAMING 2023. All rights reserved.
 * Any redistribution or reproduction of part or all of the contents in any form is prohibited other than the following:
 * you may print or download to a local hard disk extracts for your personal and non-commercial use only
 * you may copy the content to individual third parties for their personal use, but only if you acknowledge the website as the source of the material
 * You may not, except with our express written permission, distribute or commercially exploit the content. Nor may you transmit it or store it in any other website or other form of electronic retrieval system.
 */

public class RySoundUtils {

    /**
     * Play a sound to a player.
     *
     * @param player The player who you wish to hear the sound.
     * @param sound  The sound you wish to play.
     */
    public static void playSound(Player player, @NotNull String sound) {
        if (sound.isEmpty() || sound.equalsIgnoreCase("none")) return;

        try {
            XSound.matchXSound(sound).get().play(player);
        } catch (Exception ignored) {
            RyMessageUtils.sendConsole(true, "&cFailed to play sound of name &4'" + sound + "' &cto &4" +
                    player.getName() + "&c. &eThis sound probably does not support your version of spigot.");
        }
    }

}
