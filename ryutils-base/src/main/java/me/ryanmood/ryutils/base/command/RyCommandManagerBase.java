package me.ryanmood.ryutils.base.command;

import java.util.Optional;

public interface RyCommandManagerBase<CMD> {

    /**
     * Register all the commands that are in the Array List.
     */
    void registerAll();

    /**
     * Register a command or multiple commands.
     *
     * @param command The command(s) you wish to register.
     */
    void register(CMD... command);

    /**
     * Unregister a command.
     *
     * @param command The command you wish to unregister.
     */
    void unregister(CMD command);

    /**
     * Unregister all commands.
     */
    void unregisterAll();

    /**
     * Find a command that has been registered.
     *
     * @param command The command name.
     * @return        The command class.
     */
    Optional<CMD> byCommand(String command);

}
