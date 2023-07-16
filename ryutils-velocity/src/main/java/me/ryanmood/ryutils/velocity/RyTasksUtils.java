package me.ryanmood.ryutils.velocity;

import com.velocitypowered.api.plugin.Plugin;
import com.velocitypowered.api.proxy.ProxyServer;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.TimeUnit;

/*
 * This software and its content is copyright of RyanMoodGAMING - © RyanMoodGAMING 2023. All rights reserved.
 * Any redistribution or reproduction of part or all of the contents in any form is prohibited other than the following:
 * you may print or download to a local hard disk extracts for your personal and non-commercial use only
 * you may copy the content to individual third parties for their personal use, but only if you acknowledge the website as the source of the material
 * You may not, except with our express written permission, distribute or commercially exploit the content. Nor may you transmit it or store it in any other website or other form of electronic retrieval system.
 */

public class RyTasksUtils {

    @Setter
    private static ProxyServer server;

    public static void run(@NotNull Plugin plugin, Runnable callable) {
        runAsync(plugin, callable);
    }

    public static void runAsync(@NotNull Plugin plugin, Runnable callable) {
        server.getScheduler().buildTask(plugin, callable).schedule();
    }

    public static void runLater(@NotNull Plugin plugin, Runnable callable, long delay, TimeUnit timeUnit) {
        runAsyncLater(plugin, callable, delay, timeUnit);
    }

    public static void runAsyncLater(@NotNull Plugin plugin, Runnable callable, long delay, TimeUnit timeUnit) {
        server.getScheduler().buildTask(plugin, callable).delay(delay, timeUnit).schedule();
    }

    public static void runTimer(@NotNull Plugin plugin, Runnable callable, long delay, long interval, TimeUnit timeUnit) {
        runAsyncTimer(plugin, callable, delay, interval, timeUnit);
    }

    public static void runAsyncTimer(@NotNull Plugin plugin, Runnable callable, long delay, long interval, TimeUnit timeUnit) {
        server.getScheduler().buildTask(plugin, callable).delay(delay, timeUnit).repeat(interval, timeUnit).schedule();
    }

}
