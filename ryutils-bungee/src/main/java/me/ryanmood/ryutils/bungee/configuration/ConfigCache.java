package me.ryanmood.ryutils.bungee.configuration;

import net.md_5.bungee.config.Configuration;

import java.util.List;

/*
 * This software and its content is copyright of RyanMoodGAMING - © RyanMoodGAMING 2023. All rights reserved.
 * Any redistribution or reproduction of part or all of the contents in any form is prohibited other than the following:
 * you may print or download to a local hard disk extracts for your personal and non-commercial use only
 * you may copy the content to individual third parties for their personal use, but only if you acknowledge the website as the source of the material
 * You may not, except with our express written permission, distribute or commercially exploit the content. Nor may you transmit it or store it in any other website or other form of electronic retrieval system.
 */

public abstract class ConfigCache {

    private static Configuration configuration;

    /**
     *
     * @param configuration The config that is getting cached.
     */
    public ConfigCache(Configuration configuration) {
        ConfigCache.configuration = configuration;
    }

    /**
     * Load the contents of the cache.
     */
    public abstract void load();

    /**
     * Get a string from the configuration.
     *
     * @param path Location of the string.
     * @return     The requested string.
     */
    public static String getString(String path) {
        return configuration.getString(path);
    }

    /**
     * Get a string list from the configuration.
     *
     * @param path Location of the string list.
     * @return     The requested string list.
     */
    public static List<String> getStringList(String path) {
        return configuration.getStringList(path);
    }

    /**
     * Get a boolean from the configuration.
     *
     * @param path Location of the boolean.
     * @return     The requested boolean.
     */
    public static boolean getBoolean(String path) {
        return configuration.getBoolean(path);
    }

    /**
     * Get a int from the configuration.
     *
     * @param path Location of the int.
     * @return     The requested int.
     */
    public static int getInt(String path) {
        return configuration.getInt(path);
    }

    /**
     * Get a double from the configuration.
     *
     * @param path Location of the double.
     * @return     The requested double.
     */
    public static double getDouble(String path) {
        return configuration.getDouble(path);
    }

    /**
     * Get a configuration from the configuration.
     *
     * @param path Location of the configuration.
     * @return     The requested configuration.
     */
    public static Configuration getConfigSection(String path) {
        return configuration.getSection(path);
    }

}
