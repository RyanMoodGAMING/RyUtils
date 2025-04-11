package me.ryanmood.ryutils.folia.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import java.util.Set;

@SuppressWarnings("unused")
public interface RyExecutable {

    /**
     * @return The name of the command.
     */
    String getName();

    /**
     * @return The usage (syntax) of the command.
     */
    String getUsage();

    /**
     * @return The required arguments length.
     */
    int getArgsLength();

    /**
     * @return A set with the alliases of the command (incl. Executable#getName).
     */
    Set<String> getNameList();

    /**
     * @return Boolean; is the usage set.
     */
    boolean hasUsage();

    /**
     * Re-sets the argsLength variable.
     * @param argsLength The new amount of arguments required for the command.
     * @return The command class.
     */
    Command setArgsLength(int argsLength);

    /**
     * Change the command's usage (syntax).
     *
     * @param usage The new usage.
     * @return      The command class.
     */
    Command setUsage(String usage);

    /**
     * Change the command's description.
     *
     * @param description The new description.
     * @return            The command class.
     */
    Command setDescription(String description);

    /**
     * What code should happen when the command is triggered.
     *
     * @param sender       The person who triggered the command.
     * @param commandLabel String used to trigger the code.
     * @param args         Arguments that have been provided.
     * @return             If the command was successful or not.
     */
    boolean execute(CommandSender sender, String commandLabel, String[] args);
}
