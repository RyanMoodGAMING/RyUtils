package me.ryanmood.ryutils.base;

import me.ryanmood.ryutils.base.patterns.impl.adventure.AmpersandPattern;
import me.ryanmood.ryutils.base.patterns.impl.adventure.HexPattern;
import me.ryanmood.ryutils.base.patterns.impl.StripPattern;
import net.kyori.adventure.text.Component;

import java.util.List;
import java.util.stream.Collectors;

public interface RyMessagesBase<Player, Sender> {

    /**
     * Translate a message from plain text to be formatted (usually with legacy colours).
     *
     * @param message The message you wish to be formatted.
     * @return A formatted string.
     */
    String translate(String message);

    /**
     * Translate a message from plain text to be formatted specifically for the player (usually with legacy colours).
     *
     * @param player  The player that is being translated.
     * @param message The message you wish to be formatted.
     * @return A formatted string.
     */
    String translate(Player player, String message);

    /**
     * Translate a list of messages from plain text to be formatted (usually with legacy colours).
     *
     * @param messages The messages you wish to be formatted.
     * @return A formatted string.
     */
    default List<String> translate(List<String> messages) {
        return messages.stream().map(this::translate).collect(Collectors.toList());
    }

    /**
     * Translate a message from plain text to be formatted (usually with modern colours).
     *
     * @param message The message you wish to be formatted.
     * @return A formatted string.
     */
    Component adventureTranslate(String message);

    /**
     * Translate a message from plain text to be formatted specifically for the player (usually with modern colours).
     *
     * @param player  The player that is being translated.
     * @param message The message you wish to be formatted.
     * @return A formatted string.
     */
    Component adventureTranslate(Player player, String message);

    /**
     * Translate a list of messages from plain text to be formatted (usually with modern colours).
     *
     * @param messages The messages you wish to be formatted.
     * @return A formatted string.
     */
    default List<Component> adventureTranslate(List<String> messages) {
        return messages.stream().map(this::adventureTranslate).collect(Collectors.toList());
    }

    /**
     * Translate a string from legacy to Adventure API.
     *
     * @param input The string that needs translating.
     * @return String which is in an adventure format.
     */
    default String legacyToAdventure(String input) {
        HexPattern hex = new HexPattern();
        String result = hex.process(input);

        result = result.replaceAll("§", "&");

        AmpersandPattern ampersand = new AmpersandPattern();
        result = ampersand.process(result);

        return result;
    }

    /**
     * Strip all the colours from a piece of text.
     *
     * @param input The string that needs the colours stripping from.
     * @return String which has no colour codes in it.
     */
    default String stripColours(String input) {
        StripPattern strip = new StripPattern();
        String result = strip.process(input);

        return result;
    }

    /**
     * Send a player a message.
     *
     * @param player  The player who you wish to receive the message.
     * @param message The message you wish to send the player.
     */
    void sendPlayer(Player player, String message);
    /**
     * Send a player multiple messages.
     *
     * @param player  The player who you wish to receive the message.
     * @param messages The messages you wish to send the player.
     */
    void sendPlayer(Player player, String... messages);
    /**
     * Send a player multiple messages.
     *
     * @param player  The player who you wish to receive the message.
     * @param messages The messages you wish to send the player.
     */
    void sendPlayer(Player player, List<String> messages);

    /**
     * Send a sender a message.
     *
     * @param sender  The sender who you wish to receive the message.
     * @param message The message you wish to send the player.
     */
    void sendSender(Sender sender, String message);
    /**
     * Send a sender multiple messages.
     *
     * @param sender   The sender who you wish to receive the message.
     * @param messages The messages you wish to send the player.
     */
    void sendSender(Sender sender, String... messages);
    /**
     * Send a sender multiple messages.
     *
     * @param sender   The sender who you wish to receive the message.
     * @param messages The messages you wish to send the player.
     */
    void sendSender(Sender sender, List<String> messages);

    /**
     * Send a message to console.
     *
     * @param prefix  Should the plugin prefix be put in front of the player?
     * @param message The message you wish to send to console.
     */
    void sendConsole(boolean prefix, String message);
    /**
     * Send a multiple messages to console.
     *
     * @param prefix  Should the plugin prefix be put in front of the player?
     * @param messages The messages you wish to send to console.
     */
    void sendConsole(boolean prefix, String... messages);
    /**
     * Send a multiple messages to console.
     *
     * @param prefix  Should the plugin prefix be put in front of the player?
     * @param messages The messages you wish to send to console.
     */
    void sendConsole(boolean prefix, List<String> messages);

    /**
     * Send a broadcast across the whole server.
     *
     * @param message The message to broadcast.
     */
    void broadcast(String message);
    /**
     * Send a broadcast across the whole server to players who have permission to see it.
     *
     * @param permission The permission needed to see the broadcast.
     * @param message    The message to broadcast.
     */
    void broadcast(String permission, String message);
    /**
     * Send a broadcast across the whole server that is formatted as player.
     *
     * @param player  The player to be used for formatting (placeholderapi etc).
     * @param message The message to be broadcasted.
     */
    void broadcast(Player player, String message);

    /**
     * Send a broadcast across the whole server that is formatted as a player but requires a permission to be seen.
     *
     * @param player     The player to be used for formatting (placeholderapi etc)
     * @param permission The permission needed to see the broadcast.
     * @param message   The message to be broadcasted.
     */
    void broadcast(Player player, String permission, String message);

    /**
     * Send an error log in console.
     *
     * @param error The error that has occurred.
     */
    void sendError(String error);
    /**
     * Send an error log in console and disable the plugin / shutdown the application.
     *
     * @param error   The error that has occurred.
     * @param disable Should the plugin be disabled?
     */
    void sendError(String error, boolean disable);
    /**
     * Send an error log in console with an exception.
     *
     * @param error     The error that has occurred.
     * @param exception The exception which occurred to post.
     */
    void sendError(String error, Exception exception);
    /**
     * Send an error log in console with an exception if enabled.
     *
     * @param error     The error that has occurred.
     * @param exception The exception which occurred to post.
     * @param debug     Should the exception be posted?
     */
    void sendError(String error, Exception exception, boolean debug);
    /**
     * Send an error log in console with an exception (if enabled) and disable the plugin / shutdown the application.
     *
     * @param error     The error that has occurred.
     * @param exception The exception which occurred to post.
     * @param debug     Should the exception be posted?
     * @param disable   Should the plugin be disabled?
     */
    void sendError(String error, Exception exception, boolean debug, boolean disable);

    /**
     * Send a long error in console.
     *
     * @param errors The errors.
     */
    void sendLongError(String... errors);

    /**
     * Send a message in console saying that license verification was successful.
     */
    void sendLicenseSuccessful();

    /**
     * Send an error in regard to licenses.
     *
     * @param error The error that occurred.
     */
    void sendLicenseError(String error);
    /**
     * Send an error in regard to licenses and disable the plugin / shutdown the application.
     *
     * @param error   The error that occurred.
     * @param disable Should the plugin be disabled?
     */
    void sendLicenseError(String error, boolean disable);
}
