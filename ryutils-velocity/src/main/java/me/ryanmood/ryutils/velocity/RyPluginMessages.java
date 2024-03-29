package me.ryanmood.ryutils.velocity;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.connection.PluginMessageEvent;
import com.velocitypowered.api.event.proxy.ProxyInitializeEvent;
import com.velocitypowered.api.plugin.Plugin;
import com.velocitypowered.api.proxy.Player;
import com.velocitypowered.api.proxy.ProxyServer;
import com.velocitypowered.api.proxy.ServerConnection;
import com.velocitypowered.api.proxy.messages.MinecraftChannelIdentifier;
import com.velocitypowered.api.proxy.server.RegisteredServer;
import lombok.Getter;
import net.kyori.adventure.text.Component;

import java.util.Optional;

@SuppressWarnings("unused")
public abstract class RyPluginMessages {

    private Plugin instance;
    private ProxyServer proxyServer;

    @Getter
    private MinecraftChannelIdentifier identifier;

    /**
     * Create a plugin message instance.
     */
    public RyPluginMessages() {
        this(RySetup.getPluginInstance(), RySetup.getProxyServer(), "custom");
    }

    /**
     * Create a plugin message instance.
     *
     * @param instance    The plugin instance.
     * @param proxyServer The proxy server.
     */
    public RyPluginMessages(Plugin instance, ProxyServer proxyServer) {
        this(instance, proxyServer, "custom");
    }

    /**
     * Create a plugin message instance.
     *
     * @param identifier The identifier the plugin will use.
     */
    public RyPluginMessages(String identifier) {
        this(RySetup.getPluginInstance(), RySetup.getProxyServer(), identifier);
    }

    /**
     * Create a plugin message instance.
     *
     * @param instance    The instance of the plugin.
     * @param proxyServer The proxy server.
     * @param identifier  The identifier the plugin will use.
     */
    public RyPluginMessages(Plugin instance, ProxyServer proxyServer, String identifier) {
        this.proxyServer = proxyServer;
        this.identifier = MinecraftChannelIdentifier.from(identifier + ":main");
        this.instance = instance;

        proxyServer.getEventManager().register(instance, this);
    }

    protected abstract void receievedPlayerActions(Player player, String subchannel, ByteArrayDataInput input);
    protected abstract void receievedServerActions(ServerConnection server, String subchannel, ByteArrayDataInput input);

    @Subscribe
    public void onProxyInitialization(ProxyInitializeEvent event) {
        this.proxyServer.getChannelRegistrar().register(this.identifier);
    }

    @Subscribe
    public void onPluginMessage(PluginMessageEvent event) {
        if (event.getIdentifier() != this.identifier) return;

        ByteArrayDataInput input = ByteStreams.newDataInput(event.getData());
        String subchannel = input.readUTF();

        if (event.getSource() instanceof Player) {
            Player player = (Player) event.getSource();

            this.receievedPlayerActions(player, subchannel, input);
            return;
        }

        if (event.getSource() instanceof RegisteredServer) {
            ServerConnection server = (ServerConnection) event.getSource();

            this.receievedServerActions(server, subchannel, input);
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
        server.sendPluginMessage(this.identifier, output.toByteArray());
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

        if (connection.isPresent()) connection.get().sendPluginMessage(this.identifier, output.toByteArray());
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
        player.sendPluginMessage(this.identifier, output.toByteArray());
    }

    // Responses

    public void kickAllResponse(String subchannel, ByteArrayDataInput input) {
        if (!subchannel.equalsIgnoreCase("RyUtils-KickAll")) return;

        Component reason = RyMessageUtils.translate(input.readUTF());

        for (Player player : this.proxyServer.getAllPlayers()) {
            player.disconnect(reason);
        }
    }

}
