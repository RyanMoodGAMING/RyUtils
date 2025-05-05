package me.ryanmood.ryutils.velocity.command;

import com.velocitypowered.api.command.CommandMeta;
import com.velocitypowered.api.command.SimpleCommand;
import com.velocitypowered.api.proxy.ProxyServer;
import lombok.AccessLevel;
import lombok.Getter;

@Getter
public abstract class RyCommand implements SimpleCommand {

    @Getter(AccessLevel.PRIVATE)
    private final Object plugin;
    @Getter(AccessLevel.PRIVATE)
    private final ProxyServer server;

    private String permission;
    private final CommandMeta meta;

    public RyCommand(Object plugin, ProxyServer server, String command) {
        this.plugin = plugin;
        this.server = server;
        this.meta = this.getServer().getCommandManager().metaBuilder(command)
                .plugin(plugin)
                .build();
    }

    public RyCommand(Object plugin, ProxyServer server, String command, String... aliases) {
        this.plugin = plugin;
        this.server = server;
        this.meta = this.getServer().getCommandManager().metaBuilder(command)
                .aliases(aliases)
                .plugin(plugin)
                .build();
    }

    public RyCommand(Object plugin, ProxyServer server, String command, String permission) {
        this.plugin = plugin;
        this.server = server;
        this.permission = permission;
        this.meta = this.getServer().getCommandManager().metaBuilder(command)
                .plugin(plugin)
                .build();
    }

    public RyCommand(Object plugin, ProxyServer server, String command, String permission, String... aliases) {
        this.plugin = plugin;
        this.server = server;
        this.permission = permission;
        this.meta = this.getServer().getCommandManager().metaBuilder(command)
                .aliases(aliases)
                .plugin(plugin)
                .build();
    }

    @Override
    public boolean hasPermission(Invocation invocation) {
        if (this.permission != null && !this.permission.isEmpty()) {
            return invocation.source().hasPermission(this.permission);
        } else {
            return SimpleCommand.super.hasPermission(invocation);
        }
    }

}
