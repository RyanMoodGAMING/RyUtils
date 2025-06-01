package me.ryanmood.ryutils.velocity;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.connection.PluginMessageEvent;
import com.velocitypowered.api.event.proxy.ProxyInitializeEvent;
import com.velocitypowered.api.proxy.Player;
import com.velocitypowered.api.proxy.ProxyServer;
import com.velocitypowered.api.proxy.ServerConnection;
import com.velocitypowered.api.proxy.messages.MinecraftChannelIdentifier;
import com.velocitypowered.api.proxy.server.RegisteredServer;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import me.ryanmood.ryutils.base.RyPluginMessageBase;
import net.kyori.adventure.text.Component;

import java.util.Optional;

@Getter
public abstract class RyPluginMessages implements RyPluginMessageBase<Player> {

    @Getter(AccessLevel.PRIVATE)
    @Setter(AccessLevel.PRIVATE)
    private Object plugin;
    @Getter(AccessLevel.PRIVATE)
    @Setter(AccessLevel.PRIVATE)
    private ProxyServer server;

    private String identifier, channel;
    private MinecraftChannelIdentifier channelIdentifier;

    /**
     * Initialize the plugin message instance.
     *
     * @param plugin The instance of the plugin.
     * @param server The instance of the proxy server.
     */
    public RyPluginMessages(Object plugin, ProxyServer server) {
        this(plugin, server, "custom", "main");
    }

    /**
     * Initialize the plugin message instance.
     *
     * @param plugin     The instance of the plugin.
     * @param server     The instance of the proxy server.
     * @param identifier The identifier of the messages.
     * @param channel    The channel of the messages.
     */
    public RyPluginMessages(Object plugin, ProxyServer server, String identifier, String channel) {
        this.plugin = plugin;
        this.server = server;
        this.identifier = identifier;
        this.channel = channel;

        this.channelIdentifier = MinecraftChannelIdentifier.create(this.identifier, this.channel);
        server.getEventManager().register(plugin, this);
    }

    /**
     * The actions that occurs when a plugin message is received for a player.
     *
     * @param subchannel The sub channel of the message.
     * @param player     The player that has sent the message.
     * @param input      The message's message.
     */
    protected abstract void receivedPlayerActions(String subchannel, Player player, ByteArrayDataInput input);

    /**
     * The actions that occurs when a plugin message is received for a server.
     *
     * @param subchannel The sub channel of the message.
     * @param server     The server that has sent the message.
     * @param input      The message's message.
     */
    protected abstract void receivedServerActions(String subchannel, ServerConnection server, ByteArrayDataInput input);

    @Subscribe
    public void onProxyInit(ProxyInitializeEvent event) {
        this.getServer().getChannelRegistrar().register(this.getChannelIdentifier());
    }

    @Subscribe
    public void onPluginMessage(PluginMessageEvent event) {
        if (!event.getIdentifier().equals(this.getChannelIdentifier())) return;

        ByteArrayDataInput input = ByteStreams.newDataInput(event.getData());
        String subchannel = input.readUTF();

        if (event.getSource() instanceof Player) {
            Player player = (Player) event.getSource();

            this.receivedPlayerActions(subchannel, player, input);
            return;
        } else if (event.getSource() instanceof ServerConnection) {
            ServerConnection connection = (ServerConnection) event.getSource();

            this.receivedServerActions(subchannel, connection, input);
            return;
        }
     }

    /**
     * Send a message to a server.
     *
     * @param server The server which is getting sent the message.
     * @param output The message been sent.
     */
    public void send(RegisteredServer server, ByteArrayDataOutput output) {
        server.sendPluginMessage(this.channelIdentifier, output.toByteArray());
    }

    /**
     * Send a message to a server.
     *
     * @param server     The server which is getting sent the message.
     * @param subchannel The subchannel the message is being sent via.
     * @param message    The message been sent.
     */
    public void send(RegisteredServer server, String subchannel, String message) {
        ByteArrayDataOutput output = ByteStreams.newDataOutput();

        output.writeUTF(subchannel);
        output.writeUTF(message);

        this.send(server, output);
    }

    /**
     * Send a message to a server via a player.
     *
     * @param player The player who'll end the message.
     * @param output The message been sent.
     */
    public void send(Player player, ByteArrayDataOutput output) {
        Optional<ServerConnection> connection = player.getCurrentServer();

        if (connection.isPresent()) connection.get().sendPluginMessage(this.channelIdentifier, output.toByteArray());
        return;
    }

    /**
     * Send a message to a server.
     *
     * @param player     The player who'll end the message.
     * @param subchannel The subchannel the message is being sent via.
     * @param message    The message been sent.
     */
    public void send(Player player, String subchannel, String message) {
        ByteArrayDataOutput output = ByteStreams.newDataOutput();

        output.writeUTF(subchannel);
        output.writeUTF(message);

        this.send(player, output);
    }

    /**
     * Send a plugin message to a player.
     * Eg: May be useful when you are making client-side mods. Players will often ignore these.
     *
     * @param player The player you wish to send the message to.
     * @param output The message that is being sent.
     */
    public void sendPlayer(Player player, ByteArrayDataOutput output) {
        player.sendPluginMessage(this.channelIdentifier, output.toByteArray());
    }

    // Responses

    public void kickAllResponse(String subchannel, ByteArrayDataInput input) {
        if (!subchannel.equalsIgnoreCase("RyUtils-KickAll")) return;

        Component reason = RyMessageUtil.getUtil().adventureTranslate(input.readUTF());

        for (Player player : this.getServer().getAllPlayers()) {
            player.disconnect(reason);
        }
    }
}
