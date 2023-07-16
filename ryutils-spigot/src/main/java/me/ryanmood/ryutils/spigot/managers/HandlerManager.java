package me.ryanmood.ryutils.spigot.managers;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import me.ryanmood.ryutils.spigot.RySetup;
import org.bukkit.plugin.Plugin;

/*
 * This software and its content is copyright of RyanMoodGAMING - © RyanMoodGAMING 2023. All rights reserved.
 * Any redistribution or reproduction of part or all of the contents in any form is prohibited other than the following:
 * you may print or download to a local hard disk extracts for your personal and non-commercial use only
 * you may copy the content to individual third parties for their personal use, but only if you acknowledge the website as the source of the material
 * You may not, except with our express written permission, distribute or commercially exploit the content. Nor may you transmit it or store it in any other website or other form of electronic retrieval system.
 */

@AllArgsConstructor
public class HandlerManager {

    @Getter(AccessLevel.PROTECTED)
    protected Plugin instance = RySetup.getPluginInstance();

}
