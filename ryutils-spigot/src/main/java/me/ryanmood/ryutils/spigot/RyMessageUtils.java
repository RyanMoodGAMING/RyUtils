package me.ryanmood.ryutils.spigot;

import lombok.Getter;
import lombok.Setter;
import me.clip.placeholderapi.PlaceholderAPI;
import me.ryanmood.ryutils.spigot.patterns.AmpersandPattern;
import me.ryanmood.ryutils.spigot.patterns.HexPattern;
import net.kyori.adventure.platform.bukkit.BukkitAudiences;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextDecoration;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.permissions.Permission;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.stream.Collectors;

/*
 * This software and its content is copyright of RyanMoodGAMING - © RyanMoodGAMING 2023. All rights reserved.
 * Any redistribution or reproduction of part or all of the contents in any form is prohibited other than the following:
 * you may print or download to a local hard disk extracts for your personal and non-commercial use only
 * you may copy the content to individual third parties for their personal use, but only if you acknowledge the website as the source of the material
 * You may not, except with our express written permission, distribute or commercially exploit the content. Nor may you transmit it or store it in any other website or other form of electronic retrieval system.
 */

@SuppressWarnings("unused")
public class RyMessageUtils {

    @Setter
    private static Plugin instance = RySetup.getPluginInstance();
    @Setter
    @Getter
    private static BukkitAudiences audiences = RySetup.getAudiences();

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

    public RyMessageUtils(Plugin plugin) {
        this.instance = plugin;
        this.audiences = BukkitAudiences.create(plugin);
    }

    /**
     * Translates the message given and for colours using Spigot API, PAPI, %prefix% and %player%.
     *
     * @param player  The player that is being translated (%player% and PAPI)
     * @param message The message you wish to be translated.
     * @return        a translated String
     */
    public static String translate(Player player, String message) {
        if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null) {
            String PAPI = PlaceholderAPI.setPlaceholders(player, message)
                    .replace("%prefix%", getPrefix())
                    .replace("%player%", player.getName());

            return HEXUtils.colorify(PAPI);
        }

