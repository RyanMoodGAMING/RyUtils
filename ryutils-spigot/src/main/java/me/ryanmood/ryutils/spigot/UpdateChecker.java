package me.ryanmood.ryutils.spigot;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/*
 * This software and its content is copyright of RyanMoodGAMING - © RyanMoodGAMING 2023. All rights reserved.
 * Any redistribution or reproduction of part or all of the contents in any form is prohibited other than the following:
 * you may print or download to a local hard disk extracts for your personal and non-commercial use only
 * you may copy the content to individual third parties for their personal use, but only if you acknowledge the website as the source of the material
 * You may not, except with our express written permission, distribute or commercially exploit the content. Nor may you transmit it or store it in any other website or other form of electronic retrieval system.
 */

@SuppressWarnings("unused")
public class UpdateChecker {

    /**
     * Update Checker will check the URL to see if version provided matches the version on the checker site.
     *
     * @param pluginName    The name of the plugin.
     * @param pluginVersion The version of the plugin.
     * @param checkURL      The url of the update checker site.
     */
    public static void updateChecker(String pluginName, String pluginVersion, String checkURL) {
        try {
            URL url = new URL(checkURL);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("User-Agent", "Mozilla/5.0");
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String input;
            StringBuffer response = new StringBuffer();
            while ((input = reader.readLine()) != null) {
                response.append(input);
            }
            reader.close();
            JsonObject object = new JsonParser().parse(response.toString()).getAsJsonObject();

            if (object.has("plugins")) {
                JsonObject plugins = object.get("plugins").getAsJsonObject();
                JsonObject info = plugins.get(pluginName).getAsJsonObject();
                String version = info.get("version").getAsString();
                if (version.equals(pluginVersion)) {
                    RyMessageUtils.sendConsole(false,"&a" + pluginName + " is on the latest version.");
                } else {
                    RyMessageUtils.sendConsole(false, "");
                    RyMessageUtils.sendConsole(true,"&cYour " + pluginName + " version is out of date!");
                    RyMessageUtils.sendConsole(true,"&cWe recommend updating ASAP!");
                    RyMessageUtils.sendConsole(false,"");
                    RyMessageUtils.sendConsole(true,"&cYour Version: &e" + pluginVersion);
                    RyMessageUtils.sendConsole(true,"&aNewest Version: &e" + version);
                    RyMessageUtils.sendConsole(false, "");
                }
            } else {
                RyMessageUtils.sendConsole(true,"&cWrong response from update API, contact plugin developer!");
            }
        } catch (Exception ex) {
            RyMessageUtils.sendConsole(true,"&cFailed to get updater check. (" + ex.getMessage() + ")");
        }
    }

}
