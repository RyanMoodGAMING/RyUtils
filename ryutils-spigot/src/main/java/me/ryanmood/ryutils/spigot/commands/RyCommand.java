package me.ryanmood.ryutils.spigot.commands;

import me.ryanmood.ryutils.spigot.RyMessageUtil;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandMap;
import org.bukkit.command.CommandSender;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public abstract class RyCommand extends org.bukkit.command.Command implements Comparable<RyCommand>, RyExecutable {

    private final String name;
    private final Set<String> aliases;
    private String usage;
    private int argsLength;


    /**
     * The command your making's information.
     *
     * @param name    The name of the command (/name).
     * @param aliases Other commands that can be used to trigger this.
     */
    public RyCommand(String name, String... aliases) {
        this(0, "", name, aliases);
    }

    /**
     * The command your making's information.
     *
     * @param argsLength The amount of args that is required.
     * @param usage      The usage of the command.
     * @param name       The name of the command (/name).
     * @param aliases    Other commands that can be used to trigger this.
     */
    public RyCommand(int argsLength, String usage, String name, String... aliases) {
        super(name, "", usage, Arrays.asList(aliases));

        this.name = name;
        this.argsLength = argsLength;
        this.usage = RyMessageUtil.getUtil().translate(usage);

        this.aliases = new HashSet<>();
        this.aliases.add(name);
        Collections.addAll(this.aliases, aliases);

        registerBukkitCommand(aliases);
    }

    public abstract boolean execute(CommandSender sender, String commandLabel, String[] args);

    private void registerBukkitCommand(String[] aliases) {
        try {
            final Field bukkitCommandMap = Bukkit.getServer().getClass().getDeclaredField("commandMap");
            bukkitCommandMap.setAccessible(true);
            CommandMap commandMap = (CommandMap) bukkitCommandMap.get(Bukkit.getServer());
            commandMap.register("command", this);
            for (String alias : aliases) {
                commandMap.register(alias, "command", this);
            }
        } catch (NoSuchFieldException | IllegalArgumentException | IllegalAccessException exception) {
            RyMessageUtil.getUtil().sendConsole(true, "&c&lERROR - &rcould not register a command properly (name: " + this.name + "), stacktrace: ");
        }
    }

    /**
     * Get the name of the command.
     *
     * @return Name of the command.
     */
    @Override
    public String getName() {
        return this.name;
    }

    /**
     * Get the usage of the command.
     *
     * @return Usage of the command.
     */
    @Override
    public String getUsage() {
        return this.usage;
    }

    /**
     * Get the args length of the command.
     *
     * @return Args length of the command.
     */
    @Override
    public int getArgsLength() {
        return this.argsLength;
    }

    /**
     * Get the aliases of the command.
     *
     * @return Aliases of the command.
     */
    @Override
    public Set<String> getNameList() {
        return this.aliases;
    }

    /**
     * Get the usage of the command.
     *
     * @return Usage of the command.
     */
    @Override
    public boolean hasUsage() {
        return !this.usage.isEmpty();
    }

    /**
     * Set the amount of args that are required.
     *
     * @param argsLength The new amount of arguments required for the command.
     * @return           The command class.
     */
    @Override
    public RyCommand setArgsLength(int argsLength) {
        this.argsLength = argsLength;
        return this;
    }

    /**
     * Change the command's usage (syntax).
     *
     * @param usage The new usage.
     * @return      The command class.
     */
    @Override
    public RyCommand setUsage(String usage) {
        this.usage = RyMessageUtil.getUtil().translate(usage);
        return this;
    }

    /**
     * Set the description of the command.
     *
     * @param description The new description.
     * @return            The command class.
     */
    @Override
    public RyCommand setDescription(String description) {
        this.description = description;
        return this;
    }

}
