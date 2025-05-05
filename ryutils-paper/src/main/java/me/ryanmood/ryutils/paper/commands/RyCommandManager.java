package me.ryanmood.ryutils.paper.commands;

import lombok.Getter;
import me.ryanmood.ryutils.base.command.RyCommandManagerBase;
import me.ryanmood.ryutils.paper.RyMessageUtil;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandMap;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Getter
public class RyCommandManager implements RyCommandManagerBase<RyCommand> {

    private Set<RyCommand> commands = new HashSet<>();

    /**
     * Register all the commands that are in the Array List.
     */
    @Override
    public void registerAll() {
        this.commands.addAll(Arrays.asList(

        ));
    }

    /**
     * Register a command or multiple commands.
     *
     * @param command The command(s) you wish to register.
     */
    @Override
    public void register(RyCommand... command) {
        this.commands.addAll(Arrays.asList(command));
    }

    /**
     * Unregister a command.
     *
     * @param command The command you wish to unregister.
     */
    @Override
    public void unregister(RyCommand command) {
        if (this.getCommandMap() == null) return;

        command.unregister(this.getCommandMap());
        this.commands.remove(command);
    }

    /**
     * Unregister all commands.
     */
    @Override
    public void unregisterAll() {
        if (this.getCommandMap() == null) return;
        this.getCommands().forEach(command -> command.unregister(this.getCommandMap()));
    }

    /**
     * Find a command that has been registered.
     *
     * @param command The command name.
     * @return        The command class.
     */
    @Override
    public Optional<RyCommand> byCommand(String command){
        return commands.stream().filter(all -> {
            if(all.getName().equalsIgnoreCase(command)){
                return true;
            }else{
                for(String alias : all.getNameList()){
                    if(alias.equalsIgnoreCase(command)){
                        return true;
                    }
                }
                return false;
            }
        }).findFirst();
    }

    private CommandMap getCommandMap() {
        try {
            final Field bukkitCommandMap = Bukkit.getServer().getClass().getDeclaredField("commandMap");
            bukkitCommandMap.setAccessible(true);
            CommandMap commandMap = (CommandMap) bukkitCommandMap.get(Bukkit.getServer());
            return commandMap;
        } catch (NoSuchFieldException | IllegalArgumentException | IllegalAccessException exception) {
            RyMessageUtil.getUtil().sendConsole(true, "&c&lERROR - &rcould not find command map - stacktrace: ");
        }
        return null;
    }
}
