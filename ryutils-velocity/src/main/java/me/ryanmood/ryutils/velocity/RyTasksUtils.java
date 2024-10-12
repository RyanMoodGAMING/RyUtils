package me.ryanmood.ryutils.velocity;

import com.velocitypowered.api.plugin.Plugin;
import com.velocitypowered.api.proxy.ProxyServer;
import com.velocitypowered.api.scheduler.ScheduledTask;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import me.ryanmood.ryutils.base.RyTasksBase;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.TimeUnit;

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
    private Map<Integer, ScheduledTask> tasks = new HashMap<>();

    @Getter(AccessLevel.PRIVATE)
    @Setter
    private ProxyServer server = RySetup.getProxyServer();

    /**
     * Create a task that runs within a scheduler.
     * @deprecated use {@link #runAsync(Object, Runnable)} instead.
     *
     * @param plugin   The instance of the plugin you are using.
     * @param callable The Runnable code you would like to happen.
     */
    public void run(Object plugin, Runnable callable) {
        this.runAsync(plugin, callable);
    }

    /**
     * Create a task that runs within an async scheduler.
     *
     * @param plugin   The instance of the plugin you are using.
     * @param callable The Runnable code you would like to happen.
     */
    public void runAsync(Object plugin, Runnable callable) {
        ScheduledTask task = this.server.getScheduler().buildTask(plugin, callable).schedule();
        this.tasks.put(randomInt(), task);
    }

    /**
     * Create a task that runs within a scheduler after a certain amount of time.
     *
     * @param plugin   The instance of the plugin you are using.
     * @param callable The Runnable code you would like to happen.
     * @param delay    The delay before the runnable runs.
     */
    @Override
    public void runLater(Object plugin, Runnable callable, long delay) {
        this.runLater(plugin, callable, delay, TimeUnit.SECONDS);
    }

    /**
     * Create a task that runs within an async scheduler after a certain amount of time.
     *
     * @param plugin   The instance of the plugin you are using.
     * @param callable The Runnable code you would like to happen.
     * @param delay    The delay before the runnable runs.
     */
    @Override
    public void runAsyncLater(Object plugin, Runnable callable, long delay) {
        this.runAsyncLater(plugin, callable, delay, TimeUnit.SECONDS);
    }

    /**
     * Create a task that runs on a timer within a scheduler after a certain amount of time.
     *
     * @param plugin   The instance of the plugin you are using.
     * @param callable The Runnable code you would like to happen.
     * @param delay    The delay before the runnable runs.
     * @param interval The interval before the runnable runs again.
     */
    @Override
    public void runTimer(Object plugin, Runnable callable, long delay, long interval) {
        this.runTimer(plugin, callable, delay, interval, TimeUnit.SECONDS);
    }

    /**
     * Create a task that runs on a timer within an async scheduler after a certain amount of time.
     *
     * @param plugin   The instance of the plugin you are using.
     * @param callable The Runnable code you would like to happen.
     * @param delay    The delay before the runnable runs.
     * @param interval The interval before the runnable runs again.
     */
    @Override
    public void runAsyncTimer(Object plugin, Runnable callable, long delay, long interval) {
        this.runAsyncTimer(plugin, callable, delay, interval, TimeUnit.SECONDS);
    }

    /**
     * Create a task that runs within a scheduler after a certain amount of time.
     * @deprecated use {@link #runAsyncLater(Object, Runnable, long, TimeUnit)} instead.
     *
     * @param plugin   The instance of the plugin you are using.
     * @param callable The Runnable code you would like to happen.
     * @param delay    The delay before the runnable runs.
     * @param timeUnit The time unit.
     */
    public void runLater(Object plugin, Runnable callable, long delay, TimeUnit timeUnit) {
        this.runAsyncLater(plugin, callable, delay, timeUnit);
    }

    /**
     * Create a task that runs within an async scheduler after a certain amount of time.
     *
     * @param plugin   The instance of the plugin you are using.
     * @param callable The Runnable code you would like to happen.
     * @param delay    The delay before the runnable runs.
     * @param timeUnit The time unit.
     */
    public void runAsyncLater(Object plugin, Runnable callable, long delay, TimeUnit timeUnit) {
        ScheduledTask task = this.server.getScheduler().buildTask(plugin, callable).delay(delay, timeUnit).schedule();
        this.tasks.put(randomInt(), task);
    }

    /**
     * Create a task that runs on a timer within a scheduler after a certain amount of time.
     * @deprecated use {@link #runAsyncTimer(Object, Runnable, long, long, TimeUnit)} instead.
     *
     * @param plugin   The instance of the plugin you are using.
     * @param callable The Runnable code you would like to happen.
     * @param delay    The delay before the runnable runs.
     * @param interval The interval before the runnable runs again.
     * @param timeUnit The time unit.
     */
    public void runTimer(Object plugin, Runnable callable, long delay, long interval, TimeUnit timeUnit) {
        this.runAsyncTimer(plugin, callable, delay, interval, timeUnit);
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
    public void runAsyncTimer(Object plugin, Runnable callable, long delay, long interval, TimeUnit timeUnit) {
        ScheduledTask task = this.server.getScheduler().buildTask(plugin, callable).delay(delay, timeUnit).repeat(interval, timeUnit).schedule();
        this.tasks.put(randomInt(), task);
    }

    /**
     * Create a task that repeats on a timer within an async scheduler after a certain amount of time.
     *
     * @param plugin   The instance of the plugin you are using.
     * @param callable The Runnable code you would like to happen.
     * @param delay    The delay before the runnable repeats.
     * @param timeUnit The time unit.
     */
    public void runAsyncRepeat(Object plugin, Runnable callable, long delay, TimeUnit timeUnit) {
        ScheduledTask task = this.server.getScheduler().buildTask(plugin, callable).repeat(delay, timeUnit).schedule();
        this.tasks.put(randomInt(), task);
    }

    @Override
    public ScheduledTask getById(int id) {
        return this.getTasks().get(id);
    }

    @Override
    public void cancel(int id) {
        this.getTasks().get(id).cancel();
        this.tasks.remove(id);
    }

    /**
     * Cancel all tasks that we have scheduled.
     */
    @Override
    public void cancelAll() {
        for (int id : this.tasks.keySet()) {
            this.getById(id).cancel();
            this.tasks.remove(id);
        }
    }

    /**
     * Cancel all tasks that a plugin has.
     *
     * @param plugin The instance of the plugin you are using.
     */
    public void cancelAll(Object plugin) {
        Collection<ScheduledTask> tasks = server.getScheduler().tasksByPlugin(plugin);

        for (ScheduledTask task : tasks) {
            task.cancel();
        }
    }

    private int randomInt() {
        Random rand = new Random();
        return rand.nextInt((999 - 100) + 1);
    }

}
