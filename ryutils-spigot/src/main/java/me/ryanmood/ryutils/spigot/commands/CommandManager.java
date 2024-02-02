package me.ryanmood.ryutils.spigot.commands;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

/*
 * This software and its content is copyright of RyanMoodGAMING - © RyanMoodGAMING 2023. All rights reserved.
 * Any redistribution or reproduction of part or all of the contents in any form is prohibited other than the following:
 * you may print or download to a local hard disk extracts for your personal and non-commercial use only
 * you may copy the content to individual third parties for their personal use, but only if you acknowledge the website as the source of the material
 * You may not, except with our express written permission, distribute or commercially exploit the content. Nor may you transmit it or store it in any other website or other form of electronic retrieval system.
 */

@SuppressWarnings("unused")
public class CommandManager {

    private static Set<Command> commands = new HashSet<>();

    /**
     * Register all the commands that are in the Array List.
     */
    public static void registerAll(){
        commands.addAll(Arrays.asList(

        ));
    }

    /**
     * Register a command or multiple commands.
     *
     * @param command The command(s) you wish to register.
     */
    public static void register(Command... command){
        commands.addAll(Arrays.asList(command));
    }

    /**
     * Find a command that has been registered.
     *
     * @param command The command name.
     * @return        The command class.
     */
    public static Optional<Command> byCommand(String command){
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

}