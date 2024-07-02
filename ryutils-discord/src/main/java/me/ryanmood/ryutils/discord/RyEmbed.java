package me.ryanmood.ryutils.discord;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import org.jetbrains.annotations.Nullable;

import java.awt.*;
import java.util.Date;

public class RyEmbed {

    private RyDiscord discord;

    public RyEmbed(RyDiscord discord) {
        this.discord = discord;
    }

    /**
     * Send an embed to a channel.
     *
     * @param channel The channel you wish to send the embed to.
     * @param builder The embed that you have build.
     */
    public void sendEmbed(TextChannel channel, EmbedBuilder builder) {
        if (channel == null) {
            RyMessageUtils.sendBotError("An error occurred while trying to send a message to a channel.");
            return;
        }

        channel.sendMessageEmbeds(builder.build()).queue();
    }

    /**
     * Send an embed to a channel.
     *
     * @param channel     The channel you wish to send the embed to.
     * @param description The description of the embed.
     * @param colour      The colour of the embed.
     */
    public void sendEmbed(TextChannel channel, String description, Color colour) {
        if (channel == null) {
            RyMessageUtils.sendBotError("An error occurred while trying to send a message to a channel.");
            return;
        }

        EmbedBuilder builder = new EmbedBuilder();
        builder.setDescription(description);
        builder.setColor(colour);

        channel.sendMessageEmbeds(builder.build()).queue();
    }

    /**
     * Send an embed to a channel.
     *
     * @param channel     The channel you wish to send the embed to.
     * @param title       The title of the embed.
     * @param description The description of the embed.
     * @param colour      The colour of the embed.
     */
    public void sendEmbed(TextChannel channel, String title, String description, Color colour) {
        if (channel == null) {
            RyMessageUtils.sendBotError("An error occurred while trying to send a message to a channel.");
            return;
        }

        EmbedBuilder builder = new EmbedBuilder();
        builder.setTitle(title);
        builder.setDescription(description);
        builder.setColor(colour);

        channel.sendMessageEmbeds(builder.build()).queue();
    }

    /**
     * Send an embed to a channel.
     *
     * @param channel     The channel you wish to send the embed to.
     * @param title       The title of the embed.
     * @param description The description of the embed.
     * @param footer      The footer of the embed.
     * @param timeStamp   Should there be a timestamp on the embed?
     * @param colour      The colour of the embed.
     */
    public void sendEmbed(TextChannel channel, String title, String description, String footer, boolean timeStamp, Color colour) {
        if (channel == null) {
            RyMessageUtils.sendBotError("An error occurred while trying to send a message to a channel.");
            return;
        }

        EmbedBuilder builder = new EmbedBuilder();
        builder.setTitle(title);
        builder.setDescription(description);
        builder.setFooter(footer);
        builder.setTimestamp(new Date().toInstant());
        builder.setColor(colour);

        channel.sendMessageEmbeds(builder.build()).queue();
    }

    /**
     * Send an embed to a channel.
     *
     * @param channel     The channel you wish to send the embed to.
     * @param author      The author of the embed.
     * @param title       The title of the embed.
     * @param description The description of the embed.
     * @param footer      The footer of the embed.
     * @param timeStamp   Should there be a timestamp on the embed?
     * @param colour      The colour of the embed.
     */
    public void sendEmbed(TextChannel channel, String author, String title, String description, String footer, boolean timeStamp, Color colour) {
        if (channel == null) {
            RyMessageUtils.sendBotError("An error occurred while trying to send a message to a channel.");
            return;
        }

        EmbedBuilder builder = new EmbedBuilder();
        builder.setAuthor(author);
        builder.setTitle(title);
        builder.setDescription(description);
        builder.setFooter(footer);
        builder.setTimestamp(new Date().toInstant());
        builder.setColor(colour);

        channel.sendMessageEmbeds(builder.build()).queue();
    }

    /**
     * Send an embed to a channel.
     *
     * @param channel     The channel you wish to send the embed to.
     * @param author      The author of the embed.
     * @param authorIcon  The url of the author icon.
     * @param title       The title of the embed.
     * @param description The description of the embed.
     * @param footer      The footer of the embed.
     * @param timeStamp   Should there be a timestamp on the embed?
     * @param colour      The colour of the embed.
     */
    public void sendEmbed(TextChannel channel, String author, String authorIcon, String title, String description, String footer, boolean timeStamp, Color colour) {
        if (channel == null) {
            RyMessageUtils.sendBotError("An error occurred while trying to send a message to a channel.");
            return;
        }

        EmbedBuilder builder = new EmbedBuilder();
        builder.setAuthor(author, authorIcon);
        builder.setTitle(title);
        builder.setDescription(description);
        builder.setFooter(footer);
        if (timeStamp) builder.setTimestamp(new Date().toInstant());
        builder.setColor(colour);

        channel.sendMessageEmbeds(builder.build()).queue();
    }

    /**
     * Send an embed to a channel.
     *
     * @param channel     The channel you wish for the embed to be sent to.
     * @param author      The author of the embed.
     * @param authorIcon  The url of the author icon.
     * @param title       The title of the embed.
     * @param description The description of the embed.
     * @param footer      The footer of the embed.
     * @param timeStamp   Should there be a timestamp on the embed?
     * @param colour      The colour of the embed.
     */
    public void sendEmbed(TextChannel channel, @Nullable String author, @Nullable String authorIcon, @Nullable String title,
                          @Nullable String description, @Nullable String footer, Boolean timeStamp, Color colour) {
        if (channel == null) {
            RyMessageUtils.sendBotError("An error occurred while trying to send a message to a channel.");
            return;
        }

        EmbedBuilder builder = new EmbedBuilder();
        if (author != null) {
            if (authorIcon == null) {
                builder.setAuthor(author, null, null);
            } else if (authorIcon != null) {
                builder.setAuthor(author, null, authorIcon);
            }
        }
        if (title != null) {
            builder.setTitle(title);
        }
        if (description != null) {
            builder.setDescription(description);
        }
        if (footer != null) {
            builder.setFooter(footer);
        }
        if (timeStamp) {
            builder.setTimestamp(new Date().toInstant());
        }
        builder.setColor(colour);

        channel.sendMessageEmbeds(builder.build()).queue();
    }

}
