package me.ryanmood.ryutils.bungee;

import com.google.common.collect.ImmutableMap;
import lombok.Getter;
import lombok.Setter;
import net.kyori.adventure.platform.bungeecord.BungeeAudiences;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.serializer.bungeecord.BungeeComponentSerializer;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Plugin;

import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
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
    private static BungeeAudiences audiences = RySetup.getAudiences();

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

    private static final Pattern HEX_PATTERN = Pattern.compile("(?i)&#([0-9A-F]{6})");
    private static final Pattern AMPERSAND_PATTERN = Pattern.compile("(?i)&([0-9A-FK-ORX#])");
    private static final Map<String, String> codeTranslations = new ImmutableMap.Builder<String, String>()
            .put("0", "<black>")
            .put("1", "<dark_blue>")
            .put("2", "<dark_green>")
            .put("3", "<dark_aqua>")
            .put("4", "<dark_red>")
            .put("5", "<dark_purple>")
            .put("6", "<gold>")
            .put("7", "<gray>")
            .put("8", "<dark_gray>")
            .put("9", "<blue>")
            .put("a", "<green>")
            .put("b", "<aqua>")
            .put("c", "<red>")
            .put("d", "<light_purple>")
            .put("e", "<yellow>")
            .put("f", "<white>")
            .put("k", "<obfuscated>")
            .put("l", "<bold>")
            .put("m", "<strikethrough>")
            .put("n", "<underlined>")
            .put("o", "<italic>")
            .put("r", "<reset>")
            .build();

    public RyMessageUtils(Plugin plugin) {
        this.instance = plugin;
        this.audiences = BungeeAudiences.create(plugin);
    }

    /**
     * Translates the message given and for colours using Bungee API, %prefix% and %player%.
     *
     * @param player  The player that is being translated (%player% and PAPI)
     * @param message The message you wish to be translated.
     * @return        a translated String
     */
    public static String translate(ProxiedPlayer player, String message) {

        return ChatColor.translateAlternateColorCodes('&', message)
                .replace("%prefix%", getPrefix())
                .replace("%player%", player.getName());

    }

    /**
     * Translates the message given for colours using Bungee API and %prefix%.
     *
     * @param message The message you wish to be translated.
     * @return        a translated String
     */
    public static String translate(String message) {
        return ChatColor.translateAlternateColorCodes('&', message)
                .replace("%prefix%", getPrefix());
    }

    /**
     * Translates the string list for colours using Bungee API and %prefix%.
     *
     * @param messages The string list you wish to be translated.
     * @return         a string list of translated messages.
     */
    public static List<String> translate(List<String> messages) {
        return messages.stream().map(RyMessageUtils::translate).collect(Collectors.toList());
    }

    /**
     * Translates the message given and for colours using Adventure API, %prefix% and %player%.
     *
     * @param player  The player that is being translated (%player% and PAPI)
     * @param message The message you wish to be translated.
     * @return        a translated Component
     */
    public static Component adventureTranslate(ProxiedPlayer player, String message) {
        message = legacyToAdventure(message)
                .replace("%prefix%", getPrefix())
                .replace("%player%", player.getName());


        Component component = MiniMessage.miniMessage().deserialize(message);

        return component;
    }

    /**
     * Translates the message given and for colours using Adventure API, %prefix%.
     *
     * @param message The message you wish to be translated.
     * @return        a translated Component
     */
    public static Component adventureTranslate(String message) {
        message = legacyToAdventure(message);

        Component component = MiniMessage.miniMessage().deserialize(message);

        return component;
    }

    /**
     * Translates the string list for colours using Adventure API and %prefix%.
     *
     * @param messages The string list you wish to be translated.
     * @return         a string list of translated messages.
     */
    public static List<Component> adventureTranslate(List<String> messages) {
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
        String result = HEX_PATTERN.matcher(input).replaceAll(matchResult -> {
            String hex = matchResult.group(1);
            return "<#" + hex + ">";
        });

        result = AMPERSAND_PATTERN.matcher(result).replaceAll(matchResult -> {
            String colour = matchResult.group(1);
            String adventure = codeTranslations.get(colour.toLowerCase());

            if (adventure == null) {
                return matchResult.group();
            }

            return adventure;
        });

        return result;
    }


    /**
     * Send a player a message.
     *
     * @param player  The player who you wish to receive the message.
     * @param message The message you wish to send the player.
     */
    public static void sendPlayer(ProxiedPlayer player, String message) {
        if (getAudiences() != null) {
            getAudiences().player(player).sendMessage(adventureTranslate(player, message));
        } else {
            BaseComponent[] text = TextComponent.fromLegacyText(translate(player, message));
            player.sendMessage(text);
        }
    }

    /**
     * Send a player multiple messages at once.
     *
     * @param player   The player who you wish to receive the messages.
     * @param messages The string list of messages you wish to send to the player.
     */
    public static void sendPlayer(ProxiedPlayer player, String... messages) {
        for (String message : messages) {
            if (getAudiences() != null) {
                getAudiences().player(player).sendMessage(adventureTranslate(player, message));
            } else {
                BaseComponent[] text = TextComponent.fromLegacyText(translate(player, message));
                player.sendMessage(text);
            }
        }
    }

    /**
     * Send a player multiple messages at once.
     *
     * @param player   The player who you wish to receive the messages.
     * @param messages The string list of messages you wish to send to the player.
     */
    public static void sendPlayer(ProxiedPlayer player, List<String> messages) {
        for (String message : messages) {
            if (getAudiences() != null) {
                getAudiences().player(player).sendMessage(adventureTranslate(player, message));
            } else {
                BaseComponent[] text = TextComponent.fromLegacyText(translate(player, message));
                player.sendMessage(text);
            }
        }
    }

    /**
     * Send a sender a message.
     *
     * @param sender  The sender who you wish to receive the messages.
     * @param message The message you wish to send to the sender.
     */
    public static void sendSender(CommandSender sender, String message) {
        if (getAudiences() != null) {
            getAudiences().sender(sender).sendMessage(adventureTranslate(message));
        } else {
            BaseComponent[] text = TextComponent.fromLegacyText(translate(message));
            sender.sendMessage(text);
        }
    }

    /**
     * Send a sender multiple messages
     *
     * @param sender   The sender who you wish to receive the messages.
     * @param messages The messages you wish to send to the sender.
     */
    public static void sendSender(CommandSender sender, String... messages) {
        for (String message : messages) {
            if (getAudiences() != null) {
                getAudiences().sender(sender).sendMessage(adventureTranslate(message));
            } else {
                BaseComponent[] text = TextComponent.fromLegacyText(translate(message));
                sender.sendMessage(text);
            }
        }
    }

    /**
     * Send a sender multiple messages
     *
     * @param sender   The sender who you wish to receive the messages.
     * @param messages The messages you wish to send to the sender.
     */
    public static void sendSender(CommandSender sender, List<String> messages) {
        for (String message : messages) {
            if (getAudiences() != null) {
                getAudiences().sender(sender).sendMessage(adventureTranslate(message));
            } else {
                BaseComponent[] text = TextComponent.fromLegacyText(translate(message));
                sender.sendMessage(text);
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
            BaseComponent[] text = TextComponent.fromLegacyText(translate(message));
            ProxyServer.getInstance().getConsole().sendMessage(text);
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
                BaseComponent[] text = TextComponent.fromLegacyText(translate(message));
                ProxyServer.getInstance().getConsole().sendMessage(text);
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
                BaseComponent[] text = TextComponent.fromLegacyText(translate(message));
                ProxyServer.getInstance().getConsole().sendMessage(text);
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
    public static void broadcast(ProxiedPlayer player, String permission, String message) {
        for (ProxiedPlayer online : ProxyServer.getInstance().getPlayers()) {
            if (online.hasPermission(permission)) {
                if (getAudiences() != null) {
                    getAudiences().player(player).sendMessage(adventureTranslate(player, message));
                } else {
                    BaseComponent[] text = TextComponent.fromLegacyText(translate(player, message));
                    online.sendMessage(text);
                }
            }
        }
    }

    /**
     * Send a permission based broadcast to all online players.
     *
     * @param player     The player who is making the broadcast.
     * @param message    The message you wish to be broadcast.
     */
    public static void broadcast(ProxiedPlayer player, String message) {
        if (getAudiences() != null) {
            getAudiences().players().sendMessage(adventureTranslate(player, message));
        } else {
            BaseComponent[] text = TextComponent.fromLegacyText(translate(player, message));
            ProxyServer.getInstance().broadcast(text);
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
            BaseComponent[] text = TextComponent.fromLegacyText(translate(message));
            ProxyServer.getInstance().broadcast(text);
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
        if (disablePlugin) {
            ProxyServer.getInstance().getPluginManager().unregisterCommands(instance);
            ProxyServer.getInstance().getPluginManager().unregisterListeners(instance);
            instance.onDisable();
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
        if (disablePlugin) {
            ProxyServer.getInstance().getPluginManager().unregisterCommands(instance);
            ProxyServer.getInstance().getPluginManager().unregisterListeners(instance);
            instance.onDisable();
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
        if (disablePlugin) {
            ProxyServer.getInstance().getPluginManager().unregisterCommands(instance);
            ProxyServer.getInstance().getPluginManager().unregisterListeners(instance);
            instance.onDisable();
        }
    }
}