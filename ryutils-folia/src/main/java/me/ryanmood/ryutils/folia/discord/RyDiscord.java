package me.ryanmood.ryutils.folia.discord;

import lombok.AccessLevel;
import lombok.Getter;
import me.ryanmood.ryutils.base.discord.RyDiscordBase;
import me.ryanmood.ryutils.folia.RyMessageUtil;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.requests.GatewayIntent;
import org.bukkit.entity.Player;

import java.awt.*;

@Getter
public abstract class RyDiscord implements RyDiscordBase<Player> {

    @Getter(AccessLevel.PRIVATE)
    private String discordToken;

    private JDA jda;

    /**
     * Set the channels that the bot can send messages to.
     */
    @Override
    public abstract void setChannels();

    /**
     * Code which is executed when the bot is connected.
     */
    @Override
    public abstract void onConnect();

    /**
     * Code which is executed before the bot is shutdown.
     */
    @Override
    public abstract void onShutdown();

    /**
     * Connects the bot to Discord.
     */
    @Override
    public void connectBot() {
        try {
            this.jda = JDABuilder.createDefault(this.getDiscordToken())
                    .enableIntents(GatewayIntent.GUILD_MEMBERS, GatewayIntent.MESSAGE_CONTENT)
                    .build()
                    .awaitReady();
        } catch (InterruptedException e) {
            RyMessageUtil.getUtil().sendError("An error occured while connecting to the discord bot.", e);
        }

        if (this.jda == null) {
            RyMessageUtil.getUtil().sendError("Plugin has been disabled due to JDA been null.", true);
        }

        this.onConnect();
    }

    /**
     * Disconnects the bot from Discord.
     */
    @Override
    public void shutdownBot() {
        if (jda != null) {
            this.onShutdown();
            this.jda.shutdown();
        }
    }

    /**
     * Send a message to a channel.
     *
     * @param channel The channel you wish to send the message to.
     * @param content The content you wish to send in the message.
     */
    @Override
    public void sendMessage(TextChannel channel, String content) {
        if (channel == null) {
            RyMessageUtil.getUtil().sendError("An error occurred while trying to send a message to a channel.");
            return;
        }

        channel.sendMessage(content).queue();
    }

    /**
     * Send an embed to a channel.
     *
     * @param channel The channel you wish to send the message to.
     * @param embed   The embed you wish to send.
     */
    @Override
    public void sendEmbed(TextChannel channel, EmbedBuilder embed) {
        if (channel == null) {
            RyMessageUtil.getUtil().sendError("An error occurred while trying to send a message to a channel.");
            return;
        }

        channel.sendMessageEmbeds(embed.build()).queue();
    }

    /**
     * Send a player a message in an embed.
     *
     * @param channel         The channel the embed should be sent in.
     * @param player   The player.
     * @param content         The content you wish to be sent.
     * @param contentInAuthor Should the content be placed in the author field?
     * @param colour          The colour of the embed should be.
     */
    @Override
    public void sendPlayerEmbed(TextChannel channel, Player player, String content, boolean contentInAuthor, Color colour) {
        if (channel == null) {
            RyMessageUtil.getUtil().sendError("An error occurred while trying to send a message to a channel.");
            return;
        }

        EmbedBuilder builder = new EmbedBuilder();

        if (player != null) builder.setAuthor(contentInAuthor ? content : RyMessageUtil.getUtil().stripColours(player.getDisplayName()),
                null, "https://crafatar.com/avatars/" + player.getUniqueId().toString() + "?overlay=1");
        if (!contentInAuthor) builder.setDescription(content);
        builder.setColor(colour);

        this.sendEmbed(channel, builder);
    }

    /**
     * Check if someone is boosting.
     *
     * @param guildId The ID of the guild.
     * @param userId  The ID of the user.
     * @return        {@link Boolean} of if the member is boosting.
     */
    @Override
    public boolean isBoosting(long guildId, String userId) {
        Guild guild = this.getJda().getGuildById(guildId);
        if (guild == null) {
            RyMessageUtil.getUtil().sendError("Guild provided is null.");
            return false;
        }

        Member member = guild.getMemberById(userId);
        if (member == null) {
            RyMessageUtil.getUtil().sendError("Member provided is null.");
            return false;
        }

        return member.isBoosting();
    }

    /**
     * Check if someone is boosting.
     *
     * @param guild  The guild.
     * @param userId The ID of the user.
     * @return       {@link Boolean} of if the member is boosting.
     */
    @Override
    public boolean isBoosting(Guild guild, String userId) {
        if (guild == null) {
            RyMessageUtil.getUtil().sendError("Guild provided is null.");
            return false;
        }

        Member member = guild.getMemberById(userId);
        if (member == null) {
            RyMessageUtil.getUtil().sendError("Member provided is null.");
            return false;
        }

        return member.isBoosting();
    }

    /**
     * Check if someone is boosting.
     *
     * @param member The member to check.
     * @return
     */
    @Override
    public boolean isBoosting(Member member) {
        if (member == null) {
            RyMessageUtil.getUtil().sendError("Member provided is null.");
            return false;
        }

        return member.isBoosting();
    }

    /**
     * Check if a player has a specific role in a discord server.
     *
     * @param guildId The ID of the guild.
     * @param userId  The ID of the user.
     * @param roleId  The ID of the role.
     * @return        {@link Boolean} of if the user has the role.
     */
    @Override
    public boolean hasRole(long guildId, String userId, String roleId) {
        Guild guild = this.getJda().getGuildById(guildId);
        if (guild == null) {
            RyMessageUtil.getUtil().sendError("Guild provided is null.");
            return false;
        }

        Member member = guild.getMemberById(userId);
        if (member == null) {
            RyMessageUtil.getUtil().sendError("Member provided is null.");
            return false;
        }

        Role role = guild.getRoleById(roleId);
        if (role == null) {
            RyMessageUtil.getUtil().sendError("Role provided is null.");
            return false;
        }

        return member.getRoles().contains(role);
    }

