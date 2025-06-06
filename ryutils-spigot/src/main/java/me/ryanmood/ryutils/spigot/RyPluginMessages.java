package me.ryanmood.ryutils.spigot;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import me.ryanmood.ryutils.base.RyPluginMessageBase;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.messaging.PluginMessageListener;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.util.UUID;

public abstract class RyPluginMessages implements RyPluginMessageBase<Player>, PluginMessageListener {

    @Setter(AccessLevel.PRIVATE)
    @Getter(AccessLevel.PRIVATE)
    public Plugin plugin;

    @Getter
    private String outgoingChannelName, incomingChannelName;

    /**
     * Initialize the plugin message instance.
     *
     * @param plugin The plugin instance.
     */
    public RyPluginMessages(Plugin plugin) {
        this(plugin, "BungeeCord", "BungeeCord");
    }

    /**
     * Initialize the plugin message instance.
     *
     * @param plugin              The plugin instance.
     * @param outgoingChannelName The name of the outgoing channel.
     * @param incomingChannelName The name of the incoming channel.
     */
    public RyPluginMessages(Plugin plugin, String outgoingChannelName, String incomingChannelName) {
        this.plugin = plugin;
        this.outgoingChannelName = outgoingChannelName;
        this.incomingChannelName = incomingChannelName;

        plugin.getServer().getMessenger().registerOutgoingPluginChannel(plugin, outgoingChannelName);
        plugin.getServer().getMessenger().registerIncomingPluginChannel(plugin, incomingChannelName, this);
    }

    /**
     * The actions that occurs when a plugin message is recieved.
     *
     * @param subchannel The sub channel of the message.
     * @param player     The player that has sent the message.
     * @param input      The message's message.
     */
    protected abstract void receivedActions(String subchannel, Player player, ByteArrayDataInput input);

    @Override
    public void onPluginMessageReceived(String channel, Player player, byte[] message) {
        if (!channel.equals(this.getIncomingChannelName())) return;

        ByteArrayDataInput input = ByteStreams.newDataInput(message);
        String subchannel = input.readUTF();

        this.receivedActions(subchannel, player, input);
    }

    /**
     * Send a plugin message.
     *
     * @param player The player who is sending the message.
     * @param output The message which is getting sent.
     */
    @Override
    public void send(Player player, ByteArrayDataOutput output) {
        player.sendPluginMessage(plugin, outgoingChannelName, output.toByteArray());
    }

    /**
     * Send a plugin message.
     *
     * @param player     The player that is sending the message.
     * @param subchannel The subchannel the message is being sent via.
     * @param message    The message that is being sent.
     */
    public void send(Player player, String subchannel, String message) {
        ByteArrayDataOutput output = ByteStreams.newDataOutput();

        output.writeUTF(subchannel);
        output.writeUTF(message);

        send(player, output);
    }

    /**
     * Send a player to a different server.
     *
     * @param target     Player who is being sent.
     * @param serverName Name of the server.
     */
    public void sendConnect(Player target, String serverName) {
        ByteArrayDataOutput output = ByteStreams.newDataOutput();

        output.writeUTF("Connect");
        output.writeUTF("pvp");

        send(target, output);
    }

    /**
     * Send a player to a different server.
     *
     * @param player     Player who is sending the request.
     * @param targetName Name of the player who is being sent.
     * @param serverName Name of the server.
     */
    public void sendConnect(Player player, String targetName, String serverName) {
        ByteArrayDataOutput output = ByteStreams.newDataOutput();

        output.writeUTF("ConnectOther");
        output.writeUTF(targetName);
        output.writeUTF(serverName);

        send(player, output);
    }

    /**
     * Request the IP address of a Player.
     *
     * @param target The player who you wish to find the IP address of.
     */
    public void sendIP(Player target) {
        ByteArrayDataOutput output = ByteStreams.newDataOutput();

        output.writeUTF("IP");

        send(target, output);
    }

    /**
     * Request the player counter for a server.
     *
     * @param player     Player sending the request.
     * @param serverName Server your wanting the player count from.
     */
    public void sendPlayerCount(Player player, String serverName) {
        ByteArrayDataOutput output = ByteStreams.newDataOutput();

        output.writeUTF("PlayerCount");
        output.writeUTF(serverName);

        send(player, output);
    }

    /**
     * Request the player list for a server.
     *
     * @param player     Player sending the request.
     * @param serverName Server your wanting the player list from.
     */
    public void sendPlayerList(Player player, String serverName) {
        ByteArrayDataOutput output = ByteStreams.newDataOutput();

        output.writeUTF("PlayerList");
        output.writeUTF(serverName);

        send(player, output);
    }

