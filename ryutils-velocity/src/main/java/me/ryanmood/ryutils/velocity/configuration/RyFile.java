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
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@SuppressWarnings("unused")
public abstract class RyFile {

    /**
     * The plugin's instance.
     */
    private Object instance;
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
     * General Settings for the YAML configuration.
     */
    @Getter
    private GeneralSettings generalSettings;
    /**
     * Loader Settings for the YAML configuration.
     */
    @Getter
    private LoaderSettings loaderSettings;
    /**
     * Dumper Settings for the YAML configuration.
     */
    @Getter
    private DumperSettings dumperSettings;
    /**
     * Updater Settings for the YAML configuration.
     */
    @Getter
    private UpdaterSettings updaterSettings;

    /**
     * The Yaml Document for the config file.
     */
    @Getter
    private YamlDocument config;

    /**
     * The configuration file.
     *
     * @param name The name of the config.
     */
    public RyFile(String name) {
        this(RySetup.getPluginInstance(), RySetup.getProxyServer(), name, RySetup.getPluginPath(), false, null, true);
    }

    /**
     * The configuration file.
     *
     * @param instance The plugin's instance.
     * @param name     The name of the config.
     */
    public RyFile(Object instance, ProxyServer proxyServer, String name) {
        this (instance, proxyServer, name, RySetup.getPluginPath(), false, null, true);
    }

    /**
     * The configuration file.
     *
     * @param name       The name of the plugin.
     * @param autoUpdate Should the config auto update?
     */
    public RyFile(String name, boolean autoUpdate) {
        this(RySetup.getPluginInstance(), RySetup.getProxyServer(), name, RySetup.getPluginPath(), autoUpdate, "config-version", true);
    }

    /**
     * The configuration file.
     *
     * @param name              The name of the plugin.
     * @param autoUpdate        Should the config auto update?
     * @param useDefaultOptions Should the default options be used if they are missing form the config?
     */
    public RyFile(String name, boolean autoUpdate, boolean useDefaultOptions) {
        this(RySetup.getPluginInstance(), RySetup.getProxyServer(), name, RySetup.getPluginPath(), autoUpdate, "config-version", useDefaultOptions);
    }

    /**
     * The configuration file.
     *
     * @param instance    The plugin's instance.
     * @param proxyServer The server's proxy.
     * @param name        The name of the plugin.
     * @param autoUpdate  Should the config auto update?
     */
    public RyFile(Object instance, ProxyServer proxyServer, String name, boolean autoUpdate) {
        this(instance, proxyServer, name, RySetup.getPluginPath(), autoUpdate, "config-version", true);
    }

    /**
     * The configuration file.
     *
     * @param instance          The plugin's instance.
     * @param proxyServer       The server's proxy.
     * @param name              The name of the plugin.
     * @param autoUpdate        Should the config auto update?
     * @param useDefaultOptions Should the default options be used if they are missing form the config?
     */
    public RyFile(Object instance, ProxyServer proxyServer, String name, boolean autoUpdate, boolean useDefaultOptions) {
        this(instance, proxyServer, name, RySetup.getPluginPath(), autoUpdate, "config-version", useDefaultOptions);
    }

    /**
     * The configuration file.
     *
     * @param name          The name of the config.
     * @param dataDirectory The location of the config.
     * @param autoUpdate    Should the config auto update?
     * @param versionPath   The path which shows the version of the config.
     */
    public RyFile(String name, @DataDirectory Path dataDirectory, boolean autoUpdate, @Nullable String versionPath) {
        this(RySetup.getPluginInstance(), RySetup.getProxyServer(), name, dataDirectory, autoUpdate, versionPath, true);
    }

