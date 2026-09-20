package me.ryanmood.ryutils.spigot;

import com.cryptomorin.xseries.reflection.XReflection;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import me.clip.placeholderapi.PlaceholderAPI;
import me.ryanmood.ryutils.base.RyMessagesBase;
import me.ryanmood.ryutils.base.exceptions.UninitializedUtilException;
import me.ryanmood.ryutils.base.patterns.impl.adventure.AmpersandPattern;
import me.ryanmood.ryutils.spigot.patterns.AdventureHexPattern;
import net.kyori.adventure.platform.bukkit.BukkitAudiences;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

@Getter
@Setter
public class RyMessageUtil implements RyMessagesBase<Player, CommandSender> {

    @Setter(AccessLevel.PRIVATE) @Getter(AccessLevel.PRIVATE)
    public Plugin plugin;
    @Setter(AccessLevel.PRIVATE)
    private BukkitAudiences audiences;

    private final MiniMessage miniMessage = MiniMessage.miniMessage();
    private final AdventureHexPattern adventureHexPattern = new AdventureHexPattern();
    private final AmpersandPattern ampersandPattern = new AmpersandPattern();

    private final LegacyComponentSerializer legacyScoreboardSerializer = LegacyComponentSerializer.builder()
            .character('§')
            .build();
    private final LegacyComponentSerializer hexScoreboardSerializer = LegacyComponentSerializer.builder()
            .character('§')
            .hexColors()
            .useUnusualXRepeatedCharacterHexFormat()
            .build();
    private final Map<Class<?>, Optional<Method>> richMessageMethods = new ConcurrentHashMap<>();
    private final AtomicBoolean nativeRichMessageLogged = new AtomicBoolean();
    private final AtomicBoolean nativeRichMessageFallbackLogged = new AtomicBoolean();

    public static RyMessageUtil instance;

    private String prefix = "&7» &f";
    private String errorPrefix = "&cError &7» &c";
    private String breaker = "&7&m------------------------------------";
    private String supportMessage = "Please contact the plugin author for support.";

    public RyMessageUtil(Plugin plugin) {
        this.plugin = plugin;
        this.audiences = BukkitAudiences.create(plugin);
        instance = this;
    }

    public RyMessageUtil(Plugin plugin, String prefix) {
        this.plugin = plugin;
        this.prefix = prefix;
        this.audiences = BukkitAudiences.create(plugin);
        instance = this;
    }


    /**
     * Translate a message from plain text to be formatted (usually with legacy colours).
     *
     * @param message The message you wish to be formatted.
     * @return A formatted string.
     */
    @Override
    public String translate(String message) {
        if (this.hasHexColours()) return HexAPI.process(message);
        else return ChatColor.translateAlternateColorCodes('&', message);
    }

    /**
     * Translate a message from plain text to be formatted specifically for the player (usually with legacy colours).
     *
     * @param player  The player that is being translated.
     * @param message The message you wish to be formatted.
     * @return A formatted string.
     */
    @Override
    public String translate(Player player, String message) {
        if (this.plugin.getServer().getPluginManager().getPlugin("PlaceholderAPI") != null)
            message = PlaceholderAPI.setPlaceholders(player, message);

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
        message = message.replaceAll("%prefix%", this.prefix)
                .replaceAll("%error-prefix%", this.errorPrefix)
                .replaceAll("%support%", this.supportMessage);

        return this.miniMessage.deserialize(this.legacyToAdventure(message));
    }

    /**
     * Translate a message from plain text to be formatted specifically for the player (usually with modern colours).
     *
     * @param player  The player that is being translated.
     * @param message The message you wish to be formatted.
     * @return A formatted string.
     */
    @Override
    public Component adventureTranslate(Player player, String message) {
        if (this.plugin.getServer().getPluginManager().getPlugin("PlaceholderAPI") != null)
            message = PlaceholderAPI.setPlaceholders(player, message);

        return this.adventureTranslate(message);
    }

    /**
     * Renders legacy ampersand codes and MiniMessage for string-based scoreboards.
     * RGB is retained on supported services and downsampled on legacy versions.
     *
     * @param line The line you wish to be formatted.
     * @return A formatted string.
     */
    public String translateScoreboard(String line) {
        Component component = this.adventureTranslate(line);
        LegacyComponentSerializer serializer = this.hasHexColours() ? hexScoreboardSerializer
                : legacyScoreboardSerializer;

        return serializer.serialize(component);
    }

