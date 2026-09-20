package me.ryanmood.ryutils.spigot;

import com.cryptomorin.xseries.reflection.XReflection;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import me.ryanmood.ryutils.base.RyVersionBase;
import me.ryanmood.ryutils.base.exceptions.UninitializedUtilException;
import org.bukkit.plugin.Plugin;

public class RyVersionUtil implements RyVersionBase {

    @Setter(AccessLevel.PRIVATE) @Getter(AccessLevel.PRIVATE)
    public Plugin plugin;

    public static RyVersionUtil instance;

    public RyVersionUtil(Plugin plugin) {
        this.plugin = plugin;
        instance = this;
    }

    /**
     * Get the NMS version from XSeries.
     *
     * @return NMS Version from XReflection.
     */
    @Override
    public String getNMSVersion() {
        return XReflection.findNMSVersionString();
    }

    /**
     *
     * Get the major version from XReflection.
     *
     * @return Int of Major Version
     */
    @Override
    public int getMajorVersion() {
        return XReflection.MAJOR_NUMBER;
    }

    /**
     *
     * Get the minor version from XReflection.
     *
     * @return Int of minor Version
     */
    @Override
    public int getMinorVersion() {
        return XReflection.MINOR_NUMBER;
    }

    /**
     *
     * Get the patch version from XReflection.
     *
     * @return Int of patch Version
     */
    @Override
    public int getPatchVersion() {
        return XReflection.PATCH_NUMBER;
    }

    /**
     * Get the version information from XReflection.
     *
     * @return String of version
     */
    @Override
    public String getVersionInfo() {
        return XReflection.getVersionInformation();
    }

    /**
     * Has the version got hex colours?
     *
     * @return {@link Boolean}
     */
    @Override
    public boolean hasHexColours() {
        return XReflection.MAJOR_NUMBER >= 16;
    }

    /**
     * Get the instance of the util.
     *
     * @return {@link RyVersionUtil}
     */
    public static RyVersionUtil getUtil() {
        if (instance == null) throw new UninitializedUtilException("RyVersionUtil#getUtil", "#getUtil", "Spigot");
        else return instance;
    }

    /**
     * Is the util initialized?
     *
     * @return {@link Boolean}
     */
    public static boolean isInitialized() {
        return instance != null;
    }
}
