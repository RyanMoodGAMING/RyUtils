package me.ryanmood.ryutils.velocity;

import com.velocitypowered.api.plugin.Plugin;
import com.velocitypowered.api.plugin.annotation.DataDirectory;
import com.velocitypowered.api.proxy.ProxyServer;
import lombok.Getter;
import lombok.Setter;

import java.nio.file.Path;

/*
 * This software and its content is copyright of RyanMoodGAMING - © RyanMoodGAMING 2024. All rights reserved.
 * Any redistribution or reproduction of part or all of the contents in any form is prohibited other than the following:
 * you may print or download to a local hard disk extracts for your personal and non-commercial use only
 * you may copy the content to individual third parties for their personal use, but only if you acknowledge the website as the source of the material
 * You may not, except with our express written permission, distribute or commercially exploit the content. Nor may you transmit it or store it in any other website or other form of electronic retrieval system.
 */

public class RySetup {

    /**
     * Setup RyUtils.
     *
     * @param pluginInstance The instance of the plugin.
     * @param proxyServer    The proxy server of the plugin.
     * @param path           The configuration path.
     */
    public RySetup(Plugin pluginInstance, ProxyServer proxyServer, @DataDirectory Path path) {
        setPluginInstance(pluginInstance);
        setProxyServer(proxyServer);
        setPluginPath(path);
    }

    /**
     * The instance of the plugin.
     */
    @Getter
    @Setter
    private static Plugin pluginInstance = null;

    /**
     * The proxy server of the plugin.
     */
    @Getter
    @Setter
    private static ProxyServer proxyServer = null;

    /**
     * The path of the plugin's folder.
     */
    @Getter
    @Setter
    private static Path pluginPath = null;

    /**
     * Should the plugin send debug messages?
     */
    @Getter
    @Setter
    private static boolean debug = false;

}
