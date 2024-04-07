package me.ryanmood.ryutils.velocity.commands;

import com.velocitypowered.api.command.CommandMeta;
import com.velocitypowered.api.proxy.ProxyServer;
import lombok.AccessLevel;
import lombok.Getter;
import me.ryanmood.ryutils.velocity.RySetup;

import java.util.*;

public class RyCommandManager {

    @Getter
    private Set<RyCommand> commands = new HashSet<>();

    @Getter(AccessLevel.PRIVATE)
    private ProxyServer server = RySetup.getProxyServer();

    public RyCommandManager() {
        this.server = RySetup.getProxyServer();
    }

    public RyCommandManager(ProxyServer server) {
        this.server = server;
    }

    /**
     * Register all the commands that are in the Array List.
     */
    public void registerAll(){
        commands.addAll(Arrays.asList(

        ));
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

    public void unregister(RyCommand ryCommand) {
        this.getServer().getCommandManager().unregister(ryCommand.getMeta());
    }

    public void unregisterAll() {
        for (RyCommand ryCommand : commands) {
            this.getServer().getCommandManager().unregister(ryCommand.getMeta());
        }
    }

    /**
     * Find a command that has been registered.
     *
     * @param command The command name.
     * @return        The command class.
     */
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
