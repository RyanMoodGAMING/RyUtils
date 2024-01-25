package me.ryanmood.ryutils.velocity;

import com.velocitypowered.api.command.CommandSource;
import com.velocitypowered.api.plugin.Plugin;
import com.velocitypowered.api.proxy.Player;
import com.velocitypowered.api.proxy.ProxyServer;
import lombok.Getter;
import lombok.Setter;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;

/*
 * This software and its content is copyright of RyanMoodGAMING - © RyanMoodGAMING 2024. All rights reserved.
 * Any redistribution or reproduction of part or all of the contents in any form is prohibited other than the following:
 * you may print or download to a local hard disk extracts for your personal and non-commercial use only
 * you may copy the content to individual third parties for their personal use, but only if you acknowledge the website as the source of the material
 * You may not, except with our express written permission, distribute or commercially exploit the content. Nor may you transmit it or store it in any other website or other form of electronic retrieval system.
 */

/*
todo Add multi-line messages.
todo Add debug messages
todo Add license messages
 */

public class RyMessageUtils {

    @Setter
    private static Plugin instance = RySetup.getPluginInstance();
    @Setter
    private static ProxyServer server = RySetup.getProxyServer();

    @Getter
    @Setter
    private static String prefix = "";
    @Getter
    @Setter
    private static String errorPrefix = "&cError &7» &c";
    @Getter
    @Setter
    private static String breaker = "&7&m------------------------------------";
    @Getter
    @Setter
    private static String supportMessage = "Please contact the plugin author for support.";

    /**
     * Translates the message given and for colours, %prefix% and %player%.
     *
     * @param player  The player that is being translated (%player%)
     * @param message The message you wish to be translated.
     * @return        a translated String
     */
    public static Component translate(Player player, String message) {
        message = message.replace("%player%", player.getUsername())
                .replace("%prefix%", getPrefix());

        Component component  = MiniMessage.miniMessage().deserialize(message);

        return component;
    }

    /**
     * Translates the message given for colours and %prefix%.
     *
     * @param message The message you wish to be translated.
     * @return        a translated String
     */
    public static Component translate(String message) {
        Component component = MiniMessage.miniMessage().deserialize(message);
        return component;
    }

    /**
     * Send a player a message.
     *
     * @param player  The player who you wish to receive the message.
     * @param message The message you wish to send the player.
     */
    public static void sendPlayer(Player player, String message) {
        player.sendMessage(translate(player, message));
    }

    /**
     * Send a sender a message.
     *
     * @param sender  The sender who you wish to receive the messages.
     * @param message The message you wish to send to the sender.
     */
    public static void sendSender(CommandSource sender, String message) {
        sender.sendMessage(translate(message));
    }

    /**
     * Send console a message.
     *
     * @param prefix  If you would like the plugin prefix to be added at the beginning of the message.
     * @param message The message you wish for console to receive.
     */
    public static void sendConsole(boolean prefix, String message) {
        if (prefix) {
            server.getConsoleCommandSource().sendMessage(translate(getPrefix() + " " + message));
        } else {
            server.getConsoleCommandSource().sendMessage(translate(message));
        }
    }

    /**
     * Send a permission based broadcast to all online players.
     *
     * @param player     The player who is making the broadcast.
     * @param permission The permission the players require to see the broadcast.
     * @param message    The message you wish to be broadcast.
     */
    public static void broadcast(Player player, String permission, String message) {
        for (Player online : RySetup.getProxyServer().getAllPlayers()) {
            if (online.hasPermission(permission)) {
                online.sendMessage(translate(message));
            }
        }
    }

}
