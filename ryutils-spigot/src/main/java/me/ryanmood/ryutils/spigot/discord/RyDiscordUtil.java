package me.ryanmood.ryutils.spigot.discord;

import lombok.Getter;
import me.ryanmood.ryutils.spigot.RyMessageUtils;
import me.ryanmood.ryutils.spigot.RySetup;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.requests.GatewayIntent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.Nullable;

import java.awt.*;
import java.util.Date;

/*
 * This software and its content is copyright of RyanMoodGAMING - © RyanMoodGAMING 2023. All rights reserved.
 * Any redistribution or reproduction of part or all of the contents in any form is prohibited other than the following:
 * you may print or download to a local hard disk extracts for your personal and non-commercial use only
 * you may copy the content to individual third parties for their personal use, but only if you acknowledge the website as the source of the material
 * You may not, except with our express written permission, distribute or commercially exploit the content. Nor may you transmit it or store it in any other website or other form of electronic retrieval system.
 */

public abstract class RyDiscordUtil {

    /**
     * The JDA instance.
     */
    @Getter
    private JDA jda;
    private String botToken;

    /**
     * Bot information.
     *
     * @param botToken The token of the Discord bot.
     */
    protected RyDiscordUtil(String botToken) {
        this.botToken = botToken;
    }

    /**
     * Set the channels that the bot can send messages to.
     */
    protected abstract void setChannels();

    /**
     * Connects the bot to Discord.
     *
     * @param instance The instance of the plugin.
     */
    public void connectBot(Plugin instance) {
        try {
            this.jda = JDABuilder.createDefault(this.botToken).enableIntents(GatewayIntent.GUILD_MEMBERS)
                    .build().awaitReady();
        } catch (InterruptedException exception) {
            RyMessageUtils.sendPluginError("An error occurred while connecting to the discord bot.", exception, RySetup.isDebug());
        }
        if (this.jda == null) {
            RyMessageUtils.sendPluginError("Plugin has been disabled due to JDA been null.");
            Bukkit.getServer().getPluginManager().disablePlugin(instance);
            return;
        }
        onConnect();
    }

    /**
     * Disconnects the bot from Discord.
     */
    public void shutdownBot() {
        if (jda != null) {
            onShutdown();
            jda.shutdown();
        }
    }

    /**
     * Executes after the bot has connected and JDA isn't null.
     */
    protected void onConnect() {
        // Override if you need something to happen, otherwise nothing will happen.
    }

    /**
     * Executes before the bot shuts down and JDA isn't null.
     */
    protected void onShutdown() {
        // Override if you need something to happen, otherwise nothing will happen.
    }

    /**
     * Send a message to a channel.
     *
     * @param channel The channel you wish to send a message to.
     * @param content The content you wish to send in the message.
     */
    public void sendMessage(TextChannel channel, String content) {
        if (channel == null) {
            RyMessageUtils.sendPluginError("An error occurred while trying to send a message to a channel.");
            return;
        }

        channel.sendMessage(content).queue();
    }

    /**
     * Send a player message in an embed.
     *
     * @param channel         The channel the embed should be sent in.
     * @param player          The player.
     * @param content         The content you wish to be sent.
     * @param contentInAuthor Should the content be in the author field?
     * @param colour           The colour of the emebed.
     */
    public void sendPlayerEmbed(TextChannel channel, @Nullable Player player, String content, boolean contentInAuthor, Color colour) {
        if (channel == null) {
            RyMessageUtils.sendPluginError("An error occurred while trying to send a message to a channel.");
            return;
        }

        EmbedBuilder builder = new EmbedBuilder();

        if (player != null) {
            builder.setAuthor(contentInAuthor ? content : ChatColor.stripColor(player.getDisplayName()), null,
                    "https://crafatar.com/avatars/" + player.getUniqueId().toString() + "?overlay=1");
        }

        if (!contentInAuthor) {
            builder.setDescription(content);
        }
        builder.setColor(colour);

        channel.sendMessageEmbeds(builder.build()).queue();
    }

    /**
     * Send an embed to a channel.
     *
     * @param channel The channel you wish to send the embed to.
     * @param builder The embed that you have build.
     */
    public void sendEmbed(TextChannel channel, EmbedBuilder builder) {
        if (channel == null) {
            RyMessageUtils.sendPluginError("An error occurred while trying to send a message to a channel.");
            return;
        }

        channel.sendMessageEmbeds(builder.build()).queue();
    }

