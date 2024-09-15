package me.ryanmood.ryutils.spigot;

import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;
import org.jetbrains.annotations.NotNull;

import java.util.*;

/*
 * This software and its content is copyright of RyanMoodGAMING - © RyanMoodGAMING 2023. All rights reserved.
 * Any redistribution or reproduction of part or all of the contents in any form is prohibited other than the following:
 * you may print or download to a local hard disk extracts for your personal and non-commercial use only
 * you may copy the content to individual third parties for their personal use, but only if you acknowledge the website as the source of the material
 * You may not, except with our express written permission, distribute or commercially exploit the content. Nor may you transmit it or store it in any other website or other form of electronic retrieval system.
 */

@SuppressWarnings("unused")
public class RyTasksUtils {

    @Getter
    private static final Map<Integer, BukkitTask> tasks = new HashMap<>();

    /**
     * Create a task that runs within a scheduler.
     *
     * @param plugin   The instance of the plugin you are using.
     * @param callable The Runnable code you would like to happen.
     */
    public static void run(@NotNull JavaPlugin plugin, Runnable callable) {
        BukkitTask task = plugin.getServer().getScheduler().runTask(plugin, callable);
        tasks.put(task.getTaskId(), task);
    }

    /**
     * Create a task that runs within an async scheduler.
     *
     * @param plugin   The instance of the plugin you are using.
     * @param callable The Runnable code you would like to happen.
     */
    public static void runAsync(@NotNull JavaPlugin plugin, Runnable callable) {
        BukkitTask task = plugin.getServer().getScheduler().runTaskAsynchronously(plugin, callable);
        tasks.put(task.getTaskId(), task);
    }

    /**
     * Create a task that runs within a scheduler after a certain amount of time.
     *
     * @param plugin   The instance of the plugin you are using.
     * @param callable The Runnable code you would like to happen.
     * @param delay    The delay before the runnable runs.
     */
    public static void runLater(@NotNull JavaPlugin plugin, Runnable callable, long delay) {
        BukkitTask task = plugin.getServer().getScheduler().runTaskLater(plugin, callable, delay);
        tasks.put(task.getTaskId(), task);
    }

    /**
     * Create a task that runs within an async scheduler after a certain amount of time.
     *
     * @param plugin   The instance of the plugin you are using.
     * @param callable The Runnable code you would like to happen.
     * @param delay    The delay before the runnable runs.
     */
    public static void runAsyncLater(@NotNull JavaPlugin plugin, Runnable callable, long delay) {
        BukkitTask task = plugin.getServer().getScheduler().runTaskLaterAsynchronously(plugin, callable, delay);
        tasks.put(task.getTaskId(), task);
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
        BukkitTask task = plugin.getServer().getScheduler().runTaskTimer(plugin, callable, delay, interval);
        tasks.put(task.getTaskId(), task);
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
        BukkitTask task = plugin.getServer().getScheduler().runTaskTimerAsynchronously(plugin, callable, delay, interval);
        tasks.put(task.getTaskId(), task);
    }

    public static BukkitTask getById(int id) {
        return tasks.get(id);
    }

    public static void cancel(int id) {
        BukkitTask task = tasks.get(id);
        if (task == null) return;

        task.cancel();
        tasks.remove(id);
    }

    public static void cancelAll() {
        for (BukkitTask task : tasks.values()) {
            task.cancel();
            tasks.remove(task.getTaskId());
        }
    }

}