    /**
     * Send a player a message.
     *
     * @param player  The player who you wish to receive the message.
     * @param message The message you wish to send the player.
     */
    @Override
    public void sendPlayer(Player player, String message) {
        if (message.equalsIgnoreCase("none")) return;
        if (this.sendNativeMessage(player, message)) return;
        else this.getAudiences().player(player).sendMessage(this.adventureTranslate(message));
    }

    /**
     * Send a player multiple messages.
     *
     * @param player   The player who you wish to receive the message.
     * @param messages The messages you wish to send the player.
     */
    @Override
    public void sendPlayer(Player player, String... messages) {
        Arrays.stream(messages).forEach(msg -> this.sendPlayer(player, msg));
    }

    /**
     * Send a player multiple messages.
     *
     * @param player   The player who you wish to receive the message.
     * @param messages The messages you wish to send the player.
     */
    @Override
    public void sendPlayer(Player player, List<String> messages) {
        messages.forEach(msg -> this.sendPlayer(player, msg));
    }

    /**
     * Send a sender a message.
     *
     * @param commandSender The sender who you wish to receive the message.
     * @param message       The message you wish to send the player.
     */
    @Override
    public void sendSender(CommandSender commandSender, String message) {
        if (commandSender instanceof Player) {
            this.sendPlayer((Player) commandSender, message);
            return;
        }

        if (sendNativeMessage(commandSender, message)) return;
        else this.getAudiences().sender(commandSender).sendMessage(this.adventureTranslate(message));
    }

    /**
     * Send a sender multiple messages.
     *
     * @param commandSender The sender who you wish to receive the message.
     * @param messages      The messages you wish to send the player.
     */
    @Override
    public void sendSender(CommandSender commandSender, String... messages) {
        Arrays.stream(messages).forEach(msg -> this.sendSender(commandSender, msg));
    }

    /**
     * Send a sender multiple messages.
     *
     * @param commandSender The sender who you wish to receive the message.
     * @param messages      The messages you wish to send the player.
     */
    @Override
    public void sendSender(CommandSender commandSender, List<String> messages) {
        messages.forEach(msg -> this.sendSender(commandSender, msg));
    }

    /**
     * Send a message to console.
     *
     * @param prefix  Should the plugin prefix be put in front of the player?
     * @param message The message you wish to send to console.
     */
    @Override
    public void sendConsole(boolean prefix, String message) {
        if (prefix) message = this.prefix + message;

        this.getAudiences().console().sendMessage(this.adventureTranslate(message));
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
        this.audiences.all().sendMessage(this.adventureTranslate(message));
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
                this.audiences.player(online).sendMessage(this.adventureTranslate(message));
            }
        }
    }

    /**
     * Send a broadcast across the whole server that is formatted as player.
     *
     * @param player  The player to be used for formatting (placeholderapi etc).
     * @param message The message to be broadcasted.
     */
    @Override
    public void broadcast(Player player, String message) {
        this.getAudiences().all().sendMessage(this.adventureTranslate(player, message));
    }

    /**
     * Send a broadcast across the whole server that is formatted as a player but requires a permission to be seen.
     *
     * @param player     The player to be used for formatting (placeholderapi etc)
     * @param permission The permission needed to see the broadcast.
     * @param message    The message to be broadcasted.
     */
    @Override
    public void broadcast(Player player, String permission, String message) {
        for (Player online : Bukkit.getOnlinePlayers()) {
            if (online.hasPermission(permission)) {
                this.audiences.player(online).sendMessage(this.adventureTranslate(player, message));
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

    public boolean hasHexColours() {
        return XReflection.MAJOR_NUMBER >= 16;
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

    private boolean sendNativeMessage(Object player, String message) {
        Optional<Method> method = this.richMessageMethods.computeIfAbsent(player.getClass(), this::findNativeMessageMethod);
        if (!method.isPresent()) {
            return false;
        }

        try {
            if (this.getPlugin().getServer().getPluginManager().getPlugin("PlaceholderAPI") != null) {
                message = PlaceholderAPI.setPlaceholders((Player) player, message);
            }

            method.get().invoke(player, this.adventureTranslate(message));
            this.nativeRichMessageLogged.compareAndSet(false, true);

            return true;
        } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
            this.nativeRichMessageFallbackLogged.set(false);
            return false;
        }
    }

    private Optional<Method> findNativeMessageMethod(Class<?> playerClass) {
        try {
            return Optional.of(playerClass.getMethod("sendRichMessage", String.class));
        } catch (NoSuchMethodException | SecurityException ignored) {
            return Optional.empty();
        }
    }
}
