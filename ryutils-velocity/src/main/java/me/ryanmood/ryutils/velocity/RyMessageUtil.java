package me.ryanmood.ryutils.velocity;

import com.google.inject.Inject;
import com.velocitypowered.api.command.CommandSource;
import com.velocitypowered.api.plugin.PluginContainer;
import com.velocitypowered.api.plugin.PluginManager;
import com.velocitypowered.api.proxy.Player;
import com.velocitypowered.api.proxy.ProxyServer;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import me.ryanmood.ryutils.base.RyMessagesBase;
import me.ryanmood.ryutils.base.exceptions.UninitializedUtilException;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;

import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

@Getter
@Setter
public class RyMessageUtil implements RyMessagesBase<Player, CommandSource> {

    @Getter(AccessLevel.PRIVATE)
    @Setter(AccessLevel.PRIVATE)
    private PluginContainer plugin;
    @Getter(AccessLevel.PRIVATE)
    @Setter(AccessLevel.PRIVATE)
    private ProxyServer server;
    @Setter(AccessLevel.PRIVATE)
    @Getter(AccessLevel.PRIVATE)
    private Logger logger;
    @Setter(AccessLevel.PRIVATE)
    @Getter(AccessLevel.PRIVATE)
    private PluginManager pluginManager;
    @Setter(AccessLevel.PRIVATE)
    private String pluginId;
    private static RyMessageUtil instance;

    private String prefix = "&7» &f";
    private String errorPrefix = "&cError &7» &c";
    private String breaker = "&7&m------------------------------------";
    private String supportMessage = "Please contact the plugin author for support.";

    public RyMessageUtil(PluginContainer pluginContainer) {
        this.plugin = plugin;
    }

    public RyMessageUtil(PluginContainer pluginContainer, String prefix) {
        this.plugin = pluginContainer;
        this.prefix = prefix;
    }

    public RyMessageUtil(PluginContainer pluginContainer, ProxyServer server, String prefix) {
        this.plugin = pluginContainer;
        this.server = server;
        this.prefix = prefix;

    }

    @Inject
    public RyMessageUtil(Logger logger, PluginManager pluginManager, ProxyServer server, PluginContainer pluginContainer) {
        this.logger = logger;
        this.pluginManager = pluginManager;
        this.server = server;
        this.plugin = pluginContainer;
    }

    /**
     * Translate a message from plain text to be formatted (usually with legacy colours).
     *
     * @param message The message you wish to be formatted.
     * @return A formatted string.
     *
     * @Deprecated Please use {@link #adventureTranslate(String)}
     */
    @Deprecated
    @Override
    public String translate(String message) {
        message.replaceAll("%prefix%", this.getPrefix());
        message.replaceAll("%error_prefix%", this.getErrorPrefix());

        return this.stripColours(message);
    }

    /**
     * Translate a message from plain text to be formatted (usually with legacy colours).
     *
     * @param player  The player that is being translated.
     * @param message The message you wish to be formatted.
     * @return A formatted string.
     * <br><br>
     * * @Deprecated Please use {@link #adventureTranslate(Player, String)}
     */
    @Deprecated
    public String translate(Player player, String message) {
        return this.translate(message);
    }

    /**
     * Translate a message from plain text to be formatted (usually with modern colours).
     *
     * @param message The message you wish to be formatted.
     * @return A formatted string.
     */
    @Override
    public Component adventureTranslate(String message) {
        message = this.legacyToAdventure(message);
        message.replaceAll("%prefix%", this.getPrefix())
                .replaceAll("%error_prefix%", this.getErrorPrefix());

        return MiniMessage.miniMessage().deserialize(message);
    }

    public Component adventureTranslate(Player player, String message) {
        // PlaceholderAPI Checks when they support Folia

        return this.adventureTranslate(message);
    }

    /**
     * Send a player a message.
     *
     * @param player  The player who you wish to receive the message.
     * @param message The message you wish to send the player.
     */
    public void sendPlayer(Player player, String message) {
        player.sendMessage(this.adventureTranslate(player, message));
    }

