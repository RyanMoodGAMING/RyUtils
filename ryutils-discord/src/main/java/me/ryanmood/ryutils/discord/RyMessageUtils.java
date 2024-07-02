package me.ryanmood.ryutils.discord;

import lombok.Getter;
import lombok.Setter;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;

import java.awt.*;
import java.util.List;

public class RyMessageUtils {

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
     * Send a message to a channel.
     *
     * @param user The user you wish to send a message to.
     * @param message The content you wish to send in the message.
     */
    public static void sendUser(User user, String message) {
        if (user == null) {
            RyMessageUtils.sendBotError("An error occured while trying to DM a player.");
            return;
        }

        user.openPrivateChannel().flatMap(channel -> channel.sendMessage(message)).queue();
    }

    /**
     * Send a message to a channel.
     *
     * @param user The user you wish to send a message to.
     * @param embed The embed you wish to send in the message.
     */
    public static void sendUser(User user, MessageEmbed embed) {
        if (user == null) {
            RyMessageUtils.sendBotError("An error occured while trying to DM a player.");
            return;
        }

        user.openPrivateChannel().flatMap(channel -> channel.sendMessageEmbeds(embed)).queue();
    }

    /**
     * Send a message to a channel.
     *
     * @param channel The channel you wish to send a message to.
     * @param message The content you wish to send in the message.
     */
    public static void sendChannel(TextChannel channel, String message) {
        if (channel == null) {
            RyMessageUtils.sendBotError("An error occurred while trying to send a message to a channel.");
            return;
        }

        channel.sendMessage(message).queue();
    }

    /**
     * Send a message to a channel.
     *
     * @param channel The channel you wish to send a message to.
     * @param embed The embed you wish to send in the message.
     */
    public static void sendChannel(TextChannel channel, MessageEmbed embed) {
        if (channel == null) {
            RyMessageUtils.sendBotError("An error occurred while trying to send a message to a channel.");
            return;
        }

        channel.sendMessageEmbeds(embed).queue();
    }

    public static void sendConsole(boolean prefix, String message) {
        if (prefix) message = getPrefix() + message;
        System.out.println(message);
    }

    public static void sendConsole(boolean prefix, String... messages) {
        for (String message : messages) {
            if (prefix) message = getPrefix() + message;
            System.out.println(message);
        }
    }

    public static void sendConsole(boolean prefix, List<String> messages) {
        for (String message : messages) {
            if (prefix) message = getPrefix() + message;
            System.out.println(message);
        }
    }

    public static void sendBotError(String error) {
        sendConsole(true, breaker,
                Color.WHITE + "An error has occurred.",
                Color.WHITE + "Error: " + Color.RED + error,
                getSupportMessage(),
                breaker);
    }

    public static void sendBotError(String error, boolean shutdown) {
        sendConsole(true, breaker,
                Color.WHITE + "An error has occurred.",
                Color.WHITE + "Error: " + Color.RED + error,
                getSupportMessage(),
                breaker);
        if (shutdown) {
            System.exit(0);
        }
    }

    public static void sendBotError(String error, Exception exception, boolean debug) {
        sendConsole(true, breaker,
                Color.WHITE + "An error has occurred.",
                Color.WHITE + "Error: " + Color.RED + error,
                getSupportMessage(),
                breaker);
        if (debug) {
            sendConsole(true, Color.WHITE + "As you have debug enabled, the following stacktrace error is due to this:");
            exception.printStackTrace();
        }
    }

    public static void sendBotError(String error, Exception exception, boolean debug, boolean shutdown) {
        sendConsole(true, breaker,
                Color.WHITE + "An error has occurred.",
                Color.WHITE + "Error: " + Color.RED + error,
                getSupportMessage(),
                breaker);
        if (debug) {
            sendConsole(true, Color.WHITE + "As you have debug enabled, the following stacktrace error is due to this:");
            exception.printStackTrace();
        }
        if (shutdown) {
            System.exit(0);
        }
    }
}
