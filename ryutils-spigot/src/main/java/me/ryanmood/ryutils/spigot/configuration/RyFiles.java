package me.ryanmood.ryutils.spigot.configuration;

import me.ryanmood.ryutils.spigot.RyMessageUtils;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.util.HashMap;

/*
 * This software and its content is copyright of RyanMoodGAMING - © RyanMoodGAMING 2023. All rights reserved.
 * Any redistribution or reproduction of part or all of the contents in any form is prohibited other than the following:
 * you may print or download to a local hard disk extracts for your personal and non-commercial use only
 * you may copy the content to individual third parties for their personal use, but only if you acknowledge the website as the source of the material
 * You may not, except with our express written permission, distribute or commercially exploit the content. Nor may you transmit it or store it in any other website or other form of electronic retrieval system.
 */

public class RyFiles {

    /**
     * String - Config name eg config.yml
     */
    private static HashMap<String, File> files;
    /**
     * String - Config name eg config.yml
     */
    private static HashMap<String, FileConfiguration> fileConfigs;
    /**
     * String - Config name eg config.yml
     */
    private static HashMap<String, ConfigCache> fileCache;

    /**
     * The files that need to be generated.
     *
     * @param files       The files.
     * @param fileConfigs The configs of the files.
     * @param fileCache   The file's cache.
     */
    public RyFiles(HashMap<String, File> files, HashMap<String, FileConfiguration> fileConfigs, HashMap<String, ConfigCache> fileCache) {
        RyFiles.files = files;
        RyFiles.fileConfigs = fileConfigs;
        RyFiles.fileCache = fileCache;
    }

    /**
     * Load the config files.
     *
     * @param instance The instance of the plugin.
     */
    public static void loadFiles(Plugin instance) {
        if (!instance.getDataFolder().exists()) {
            instance.getDataFolder().mkdir();
        }

        for (String fileName : files.keySet()) {
            File file = files.get(fileName);
            FileConfiguration fileConfig = fileConfigs.get(fileName);
            ConfigCache configCache = fileCache.get(fileName);

            file = new File(instance.getDataFolder(), fileName);
            if (!file.exists()) {
                instance.saveResource(fileName, false);
            }
            fileConfig = YamlConfiguration.loadConfiguration(file);
            configCache.load();

            files.replace(fileName, file);
            fileConfigs.replace(fileName, fileConfig);

        }
    }

    /**
     * Save the config files.
     */
    public static void saveFiles() {
        for (String fileName : files.keySet()) {
            FileConfiguration fileConfig = fileConfigs.get(fileName);
            File file = files.get(fileName);

            try {
                fileConfig.save(file);
            } catch (Exception e) {
                RyMessageUtils.sendPluginError("An error occured while saving " + fileName);
            }
        }
    }

    /**
     * Reload the config files.
     *
     * @param instance The instance of the plugin.
     */
    public static void reloadFiles(Plugin instance) {
        for (String fileName : files.keySet()) {
            FileConfiguration fileConfig = fileConfigs.get(fileName);
            File file = files.get(fileName);
            ConfigCache configCache = fileCache.get(fileName);

            file = new File(instance.getDataFolder(), fileName);
            try {
                fileConfig = YamlConfiguration.loadConfiguration(file);
            } catch (Exception e) {
                RyMessageUtils.sendPluginError("Error occured while reloading " + fileName);
            }
            configCache.load();
        }
    }
}
