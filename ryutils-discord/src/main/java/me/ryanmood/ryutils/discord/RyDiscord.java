package me.ryanmood.ryutils.discord;

import lombok.Getter;
import lombok.Setter;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.requests.GatewayIntent;

import java.util.*;

/*
 * This software and its content is copyright of RyanMoodGAMING - © RyanMoodGAMING 2023. All rights reserved.
 * Any redistribution or reproduction of part or all of the contents in any form is prohibited other than the following:
 * you may print or download to a local hard disk extracts for your personal and non-commercial use only
 * you may copy the content to individual third parties for their personal use, but only if you acknowledge the website as the source of the material
 * You may not, except with our express written permission, distribute or commercially exploit the content. Nor may you transmit it or store it in any other website or other form of electronic retrieval system.
 */

@SuppressWarnings("unused")
public abstract class RyDiscord {

    /**
     * The JDA instance.
     */
    @Getter
    private JDA jda;
    private String botToken;

    @Getter
    private RyEmbed embed;

    @Getter
    @Setter
    private boolean debug;

    private Collection<Object> eventListeners = new HashSet<>();

    /**
     * Bot information.
     *
     * @param botToken The token of the Discord bot.
     */
    protected RyDiscord(String botToken) {
        this.botToken = botToken;
    }

    /**
     * Set the channels that the bot can send messages to.
     */
    protected abstract void setChannels();

    /**
     * Connects the bot to Discord.
     */
    public void connectBot() {
        this.onPreBuild();
        try {
            this.jda = JDABuilder.createDefault(this.botToken).enableIntents(GatewayIntent.GUILD_MEMBERS, GatewayIntent.MESSAGE_CONTENT)
                    .addEventListeners(this.eventListeners)
                    .build().awaitReady();
        } catch (InterruptedException exception) {
            RyMessageUtils.sendBotError("An error occurred while connecting to the discord bot.", exception, this.isDebug());
        }
        if (this.jda == null) {
            RyMessageUtils.sendBotError("Bot has shutdown due to JDA been null.", true);
            return;
        }
        this.embed = new RyEmbed(this);
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
     * Executes before the JDA is built.
     * <br><br>
     * This is where you should use {@link #addEvent(Object)} here and other {@link #getJda()} options
     * that are required to be done before the JDA is built.
     */
    protected abstract void onPreBuild();

    /**
     * Executes after the bot has connected and JDA isn't null.
     */
    protected abstract void onConnect();

    /**
     * Executes before the bot shuts down and JDA isn't null.
     */
    protected abstract void onShutdown();

    /**
     * Add an event to be registered.
     *
     * @param event
     */
    public void addEvent(Object event) {
        this.eventListeners.add(event);
        return;
    }

    /**
     * Send a message to a channel.
     *
     * @param channel The channel you wish to send a message to.
     * @param content The content you wish to send in the message.
     */
    public void sendMessage(TextChannel channel, String content) {
        if (channel == null) {
            RyMessageUtils.sendBotError("An error occurred while trying to send a message to a channel.");
            return;
        }

        channel.sendMessage(content).queue();
    }

    /**
     * Check if someone is boosting.
     *
     * @param guildID The ID of the guild.
     * @param userID  The ID of the user.
     * @return boolean for if the member is boosting.
     */
    public boolean isBoosting(long guildID, String userID) {
        Guild guild = this.jda.getGuildById(guildID);
        if (guild == null) {
            RyMessageUtils.sendBotError("Guild provided is null.");
            return false;
        }

        Member member = guild.getMemberById(userID);
        if (member == null) {
            RyMessageUtils.sendBotError("Member provided is null.");
            return false;
        }

        return member.isBoosting();
    }

    /**
     * Check if someone is boosting.
     *
     * @param guild  The guild.
     * @param userID The ID of the user.
     * @return boolean for if the member is boosting.
     */
    public boolean isBoosting(Guild guild, String userID) {
        if (guild == null) {
            RyMessageUtils.sendBotError("Guild provided is null.");
            return false;
        }

        Member member = guild.getMemberById(userID);
        if (member == null) {
            RyMessageUtils.sendBotError("Member provided is null.");
            return false;
        }
        return member.isBoosting();
    }

    /**
     * @param member The member that is being checked.
     * @return boolean for if the member is boosting.
     */
    public boolean isBoosting(Member member) {
        if (member == null) {
            RyMessageUtils.sendBotError("Member provided is null.");
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
     * @return boolean for if the member has the role.
     */
    public boolean hasRole(long guildID, String userID, String roleID) {
        Guild guild = this.jda.getGuildById(guildID);
        if (guild == null) {
            RyMessageUtils.sendBotError("Guild provided is null.");
            return false;
        }

        Member member = guild.getMemberById(userID);
        if (member == null) {
            RyMessageUtils.sendBotError("Member provided is null.");
            return false;
        }

        Role role = guild.getRoleById(roleID);

        return hasRole(guild, member, role);
    }

    /**
     * Check if a player has a specific role.
     *
     * @param guild  The guild.
     * @param userID The ID of the user.
     * @param roleID The ID of the role.
     * @return boolean for if the member has the role.
     */
    public boolean hasRole(Guild guild, String userID, String roleID) {
        Member member = guild.getMemberById(userID);
        if (member == null) {
            RyMessageUtils.sendBotError("Member provided is null.");
            return false;
        }
        Role role = guild.getRoleById(roleID);

        return hasRole(guild, member, role);
    }

    /**
     * Check if a player has a specific role.
     *
     * @param guild  The guild.
     * @param member The member.
     * @param roleID The ID of the role.
     * @return boolean for if the member has the role.
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
     * @return boolean for if the member has the role.
     */
    public boolean hasRole(Guild guild, Member member, Role role) {
        if (guild == null) {
            RyMessageUtils.sendBotError("Guild provided is null.");
            return false;
        }

        if (member == null) {
            RyMessageUtils.sendBotError("Member provided is null.");
            return false;
        }

        if (role == null) {
            RyMessageUtils.sendBotError("Role provided is null.");
            return false;
        }

        return member.getRoles().contains(role);
    }
}
