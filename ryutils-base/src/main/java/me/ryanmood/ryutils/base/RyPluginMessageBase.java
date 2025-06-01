package me.ryanmood.ryutils.base;

import com.google.common.io.ByteArrayDataOutput;

public interface RyPluginMessageBase<Player> {

    /**
     * Send a plugin message.
     *
     * @param player The player who is sending the message.
     * @param output The message which is getting sent.
     */
    void send(Player player, ByteArrayDataOutput output);

}