    /**
     * Request the server list.
     *
     * @param player Player sending the request.
     */
    public void sendGetServerList(Player player) {
        ByteArrayDataOutput output = ByteStreams.newDataOutput();

        output.writeUTF("GetServers");

        send(player, output);
    }

    /**
     * Request the name of the server.
     *
     * @param player Player sending the request.
     */
    public void sendGetServerName(Player player) {
        ByteArrayDataOutput output = ByteStreams.newDataOutput();

        output.writeUTF("GetServer");

        send(player, output);
    }

    /**
     * Send a message to a player.
     *
     * @param player        Player who is sending the request.
     * @param receiversName Name of the receiver.
     * @param message       The message you wish to be sent.
     */
    public void sendMessage(Player player, String receiversName, String message) {
        ByteArrayDataOutput output = ByteStreams.newDataOutput();

        output.writeUTF("Message");
        output.writeUTF(receiversName);
        output.writeUTF(RyMessageUtil.getUtil().translate(message));

        send(player, output);
    }

    /**
     * Send a message to a server.
     *
     * @param player           Player who is sending the request.
     * @param serverName       Name of the server.
     * @param pluginSubchannel Subchannel of the plugin.
     * @param messageOutput    The message that you want to be sent.
     */
    public void sendForward(Player player, String serverName, String pluginSubchannel, ByteArrayOutputStream messageOutput) {
        ByteArrayDataOutput output = ByteStreams.newDataOutput();

        output.writeUTF("Forward");
        output.writeUTF(serverName);
        output.writeUTF(pluginSubchannel);

        output.writeShort(messageOutput.toByteArray().length);
        output.write(messageOutput.toByteArray());

        send(player, output);
    }

    /**
     * Send a message to a player.
     *
     * @param player           Player who is sending the request.
     * @param targetName       Name of the target.
     * @param pluginSubchannel Subchannel of the plugin.
     * @param messageOutput    The message that you want to be sent.
     */
    public void sendForwardToPlayer(Player player, String targetName, String pluginSubchannel, ByteArrayOutputStream messageOutput) {
        ByteArrayDataOutput output = ByteStreams.newDataOutput();

        output.writeUTF("ForwardToPlayer");
        output.writeUTF(targetName);
        output.writeUTF(pluginSubchannel);

        output.writeShort(messageOutput.toByteArray().length);
        output.write(messageOutput.toByteArray());

        send(player, output);
    }

    /**
     * Request the UUID of a player.
     *
     * @param player Player you wish to get the UUID of / is sending the request.
     */
    public void sendUUID(Player player) {
        ByteArrayDataOutput output = ByteStreams.newDataOutput();

        output.writeUTF("UUID");

        send(player, output);
    }

    /**
     * Request the UUID of a player.
     *
     * @param player     Player who is sending the request.
     * @param targetName Name of the player that you wish to get the UUID of.
     */
    public void sendUUID(Player player, String targetName) {
        ByteArrayDataOutput output = ByteStreams.newDataOutput();

        output.writeUTF("UUIDOther");
        output.writeUTF(targetName);

        send(player, output);
    }

    /**
     * Get the ip and port of a server.
     *
     * @param player     Player who is sending the request.
     * @param serverName Name of the server.
     */
    public void sendServerIp(Player player, String serverName) {
        ByteArrayDataOutput output = ByteStreams.newDataOutput();

        output.writeUTF("ServerIP");
        output.writeUTF(serverName);

        send(player, output);
    }

    /**
     * Kick a player from the network.
     *
     * @param player Player who you wish to kick / is sending the request.
     * @param reason Reason for the kick.
     */
    public void sendKickPlayer(Player player, String reason) {
        sendKickPlayer(player, player.getName(), reason);
    }

    /**
     * Kick a player from the network.
     *
     * @param player     Player that is sending the request.
     * @param targetName Name of who you wish to kick.
     * @param reason     Reason for the kick.
     */
    public void sendKickPlayer(Player player, String targetName, String reason) {
        ByteArrayDataOutput output = ByteStreams.newDataOutput();

        output.writeUTF("KickPlayer");
        output.writeUTF(targetName);
        output.writeUTF(RyMessageUtil.getUtil().translate(reason));

        send(player, output);
    }

    /**
     * Kick all players that are connected to the network.
     *
     * @param player Player that is sending the request.
     * @param reason Reason for the kick.
     */
    public void sendKickAll(Player player, String reason) {
        ByteArrayDataOutput output = ByteStreams.newDataOutput();

        output.writeUTF("RyUtils-KickAll");
        output.writeUTF(RyMessageUtil.getUtil().translate(reason));

        send(player, output);
    }