    /**
     * Send a player multiple messages.
     *
     * @param player   The player who you wish to receive the message.
     * @param messages The messages you wish to send the player.
     */
    public void sendPlayer(Player player, String... messages) {
        Arrays.stream(messages).forEach(msg -> this.sendPlayer(player, msg));
    }

    /**
     * Send a player multiple messages.
     *
     * @param player   The player who you wish to receive the message.
     * @param messages The messages you wish to send the player.
     */
    public void sendPlayer(Player player, List<String> messages) {
        messages.forEach(msg -> this.sendPlayer(player, msg));
    }

    /**
     * Send a sender a message.
     *
     * @param sender  The sender who you wish to receive the message.
     * @param message The message you wish to send the player.
     */
    public void sendSender(CommandSource sender, String message) {
        sender.sendMessage(this.adventureTranslate(message));
    }

    /**
     * Send a sender multiple messages.
     *
     * @param sender   The sender who you wish to receive the message.
     * @param messages The messages you wish to send the player.
     */
    public void sendSender(CommandSource sender, String... messages) {
        Arrays.stream(messages).forEach(msg -> this.sendSender(sender, msg));
    }

    /**
     * Send a sender multiple messages.
     *
     * @param sender   The sender who you wish to receive the message.
     * @param messages The messages you wish to send the player.
     */
    public void sendSender(CommandSource sender, List<String> messages) {
        messages.forEach(msg -> this.sendSender(sender, msg));
    }

    /**
     * Send a message to console.
     *
     * @param prefix  Should the plugin prefix be put in front of the player?
     * @param message The message you wish to send to console.
     */
    @Override
    public void sendConsole(boolean prefix, String message) {
        if (prefix) message = this.getPrefix() + message;
        this.getServer().getConsoleCommandSource().sendMessage(this.adventureTranslate(message));
    }

    /**
     * Send a multiple messages to console.
     *
     * @param prefix   Should the plugin prefix be put in front of the player?
     * @param messages The messages you wish to send to console.
     */
    @Override
    public void sendConsole(boolean prefix, String... messages) {
        Arrays.stream(messages).forEach(msg -> this.sendConsole(prefix, msg));
    }

    /**
     * Send a multiple messages to console.
     *
     * @param prefix   Should the plugin prefix be put in front of the player?
     * @param messages The messages you wish to send to console.
     */
    @Override
    public void sendConsole(boolean prefix, List<String> messages) {
        messages.forEach(msg -> this.sendConsole(prefix, msg));
    }

    /**
     * Send a broadcast across the whole server.
     *
     * @param message The message to broadcast.
     */
    @Override
    public void broadcast(String message) {
        for (Player online : this.getServer().getAllPlayers()) {
            online.sendMessage(this.adventureTranslate(message));
        }
    }

    /**
     * Send a broadcast across the whole server to players who have permission to see it.
     *
     * @param permission The permission needed to see the broadcast.
     * @param message    The message to broadcast.
     */
    @Override
    public void broadcast(String permission, String message) {
        for (Player online : this.getServer().getAllPlayers()) {
            if (online.hasPermission(permission)) online.sendMessage(this.adventureTranslate(message));
        }
    }

    /**
     * Send a broadcast across the whole server that is formatted as player.
     *
     * @param player  The player to be used for formatting (placeholderapi etc).
     * @param message The message to be broadcasted.
     */
    public void broadcast(Player player, String message) {
        for (Player online : this.getServer().getAllPlayers()) {
            online.sendMessage(this.adventureTranslate(player, message));
        }
    }

    /**
     * Send a broadcast across the whole server that is formatted as a player but requires a permission to be seen.
     *
     * @param player     The player to be used for formatting (placeholderapi etc)
     * @param permission The permission needed to see the broadcast.
     * @param message    The message to be broadcasted.
     */
    public void broadcast(Player player, String permission, String message) {
        for (Player online : this.getServer().getAllPlayers()) {
            if (online.hasPermission(permission)) online.sendMessage(this.adventureTranslate(player, message));
        }
    }

