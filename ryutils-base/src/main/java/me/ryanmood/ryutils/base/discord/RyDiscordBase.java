package me.ryanmood.ryutils.base.discord;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;

import javax.annotation.Nullable;
import java.awt.*;
import java.util.Date;

public interface RyDiscordBase<Player> {

    /**
     * Set the channels that the bot can send messages to.
     */
    abstract void setChannels();

    /**
     * Code which is executed when the bot is connected.
     */
    abstract void onConnect();

    /**
     * Code which is executed before the bot is shutdown.
     */
    abstract void onShutdown();

    /**
     * Connects the bot to Discord.
     */
    void connectBot();

    /**
     * Disconnects the bot from Discord.
     */
    void shutdownBot();

    /**
     * Send a message to a channel.
     *
     * @param channel The channel you wish to send the message to.
     * @param content The content you wish to send in the message.
     */
    void sendMessage(TextChannel channel, String content);

    /**
     * Send an embed to a channel.
     *
     * @param channel The channel you wish to send the message to.
     * @param embed   The embed you wish to send.
     */
    void sendEmbed(TextChannel channel, EmbedBuilder embed);

    /**
     * Send an embed to a channel.
     *
     * @param channel     The channel you wish to send the message to.
     * @param description The description to show in the embed.
     */
    default void sendEmbed(TextChannel channel, String description) {
        this.sendEmbed(channel, null, null, null, null, description, null, null,false);
    }

    /**
     * Send an embed to a channel.
     *
     * @param channel     The channel you wish to send the message in.
     * @param colour      The colour of the embed.
     * @param description The description to show in the embed.
     */
    default void sendEmbed(TextChannel channel, Color colour, String description) {
        this.sendEmbed(channel, colour, null, null, null, description, null, null, false);
    }

    /**
     * Send an embed to a channel.
     *
     * @param channel     The channel you wish to send the message in.
     * @param colour      The colour of the embed.
     * @param description The description to show in the embed.
     * @param title       The title of the embed.
     */
    default void sendEmbed(TextChannel channel, Color colour, String description, String title) {
        this.sendEmbed(channel, colour, null, null, title, description, null, null, false);
    }

    /**
     * Send an emebd to a channel.
     *
     * @param channel     The channel you wish to send the message in.
     * @param colour      The colour of the embed.
     * @param description The description to show in the embed.
     * @param title       The title of the embed.
     * @param footer      The footer of the embed.
     */
    default void sendEmbed(TextChannel channel, Color colour, String description, String title, String footer) {
        this.sendEmbed(channel, colour, null, null, title, description, footer, null, false);
    }

    /**
     * Send an embed to a channel.
     *
     * @param channel     The channel you wish to send the message in.
     * @param colour      The colour of the embed.
     * @param description The description to show in the embed.
     * @param title       The title of the embed.
     * @param footer      The footer of the embed.
     * @param timeStamp   Should there be a timestamp?
     */
    default void sendEmbed(TextChannel channel, Color colour, String description, String title, String footer, boolean timeStamp) {
        this.sendEmbed(channel, colour, null, null, title, description, footer, null, timeStamp);
    }

    /**
     * Send an embed to a channel.
     *
     * @param channel     The channel you wish to send the message in.
     * @param colour      The colour of the embed.
     * @param description The description to show in the embed.
     * @param title       The title of the embed.
     * @param footer      The footer of the embed.
     * @param footerIcon  The link to the icon for the footer.
     * @param timeStamp   Should there be a timestamp?
     */
    default void sendEmbed(TextChannel channel, Color colour, String description, String title, String footer, String footerIcon, boolean timeStamp) {
        this.sendEmbed(channel, colour, null,null, title, description, footer, footerIcon, timeStamp);
    }

     /**
     * Send an embed to a channel.
     *
     * @param channel     The channel you wish to send the message in.
     * @param colour      The colour of the embed.
     * @param description The description to show in the embed.
     * @param title       The title of the embed.
     * @param footer      The footer of the embed.
     * @param timeStamp   Should there be a timestamp?
     * @param author      The author of the embed.
     */
    default void sendEmbed(TextChannel channel, Color colour, String description, String title, String footer, boolean timeStamp, String author) {
        this.sendEmbed(channel, colour, author, null, title, description, footer, null, timeStamp);
    }

    /**
     * Send an embed to a channel.
     *
     * @param channel     The channel you wish to send the message in.
     * @param colour      The colour of the embed.
     * @param description The description to show in the embed.
     * @param title       The title of the embed.
     * @param footer      The footer of the embed.
     * @param footerIcon  The link to the icon for the footer.
     * @param timeStamp   Should there be a timestamp?
     * @param author      The author of the embed.
     * @param authorIcon  The icon of the author icon.
     */
    default void sendEmbed(TextChannel channel, Color colour, String description, String title, String footer, String footerIcon, boolean timeStamp,
                           String author, String authorIcon) {
        this.sendEmbed(channel, colour, author, authorIcon, title, description, footer, footerIcon, timeStamp);
    }

    /**
     * Send an embed to a channel.
     *
     * @param channel     The channel you wish to send the message in.
     * @param colour      The colour of the embed.
     * @param description The description to show in the embed.
     * @param title       The title of the embed.
     * @param footer      The footer of the embed.
     * @param timeStamp   Should there be a timestamp?
     * @param author      The author of the embed.
     * @param authorIcon  The icon of the author icon.
     */
    default void sendEmbed(TextChannel channel, Color colour, String description, String title, String footer, boolean timeStamp, String author, String authorIcon) {
        this.sendEmbed(channel, colour, author, authorIcon, title, description, footer, null, timeStamp);
    }