    /**
     * The configuration file.
     *
     * @param name              The name of the config.
     * @param dataDirectory     The location of the config.
     * @param autoUpdate        Should the config auto update?
     * @param versionPath       The path which shows the version of the config.
     * @param useDefaultOptions Should the default options be used if they are missing form the config?
     */
    public RyFile(String name, @DataDirectory Path dataDirectory, boolean autoUpdate, @Nullable String versionPath, boolean useDefaultOptions) {
        this(RySetup.getPluginInstance(), RySetup.getProxyServer(), name, dataDirectory, autoUpdate, versionPath, useDefaultOptions);
    }

    /**
     * The configuration file.
     *
     * @param instance      The plugin's instance.
     * @param name          The name of the config.
     * @param dataDirectory The location of the config.
     * @param autoUpdate    Should the config auto update?
     * @param versionPath   The path which shows the version of the config.
     */
    public RyFile(Object instance, ProxyServer proxyServer, String name, @DataDirectory Path dataDirectory, boolean autoUpdate,
                  @Nullable String versionPath, boolean useDefaultOptions) {
        this.fullName = name.endsWith(".yml") ? name : name + "yml";
        this.directory = dataDirectory.toFile();
        this.dataDirectory = dataDirectory;
        this.instance = instance;
        this.server = proxyServer;

        GeneralSettings generalSettings = GeneralSettings.builder()
                .setUseDefaults(useDefaultOptions)
                .build();

        LoaderSettings loaderSettings = LoaderSettings.builder()
                .setAutoUpdate(autoUpdate)
                .build();

        DumperSettings dumperSettings = DumperSettings.DEFAULT;

        UpdaterSettings updaterSettings;

        if (autoUpdate && versionPath != null) {
            updaterSettings = UpdaterSettings.builder()
                    .setVersioning(new BasicVersioning(versionPath))
                    .setOptionSorting(UpdaterSettings.OptionSorting.SORT_BY_DEFAULTS)
                    .setAutoSave(true)
                    .build();
        } else {
            updaterSettings = UpdaterSettings.builder()
                    .setAutoSave(false)
                    .build();
        }

        this.setupAndLoad(generalSettings, loaderSettings, dumperSettings, updaterSettings);
    }

    public RyFile(Object instance, ProxyServer proxyServer, String name, @DataDirectory Path dataDirectory, GeneralSettings generalSettings,
                  LoaderSettings loaderSettings, DumperSettings dumperSettings, UpdaterSettings updaterSettings) {
        this.fullName = name.endsWith(".yml") ? name : name + "yml";
        this.directory = dataDirectory.toFile();
        this.dataDirectory = dataDirectory;
        this.instance = instance;
        this.server = proxyServer;

        this.setupAndLoad(generalSettings, loaderSettings, dumperSettings, updaterSettings);
    }

    private void setupAndLoad(GeneralSettings generalSettings, LoaderSettings loaderSettings, DumperSettings dumperSettings,
                       UpdaterSettings updaterSettings) {
        this.generalSettings = generalSettings;
        this.loaderSettings = loaderSettings;
        this.dumperSettings = dumperSettings;
        this.updaterSettings = updaterSettings;

        try {
            this.config = YamlDocument.create(new File(dataDirectory.toFile(), this.fullName),
                    getClass().getResourceAsStream("/" + this.fullName),
                    generalSettings,
                    loaderSettings,
                    dumperSettings,
                    updaterSettings);

            this.file = this.config.getFile();
            if (loaderSettings.isAutoUpdate()) this.config.update();
            this.config.save();
            this.loadConfig();
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
            this.loadConfig();
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
            this.loadConfig();
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
     * @return              The requested string.
     */
    public List<String> getStringList(String path, List<String> defaultResult) {
        List<String> result = getStringList(path);
        if (result.size() == 0 || result == null) return defaultResult;
        else return result;
    }

    /**
     * Get a string list from the configuration.
     *
     * @param path           Location of the string list.
     * @param defaultResults The default result if it is null.
     * @return               The requested string list.
     */
    public List<String> getStringList(String path, String... defaultResults) {
        List<String> result = getStringList(path);
        if (result.size() == 0 || result == null) return Arrays.asList(defaultResults);
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
