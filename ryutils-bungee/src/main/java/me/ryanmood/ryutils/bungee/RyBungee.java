package me.ryanmood.ryutils.bungee;

import lombok.Getter;
import me.ryanmood.ryutils.base.RyInitializeBase;
import me.ryanmood.ryutils.base.RySoundBase;
import me.ryanmood.ryutils.base.command.RyCommandManagerBase;
import me.ryanmood.ryutils.base.exceptions.UnsupportedPlatformException;
import net.md_5.bungee.api.plugin.Plugin;

@Getter
public class RyBungee implements RyInitializeBase {

    private final RyMessageUtil messageUtil;
    private final RyTaskUtil taskUtil;

    public RyBungee(Plugin plugin) {
        this.messageUtil = new RyMessageUtil(plugin);
        this.taskUtil = new RyTaskUtil(plugin);
    }

    public RyBungee(Plugin plugin, String prefix) {
        this.messageUtil = new RyMessageUtil(plugin, prefix);
        this.taskUtil = new RyTaskUtil(plugin);
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
