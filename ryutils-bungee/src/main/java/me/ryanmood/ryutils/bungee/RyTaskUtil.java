package me.ryanmood.ryutils.bungee;

import lombok.AccessLevel;
import lombok.Getter;
import me.ryanmood.ryutils.base.RyTaskBase;
import me.ryanmood.ryutils.base.exceptions.UninitializedUtilException;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.api.scheduler.ScheduledTask;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class RyTaskUtil implements RyTaskBase<ScheduledTask> {

    @Getter(AccessLevel.PRIVATE)
    private Plugin plugin;
    private static RyTaskUtil instance;

    @Getter
    private final Map<Integer, ScheduledTask> tasks = new HashMap<>();

    public RyTaskUtil(Plugin plugin) {
        this.plugin = plugin;
        instance = this;
    }

    /**
     * Create a task that runs within a scheduler.
     *
     * @param callable The Runnable code you would like to happen.
     * @deprecated This will one day be removed as all Velocity timers are async.
     */
    @Deprecated
    @Override
    public int run(Runnable callable) {
        return this.runAsync(callable);
    }

    /**
     * Create a task that runs within an async scheduler.
     *
     * @param callable The Runnable code you would like to happen.
     */
    @Override
    public int runAsync(Runnable callable) {
        ScheduledTask task = ProxyServer.getInstance().getScheduler().runAsync(plugin, callable);
        this.getTasks().put(task.getId(), task);

        return task.getId();
    }

    /**
     * Create a task that runs within a scheduler after a certain amount of time.
     *
     * @param callable The Runnable code you would like to happen.
     * @param delay    The delay before the runnable runs.
     * @deprecated This will one day be removed as all Velocity timers are async.
     */
    @Deprecated
    @Override
    public int runLater(Runnable callable, long delay) {
        return this.runAsync(callable);
    }

    /**
     * Create a task that runs within an async scheduler after a certain amount of time.
     *
     * @param callable The Runnable code you would like to happen.
     * @param delay    The delay before the runnable runs.
     */
    @Override
    public int runAsyncLater(Runnable callable, long delay) {
        return this.runAsyncLater(callable, delay, TimeUnit.MILLISECONDS);
    }

    /**
     * Create a task that runs within an async scheduler after a certain amount of time.
     *
     * @param callable The Runnable code you would like to happen.
     * @param delay    The delay before the runnable runs.
     * @param timeUnit The time unit for the delay.
     */
    public int runAsyncLater(Runnable callable, long delay, TimeUnit timeUnit) {
        ScheduledTask task = ProxyServer.getInstance().getScheduler().schedule(plugin, callable, delay, timeUnit);
        this.getTasks().put(task.getId(), task);

        return task.getId();
    }

    /**
     * Create a task that runs on a timer within a scheduler after a certain amount of time.
     *
     * @param callable The Runnable code you would like to happen.
     * @param delay    The delay before the runnable runs.
     * @param interval The interval before the runnable runs again.
     * @deprecated This will one day be removed as all Velocity timers are async.
     */
    @Deprecated
    @Override
    public int runTimer(Runnable callable, long delay, long interval) {
        return this.runAsyncTimer(callable, delay, interval);
    }

    /**
     * Create a task that runs on a timer within an async scheduler after a certain amount of time.
     *
     * @param callable The Runnable code you would like to happen.
     * @param delay    The delay before the runnable runs.
     * @param interval The interval before the runnable runs again.
     */
    @Override
    public int runAsyncTimer(Runnable callable, long delay, long interval) {
        return this.runAsyncTimer(callable, delay, interval, TimeUnit.MILLISECONDS);
    }

    /**
     * Create a task that runs on a timer within an async scheduler after a certain amount of time.
     *
     * @param callable The Runnable code you would like to happen.
     * @param delay    The delay before the runnable runs.
     * @param interval The interval before the runnable runs again.
     * @param timeUnit The time unit for the delay.
     */
    public int runAsyncTimer(Runnable callable, long delay, long interval, TimeUnit timeUnit) {
        ScheduledTask task = ProxyServer.getInstance().getScheduler().schedule(plugin, callable, delay, interval, timeUnit);
        this.getTasks().put(task.getId(), task);

        return task.getId();
    }


    /**
     * Get a task based of its id.
     *
     * @param id The id of the task
     * @return The runnable task that was requested.
     */
    @Override
    public ScheduledTask getById(int id) {
        return this.getTasks().get(id);
    }

    /**
     * Cancel a task based off its id.
     *
     * @param id The ID of the task you wish to cancel.
     */
    @Override
    public void cancel(int id) {
        ProxyServer.getInstance().getScheduler().cancel(this.getTasks().get(id));
        this.getTasks().remove(id);
    }

    /**
     * Cancel all tasked that are running for the plugin.
     */
    @Override
    public void cancelAll() {
        ProxyServer.getInstance().getScheduler().cancel(this.getPlugin());
    }

    /**
     * Get the instance of the util.
     *
     * @return {@link RyTaskUtil}
     */
    public static RyTaskUtil getUtil() {
        if (instance == null) throw new UninitializedUtilException("RyTaskUtil#getUtil", "#getUtil", "Bungeecord");
        else return instance;
    }
}
