package me.ryanmood.ryutils.base;

public interface RyVersionBase {

    /**
    Get the NMS version from XSeries.

    @return NMS Version from XReflection.
     */
    String getNMSVersion();

    /**
     *
     * Get the major version from XReflection.
     *
     * @return Int of Major Version
     */
    int getMajorVersion();

    /**
     *
     * Get the minor version from XReflection.
     *
     * @return Int of minor Version
     */
    int getMinorVersion();

    /**
     *
     * Get the patch version from XReflection.
     *
     * @return Int of patch Version
     */
    int getPatchVersion();

    /**
     * Get the version information from XReflection.
     *
     * @return String of version
     */
    String getVersionInfo();

    /**
     * Has the version got hex colours?
     *
     * @return {@link Boolean}
     */
    boolean hasHexColours();

}
