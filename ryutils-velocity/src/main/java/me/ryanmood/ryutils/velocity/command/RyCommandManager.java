package me.ryanmood.ryutils.velocity.command;

import com.velocitypowered.api.proxy.ProxyServer;
import lombok.AccessLevel;
import lombok.Getter;
import me.ryanmood.ryutils.base.command.RyCommandManagerBase;

import java.util.*;

@Getter
public class RyCommandManager implements RyCommandManagerBase<RyCommand> {

    @Getter(AccessLevel.PRIVATE)
    private ProxyServer server;

    private Set<RyCommand> commands = new HashSet<>();

    public RyCommandManager(ProxyServer server) {
        this.server = server;
    }

    /**
     * Register all the commands that are in the Array List.
     */
    @Override
    public void registerAll() {
        this.getCommands().forEach(command -> {
            this.getServer().getCommandManager().register(command.getMeta(), command);
        });
    }

    /**
     * Register a command or multiple commands.
     *
     * @param command The command(s) you wish to register.
     */
    public void register(RyCommand... command){
        commands.addAll(Arrays.asList(command));

        for (RyCommand ryCommand : commands) {
            this.getServer().getCommandManager().register(ryCommand.getMeta(), ryCommand);
        }
    }

    /**
     * Unregister a command.
     *
     * @param command The command you wish to unregister.
     */
    public void unregister(RyCommand command) {
        this.getServer().getCommandManager().unregister(command.getMeta());
    }

    /**
     * Unregister all commands.
     */
    public void unregisterAll() {
        for (RyCommand command : commands) {
            this.getServer().getCommandManager().unregister(command.getMeta());
        }
    }

    /**
     * Find a command that has been registered.
     *
     * @param command The command name.
     * @return The command class.
     */
    @Override
    public Optional<RyCommand> byCommand(String command) {
        return commands.stream().filter(all -> {
            if (all.getMeta().getAliases().contains(command)) {
                return true;
            } else {
                for (String alias : all.getMeta().getAliases()) {
                    if (alias.equalsIgnoreCase(command)) {
                        return true;
                    }
                }
                return false;
            }
        }).findFirst();
    }
}
