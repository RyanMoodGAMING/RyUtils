package me.ryanmood.ryutils.spigot;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.plugin.java.JavaPlugin;

/*
 * This software and its content is copyright of RyanMoodGAMING - © RyanMoodGAMING 2023. All rights reserved.
 * Any redistribution or reproduction of part or all of the contents in any form is prohibited other than the following:
 * you may print or download to a local hard disk extracts for your personal and non-commercial use only
 * you may copy the content to individual third parties for their personal use, but only if you acknowledge the website as the source of the material
 * You may not, except with our express written permission, distribute or commercially exploit the content. Nor may you transmit it or store it in any other website or other form of electronic retrieval system.
 */

public class RySetup {

    /**
     * The plugin's instance.
     */
    @Getter
    @Setter
    private static JavaPlugin pluginInstance = null;

    /**
     * Should the plugin send debug messages?
     */
    @Getter
    @Setter
    private static boolean debug = false;

}
