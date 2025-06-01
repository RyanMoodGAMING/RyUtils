package me.ryanmood.ryutils.bungee;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import lombok.AccessLevel;
import lombok.Getter;
import me.ryanmood.ryutils.base.RyPluginMessageBase;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.PluginMessageEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.event.EventHandler;

@Getter
public abstract class RyPluginMessages implements RyPluginMessageBase<ProxiedPlayer>, Listener {

    @Getter(AccessLevel.PRIVATE)
    private Plugin plugin;

    private String outgoingChannelName;
    private String incomingChannelName;

    /**
     * Initialize the plugin message instance.
     *
     * @param plugin The instance of the plugin instance.
     */
    public RyPluginMessages(Plugin plugin) {
        this(plugin, "BungeeCord", "BungeeCord");
    }

    /**
     * Initialize the plugin message instance.
     *
     * @param plugin              The instance of the plugin instance.
     * @param outgoingChannelName The name of the outgoing channel.
     * @param incomingChannelName The name of the incoming channel.
     */
    public RyPluginMessages(Plugin plugin, String outgoingChannelName, String incomingChannelName) {
        this.plugin = plugin;
        this.outgoingChannelName = outgoingChannelName;
        this.incomingChannelName = incomingChannelName;

        plugin.getProxy().registerChannel(outgoingChannelName);
        plugin.getProxy().registerChannel(incomingChannelName);
        plugin.getProxy().getPluginManager().registerListener(plugin, this);
    }

    /**
     * The actions that occur when a plugin message is received.
     *
     * @param subchannel The subchannel of the message.
     * @param input      The message's message.
     */
    protected abstract void receievedActions(String subchannel, ByteArrayDataInput input);

    @EventHandler
    public void onPluginMessaegeReceived(PluginMessageEvent event) {
        if (!event.getTag().equalsIgnoreCase(this.getIncomingChannelName())) return;

        ByteArrayDataInput input = ByteStreams.newDataInput(event.getData());
        String subchannel = input.readUTF();

        this.receievedActions(subchannel, input);
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
