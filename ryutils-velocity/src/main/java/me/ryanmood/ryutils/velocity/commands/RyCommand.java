package me.ryanmood.ryutils.velocity.commands;

import com.velocitypowered.api.command.CommandMeta;
import com.velocitypowered.api.command.SimpleCommand;
import com.velocitypowered.api.plugin.Plugin;
import com.velocitypowered.api.proxy.ProxyServer;
import lombok.AccessLevel;
import lombok.Getter;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public abstract class RyCommand implements SimpleCommand {

    @Getter(AccessLevel.PRIVATE)
    private final Object instance;
    @Getter(AccessLevel.PRIVATE)
    private final ProxyServer server;

    @Getter
    private String permission;

    @Getter
    private final CommandMeta meta;

    public RyCommand(Object instance, ProxyServer server, String command) {
        this.instance = instance;
        this.server = server;
        this.meta = this.getServer().getCommandManager().metaBuilder(command)
                .plugin(instance).build();
    }

    public RyCommand(Object instance, ProxyServer server, String command, String permission) {
        this.instance = instance;
        this.server = server;
        this.permission = permission;
        this.meta = this.getServer().getCommandManager().metaBuilder(command)
                .plugin(instance).build();
    }

    public RyCommand(Object instance, ProxyServer server, String command, String... aliases) {
        this.instance = instance;
        this.server = server;
        this.meta = this.getServer().getCommandManager().metaBuilder(command)
                .aliases(aliases).plugin(instance).build();
    }

    public RyCommand(Object instance, ProxyServer server, String command, String permission, String... aliases) {
        this.instance = instance;
        this.server = server;
        this.permission = permission;
        this.meta = this.getServer().getCommandManager().metaBuilder(command)
                .aliases(aliases).plugin(instance).build();
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
