package me.ryanmood.ryutils.base;

import me.ryanmood.ryutils.base.patterns.StripPattern;
import net.kyori.adventure.text.Component;

import java.util.List;

public interface RyMessageBase {

    /**
     * Translates the message given for colours using Spigot API and %prefix%.
     *
     * @param message The message you wish to be translated.
     * @return        a translated String
     */
    String translate(String message);

    /**
     * Translates the string list for colours using Spigot API and %prefix%.
     *
     * @param messages The string list you wish to be translated.
     * @return         a string list of translated messages.
     */
    List<String> translate(List<String> messages);

    /**
     * Translates the message given for colours using AdventureAPI and %prefix%.
     *
     * @param message The message you wish to be translated.
     * @return        a translated Component
     */
    Component adventureTranslate(String message);

    /**
     * Translates the string list for colours using AdventureAPI and %prefix%.
     *
     * @param messages The string list you wish to be translated.
     * @return         a component list of translated messages.
     */
    List<Component> adventureTranslate(List<String> messages);

    /**
     * Translate a string from legacy to Adventure API.
     *
     * @param input The string that needs translating.
     * @return      String which is in an adventure format.
     */
    String legacyToAdventure(String input);

    /**
     * Strip all the colours from a piece of text.
     *
     * @param input The string that needs the colours stripping from.
     * @return      String which has no colour codes in it.
     */
    default String stripColours(String input) {
        StripPattern strip = new StripPattern();
        String result = strip.process(input);

        return result;
    }

    /**
     * Send console a message.
     *
     * @param prefix  If you would like the plugin prefix to be added at the beginning of the message.
     * @param message The message you wish for console to receive.
     */
    void sendConsole(boolean prefix, String message);

    /**
     * Send console multiple messages.
     *
     * @param prefix   If you would like the plugin prefix to be added at the beginning of the message.
     * @param messages The messages you wish to send to console.
     */
    void sendConsole(boolean prefix, String... messages);

    /**
     * Send console multiple messages.
     *
     * @param prefix   If you would like the plugin prefix to be added at the beginning of the message.
     * @param messages The messages you wish to send to console.
     */
    void sendConsole(boolean prefix, List<String> messages);

    /**
     * Send a permission based broadcast to all online players.
     *
     * @param player     The player who is making the broadcast.
     * @param permission The permission the players require to see the broadcast.
     * @param message    The message you wish to be broadcast.
     */
   // void broadcast(Object player, String permission, String message);

    /**
     * Send a permission based broadcast to all online players.
     *
     * @param permission The permission the players require to see the broadcast.
     * @param message    The message you wish to be sent to the players.
     */
    void broadcast(String permission, String message);

    /**
     * Send a broadcast to all online players.
     *
     * @param message The message you wish to be sent to the players.
     */
    void broadcast(String message);

    /**
     * Send a broadcast to all online players.
     *
     * @param player  The player who is sending the broadcast.
     * @param message The message you wish to be sent to players.
     */
    //void broadcast(Object player, String message);

    /**
     * Sends a message to console saying that the license has been authenticated.
     */
    void sendLicenseSucessful();

    /**
     * Sends a message to console saying that there was a license error.
     *
     * @param error The error that occurred.
     */
    void sendLicenseError(String error);

    /**
     * Sends a message to console saying that there was a license error.
     *
     * @param error         The error that occurred.
     * @param disablePlugin Should the plugin be disabled?
     */
    void sendLicenseError(String error, boolean disablePlugin);

    /**
     * Sends a message to console saying that there was an error.
     *
     * @param error The error that occurred.
     */
    void sendPluginError(String error);

    /**
     * Sends a message to console saying that there was an error.
     *
     * @param error         The error that has occurred.
     * @param disablePlugin Should the plugin be disabled due to the error?
     */
    void sendPluginError(String error, boolean disablePlugin);

    /**
     * Sends a message to console saying that there was an error.
     *
     * @param error     The error that occurred.
     * @param exception The exception that occurred.
     * @param debug     Is debug enabled?
     */
    void sendPluginError(String error, Exception exception, boolean debug);

    /**
     * Sends a message to console saying an that there was an error.
     *
     * @param error         The error that occurred.
     * @param exception     The exception that occurred.
     * @param debug         Is debug enabled?
     * @param disablePlugin Should the plugin be disabled due to the error?
     */
    void sendPluginError(String error, Exception exception, boolean debug, boolean disablePlugin);

}
