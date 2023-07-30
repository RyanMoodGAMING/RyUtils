package me.ryanmood.ryutils.spigot.configuration;

import lombok.Getter;
import me.ryanmood.ryutils.spigot.RyMessageUtils;
import me.ryanmood.ryutils.spigot.RySetup;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.List;

/*
 * This software and its content is copyright of RyanMoodGAMING - © RyanMoodGAMING 2023. All rights reserved.
 * Any redistribution or reproduction of part or all of the contents in any form is prohibited other than the following:
 * you may print or download to a local hard disk extracts for your personal and non-commercial use only
 * you may copy the content to individual third parties for their personal use, but only if you acknowledge the website as the source of the material
 * You may not, except with our express written permission, distribute or commercially exploit the content. Nor may you transmit it or store it in any other website or other form of electronic retrieval system.
 */

public abstract class RyFile {

    /**
     * The plugin's instance.
     */
    private JavaPlugin instance;

    /**
     * The file's full name.
     */
    @Getter
    private String fullName;
    /**
     * The file of the config.
     */
    @Getter
    private File file;
    /**
     * The configuration of the file.
     */
    @Getter
    private FileConfiguration config;

    /**
     * The configuration file.
     *
     * @param name The name of the config.
     */
    protected RyFile(String name) {
        this.fullName = name.endsWith(".yml") ? name : name + "yml";
        this.instance = RySetup.getPluginInstance();
        loadFile();
    }

    /**
     * The cache of the config.
     */
    public abstract void loadConfig();

    /**
     * Create and load the files.
     */
    public void loadFile()  {
        if (!this.instance.getDataFolder().exists()) {
            this.instance.getDataFolder().mkdir();
        }

        File file = new File(this.instance.getDataFolder(), this.fullName);
        if (!file.exists()) {
            this.instance.saveResource(this.fullName, false);
        }

        this.file = file;
        this.config = YamlConfiguration.loadConfiguration(file);
        this.loadConfig();
    }

    /**
     * Save the config file.
     */
    public void saveFile() {
        try {
            this.config.save(this.file);
        } catch (Exception exception) {
            RyMessageUtils.sendPluginError("An error occurred while saving " + this.fullName, exception, RySetup.isDebug());
        }
    }

    /**
     * Reload the config file.
     */
    public void reloadFile() {
        this.file = new File(this.instance.getDataFolder(), this.fullName);
        try {
            this.config = YamlConfiguration.loadConfiguration(this.file);
        } catch (Exception exception) {
            RyMessageUtils.sendPluginError("An error occurred while saving " + this.fullName, exception, RySetup.isDebug());
        }
        this.loadConfig();
    }

    /**
     * Get a string from the configuration.
     *
     * @param path Location of the string.
     * @return     The requested string.
     */
    public String getString(String path) {
        return this.getConfig().getString(path);
    }

    /**
     * Get a string from the configuration.
     *
     * @param path          Location of the string.
     * @param defaultResult The default result if it is null.
     * @return     The requested string.
     */
    public String getString(String path, String defaultResult) {
        String result = getString(path);
        if (result == null) return defaultResult;
        else return result;
    }

    /**
     * Get a string list from the configuration.
     *
     * @param path Location of the string list.
     * @return     The requested string list.
     */
    public List<String> getStringList(String path) {
        return this.getConfig().getStringList(path);
    }

    /**
     * Get a string list from the configuration.
     *
     * @param path          Location of the string list.
     * @param defaultResult The default result if it is null.
     * @return     The requested string.
     */
    public List<String> getStringList(String path, List<String> defaultResult) {
        List<String> result = getStringList(path);
        if (result.size() == 0 || result == null) return defaultResult;
        else return result;
    }

    /**
     * Get a boolean from the configuration.
     *
     * @param path Location of the boolean.
     * @return     The requested boolean.
     */
    public boolean getBoolean(String path) {
        return this.getConfig().getBoolean(path);
    }

    /**
     * Get an int from the configuration.
     *
     * @param path Location of the int.
     * @return     The requested int.
     */
    public int getInt(String path) {
        return this.getConfig().getInt(path);
    }

    /**
     * Get a double from the configuration.
     *
     * @param path Location of the double.
     * @return     The requested double.
     */
    public double getDouble(String path) {
        return this.getConfig().getDouble(path);
    }

    /**
     * Get a long from the configuration.
     *
     * @param path Location of the long.
     * @return     The requested long.
     */
    public long getLong(String path) {
        return this.getConfig().getLong(path);
    }

    /**
     * Get a configuration section from the configuration.
     *
     * @param path Location of the configuration section.
     * @return     The requested configuration section.
     */
    public ConfigurationSection getSection(String path) {
        return this.getConfig().getConfigurationSection(path);
    }

    /**
     * Get an object from the configuration.
     *
     * @param path Location of the object.
     * @return The requested object.
     */
    public Object getObject(String path) {
        return this.getConfig().get(path);
    }

    /**
     * Get an object from the configuration.
     *
     * @param path          Location of the object.
     * @param defaultResult The default result if it is null.
     * @return The requested object.
     */
    public Object getObject(String path, Object defaultResult) {
        Object object = this.getConfig().get(path);
        if (object == null) return defaultResult;
        else return object;
    }

}