    /**
     * Send an embed to a channel.
     *
     * @param channel     The channel you wish to send the embed to.
     * @param description The description of the embed.
     * @param colour       The colour of the embed.
     */
    public void sendEmbed(TextChannel channel, String description, Color colour) {
        if (channel == null) {
            RyMessageUtils.sendPluginError("An error occurred while trying to send a message to a channel.");
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
     * @param colour       The colour of the embed.
     */
    public void sendEmbed(TextChannel channel, String title, String description, Color colour) {
        if (channel == null) {
            RyMessageUtils.sendPluginError("An error occurred while trying to send a message to a channel.");
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
            RyMessageUtils.sendPluginError("An error occurred while trying to send a message to a channel.");
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
            RyMessageUtils.sendPluginError("An error occurred while trying to send a message to a channel.");
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
            RyMessageUtils.sendPluginError("An error occurred while trying to send a message to a channel.");
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
            RyMessageUtils.sendPluginError("An error occurred while trying to send a message to a channel.");
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

    /**
     * Check if someone is boosting.
     *
     * @param guildID The ID of the guild.
     * @param userID  The ID of the user.
     * @return        boolean for if the member is boosting.
     */
    public boolean isBoosting(long guildID, String userID) {
        Guild guild = this.jda.getGuildById(guildID);
        if (guild == null) {
            RyMessageUtils.sendPluginError("Guild provided is null.");
            return false;
        }

        Member member = guild.getMemberById(userID);
        if (member == null) {
            RyMessageUtils.sendPluginError("Member provided is null.");
            return false;
        }

        return member.isBoosting();
    }

    /**
     * Check if someone is boosting.
     *
     * @param guild  The guild.
     * @param userID The ID of the user.
     * @return       boolean for if the member is boosting.
     */
    public boolean isBoosting(Guild guild, String userID) {
        if (guild == null) {
            RyMessageUtils.sendPluginError("Guild provided is null.");
            return false;
        }

        Member member = guild.getMemberById(userID);
        if (member == null) {
            RyMessageUtils.sendPluginError("Member provided is null.");
            return false;
        }
        return member.isBoosting();
    }

    /**
     *
     * @param member The member that is being checked.
     * @return       boolean for if the member is boosting.
     */
    public boolean isBoosting(Member member) {
        if (member == null) {
            RyMessageUtils.sendPluginError("Member provided is null.");
            return false;
        }
        return member.isBoosting();
    }

    /**
     * Check if a player has a specific role.
     *
     * @param guildID The ID of the guild.
     * @param userID  The ID of the user.
     * @param roleID  The ID of the role.
     * @return        boolean for if the member has the role.
     */
    public boolean hasRole(long guildID, String userID, String roleID) {
        Guild guild = this.jda.getGuildById(guildID);
        if (guild == null) {
            RyMessageUtils.sendPluginError("Guild provided is null.");
            return false;
        }

        Member member = guild.getMemberById(userID);
        if (member == null) {
            RyMessageUtils.sendPluginError("Member provided is null.");
            return false;
        }

        Role role = guild.getRoleById(roleID);

        return hasRole(guild, member, role);
    }

    /**
     * Check if a player has a specific role.
     *
     * @param guild   The guild.
     * @param userID  The ID of the user.
     * @param roleID  The ID of the role.
     * @return        boolean for if the member has the role.
     */
    public boolean hasRole(Guild guild, String userID, String roleID) {
        Member member = guild.getMemberById(userID);
        if (member == null) {
            RyMessageUtils.sendPluginError("Member provided is null.");
            return false;
        }
        Role role = guild.getRoleById(roleID);

        return hasRole(guild, member, role);
    }

    /**
     * Check if a player has a specific role.
     *
     * @param guild   The guild.
     * @param member    The member.
     * @param roleID  The ID of the role.
     * @return        boolean for if the member has the role.
     */
    public boolean hasRole(Guild guild, Member member, String roleID) {
        Role role = guild.getRoleById(roleID);

        return hasRole(guild, member, role);
    }

    /**
     * Check if a player has a specific role.
     *
     * @param guild  The guild.
     * @param member The member.
     * @param role   The role.
     * @return        boolean for if the member has the role.
     */
    public boolean hasRole(Guild guild, Member member, Role role) {
        if (guild == null) {
            RyMessageUtils.sendPluginError("Guild provided is null.");
            return false;
        }

        if (member == null) {
            RyMessageUtils.sendPluginError("Member provided is null.");
            return false;
        }

        if (role == null) {
            RyMessageUtils.sendPluginError("Role provided is null.");
            return false;
        }

        return member.getRoles().contains(role);
    }

}
