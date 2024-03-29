package me.ryanmood.ryutils.velocity;

import com.velocitypowered.api.plugin.Plugin;
import com.velocitypowered.api.proxy.ProxyServer;
import com.velocitypowered.api.scheduler.ScheduledTask;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.concurrent.TimeUnit;

/*
 * This software and its content is copyright of RyanMoodGAMING - © RyanMoodGAMING 2023. All rights reserved.
 * Any redistribution or reproduction of part or all of the contents in any form is prohibited other than the following:
 * you may print or download to a local hard disk extracts for your personal and non-commercial use only
 * you may copy the content to individual third parties for their personal use, but only if you acknowledge the website as the source of the material
 * You may not, except with our express written permission, distribute or commercially exploit the content. Nor may you transmit it or store it in any other website or other form of electronic retrieval system.
 */

@SuppressWarnings("unused")
public class RyTasksUtils {

    @Setter
    private static ProxyServer server = RySetup.getProxyServer();

    /**
     * Create a task that runs within a scheduler.
     * @deprecated use {@link #runAsync(Plugin, Runnable)} instead.
     *
     * @param plugin   The instance of the plugin you are using.
     * @param callable The Runnable code you would like to happen.
     */
    @Deprecated
    public static void run(@NotNull Plugin plugin, Runnable callable) {
        runAsync(plugin, callable);
    }

    /**
     * Create a task that runs within an async scheduler.
     *
     * @param plugin   The instance of the plugin you are using.
     * @param callable The Runnable code you would like to happen.
     */
    public static void runAsync(@NotNull Plugin plugin, Runnable callable) {
        server.getScheduler().buildTask(plugin, callable).schedule();
    }

    /**
     * Create a task that runs within a scheduler after a certain amount of time.
     * @deprecated use {@link #runAsyncLater(Plugin, Runnable, long, TimeUnit)} instead.
     *
     * @param plugin   The instance of the plugin you are using.
     * @param callable The Runnable code you would like to happen.
     * @param delay    The delay before the runnable runs.
     * @param timeUnit The time unit.
     */
    @Deprecated
    public static void runLater(@NotNull Plugin plugin, Runnable callable, long delay, TimeUnit timeUnit) {
        runAsyncLater(plugin, callable, delay, timeUnit);
    }

    /**
     * Create a task that runs within an async scheduler after a certain amount of time.
     *
     * @param plugin   The instance of the plugin you are using.
     * @param callable The Runnable code you would like to happen.
     * @param delay    The delay before the runnable runs.
     * @param timeUnit The time unit.
     */
    public static void runAsyncLater(@NotNull Plugin plugin, Runnable callable, long delay, TimeUnit timeUnit) {
        server.getScheduler().buildTask(plugin, callable).delay(delay, timeUnit).schedule();
    }

    /**
     * Create a task that runs on a timer within a scheduler after a certain amount of time.
     * @deprecated use {@link #runAsyncTimer(Plugin, Runnable, long, long, TimeUnit)} instead.
     *
     * @param plugin   The instance of the plugin you are using.
     * @param callable The Runnable code you would like to happen.
     * @param delay    The delay before the runnable runs.
     * @param interval The interval before the runnable runs again.
     * @param timeUnit The time unit.
     */
    @Deprecated
    public static void runTimer(@NotNull Plugin plugin, Runnable callable, long delay, long interval, TimeUnit timeUnit) {
        runAsyncTimer(plugin, callable, delay, interval, timeUnit);
    }

    /**
     * Create a task that runs on a timer within an async scheduler after a certain amount of time.
     *
     * @param plugin   The instance of the plugin you are using.
     * @param callable The Runnable code you would like to happen.
     * @param delay    The delay before the runnable runs.
     * @param interval The interval before the runnable runs again.
     * @param timeUnit The time unit.
     */
    public static void runAsyncTimer(@NotNull Plugin plugin, Runnable callable, long delay, long interval, TimeUnit timeUnit) {
        server.getScheduler().buildTask(plugin, callable).delay(delay, timeUnit).repeat(interval, timeUnit).schedule();
    }

    /**
     * Create a task that repeats on a timer within an async scheduler after a certain amount of time.
     *
     * @param plugin   The instance of the plugin you are using.
     * @param callable The Runnable code you would like to happen.
     * @param delay    The delay before the runnable repeats.
     * @param timeUnit The time unit.
     */
    public static void runAsyncRepeat(@NotNull Plugin plugin, Runnable callable, long delay, TimeUnit timeUnit) {
        server.getScheduler().buildTask(plugin, callable).repeat(delay, timeUnit).schedule();
    }

    /**
     * Cancel all tasks that a plugin has.
     *
     * @param plugin The instance of the plugin you are using.
     */
    public static void cancelAll(@NotNull Plugin plugin) {
        Collection<ScheduledTask> tasks = server.getScheduler().tasksByPlugin(plugin);

        for (ScheduledTask task : tasks) {
            task.cancel();
        }
    }

}