        return HEXUtils.colorify(message)
                .replace("%prefix%", getPrefix())
                .replace("%player%", player.getName());

    }

    /**
     * Translates the message given for colours using Spigot API and %prefix%.
     *
     * @param message The message you wish to be translated.
     * @return        a translated String
     */
    public static String translate(String message) {
        return HEXUtils.colorify(message)
                .replace("%prefix%", getPrefix());
    }

    /**
     * Translates the string list for colours using Spigot API and %prefix%.
     *
     * @param messages The string list you wish to be translated.
     * @return         a string list of translated messages.
     */
    public static List<String> translate(@NotNull List<String> messages) {
        return messages.stream().map(RyMessageUtils::translate).collect(Collectors.toList());
    }

    /**
     * Translates the message given and for colours using AdventureAPI, PAPI, %prefix% and %player%.
     *
     * @param player  The player that is being translated (%player% and PAPI)
     * @param message The message you wish to be translated.
     * @return        a translated Component
     */
    public static Component adventureTranslate(Player player, String message) {
       if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null) {
           message = PlaceholderAPI.setPlaceholders(player, message)
                   .replace("%prefix%", getPrefix())
                   .replace("%player%", player.getName());
       } else {
           message = message
                   .replace("%prefix%", getPrefix())
                   .replace("%player%", player.getName());
       }

        return adventureTranslate(message);
    }

    /**
     * Translates the message given for colours using AdventureAPI and %prefix%.
     *
     * @param message The message you wish to be translated.
     * @return        a translated Component
     */
    public static Component adventureTranslate(String message) {
        message = legacyToAdventure(message);

        Component component = MiniMessage.miniMessage().deserialize(message)
                .decorationIfAbsent(TextDecoration.ITALIC, TextDecoration.State.FALSE)
                .decorationIfAbsent(TextDecoration.BOLD, TextDecoration.State.FALSE)
                .decorationIfAbsent(TextDecoration.OBFUSCATED, TextDecoration.State.FALSE);

        return component;
    }

    /**
     * Translates the string list for colours using AdventureAPI and %prefix%.
     *
     * @param messages The string list you wish to be translated.
     * @return         a component list of translated messages.
     */
    public static List<Component> adventureTranslate(@NotNull List<String> messages) {
        return messages.stream().map(RyMessageUtils::adventureTranslate).collect(Collectors.toList());
    }

    /**
     * Translate a string from legacy to Adventure API.
     *
     * @param input The string that needs translating.
     * @return      String which is in an adventure format.
     *
     * @Author: EternalCodeTeam (https://github.com/EternalCodeTeam/ChatFormatter/)
     */
    public static String legacyToAdventure(String input) {
        HexPattern hex = new HexPattern();
        String result = hex.process(input);

        result = result.replaceAll("§", "&");

        AmpersandPattern ampersand = new AmpersandPattern();
        result = ampersand.process(result);

        return result;
    }

    /**
     * Send a player a message.
     *
     * @param player  The player who you wish to receive the message.
     * @param message The message you wish to send the player.
     */
    public static void sendPlayer(@NotNull Player player, @NotNull String message) {
        if (getAudiences() != null) {
            getAudiences().player(player).sendMessage(adventureTranslate(player, message));
        } else {
            player.sendMessage(translate(player, message));
        }
    }

    /**
     * Send a player multiple messages at once.
     *
     * @param player   The player who you wish to receive the messages.
     * @param messages The string list of messages you wish to send to the player.
     */
    public static void sendPlayer(@NotNull Player player, @NotNull String... messages) {
        for (String message : messages) {
            if (getAudiences() != null) {
                getAudiences().player(player).sendMessage(adventureTranslate(player, message));
            } else {
                player.sendMessage(translate(player, message));
            }
        }
    }

    /**
     * Send a player multiple messages at once.
     *
     * @param player   The player who you wish to receive the messages.
     * @param messages The string list of messages you wish to send to the player.
     */
    public static void sendPlayer(Player player, @NotNull List<String> messages) {
        for (String message : messages) {
            if (getAudiences() != null) {
                getAudiences().player(player).sendMessage(adventureTranslate(player, message));
            } else {
                player.sendMessage(translate(player, message));
            }
        }
    }

    /**
     * Send a sender a message.
     *
     * @param sender  The sender who you wish to receive the messages.
     * @param message The message you wish to send to the sender.
     */
    public static void sendSender(@NotNull CommandSender sender, @NotNull String message) {
        if (getAudiences() != null) {
            getAudiences().sender(sender).sendMessage(adventureTranslate(message));
        } else {
            sender.sendMessage(translate(message));
        }
    }

    /**
     * Send a sender multiple messages
     *
     * @param sender   The sender who you wish to receive the messages.
     * @param messages The messages you wish to send to the sender.
     */
    public static void sendSender(@NotNull CommandSender sender, @NotNull String... messages) {
        for (String message : messages) {
            if (getAudiences() != null) {
                getAudiences().sender(sender).sendMessage(adventureTranslate(message));
            } else {
                sender.sendMessage(translate(message));
            }
        }
    }

    /**
     * Send a sender multiple messages
     *
     * @param sender   The sender who you wish to receive the messages.
     * @param messages The messages you wish to send to the sender.
     */
    public static void sendSender(@NotNull CommandSender sender, @NotNull List<String> messages) {
        for (String message : messages) {
            if (getAudiences() != null) {
                getAudiences().sender(sender).sendMessage(adventureTranslate(message));
            } else {
                sender.sendMessage(translate(message));
            }
        }
    }

    /**
     * Send console a message.
     *
     * @param prefix  If you would like the plugin prefix to be added at the beginning of the message.
     * @param message The message you wish for console to receive.
     */
    public static void sendConsole(boolean prefix, String message) {
        if (prefix) message = getPrefix() + message;

        if (getAudiences() != null) {
            getAudiences().console().sendMessage(adventureTranslate(message));
        } else {
            Bukkit.getConsoleSender().sendMessage(translate(message));
        }
    }

    /**
     * Send console multiple messages.
     *
     * @param prefix   If you would like the plugin prefix to be added at the beginning of the message.
     * @param messages The messages you wish to send to console.
     */
    public static void sendConsole(boolean prefix, String... messages) {
        for (String message : messages) {
            if (prefix) message = getPrefix() + message;

            if (getAudiences() != null) {
                getAudiences().console().sendMessage(adventureTranslate(message));
            } else {
                Bukkit.getConsoleSender().sendMessage(translate(message));
            }
        }
    }

    /**
     * Send console multiple messages.
     *
     * @param prefix   If you would like the plugin prefix to be added at the beginning of the message.
     * @param messages The messages you wish to send to console.
     */
    public static void sendConsole(boolean prefix, List<String> messages) {
        for (String message : messages) {
            if (prefix) message = getPrefix() + message;

            if (getAudiences() != null) {
                getAudiences().console().sendMessage(adventureTranslate(message));
            } else {
                Bukkit.getConsoleSender().sendMessage(translate(message));
            }
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
        for (Player online : Bukkit.getOnlinePlayers()) {
            if (online.hasPermission(permission)) {
                if (getAudiences() != null) {
                    getAudiences().player(online).sendMessage(adventureTranslate(player, message));
                } else {
                    online.sendMessage(translate(player, message));
                }
            }
        }
    }

    /**
     * Send a permission based broadcast to all online players.
     *
     * @param player     The player who is making the broadcast.
     * @param permission The permission the players require to see the broadcast.
     * @param message    The message you wish to be broadcast.
     */
    public static void broadcast(Player player, Permission permission, String message) {
        for (Player online : Bukkit.getOnlinePlayers()) {
            if (online.hasPermission(permission)) {
                if (getAudiences() != null) {
                    getAudiences().player(online).sendMessage(adventureTranslate(player, message));
                } else {
                    online.sendMessage(translate(player, message));
                }
            }
        }
    }

    /**
     * Send a broadcast to all online players.
     *
     * @param message The message you wish to be sent to the players.
     */
    public static void broadcast(String message) {
        if (getAudiences() != null) {
            getAudiences().players().sendMessage(adventureTranslate(message));
        } else {
            Bukkit.getConsoleSender().sendMessage(translate(message));
        }
    }

    /**
     * Send a broadcast to all online players.
     *
     * @param player  The player who is sending the broadcast.
     * @param message The message you wish to be sent to players.
     */
    public static void broadcast(Player player, String message) {
        if (getAudiences() != null) {
            getAudiences().players().sendMessage(adventureTranslate(player, message));
        } else {
            Bukkit.getConsoleSender().sendMessage(translate(player, message));
        }
    }

    /**
     * Sends a message to console saying that the license has been authenticated.
     */
    public static void sendLicenseSucessful() {
        sendConsole(true, breaker,
                "&fLicense has been authenticated. ",
                breaker);
    }

    /**
     * Sends a message to console saying that there was a license error.
     *
     * @param error The error that occurred.
     */
    public static void sendLicenseError(String error) {
        sendConsole(true, breaker,
                "&fAn error occurred while verifying your license.",
                "&fError: &c" + error,
                getSupportMessage(),
                breaker);
    }

    /**
     * Sends a message to console saying that there was a license error.
     *
     * @param error         The error that occurred.
     * @param disablePlugin Should the plugin be disabled?
     */
    public static void sendLicenseError(String error, boolean disablePlugin) {
        sendConsole(true, breaker,
                "&fAn error occurred while verifying your license.",
                "&fError: &c" + error,
                getSupportMessage(),
                breaker);
        if (disablePlugin && instance != null) {
              Bukkit.getPluginManager().disablePlugin(instance);
        }
    }

    /**
     * Sends a message to console saying that there was an error.
     *
     * @param error The error that occurred.
     */
    public static void sendPluginError(String error) {
        sendConsole(true, breaker,
                "&fAn error has occurred.",
                "&fError: &c" + error,
                getSupportMessage(),
                breaker);
    }

    /**
     * Sends a message to console saying that there was an error.
     *
     * @param error         The error that has occurred.
     * @param disablePlugin Should the plugin be disabled due to the error?
     */
    public static void sendPluginError(String error, boolean disablePlugin) {
        sendConsole(true, breaker,
                "&fAn error has occurred.",
                "&fError: &c" + error,
                getSupportMessage(),
                breaker);
        if (disablePlugin && instance != null) {
            Bukkit.getPluginManager().disablePlugin(instance);
        }
    }

    /**
     * Sends a message to console saying that there was an error.
     *
     * @param error     The error that occurred.
     * @param exception The exception that occurred.
     * @param debug     Is debug enabled?
     */
    public static void sendPluginError(String error, Exception exception, boolean debug) {
        sendConsole(true, breaker,
                "&fAn error has occurred.",
                "&fError: &c" + error,
                getSupportMessage(),
                breaker);
        if (debug) {
            sendConsole(true, "&fAs you have debug enabled in your config.yml, the following stacktrace error is due to this:");
            exception.printStackTrace();
        }
    }

    /**
     * Sends a message to console saying an that there was an error.
     *
     * @param error         The error that occurred.
     * @param exception     The exception that occurred.
     * @param debug         Is debug enabled?
     * @param disablePlugin Should the plugin be disabled due to the error?
     */
    public static void sendPluginError(String error, Exception exception, boolean debug, boolean disablePlugin) {
        sendConsole(true, breaker,
                "&fAn error has occurred.",
                "&fError: &c" + error,
                getSupportMessage(),
                breaker);
        if (debug) {
            sendConsole(true, "&fAs you have debug enabled in your config.yml, the following stacktrace error is due to this:");
            exception.printStackTrace();
        }
        if (disablePlugin && instance != null) {
            Bukkit.getPluginManager().disablePlugin(instance);
        }
    }

}
