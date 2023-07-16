package me.ryanmood.ryutils.spigot;

import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

/*
 * This software and its content is copyright of RyanMoodGAMING - © RyanMoodGAMING 2023. All rights reserved.
 * Any redistribution or reproduction of part or all of the contents in any form is prohibited other than the following:
 * you may print or download to a local hard disk extracts for your personal and non-commercial use only
 * you may copy the content to individual third parties for their personal use, but only if you acknowledge the website as the source of the material
 * You may not, except with our express written permission, distribute or commercially exploit the content. Nor may you transmit it or store it in any other website or other form of electronic retrieval system.
 */

public class RyTasksUtils {

    /**
     * Create a task that runs within a scheduler.
     *
     * @param plugin   The instance of the plugin you are using.
     * @param callable The Runnable code you would like to happen.
     */
    public static void run(@NotNull JavaPlugin plugin, Runnable callable) {
        plugin.getServer().getScheduler().runTask(plugin, callable);
    }

    /**
     * Create a task that runs within an async scheduler.
     *
     * @param plugin   The instance of the plugin you are using.
     * @param callable The Runnable code you would like to happen.
     */
    public static void runAsync(@NotNull JavaPlugin plugin, Runnable callable) {
        plugin.getServer().getScheduler().runTaskAsynchronously(plugin, callable);
    }

    /**
     * Create a task that runs within a scheduler after a certain amount of time.
     *
     * @param plugin   The instance of the plugin you are using.
     * @param callable The Runnable code you would like to happen.
     * @param delay    The delay before the runnable runs.
     */
    public static void runLater(@NotNull JavaPlugin plugin, Runnable callable, long delay) {
        plugin.getServer().getScheduler().runTaskLater(plugin, callable, delay);
    }

    /**
     * Create a task that runs within an async scheduler after a certain amount of time.
     *
     * @param plugin   The instance of the plugin you are using.
     * @param callable The Runnable code you would like to happen.
     * @param delay    The delay before the runnable runs.
     */
    public static void runAsyncLater(@NotNull JavaPlugin plugin, Runnable callable, long delay) {
        plugin.getServer().getScheduler().runTaskLaterAsynchronously(plugin, callable, delay);
    }

    /**
     * Create a task that runs on a timer within a scheduler after a certain amount of time.
     *
     * @param plugin   The instance of the plugin you are using.
     * @param callable The Runnable code you would like to happen.
     * @param delay    The delay before the runnable runs.
     * @param interval The interval before the runnable runs again.
     */
    public static void runTimer(@NotNull JavaPlugin plugin, Runnable callable, long delay, long interval) {
        plugin.getServer().getScheduler().runTaskTimer(plugin, callable, delay, interval);
    }

    /**
     * Create a task that runs on a timer within an async scheduler after a certain amount of time.
     *
     * @param plugin   The instance of the plugin you are using.
     * @param callable The Runnable code you would like to happen.
     * @param delay    The delay before the runnable runs.
     * @param interval The interval before the runnable runs again.
     */
    public static void runAsyncTimer(@NotNull JavaPlugin plugin, Runnable callable, long delay, long interval) {
        plugin.getServer().getScheduler().runTaskTimerAsynchronously(plugin, callable, delay, interval);
    }

}