    /**
     * Check if a player has a specific role in a discord server.
     *
     * @param guild  The guild.
     * @param userId The ID of the user.
     * @param roleId The ID of the role.
     * @return        {@link Boolean} of if the user has the role.
     */
    @Override
    public boolean hasRole(Guild guild, String userId, String roleId) {
        if (guild == null) {
            RyMessageUtil.getUtil().sendError("Guild provided is null.");
            return false;
        }

        Member member = guild.getMemberById(userId);
        if (member == null) {
            RyMessageUtil.getUtil().sendError("Member provided is null.");
            return false;
        }

        Role role = guild.getRoleById(roleId);
        if (role == null) {
            RyMessageUtil.getUtil().sendError("Role provided is null.");
            return false;
        }

        return member.getRoles().contains(role);
    }

    /**
     * Check if a player has a specific role in a discord server.
     *
     * @param guild  The guild.
     * @param member The user.
     * @param roleId The ID of the role.
     * @return       {@link Boolean} of if the user has the role.
     */
    @Override
    public boolean hasRole(Guild guild, Member member, String roleId) {
        if (guild == null) {
            RyMessageUtil.getUtil().sendError("Guild provided is null.");
            return false;
        }

        if (member == null) {
            RyMessageUtil.getUtil().sendError("Member provided is null.");
            return false;
        }

        Role role = guild.getRoleById(roleId);
        if (role == null) {
            RyMessageUtil.getUtil().sendError("Role provided is null.");
            return false;
        }

        return member.getRoles().contains(role);
    }

    /**
     * Check if a player has a specific role in a discord server.
     *
     * @param guild  The guild.
     * @param member The user.
     * @param role   The role.
     * @return       {@link Boolean} of if the user has the role.
     */
    @Override
    public boolean hasRole(Guild guild, Member member, Role role) {
        if (guild == null) {
            RyMessageUtil.getUtil().sendError("Guild provided is null.");
            return false;
        }

        if (member == null) {
            RyMessageUtil.getUtil().sendError("Member provided is null.");
            return false;
        }

        if (role == null) {
            RyMessageUtil.getUtil().sendError("Role provided is null.");
            return false;
        }

        return member.getRoles().contains(role);
    }

    /**
     * Check if a player has been timed out in a discord server.
     *
     * @param guildId The ID of the guild.
     * @param userId  The ID of the user.
     * @return        {@link Boolean} of if the user has been timed out.
     */
    @Override
    public boolean isTimedOut(long guildId, String userId) {
        Guild guild = this.getJda().getGuildById(guildId);
        if (guild == null) {
            RyMessageUtil.getUtil().sendError("Guild provided is null.");
            return false;
        }

        Member member = guild.getMemberById(userId);
        if (member == null) {
            RyMessageUtil.getUtil().sendError("Member provided is null.");
            return false;
        }

        return member.isTimedOut();
    }

    /**
     * Check if a player has been timed out in a discord server.
     *
     * @param guild  The guild.
     * @param userId The ID of the user.
     * @return       {@link Boolean} of if the user has been timed out.
     */
    @Override
    public boolean isTimedOut(Guild guild, String userId) {
        if (guild == null) {
            RyMessageUtil.getUtil().sendError("Guild provided is null.");
            return false;
        }

        Member member = guild.getMemberById(userId);
        if (member == null) {
            RyMessageUtil.getUtil().sendError("Member provided is null.");
            return false;
        }

        return member.isTimedOut();
    }

    /**
     * Check if a player has been timed out in a discord server.
     *
     * @param guild  The guild.
     * @param member The user.
     * @return       {@link Boolean} of if the user has been timed out.
     */
    @Override
    public boolean isTimedOut(Guild guild, Member member) {
        if (guild == null) {
            RyMessageUtil.getUtil().sendError("Guild provided is null.");
            return false;
        }

        if (member == null) {
            RyMessageUtil.getUtil().sendError("Member provided is null.");
            return false;
        }

        return member.isTimedOut();
    }

    /**
     * Check if a player is in a guild.
     *
     * @param guildId The ID of the guild.
     * @param userId  The ID of the user.
     * @return        {@link Boolean} of if the user is in the guild.
     */
    @Override
    public boolean isInGuild(long guildId, String userId) {
        Guild guild = this.getJda().getGuildById(guildId);
        if (guild == null) {
            RyMessageUtil.getUtil().sendError("Guild provided is null.");
            return false;
        }

        Member member = guild.getMemberById(userId);
        if (member == null) return false;
        else return true;
    }

    /**
     * Check if a player is in a guild.
     *
     * @param guild  The guild.
     * @param userId The ID of the user.
     * @return       {@link Boolean} of if the user has been timed out.
     */
    @Override
    public boolean isInGuild(Guild guild, String userId) {
        if (guild == null) {
            RyMessageUtil.getUtil().sendError("Guild provided is null.");
            return false;
        }

        Member member = guild.getMemberById(userId);
        if (member == null) return false;
        else return true;
    }

    /**
     * Check if a player is in a guild.
     *
     * @param guild  The guild.
     * @param member The member.
     * @return       {@link Boolean} of if the user has been timed out.
     */
    @Override
    public boolean isInGuild(Guild guild, Member member) {
        if (guild == null) {
            RyMessageUtil.getUtil().sendError("Guild provided is null.");
            return false;
        }

        if (member == null) return false;
        else return true;
    }

}
