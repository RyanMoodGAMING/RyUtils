package me.ryanmood.ryutils.spigot;

import com.cryptomorin.xseries.reflection.XReflection;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import me.clip.placeholderapi.PlaceholderAPI;
import me.ryanmood.ryutils.base.RyMessagesBase;
import me.ryanmood.ryutils.base.exceptions.UninitializedUtilException;
import net.kyori.adventure.platform.bukkit.BukkitAudiences;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.util.Arrays;
import java.util.List;

@Getter
@Setter
public class RyMessageUtil implements RyMessagesBase<Player, CommandSender> {

    @Setter(AccessLevel.PRIVATE)
    @Getter(AccessLevel.PRIVATE)
    public Plugin plugin;
    @Setter(AccessLevel.PRIVATE)
    public BukkitAudiences audiences;
    public static RyMessageUtil instance;

    private String prefix = "&7» &f";
    private String errorPrefix = "&cError &7» &c";
    private String breaker = "&7&m------------------------------------";
    private String supportMessage = "Please contact the plugin author for support.";

    public RyMessageUtil(Plugin plugin) {
        this.plugin = plugin;
        this.audiences = BukkitAudiences.create(plugin);
    }

    public RyMessageUtil(Plugin plugin, String prefix) {
        this.plugin = plugin;
        this.prefix = prefix;
        this.audiences = BukkitAudiences.create(plugin);
    }

    /**
     * Translate a message from plain text to be formatted (usually with legacy colours).
     *
     * @param message The message you wish to be formatted.
     * @return A formatted string.
     */
    @Override
    public String translate(String message) {
        if (!(XReflection.MINOR_NUMBER > 15)) return ChatColor.translateAlternateColorCodes('&', message);

        message.replaceAll("%prefix%", this.getPrefix());
        message.replaceAll("%error_prefix%", this.getErrorPrefix());
        return HexAPI.process(message);
    }

    /**
     * Translate a message from plain text to be formatted specifically for the player (usually with legacy colours).
     *
     * @param player  The player that is being translated.
     * @param message The message you wish to be formatted.
     * @return A formatted string.
     */
    public String translate(Player player, String message) {
        if (Bukkit.getPluginManager().isPluginEnabled("PlaceholderAPI")) {
            message = PlaceholderAPI.setPlaceholders(player, message);
        }

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

    /**
     * Translate a message from plain text to be formatted specifically for the player (usually with modern colours).
     *
     * @param player  The player that is being translated.
     * @param message The message you wish to be formatted.
     * @return A formatted string.
     */
    public Component adventureTranslate(Player player, String message) {
        if (Bukkit.getPluginManager().isPluginEnabled("PlaceholderAPI")) {
            message = PlaceholderAPI.setPlaceholders(player, message);
        }

        return this.adventureTranslate(message);
    }

    /**
     * Send a player a message.
     *
     * @param player  The player who you wish to receive the message.
     * @param message The message you wish to send the player.
     */
    public void sendPlayer(Player player, String message) {
        if (this.audiences == null) player.sendMessage(this.translate(player, message));
        else this.getAudiences().player(player).sendMessage(this.adventureTranslate(player, message));
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
    public void sendSender(CommandSender sender, String message) {
        if (this.audiences == null) sender.sendMessage(this.translate(message));
        else this.getAudiences().sender(sender).sendMessage(this.adventureTranslate(message));
    }

    /**
     * Send a sender multiple messages.
     *
     * @param sender   The sender who you wish to receive the message.
     * @param messages The messages you wish to send the player.
     */
    public void sendSender(CommandSender sender, String... messages) {
        Arrays.stream(messages).forEach(msg -> this.sendSender(sender, msg));
    }

    /**
     * Send a sender multiple messages.
     *
     * @param sender   The sender who you wish to receive the message.
     * @param messages The messages you wish to send the player.
     */
    public void sendSender(CommandSender sender, List<String> messages) {
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

        if (this.audiences == null) Bukkit.getConsoleSender().sendMessage(this.translate(message));
        else this.getAudiences().console().sendMessage(this.adventureTranslate(message));
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
        if (this.audiences == null) Bukkit.broadcastMessage(this.translate(message));
        else this.getAudiences().all().sendMessage(this.adventureTranslate(message));
    }

    /**
     * Send a broadcast across the whole server to players who have permission to see it.
     *
     * @param permission The permission needed to see the broadcast.
     * @param message    The message to broadcast.
     */
    @Override
    public void broadcast(String permission, String message) {
        for (Player online : Bukkit.getOnlinePlayers()) {
            if (online.hasPermission(permission)) {
                if (this.audiences == null) online.sendMessage(this.translate(message));
                else this.audiences.player(online).sendMessage(this.adventureTranslate(message));
            }
        }
    }

    /**
     * Send a broadcast across the whole server that is formatted as player.
     *
     * @param player  The player to be used for formatting (placeholderapi etc).
     * @param message The message to be broadcasted.
     */
    public void broadcast(Player player, String message) {
        if (this.audiences == null) Bukkit.broadcastMessage(this.translate(player, message));
        else this.getAudiences().all().sendMessage(this.adventureTranslate(player, message));
    }

    /**
     * Send a broadcast across the whole server that is formatted as a player but requires a permission to be seen.
     *
     * @param player     The player to be used for formatting (placeholderapi etc)
     * @param permission The permission needed to see the broadcast.
     * @param message    The message to be broadcasted.
     */
    public void broadcast(Player player, String permission, String message) {
        for (Player online : Bukkit.getOnlinePlayers()) {
            if (online.hasPermission(permission)) {
                if (this.audiences == null) online.sendMessage(this.translate(player, message));
                else this.audiences.player(online).sendMessage(this.adventureTranslate(player, message));
            }
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

        if (disable && this.getPlugin() != null) Bukkit.getPluginManager().disablePlugin(this.getPlugin());
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

        if (disable && this.getPlugin() != null) Bukkit.getPluginManager().disablePlugin(this.getPlugin());
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

        if (disable && this.getPlugin() != null) Bukkit.getPluginManager().disablePlugin(this.getPlugin());
    }

    /**
     * Get the instance of the util.
     *
     * @return {@link RyMessageUtil}
     */
    public static RyMessageUtil getUtil() {
        if (instance == null) throw new UninitializedUtilException("RyMessageUtil#getUtil", "#getUtil", "Spigot");
        else return instance;
    }

}
