package me.ryanmood.ryutils.spigot.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import java.util.Set;

/*
 * This software and its content is copyright of RyanMoodGAMING - © RyanMoodGAMING 2023. All rights reserved.
 * Any redistribution or reproduction of part or all of the contents in any form is prohibited other than the following:
 * you may print or download to a local hard disk extracts for your personal and non-commercial use only
 * you may copy the content to individual third parties for their personal use, but only if you acknowledge the website as the source of the material
 * You may not, except with our express written permission, distribute or commercially exploit the content. Nor may you transmit it or store it in any other website or other form of electronic retrieval system.
 */

@SuppressWarnings("unused")
public interface Executable {

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
