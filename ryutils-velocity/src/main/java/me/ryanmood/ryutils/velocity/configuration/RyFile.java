package me.ryanmood.ryutils.velocity.configuration;

import com.velocitypowered.api.plugin.Plugin;
import com.velocitypowered.api.plugin.annotation.DataDirectory;
import com.velocitypowered.api.proxy.ProxyServer;
import dev.dejvokep.boostedyaml.YamlDocument;
import dev.dejvokep.boostedyaml.block.implementation.Section;
import dev.dejvokep.boostedyaml.dvs.versioning.BasicVersioning;
import dev.dejvokep.boostedyaml.settings.dumper.DumperSettings;
import dev.dejvokep.boostedyaml.settings.general.GeneralSettings;
import dev.dejvokep.boostedyaml.settings.loader.LoaderSettings;
import dev.dejvokep.boostedyaml.settings.updater.UpdaterSettings;
import lombok.Getter;
import me.ryanmood.ryutils.velocity.RyMessageUtils;
import me.ryanmood.ryutils.velocity.RySetup;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

@SuppressWarnings("unused")
public abstract class RyFile {

    /**
     * The plugin's instance.
     */
    private Plugin instance;
    /**
     * The proxy server.
     */
    private ProxyServer server;

    /**
     * The data directory path.
     */
    private Path dataDirectory;

    /**
     * The file's full name.
     */
    @Getter
    private String fullName;
    /**
     * The file location of the config.
     */
    @Getter
    private File directory;
    /**
     * The file of the config.
     */
    @Getter
    private File file;
    /**
     * The Yaml Document for the config file.
     */
    @Getter
    private YamlDocument config;

    protected RyFile(String name) {
        this(name, RySetup.getPluginPath(), false, null);
    }

    protected RyFile(String name, boolean autoUpdate) {
        this(name, RySetup.getPluginPath(), autoUpdate, "config-version");
    }

    protected RyFile(String name, @DataDirectory Path dataDirectory, boolean autoUpdate, @Nullable String versionPath) {
        this.fullName = name.endsWith(".yml") ? name : name + "yml";
        this.directory = dataDirectory.toFile();
        this.dataDirectory = dataDirectory;
        this.instance = RySetup.getPluginInstance();
        this.server = RySetup.getProxyServer();

        try {
            if (autoUpdate && versionPath != null) {
                this.config = YamlDocument.create(new File(dataDirectory.toFile(), this.fullName),
                        getClass().getResourceAsStream("/" + this.fullName),
                        GeneralSettings.DEFAULT,
                        LoaderSettings.builder().setAutoUpdate(true).build(),
                        DumperSettings.DEFAULT,
                        UpdaterSettings.builder().setVersioning(new BasicVersioning(versionPath))
                                .setOptionSorting(UpdaterSettings.OptionSorting.SORT_BY_DEFAULTS).build());
            } else {
                this.config = YamlDocument.create(new File(dataDirectory.toFile(), this.fullName),
                        getClass().getResourceAsStream("/" + this.fullName),
                        GeneralSettings.DEFAULT,
                        LoaderSettings.builder().setAutoUpdate(false).build(),
                        DumperSettings.DEFAULT,
                        UpdaterSettings.builder().build());
            }
            this.file = this.config.getFile();
            this.config.update();
            this.config.save();
        } catch (IOException exception) {
            RyMessageUtils.sendPluginError("An error occurred while creating " + this.getFullName(), exception, RySetup.isDebug(), true);
        }
    }

    /**
     * The cache of the config.
     */
    public abstract void loadConfig();

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
        this.file = new File(this.getDirectory(), this.fullName);
        try {
            this.config.reload();
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
    public Section getSection(String path) {
        return this.getConfig().getSection(path);
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