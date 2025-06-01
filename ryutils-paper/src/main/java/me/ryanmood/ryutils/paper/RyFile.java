package me.ryanmood.ryutils.paper;

import lombok.AccessLevel;
import lombok.Getter;
import me.ryanmood.ryutils.base.RyFileBase;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.List;

@Getter
@SuppressWarnings("unused")
public abstract class RyFile implements RyFileBase<ConfigurationSection> {

    @Getter(AccessLevel.PRIVATE)
    private JavaPlugin plugin;
    private String fullName;
    private File directory;
    private File file;
    private FileConfiguration config;

    public RyFile(JavaPlugin plugin, String name) {
        this(plugin, name, plugin.getDataFolder());
    }

    public RyFile(JavaPlugin plugin, String name, File directory) {
        this.plugin = plugin;
        this.fullName = name.endsWith(".yml") ? name : name + "yml";
        this.directory = directory;

        this.loadFile();
    }

    /**
     * The cache of the config.
     */
    @Override
    public abstract void loadConfig();

    /**
     * Load the config file.
     */
    @Override
    public void loadFile() {
        if (!this.getDirectory().exists()) {
            this.getDirectory().mkdirs();
        }

        File file = new File(this.getDirectory(), this.getFullName());
        if (!file.exists()) {
            this.getPlugin().saveResource(this.getFullName(), false);
        }

        this.file = file;
        this.config = YamlConfiguration.loadConfiguration(file);
        this.loadConfig();
    }

    /**
     * Save the config file.
     */
    @Override
    public void saveFile() {
        try {
            this.config.save(this.file);
        } catch (Exception e) {
            RyMessageUtil.getUtil().sendError("An error occured while saving " + this.getFullName(), e);
        }
    }

    /**
     * Reload the config file.
     */
    @Override
    public void reloadFile() {
        this.file = new File(this.getDirectory(), this.getFullName());
        try {
            this.config = YamlConfiguration.loadConfiguration(this.file);
        } catch (Exception e) {
            RyMessageUtil.getUtil().sendError("An error occured while reloading " + this.getFullName(), e);
        }

        this.loadConfig();
    }

    /**
     * Get a {@link String} from the configuration.
     *
     * @param path Location of the string.
     * @return     The requested {@link String}.
     */
    @Override
    public String getString(String path) {
        return this.getConfig().getString(path);
    }

    /**
     * Get a string list from the configuration.
     *
     * @param path Location of the string list.
     * @return     The requested {@link List} of {@link String}.
     */
    @Override
    public List<String> getStringList(String path) {
        return this.getConfig().getStringList(path);
    }

    /**
     * Get a {@link Boolean} from the configuration.
     *
     * @param path Location of the boolean.
     * @return     The requested {@link Boolean}.
     */
    @Override
    public boolean getBoolean(String path) {
        return this.getConfig().getBoolean(path);
    }

    /**
     * Get an {@link Integer} from the configuration.
     *
     * @param path Location of the int.
     * @return     The requested {@link Integer}.
     */
    @Override
    public int getInt(String path) {
        return this.getConfig().getInt(path);
    }

    /**
     * Get a {@link Double} from the configuration.
     *
     * @param path Location of the double.
     * @return     The requested {@link Double}.
     */
    @Override
    public double getDouble(String path) {
        return this.getConfig().getDouble(path);
    }

    /**
     * Get a long from the configuration.
     *
     * @param path Location of the long.
     * @return     The requested long.
     */
    @Override
    public long getLong(String path) {
        return this.getConfig().getLong(path);
    }

    /**
     * Get a configuration section from the configuration.
     *
     * @param path Location of the configuration section.
     * @return     The requested {@link ConfigurationSection}.
     */
    @Override
    public ConfigurationSection getSection(String path) {
        return this.getConfig().getConfigurationSection(path);
    }

    /**
     * Get an {@link Object} from the configuration.
     *
     * @param path Location of the object.
     * @return The requested {@link Object}.
     */
    @Override
    public Object getObject(String path) {
        return this.getConfig().get(path);
    }
}
