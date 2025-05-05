package me.ryanmood.ryutils.velocity;

import com.velocitypowered.api.plugin.PluginContainer;
import com.velocitypowered.api.proxy.ProxyServer;
import lombok.Getter;
import me.ryanmood.ryutils.base.RyInitializeBase;
import me.ryanmood.ryutils.base.RySoundBase;
import me.ryanmood.ryutils.base.command.RyCommandManagerBase;
import me.ryanmood.ryutils.base.exceptions.UnsupportedPlatformException;

import java.util.logging.Logger;

@Getter
public class RyVelocity implements RyInitializeBase {

    private final RyMessageUtil messageUtil;
    private final RyTaskUtil taskUtil;

    public RyVelocity(Object plugin, ProxyServer server, PluginContainer pluginContainer) {
        this.messageUtil = new RyMessageUtil(pluginContainer);
        this.taskUtil = new RyTaskUtil(plugin, server);
    }

    public RyVelocity(Object plugin, ProxyServer server, Logger logger, PluginContainer pluginContainer, String prefix) {
        this.messageUtil = new RyMessageUtil(pluginContainer, server, prefix);
        this.taskUtil = new RyTaskUtil(plugin, server);
    }

    /**
     * Get the message util instance for the platform.
     *
     * @return {@link RyMessageUtil}
     */
    @Override
    public RyMessageUtil getMessageUtil() {
        return this.messageUtil;
    }

    /**
     * Get the tasks util instance for the platform.
     *
     * @return {@link RyTaskUtil}
     */
    @Override
    public RyTaskUtil getTaskUtil() {
        return this.taskUtil;
    }

    /**
     * Get the sound util instance for the platform.
     *
     * @return {@link RySoundBase}
     */
    @Override
    public RySoundBase getSoundUtil() {
        throw new UnsupportedPlatformException("RyBungee#getSoundUtil()", "Bungeecord");
    }

    /**
     * Get the command manager instance for the platform.
     *
     * @return {@link RyCommandManagerBase}
     */
    @Override
    public RyCommandManagerBase getCommandManager() {
        throw new UnsupportedPlatformException("RyBungee#getCommandManager()", "Bungeecord");
    }
}
