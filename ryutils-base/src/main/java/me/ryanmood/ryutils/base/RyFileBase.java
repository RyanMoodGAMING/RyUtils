package me.ryanmood.ryutils.base;

import java.util.Arrays;
import java.util.List;

public interface RyFileBase<Section> {

    /**
     * The cache of the config.
     */
    abstract void loadConfig();

    /**
     * Load the config file.
     */
    void loadFile();

    /**
     * Save the config file.
     */
    void saveFile();

    /**
     * Reload the config file.
     */
    void reloadFile();

    /**
     * Get a {@link String} from the configuration.
     *
     * @param path Location of the string.
     * @return     The requested {@link String}.
     */
    String getString(String path);

    /**
     * Get a {@link String} from the configuration.
     *
     * @param path          Location of the string.
     * @param defaultResult The default result if it is null.
     * @return              The requested {@link String}.
     */
    default String getString(String path, String defaultResult) {
        return (this.getString(path) == null) ? defaultResult : this.getString(path);
    }

    /**
     * Get a string list from the configuration.
     *
     * @param path Location of the string list.
     * @return     The requested {@link List} of {@link String}.
     */
    List<String> getStringList(String path);

    /**
     * Get a string list from the configuration.
     *
     * @param path          Location of the string list.
     * @param defaultResult The default result if it is null.
     * @return              The requested {@link List} of {@link String}.
     */
    default List<String> getStringList(String path, List<String> defaultResult) {
        List<String> result = this.getStringList(path);
        return (result == null || result.size() == 0) ? defaultResult : result;
    }

    /**
     * Get a string list from the configuration.
     *
     * @param path          Location of the string list.
     * @param defaultResult The default result if it is null.
     * @return              The requested {@link List} of {@link String}.
     */
    default List<String> getStringList(String path, String... defaultResult) {
        return this.getStringList(path, Arrays.asList(defaultResult));
    }

    /**
     * Get a {@link Boolean} from the configuration.
     *
     * @param path Location of the boolean.
     * @return     The requested {@link Boolean}.
     */
    boolean getBoolean(String path);

    /**
     * Get an {@link Integer} from the configuration.
     *
     * @param path Location of the int.
     * @return     The requested {@link Integer}.
     */
    int getInt(String path);

    /**
     * Get a {@link Double} from the configuration.
     *
     * @param path Location of the double.
     * @return     The requested {@link Double}.
     */
    double getDouble(String path);

    /**
     * Get a long from the configuration.
     *
     * @param path Location of the long.
     * @return     The requested long.
     */
    long getLong(String path);

    /**
     * Get a configuration section from the configuration.
     *
     * @param path Location of the configuration section.
     * @return     The requested {@link Section}.
     */
    Section getSection(String path);

    /**
     * Get a configuration section from the configuration.
     *
     * @param path Location of the configuration section.
     * @return     The requested {@link Section}.
     */
    default Section getConfigSection(String path) {
        return this.getSection(path);
    }

    /**
     * Get an {@link Object} from the configuration.
     *
     * @param path Location of the object.
     * @return The requested {@link Object}.
     */
    Object getObject(String path);

    /**
     * Get an {@link Object} from the configuration.
     *
     * @param path          Location of the object.
     * @param defaultResult The default result if it is null.
     * @return              The requested {@link Object}.
     */
    default Object getObject(String path, Object defaultResult) {
        return (this.getObject(path) == null) ? defaultResult : this.getObject(path);
    }


}
