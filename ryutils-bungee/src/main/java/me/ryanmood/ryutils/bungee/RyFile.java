package me.ryanmood.ryutils.bungee;

import com.google.common.io.ByteStreams;
import lombok.AccessLevel;
import lombok.Getter;
import me.ryanmood.ryutils.base.RyFileBase;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;

import java.io.*;
import java.util.List;

@Getter
public abstract class RyFile implements RyFileBase<Configuration> {

    @Getter(AccessLevel.PRIVATE)
    private Plugin plugin;
    private String fullName;
    private File directory;
    private File file;
    private Configuration config;

    public RyFile(Plugin plugin, String name) {
        this(plugin, name, plugin.getDataFolder());
    }

    public RyFile(Plugin plugin, String name, File directory) {
        this.plugin = plugin;
        this.fullName = name.endsWith(".yml") ? name : name + ".yml";
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
        try {
            this.config = ConfigurationProvider.getProvider(YamlConfiguration.class).load(this.loadResource());
        } catch (IOException e) {
            RyMessageUtil.getUtil().sendError("An error occurred while trying to load the file " + this.getFullName(), e);
        }
    }

    private File loadResource() {
        if (!this.getDirectory().exists()) {
            this.getDirectory().mkdirs();
        }

        File file = new File(this.getDirectory(), this.getFullName());
        try {
            if (!file.exists()) {
                file.createNewFile();
                try (InputStream in = this.plugin.getResourceAsStream(this.getFullName())) {
                    OutputStream out = new FileOutputStream(file);
                    ByteStreams.copy(in, out);
                }
            }
        } catch (Exception e) {
            RyMessageUtil.getUtil().sendError("An error occurrd while trying to create the config " + this.getFullName(), e);
        }
        this.file = file;
        return file;
    }

    /**
     * Save the config file.
     */
    @Override
    public void saveFile() {
        try {
            ConfigurationProvider.getProvider(YamlConfiguration.class).save(this.config, this.file);
            this.loadConfig();
        } catch (Exception e) {
            RyMessageUtil.getUtil().sendError("An error occured while trying to sae the file " + this.getFullName(), e);
        }
    }

    /**
     * Reload the config file.
     */
    @Override
    public void reloadFile() {
        try {
            this.config = ConfigurationProvider.getProvider(YamlConfiguration.class).load(this.loadResource());
            this.loadConfig();
        } catch (Exception e) {
            RyMessageUtil.getUtil().sendError("An error occurred while trying to load the file " + this.getFullName(), e);
        }
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
     * @return     The requested {@link Configuration}.
     */
    @Override
    public Configuration getSection(String path) {
        return this.getConfig().getSection(path);
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
