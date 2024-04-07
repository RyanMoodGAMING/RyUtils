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
    private final Plugin instance;
    @Getter(AccessLevel.PRIVATE)
    private final ProxyServer server;

    @Getter
    private final CommandMeta meta;

    public RyCommand(Plugin instance, ProxyServer server, String command) {
        this.instance = instance;
        this.server = server;
        this.meta = this.getServer().getCommandManager().metaBuilder(command)
                .plugin(instance).build();
    }

    public RyCommand(Plugin instance, ProxyServer server, String command, String... aliases) {
        this.instance = instance;
        this.server = server;
        this.meta = this.getServer().getCommandManager().metaBuilder(command)
                .aliases(aliases).plugin(instance).build();
    }

    @Override
    public void execute(Invocation invocation) {

    }

    @Override
    public List<String> suggest(Invocation invocation) {
        return SimpleCommand.super.suggest(invocation);
    }

    @Override
    public CompletableFuture<List<String>> suggestAsync(Invocation invocation) {
        return SimpleCommand.super.suggestAsync(invocation);
    }

    @Override
    public boolean hasPermission(Invocation invocation) {
        return SimpleCommand.super.hasPermission(invocation);
    }
}