    /**
     * Send a player a message in an embed.
     *
     * @param channel         The channel the embed should be sent in.
     * @param player          The player.
     * @param content         The content you wish to be sent.
     * @param contentInAuthor Should the content be placed in the author field?
     * @param colour          The colour of the embed should be.
     */
    void sendPlayerEmbed(TextChannel channel, Player player, String content, boolean contentInAuthor, Color colour);

    /**
     * Send a player a message in an embed.
     *
     * @param channel         The channel the embed should be sent in.
     * @param playerName      The name of the player.
     * @param UUID            The UUID of the player.
     * @param content         The content you wish to be sent.
     * @param contentInAuthor Should the content be placed in the author field?
     * @param colour          The colour of the embed should be.
     */
    default void sendPlayerEmbed(TextChannel channel, String playerName, String UUID, String content, boolean contentInAuthor, Color colour) {
        EmbedBuilder builder = new EmbedBuilder();

        builder.setAuthor(contentInAuthor ? content : playerName, null, "https://crafatar.com/avatars/" + UUID + "?overlay=1");
        if (!contentInAuthor) builder.setDescription(content);
        builder.setColor(colour);

        this.sendEmbed(channel, builder);
    }

    /**
     *
     * @param channel     The channel the embed should be sent in.
     * @param colour      The colour of the embed.
     * @param author      The author of the embed.
     * @param authorIcon  The icon of the author icon.
     * @param title       The title of the embed.
     * @param description The description to show in the embed.
     * @param footer      The footer of the embed.
     * @param footerIcon  The icon of the author icon.
     * @param timeStamp   Should there be a timestamp?
     */
    default void sendEmbed(TextChannel channel, @Nullable Color colour, @Nullable String author, @Nullable String authorIcon, @Nullable String title,
                           @Nullable String description, @Nullable String footer, @Nullable String footerIcon, boolean timeStamp) {
        EmbedBuilder builder = new EmbedBuilder();

        if (author != null) {
            if (authorIcon == null) builder.setAuthor(author, null, null);
            else builder.setAuthor(author, null, authorIcon);
        }
        if (title != null) builder.setTitle(title);
        if (description != null) builder.setDescription(description);
        if (footer != null) {
            if (footerIcon == null) builder.setFooter(footer, null);
            else builder.setFooter(footerIcon, footerIcon);
        }
        if (timeStamp) builder.setTimestamp(new Date().toInstant());
        if (colour != null) builder.setColor(colour);

        this.sendEmbed(channel, builder);
    }

    /**
     * Check if someone is boosting.
     *
     * @param guildId The ID of the guild.
     * @param userId  The ID of the user.
     * @return        {@link Boolean} of if the member is boosting.
     */
    boolean isBoosting(long guildId, String userId);

    /**
     * Check if someone is boosting.
     *
     * @param guild  The guild.
     * @param userId The ID of the user.
     * @return       {@link Boolean} of if the member is boosting.
     */
    boolean isBoosting(Guild guild, String userId);

    /**
     * Check if someone is boosting.
     *
     * @param member The member to check.
     * @return
     */
    boolean isBoosting(Member member);

    /**
     * Check if a player has a specific role in a discord server.
     *
     * @param guildId The ID of the guild.
     * @param userId  The ID of the user.
     * @param roleId  The ID of the role.
     * @return        {@link Boolean} of if the user has the role.
     */
    boolean hasRole(long guildId, String userId, String roleId);

    /**
     * Check if a player has a specific role in a discord server.
     *
     * @param guild  The guild.
     * @param userId The ID of the user.
     * @param roleId The ID of the role.
     * @return        {@link Boolean} of if the user has the role.
     */
    boolean hasRole(Guild guild, String userId, String roleId);

    /**
     * Check if a player has a specific role in a discord server.
     *
     * @param guild  The guild.
     * @param member The user.
     * @param roleId The ID of the role.
     * @return       {@link Boolean} of if the user has the role.
     */
    boolean hasRole(Guild guild, Member member, String roleId);

    /**
     * Check if a player has a specific role in a discord server.
     *
     * @param guild  The guild.
     * @param member The user.
     * @param role   The role.
     * @return       {@link Boolean} of if the user has the role.
     */
    boolean hasRole(Guild guild, Member member, Role role);

    /**
     * Check if a player has been timed out in a discord server.
     *
     * @param guildId The ID of the guild.
     * @param userId  The ID of the user.
     * @return        {@link Boolean} of if the user has been timed out.
     */
    boolean isTimedOut(long guildId, String userId);

    /**
     * Check if a player has been timed out in a discord server.
     *
     * @param guild  The guild.
     * @param userId The ID of the user.
     * @return       {@link Boolean} of if the user has been timed out.
     */
    boolean isTimedOut(Guild guild, String userId);

    /**
     * Check if a player has been timed out in a discord server.
     *
     * @param guild  The guild.
     * @param member The user.
     * @return       {@link Boolean} of if the user has been timed out.
     */
    boolean isTimedOut(Guild guild, Member member);

    /**
     * Check if a player is in a guild.
     *
     * @param guildId The ID of the guild.
     * @param userId  The ID of the user.
     * @return        {@link Boolean} of if the user is in the guild.
     */
    boolean isInGuild(long guildId, String userId);

    /**
     * Check if a player is in a guild.
     *
     * @param guild  The guild.
     * @param userId The ID of the user.
     * @return       {@link Boolean} of if the user has been timed out.
     */
    boolean isInGuild(Guild guild, String userId);

    /**
     * Check if a player is in a guild.
     *
     * @param guild  The guild.
     * @param member The member.
     * @return       {@link Boolean} of if the user has been timed out.
     */
    boolean isInGuild(Guild guild, Member member);

}
