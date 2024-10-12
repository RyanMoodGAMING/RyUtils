package me.ryanmood.ryutils.base.configuration;

import java.util.List;

public interface RyFileBase {

    /**
     * The cache of the config.
     */
    public abstract void loadConfig();

    /**
     * Create and load the files.
     */
    void setupAndLoad();

    /**
     * Save the config file.
     */
    void saveFile();

    /**
     * Reload the config file.
     */
    void reloadFile();

    /**
     * Get a string from the configuration.
     *
     * @param path Location of the string.
     * @return     The requested string.
     */
    String getString(String path);

    /**
     * Get a string from the configuration.
     *
     * @param path          Location of the string.
     * @param defaultResult The default result if it is null.
     * @return     The requested string.
     */
    String getString(String path, String defaultResult);

    /**
     * Get a string list from the configuration.
     *
     * @param path Location of the string list.
     * @return     The requested string list.
     */
    List<String> getStringList(String path);

    /**
     * Get a string list from the configuration.
     *
     * @param path          Location of the string list.
     * @param defaultResult The default result if it is null.
     * @return     The requested string.
     */
    List<String> getStringList(String path, List<String> defaultResult);

    /**
     * Get a string list from the configuration.
     *
     * @param path           Location of the string list.
     * @param defaultResults The default result if it is null.
     * @return               The requested string list.
     */
    List<String> getStringList(String path, String... defaultResults);

    /**
     * Get a boolean from the configuration.
     *
     * @param path Location of the boolean.
     * @return     The requested boolean.
     */
    boolean getBoolean(String path);

    /**
     * Get an int from the configuration.
     *
     * @param path Location of the int.
     * @return     The requested int.
     */
    int getInt(String path);

    /**
     * Get a double from the configuration.
     *
     * @param path Location of the double.
     * @return     The requested double.
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
     * @return     The requested configuration section.
     */
    Object getSection(String path);

    /**
     * Get an object from the configuration.
     *
     * @param path Location of the object.
     * @return The requested object.
     */
    Object getObject(String path);

    /**
     * Get an object from the configuration.
     *
     * @param path          Location of the object.
     * @param defaultResult The default result if it is null.
     * @return The requested object.
     */
    Object getObject(String path, Object defaultResult);

}
