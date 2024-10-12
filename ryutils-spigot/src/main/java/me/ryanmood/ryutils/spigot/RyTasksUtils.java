package me.ryanmood.ryutils.spigot;

import lombok.Getter;
import me.ryanmood.ryutils.base.RyTasksBase;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitTask;

import java.util.*;

/*
 * This software and its content is copyright of RyanMoodGAMING - © RyanMoodGAMING 2023. All rights reserved.
 * Any redistribution or reproduction of part or all of the contents in any form is prohibited other than the following:
 * you may print or download to a local hard disk extracts for your personal and non-commercial use only
 * you may copy the content to individual third parties for their personal use, but only if you acknowledge the website as the source of the material
 * You may not, except with our express written permission, distribute or commercially exploit the content. Nor may you transmit it or store it in any other website or other form of electronic retrieval system.
 */

@SuppressWarnings("unused")
public class RyTasksUtils implements RyTasksBase {

    @Getter
    private static final Map<Integer, BukkitTask> tasks = new HashMap<>();

    /**
     * Create a task that runs within a scheduler.
     *
     * @param plugin   The instance of the plugin you are using.
     * @param callable The Runnable code you would like to happen.
     */
    @Override
    public void run(Object plugin, Runnable callable) {
        if (!(plugin instanceof Plugin)) return;
        Plugin instance = (Plugin) plugin;
        BukkitTask task = instance.getServer().getScheduler().runTask(instance, callable);
        tasks.put(task.getTaskId(), task);
    }

    /**
     * Create a task that runs within an async scheduler.
     *
     * @param plugin   The instance of the plugin you are using.
     * @param callable The Runnable code you would like to happen.
     */
    public void runAsync(Object plugin, Runnable callable) {
        if (!(plugin instanceof Plugin)) return;
        Plugin instance = (Plugin) plugin;
        BukkitTask task = instance.getServer().getScheduler().runTaskAsynchronously(instance, callable);
        tasks.put(task.getTaskId(), task);
    }

    /**
     * Create a task that runs within a scheduler after a certain amount of time.
     *
     * @param plugin   The instance of the plugin you are using.
     * @param callable The Runnable code you would like to happen.
     * @param delay    The delay before the runnable runs.
     */
    public void runLater(Object plugin, Runnable callable, long delay) {
        if (!(plugin instanceof Plugin)) return;
        Plugin instance = (Plugin) plugin;
        BukkitTask task = instance.getServer().getScheduler().runTaskLater(instance, callable, delay);
        tasks.put(task.getTaskId(), task);
    }

    /**
     * Create a task that runs within an async scheduler after a certain amount of time.
     *
     * @param plugin   The instance of the plugin you are using.
     * @param callable The Runnable code you would like to happen.
     * @param delay    The delay before the runnable runs.
     */
    public void runAsyncLater(Object plugin, Runnable callable, long delay) {
        if (!(plugin instanceof Plugin)) return;
        Plugin instance = (Plugin) plugin;
        BukkitTask task = instance.getServer().getScheduler().runTaskLaterAsynchronously(instance, callable, delay);
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
    public void runTimer(Object plugin, Runnable callable, long delay, long interval) {
        if (!(plugin instanceof Plugin)) return;
        Plugin instance = (Plugin) plugin;
        BukkitTask task = instance.getServer().getScheduler().runTaskTimer(instance, callable, delay, interval);
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
    public void runAsyncTimer(Object plugin, Runnable callable, long delay, long interval) {
        if (!(plugin instanceof Plugin)) return;
        Plugin instance = (Plugin) plugin;
        BukkitTask task = instance.getServer().getScheduler().runTaskTimerAsynchronously(instance, callable, delay, interval);
        tasks.put(task.getTaskId(), task);
    }

    @Override
    public BukkitTask getById(int id) {
        return tasks.get(id);
    }

    @Override
    public void cancel(int id) {
        BukkitTask task = tasks.get(id);
        if (task == null) return;

        task.cancel();
        tasks.remove(id);
    }

    @Override
    public void cancelAll() {
        for (BukkitTask task : tasks.values()) {
            task.cancel();
            tasks.remove(task.getTaskId());
        }
    }

}