    // Responses

    /**
     * Response to a get IP address request.
     *
     * @param subchannel The subchannel the message is being sent via.
     * @param input      The message's message.
     */
    public String getIpResponse(String subchannel, ByteArrayDataInput input) {
        if (!subchannel.equalsIgnoreCase("IP")) return null;

        return input.readUTF() + ":" + input.readInt();
    }

    /**
     * Response to a get player count request.
     *
     * @param subchannel The subchannel the message is being sent via.
     * @param input      The message's message.
     */
    public int getPlayerCountResponse(String subchannel, ByteArrayDataInput input) {
        if (!subchannel.equalsIgnoreCase("PlayerCount")) return 0;

        return input.readInt();
    }

    /**
     * Response to a get player list request.
     *
     * @param subchannel The subchannel the message is being sent via.
     * @param input      The message's message.
     */
    public String[] getPlayerListResponse(String subchannel, ByteArrayDataInput input) {
        if (!subchannel.equalsIgnoreCase("PlayerList")) return null;

        String serverName = input.readUTF();
        String[] playerList = input.readUTF().split(", ");

        return playerList;
    }

    /**
     * Response to a get server list request.
     *
     * @param subchannel The subchannel the message is being sent via.
     * @param input      The message's message.
     */
    public String[] getServerListResponse(String subchannel, ByteArrayDataInput input) {
        if (!subchannel.equalsIgnoreCase("GetServers")) return null;

        return input.readUTF().split(", ");
    }

    /**
     * Response to a get server name request.
     *
     * @param subchannel The subchannel the message is being sent via.
     * @param input      The message's message.
     */
    public String getServerNameResponse(String subchannel, ByteArrayDataInput input) {
        if (!subchannel.equalsIgnoreCase("GetServer")) return null;

        return input.readUTF();
    }

    /**
     * Response to a forward request.
     *
     * @param subchannel       The subchannel the message is being sent via.
     * @param pluginSubchannel The subchannel the plugin is using.
     * @param input            The message's message.
     */
    public DataInputStream getForwardResponse(String subchannel, String pluginSubchannel, ByteArrayDataInput input) {
        if (!subchannel.equalsIgnoreCase("Forward")) return null;

        String pluginSub = input.readUTF();
        if (!pluginSub.equalsIgnoreCase(pluginSubchannel)) return null;

        short length = input.readShort();
        byte[] messageBytes = new byte[length];
        input.readFully(messageBytes);

        DataInputStream messageInput = new DataInputStream(new ByteArrayInputStream(messageBytes));

        return messageInput;
    }

    /**
     * Response to a forward to player request.
     *
     * @param subchannel       The subchannel the message is being sent via.
     * @param pluginSubchannel The subchannel the plugin is using.
     * @param input            The message's message.
     */
    public DataInputStream getForwardToPlayerResponse(String subchannel, String pluginSubchannel, ByteArrayDataInput input) {
        if (!subchannel.equalsIgnoreCase("Forward")) return null;

        String pluginSub = input.readUTF();
        if (!pluginSub.equalsIgnoreCase(pluginSubchannel)) return null;

        short length = input.readShort();
        byte[] messageBytes = new byte[length];
        input.readFully(messageBytes);

        DataInputStream messageInput = new DataInputStream(new ByteArrayInputStream(messageBytes));

        return messageInput;
    }

    /**
     * Response to a get UUID request.
     *
     * @param subchannel The subchannel the message is being sent via.
     * @param input      The message's message.
     */
    public UUID getUUIDResponse(String subchannel, ByteArrayDataInput input) {
        if (!subchannel.equalsIgnoreCase("UUID")) return null;

        return UUID.fromString(input.readUTF());
    }

    /**
     * Response to a get UUID other request.
     *
     * @param subchannel The subchannel the message is being sent via.
     * @param input      The message's message.
     */
    public UUID getUUIDOtherResponse(String subchannel, ByteArrayDataInput input) {
        if (!subchannel.equalsIgnoreCase("UUIDOther")) return null;

        String playerName = input.readUTF();
        UUID uuid = UUID.fromString(input.readUTF());

        return uuid;
    }

    /**
     * Response to a get server ip request.
     *
     * @param subchannel The subchannel the message is being sent via.
     * @param input      The message's message.
     */
    public String getServerIpResponse(String subchannel, ByteArrayDataInput input) {
        if (!subchannel.equalsIgnoreCase("ServerIP")) return null;

        String serverName = input.readUTF();
        String ip = input.readUTF();
        short port = input.readShort();

        return ip + ":" + port;
    }
}
