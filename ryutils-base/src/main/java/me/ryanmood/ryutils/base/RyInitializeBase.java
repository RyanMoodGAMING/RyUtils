package me.ryanmood.ryutils.base;

import me.ryanmood.ryutils.base.command.RyCommandManagerBase;

public interface RyInitializeBase {

    /**
     * Get the message util instance for the platform.
     *
     * @return {@link RyMessagesBase}
     */
    RyMessagesBase getMessageUtil();

    /**
     * Get the tasks util instance for the platform.
     *
     * @return {@link RyTaskBase}
     */
    RyTaskBase getTaskUtil();

    /**
     * Get the sound util instance for the platform.
     *
     * @return {@link RySoundBase}
     */
    RySoundBase getSoundUtil();

    /**
     * Get the command manager instance for the platform.
     *
     * @return {@link RyCommandManagerBase}
     */
    RyCommandManagerBase getCommandManager();

}
