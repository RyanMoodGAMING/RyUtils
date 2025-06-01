package me.ryanmood.ryutils.velocity;

import com.velocitypowered.api.proxy.ProxyServer;
import com.velocitypowered.api.scheduler.ScheduledTask;
import lombok.AccessLevel;
import lombok.Getter;
import me.ryanmood.ryutils.base.RyTaskBase;
import me.ryanmood.ryutils.base.exceptions.UninitializedUtilException;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class RyTaskUtil implements RyTaskBase<ScheduledTask> {

    @Getter
    private final Map<Integer, ScheduledTask> tasks = new HashMap<>();

    @Getter(AccessLevel.PRIVATE)
    private Object plugin;
    @Getter(AccessLevel.PRIVATE)
    private ProxyServer server;
    private static RyTaskUtil instance;

    public RyTaskUtil(Object plugin, ProxyServer server) {
        this.plugin = plugin;
        this.server = server;
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
        ScheduledTask task = server.getScheduler().buildTask(this.getPlugin(), callable).schedule();
        int id = new Random().nextInt();
        this.getTasks().put(id, task);

        return id;
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
        return this.runLater(callable, delay);
    }

    /**
     * Create a task that runs within an async scheduler after a certain amount of time.
     *
     * @param callable The Runnable code you would like to happen.
     * @param delay    The delay before the runnable runs.
     */
    @Override
    public int runAsyncLater(Runnable callable, long delay) {
        ScheduledTask task = server.getScheduler().buildTask(this.getPlugin(), callable).schedule();
        int id = new Random().nextInt();
        this.getTasks().put(id, task);

        return id;
    }

    /**
     * Create a task that runs on a timer within a scheduler after a certain amount of time.
     *
     * @param callable The Runnable code you would like to happen.
     * @param delay    The delay before the runnable runs.
     * @param interval The interval before the runnable runs again.
     *
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
        ScheduledTask task = server.getScheduler().buildTask(this.getPlugin(), callable).schedule();
        int id = new Random().nextInt();
        this.getTasks().put(id, task);

        return id;
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
        this.getTasks().get(id).cancel();
        this.getTasks().remove(id);
    }

    /**
     * Cancel all tasked that are running for the plugin.
     */
    @Override
    public void cancelAll() {
        this.getServer().getScheduler().tasksByPlugin(this.getPlugin()).forEach(ScheduledTask::cancel);
        this.getTasks().clear();
    }

    /**
     * Get the instance of the util.
     *
     * @return {@link RyTaskUtil}
     */
    public static RyTaskUtil getUtil() {
        if (instance == null) throw new UninitializedUtilException("RyTaskUtil#getUtil", "#getUtil", "Velocity");
        else return instance;
    }
}
