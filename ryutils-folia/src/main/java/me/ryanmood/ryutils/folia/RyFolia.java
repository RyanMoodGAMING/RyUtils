package me.ryanmood.ryutils.folia;

import me.ryanmood.ryutils.base.RyInitializeBase;
import me.ryanmood.ryutils.folia.commands.RyCommandManager;
import org.bukkit.plugin.Plugin;

public class RyFolia implements RyInitializeBase {

    private RyMessageUtil messageUtil;
    private RyTaskUtil taskUtil;
    private RySoundUtil soundUtil;
    private RyCommandManager commandManager;

    public RyFolia(Plugin plugin) {
        this.messageUtil = new RyMessageUtil(plugin);
        this.taskUtil = new RyTaskUtil(plugin);
        this.soundUtil = new RySoundUtil();
        this.commandManager = new RyCommandManager();
    }

    public RyFolia(Plugin plugin, String prefix) {
        this.messageUtil = new RyMessageUtil(plugin, prefix);
        this.taskUtil = new RyTaskUtil(plugin);
        this.soundUtil = new RySoundUtil();
        this.commandManager = new RyCommandManager();
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
     * @return {@link RySoundUtil}
     */
    @Override
    public RySoundUtil getSoundUtil() {
        return this.soundUtil;
    }

    /**
     * Get the command manager instance for the platform.
     *
     * @return {@link RyCommandManager}
     */
    @Override
    public RyCommandManager getCommandManager() {
        return this.commandManager;
    }
}
