package me.ryanmood.ryutils.paper;

import lombok.AccessLevel;
import lombok.Getter;
import me.ryanmood.ryutils.base.RyTaskBase;
import me.ryanmood.ryutils.base.exceptions.UninitializedUtilException;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitTask;

import java.util.HashMap;
import java.util.Map;

public class RyTaskUtil implements RyTaskBase<BukkitTask> {

    @Getter
    private final Map<Integer, BukkitTask> tasks = new HashMap<>();

    @Getter(AccessLevel.PRIVATE)
    private Plugin plugin;
    private static RyTaskUtil instance;

    public RyTaskUtil(Plugin plugin) {
        this.plugin = plugin;
        instance = this;
    }

    /**
     * Create a task that runs within a scheduler.
     *
     * @param callable The Runnable code you would like to happen.
     */
    @Override
    public int run(Runnable callable) {
        BukkitTask task = Bukkit.getScheduler().runTask(this.getPlugin(), callable);
        this.getTasks().put(task.getTaskId(), task);

        return task.getTaskId();
    }

    /**
     * Create a task that runs within an async scheduler.
     *
     * @param callable The Runnable code you would like to happen.
     */
    @Override
    public int runAsync(Runnable callable) {
        BukkitTask task = Bukkit.getScheduler().runTaskAsynchronously(this.getPlugin(), callable);
        this.getTasks().put(task.getTaskId(), task);

        return task.getTaskId();
    }

    /**
     * Create a task that runs within a scheduler after a certain amount of time.
     *
     * @param callable The Runnable code you would like to happen.
     * @param delay    The delay before the runnable runs.
     */
    @Override
    public int runLater(Runnable callable, long delay) {
        BukkitTask task = Bukkit.getScheduler().runTaskLater(this.getPlugin(), callable, delay);
        this.getTasks().put(task.getTaskId(), task);

        return task.getTaskId();
    }

    /**
     * Create a task that runs within an async scheduler after a certain amount of time.
     *
     * @param callable The Runnable code you would like to happen.
     * @param delay    The delay before the runnable runs.
     */
    @Override
    public int runAsyncLater(Runnable callable, long delay) {
        BukkitTask task = Bukkit.getScheduler().runTaskLaterAsynchronously(this.getPlugin(), callable, delay);
        this.getTasks().put(task.getTaskId(), task);

        return task.getTaskId();
    }

    /**
     * Create a task that runs on a timer within a scheduler after a certain amount of time.
     *
     * @param callable The Runnable code you would like to happen.
     * @param delay    The delay before the runnable runs.
     * @param interval The interval before the runnable runs again.
     */
    @Override
    public int runTimer(Runnable callable, long delay, long interval) {
        BukkitTask task = Bukkit.getScheduler().runTaskTimer(this.getPlugin(), callable, delay, interval);
        this.getTasks().put(task.getTaskId(), task);

        return task.getTaskId();
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
        BukkitTask task = Bukkit.getScheduler().runTaskTimerAsynchronously(this.getPlugin(), callable, delay, interval);
        this.getTasks().put(task.getTaskId(), task);

        return task.getTaskId();
    }

    /**
     * Get a task based of its id.
     *
     * @param id The id of the task
     * @return The runnable task that was requested.
     */
    @Override
    public BukkitTask getById(int id) {
        return this.getTasks().get(id);
    }

    /**
     * Cancel a task based off its id.
     *
     * @param id The ID of the task you wish to cancel.
     */
    @Override
    public void cancel(int id) {
        Bukkit.getScheduler().cancelTask(id);
        this.getTasks().remove(id);
    }

    /**
     * Cancel all tasked that are running for the plugin.
     */
    @Override
    public void cancelAll() {
        Bukkit.getScheduler().cancelTasks(plugin);
        this.getTasks().clear();
    }

    /**
     * Get the instance of the util.
     *
     * @return {@link RyTaskUtil}
     */
    public static RyTaskUtil getUtil() {
        if (instance == null) throw new UninitializedUtilException("RyTaskUtil#getUtil", "#getUtil", "Paper");
        else return instance;
    }
}
