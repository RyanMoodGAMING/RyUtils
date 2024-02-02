package me.ryanmood.ryutils.bungee;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import lombok.Getter;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.PluginMessageEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

/*
 * This software and its content is copyright of RyanMoodGAMING - © RyanMoodGAMING 2023. All rights reserved.
 * Any redistribution or reproduction of part or all of the contents in any form is prohibited other than the following:
 * you may print or download to a local hard disk extracts for your personal and non-commercial use only
 * you may copy the content to individual third parties for their personal use, but only if you acknowledge the website as the source of the material
 * You may not, except with our express written permission, distribute or commercially exploit the content. Nor may you transmit it or store it in any other website or other form of electronic retrieval system.
 */

@SuppressWarnings("unused")
public abstract class RyPluginMessages implements Listener {

    @Getter
    private String outgoingChannelName;
    @Getter
    private String incomingChannelName;

    /**
     * Create a Plugin Message instance.
     */
    public RyPluginMessages() {
        this("BungeeCord", "Bungeecord");
    }

    /**
     * Create a Plugin Message instance.
     *
     * @param outgoingChannelName The name of the outgoing channel.
     * @param incomingChannelName The name of the incoming channel.
     */
    public RyPluginMessages(String outgoingChannelName, String incomingChannelName) {
        this.outgoingChannelName = outgoingChannelName;
        this.incomingChannelName = incomingChannelName;

        RySetup.getPluginInstance().getProxy().registerChannel(this.outgoingChannelName);
        RySetup.getPluginInstance().getProxy().getPluginManager().registerListener(RySetup.getPluginInstance(), this);
    }

    /**
     * The actions that occur when a plugin message is recieved.
     *
     * @param subchannel The sub channel of the message.
     * @param input      The message's message.
     */
    protected abstract void receievedActions(String subchannel, ByteArrayDataInput input);

    @EventHandler
    public void onPluginMessageReceive(PluginMessageEvent event) {
        if (!event.getTag().equalsIgnoreCase(this.getIncomingChannelName())) return;
        ByteArrayDataInput input = ByteStreams.newDataInput(event.getData());
        String subchannel = input.readUTF();

        receievedActions(subchannel, input);
    }

    /**
     * Send a plugin message.
     *
     * @param player The player who is sending the message.
     * @param output The message that is being sent.
     */
    public void send(ProxiedPlayer player, ByteArrayDataOutput output) {
        player.getServer().getInfo().sendData(this.getOutgoingChannelName(), output.toByteArray());
    }

    /**
     * Send a plugin message.
     *
     * @param player     The player that is sending the message.
     * @param subchannel The subchannel the message is being sent via.
     * @param message    The message that is being sent.
     */
    public void send(ProxiedPlayer player, String subchannel, String message) {
        ByteArrayDataOutput output = ByteStreams.newDataOutput();

        output.writeUTF(subchannel);
        output.writeUTF(message);

        send(player, output);
    }

    // Responses

    public void kickAllResponse(String subchannel, ByteArrayDataInput input) {
        if (!subchannel.equalsIgnoreCase("RyUtils-KickAll")) return;

        // String reason = input.readUTF();
        BaseComponent[] reason = TextComponent.fromLegacyText(input.readUTF());

        for (ProxiedPlayer player : ProxyServer.getInstance().getPlayers()) {
            player.disconnect(reason);
        }
    }

}