    /**
     * Send an error log in console.
     *
     * @param error The error that has occurred.
     */
    @Override
    public void sendError(String error) {
        this.sendConsole(true, this.getBreaker(),
                "&fAn error has occurred.",
                "&fError: &c" + error,
                this.getSupportMessage(),
                this.breaker);
    }

    /**
     * Send an error log in console and disable the plugin / shutdown the application.
     *
     * @param error   The error that has occurred.
     * @param disable Should the plugin be disabled?
     */
    @Override
    public void sendError(String error, boolean disable) {
        this.sendError(error);

        if (disable && this.getPlugin() != null) this.getPluginManager().getPlugin(this.getPluginId()).get().getExecutorService().shutdown();
    }

    /**
     * Send an error log in console with an exception.
     *
     * @param error     The error that has occurred.
     * @param exception The exception which occurred to post.
     */
    @Override
    public void sendError(String error, Exception exception) {
        this.sendConsole(true, this.getBreaker(),
                "&fAn error has occurred.",
                "&fError: &c" + error,
                this.getSupportMessage(),
                this.breaker,
                "&f(Debug) Stacktrace from Exception: ");
        exception.printStackTrace();
    }

    /**
     * Send an error log in console with an exception if enabled.
     *
     * @param error     The error that has occurred.
     * @param exception The exception which occurred to post.
     * @param debug     Should the exception be posted?
     */
    @Override
    public void sendError(String error, Exception exception, boolean debug) {
        if (!debug) this.sendError(error);
        else if (debug) this.sendError(error, exception);
    }

    /**
     * Send an error log in console with an exception (if enabled) and disable the plugin / shutdown the application.
     *
     * @param error     The error that has occurred.
     * @param exception The exception which occurred to post.
     * @param debug     Should the exception be posted?
     * @param disable   Should the plugin be disabled?
     */
    @Override
    public void sendError(String error, Exception exception, boolean debug, boolean disable) {
        if (!debug) this.sendError(error);
        else if (debug) this.sendError(error, exception);

        if (disable && this.getPlugin() != null) this.getPluginManager().getPlugin(this.getPluginId()).get().getExecutorService().shutdown();
    }

    /**
     * Send a long error in console.
     *
     * @param errors The errors.
     */
    @Override
    public void sendLongError(String... errors) {
        this.sendConsole(true, this.getBreaker(),
                "&fAn error has occurred.",
                "&fError(s): &c");
        Arrays.stream(errors).forEach(error -> this.sendConsole(true, "&c" + error));
        this.sendConsole(true, this.getSupportMessage(),
                this.breaker);
    }

    /**
     * Send a message in console saying that license verification was successful.
     */
    @Override
    public void sendLicenseSuccessful() {
        this.sendConsole(true, this.getBreaker(),
                "&aLicense has been authenticated. ",
                this.getBreaker());
    }

    /**
     * Send an error in regard to licenses.
     *
     * @param error The error that occurred.
     */
    @Override
    public void sendLicenseError(String error) {
        this.sendConsole(true, this.getBreaker(),
                "&fAn error occurred while verifying your license.",
                "&fError: &c" + error,
                this.getSupportMessage(),
                this.getBreaker());
    }

    /**
     * Send an error in regard to licenses and disable the plugin / shutdown the application.
     *
     * @param error   The error that occurred.
     * @param disable Should the plugin be disabled?
     */
    @Override
    public void sendLicenseError(String error, boolean disable) {
        this.sendLicenseError(error);

        if (disable && this.getPlugin() != null) this.getPluginManager().getPlugin(this.getPluginId()).get().getExecutorService().shutdown();
    }

    private String getPluginId() {
        return this.plugin.getDescription().getId();
    }

    /**
     * Get the instance of the util.
     *
     * @return {@link RyMessageUtil}
     */
    public static RyMessageUtil getUtil() {
        if (instance == null) throw new UninitializedUtilException("RyMessageUtil#getUtil", "#getUtil", "Velocity");
        else return instance;
    }
}
