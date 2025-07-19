package me.ryanmood.ryutils.java;

import dev.dejvokep.boostedyaml.YamlDocument;
import dev.dejvokep.boostedyaml.block.implementation.Section;
import dev.dejvokep.boostedyaml.dvs.versioning.BasicVersioning;
import dev.dejvokep.boostedyaml.settings.dumper.DumperSettings;
import dev.dejvokep.boostedyaml.settings.general.GeneralSettings;
import dev.dejvokep.boostedyaml.settings.loader.LoaderSettings;
import dev.dejvokep.boostedyaml.settings.updater.UpdaterSettings;
import lombok.Getter;
import me.ryanmood.ryutils.base.RyFileBase;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

@Getter
@SuppressWarnings("unused")
public abstract class RyFile implements RyFileBase<Section> {

    private Path dataDirectory;
    private String fullName;
    private File directory;
    private File file;
    private GeneralSettings generalSettings;
    private LoaderSettings loaderSettings;
    private DumperSettings dumperSettings;
    private UpdaterSettings updaterSettings;
    private YamlDocument config;

    public RyFile(Path dataDirectory, String name) {
        this(dataDirectory, name, false, null, true);
    }

    public RyFile(Path dataDirectory, String name, boolean autoUpdate) {
        this(dataDirectory, name, autoUpdate, "config-version", true);
    }

    public RyFile(Path dataDirectory, String name, boolean autoUpdate, String versionPath) {
        this(dataDirectory, name, autoUpdate, versionPath, true);
    }

    public RyFile(Path dataDirectory, String name, boolean autoUpdate,
                  @Nullable String versionPath, boolean useDefaultOptions) {

        this.directory = dataDirectory.toFile();
        this.dataDirectory = dataDirectory;
        this.fullName = name.endsWith(".yml") ? name : name + ".yml";

        this.generalSettings = GeneralSettings.builder()
                .setUseDefaults(useDefaultOptions)
                .build();

        this.loaderSettings = LoaderSettings.builder()
                .setAutoUpdate(autoUpdate)
                .build();

        this.dumperSettings = DumperSettings.DEFAULT;

        if (autoUpdate && versionPath != null) {
            this.updaterSettings = UpdaterSettings.builder()
                    .setVersioning(new BasicVersioning(versionPath))
                    .setOptionSorting(UpdaterSettings.OptionSorting.SORT_BY_DEFAULTS)
                    .setAutoSave(true)
                    .build();
        } else {
            this.updaterSettings = UpdaterSettings.builder()
                    .setAutoSave(false)
                    .build();
        }

        this.loadFile();
    }

    public RyFile(Path dataDirectory, String name, GeneralSettings generalSettings,
                  LoaderSettings loaderSettings, DumperSettings dumperSettings, UpdaterSettings updaterSettings) {

        this.directory = dataDirectory.toFile();
        this.dataDirectory = dataDirectory;
        this.fullName = name.endsWith(".yml") ? name : name + ".yml";

        this.generalSettings = generalSettings;
        this.loaderSettings = loaderSettings;
        this.dumperSettings = dumperSettings;
        this.updaterSettings = updaterSettings;

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
            this.config = YamlDocument.create(new File(this.dataDirectory.toFile(), this.getFullName()),
                    getClass().getResourceAsStream("/" + this.getFullName()),
                    this.getGeneralSettings(),
                    this.getLoaderSettings(),
                    this.getDumperSettings(),
                    this.getUpdaterSettings());

            this.file = this.config.getFile();
            if (this.loaderSettings.isAutoUpdate()) this.config.update();
            this.config.save();
            this.loadConfig();

        } catch (IOException e) {
            RyMessageUtil.getUtil().sendError("An error occurred while creating " + this.getFullName(), e);
        }
    }

    /**
     * Save the config file.
     */
    @Override
    public void saveFile() {
        try {
            this.config.save(this.file);
            this.loadConfig();
        } catch (Exception e) {
            RyMessageUtil.getUtil().sendError("An error occurred while saving " + this.getFullName(), e);
        }
    }

    /**
     * Reload the config file.
     */
    @Override
    public void reloadFile() {
        this.file = new File(this.directory, this.getFullName());
        try {
            this.config.reload();
            this.loadConfig();
        } catch (Exception e) {
            RyMessageUtil.getUtil().sendError("An error occurred while reloading " + this.getFullName(), e);
        }
    }

    /**
     * Get a {@link String} from the configuration.
     *
     * @param path Location of the string.
     * @return The requested {@link String}.
     */
    @Override
    public String getString(String path) {
        return this.getConfig().getString(path);
    }

    /**
     * Get a string list from the configuration.
     *
     * @param path Location of the string list.
     * @return The requested {@link List} of {@link String}.
     */
    @Override
    public List<String> getStringList(String path) {
        return this.getConfig().getStringList(path);
    }

    /**
     * Get a {@link Boolean} from the configuration.
     *
     * @param path Location of the boolean.
     * @return The requested {@link Boolean}.
     */
    @Override
    public boolean getBoolean(String path) {
        return this.getConfig().getBoolean(path);
    }

    /**
     * Get an {@link Integer} from the configuration.
     *
     * @param path Location of the int.
     * @return The requested {@link Integer}.
     */
    @Override
    public int getInt(String path) {
        return this.getConfig().getInt(path);
    }

    /**
     * Get a {@link Double} from the configuration.
     *
     * @param path Location of the double.
     * @return The requested {@link Double}.
     */
    @Override
    public double getDouble(String path) {
        return this.getConfig().getDouble(path);
    }

    /**
     * Get a long from the configuration.
     *
     * @param path Location of the long.
     * @return The requested long.
     */
    @Override
    public long getLong(String path) {
        return this.getConfig().getLong(path);
    }

    /**
     * Get a configuration section from the configuration.
     *
     * @param path Location of the configuration section.
     * @return The requested {@link Section}.
     */
    @Override
    public Section getSection(String path) {
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
